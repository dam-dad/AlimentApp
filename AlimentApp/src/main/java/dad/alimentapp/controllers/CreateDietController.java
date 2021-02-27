package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.models.Product;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.models.app.DailyMenu;
import dad.alimentapp.models.app.Diet;
import dad.alimentapp.models.app.Menu;
import dad.alimentapp.models.app.NutritionalValues;
import dad.alimentapp.models.app.ProductMomentDay;
import dad.alimentapp.service.DietService;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class CreateDietController implements Initializable {

	// VIEW
	@FXML
	private HBox view;

	@FXML
	private TextField nameDietText;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label weekdayLabel;

	@FXML
	private Button breakfastAddButton;

	@FXML
	private Button breakfastRemoveButton;

	@FXML
	private Button midMorningAddButton;

	@FXML
	private Button midMorningRemoveButton;

	@FXML
	private Button lunchAddButton;

	@FXML
	private Button lunchRemoveButton;

	@FXML
	private Button snackAddButton;

	@FXML
	private Button snackRemoveButton;

	@FXML
	private Button dinnerAddButton;

	@FXML
	private Button dinnerRemoveButton;

	@FXML
	private ListView<Product> breakfastList;

	@FXML
	private ListView<Product> midMorningList;

	@FXML
	private ListView<Product> lunchList;

	@FXML
	private ListView<Product> snackList;

	@FXML
	private ListView<Product> dinnerList;

	@FXML
	private TextField nameMenuText;

	@FXML
	private Label kcalTotLabel;

	@FXML
	private Label proteinTotLabel;

	@FXML
	private Label hydratesTotLabel;

	@FXML
	private Label fatsTotLabel;

	@FXML
	private Label fibresTotLabel;

	@FXML
	private PieChart menuChart;

	@FXML
	private Button saveDietButton;

	@FXML
	private Button loadExistingMenusButton;

	// CONTROLLERS
	private ProductController productController;

	// MODEL
	private ObjectProperty<Diet> diet = new SimpleObjectProperty<>();
	private ObjectProperty<Weekday> actualWeekday = new SimpleObjectProperty<>(Weekday.LUNES);
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>(new Menu());
	private ObjectProperty<NutritionalValues> nutritionalValues = new SimpleObjectProperty<>(new NutritionalValues());

	// STAGE
	private static Stage loadAllMenuStage;

	public CreateDietController(Diet diet) throws IOException {
		this.diet.set(diet);
		loadActualWeekday();
		loadMenuWeekday();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTotals();
		// BINDINGS
		nameDietText.textProperty().bindBidirectional(diet.get().nameProperty());

		// BUTTONS
		saveDietButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getBreakfastProducts().getProducts()).isEqualTo(0)
						.and(Bindings.size(menuSelected.get().getMidMorningProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getLunchProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getSnackProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getDinnerProducts().getProducts()).isEqualTo(0)));

		breakfastRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getBreakfastProducts().getProducts()).isEqualTo(0));
		midMorningRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getMidMorningProducts().getProducts()).isEqualTo(0));
		lunchRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getLunchProducts().getProducts()).isEqualTo(0));
		snackRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getSnackProducts().getProducts()).isEqualTo(0));
		dinnerRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getDinnerProducts().getProducts()).isEqualTo(0));

		Bindings.bindBidirectional(weekdayLabel.textProperty(), actualWeekday, new StringConverter<Weekday>() {
			@Override
			public String toString(Weekday weekday) {
				return weekday.name();
			}

			@Override
			public Weekday fromString(String string) {
				return Weekday.valueOf(string);
			}
		});

		nameMenuText.textProperty().bindBidirectional(menuSelected.get().nameProperty());
		breakfastList.itemsProperty().bindBidirectional(menuSelected.get().getBreakfastProducts().productsProperty());
		midMorningList.itemsProperty().bindBidirectional(menuSelected.get().getMidMorningProducts().productsProperty());
		lunchList.itemsProperty().bindBidirectional(menuSelected.get().getLunchProducts().productsProperty());
		snackList.itemsProperty().bindBidirectional(menuSelected.get().getSnackProducts().productsProperty());
		dinnerList.itemsProperty().bindBidirectional(menuSelected.get().getDinnerProducts().productsProperty());

		kcalTotLabel.textProperty().bindBidirectional(nutritionalValues.get().kcalsTotalsProperty(),
				new NumberStringConverter());
		proteinTotLabel.textProperty().bindBidirectional(nutritionalValues.get().proteinsTotalsProperty(),
				new NumberStringConverter());
		hydratesTotLabel.textProperty().bindBidirectional(nutritionalValues.get().hydratesTotalsProperty(),
				new NumberStringConverter());
		fatsTotLabel.textProperty().bindBidirectional(nutritionalValues.get().fatsTotalsProperty(),
				new NumberStringConverter());
		fibresTotLabel.textProperty().bindBidirectional(nutritionalValues.get().fibresTotalsProperty(),
				new NumberStringConverter());

		diet.get().dailyMenuProperty().addListener((o, ov, nv) -> {
			System.out.println("Listener 1");
		});

		menuSelected.addListener((o, ov, nv) -> {
			if (ov != null) {
				nameMenuText.textProperty().unbindBidirectional(menuSelected.get().nameProperty());
				breakfastList.itemsProperty()
						.unbindBidirectional(menuSelected.get().getBreakfastProducts().productsProperty());
				midMorningList.itemsProperty()
						.unbindBidirectional(menuSelected.get().getMidMorningProducts().productsProperty());
				lunchList.itemsProperty().unbindBidirectional(menuSelected.get().getLunchProducts().productsProperty());
				snackList.itemsProperty().unbindBidirectional(menuSelected.get().getSnackProducts().productsProperty());
				dinnerList.itemsProperty()
						.unbindBidirectional(menuSelected.get().getDinnerProducts().productsProperty());
			}

			if (nv != null) {
				nameMenuText.textProperty().bindBidirectional(menuSelected.get().nameProperty());
				breakfastList.itemsProperty()
						.bindBidirectional(menuSelected.get().getBreakfastProducts().productsProperty());
				midMorningList.itemsProperty()
						.bindBidirectional(menuSelected.get().getMidMorningProducts().productsProperty());
				lunchList.itemsProperty().bindBidirectional(menuSelected.get().getLunchProducts().productsProperty());
				snackList.itemsProperty().bindBidirectional(menuSelected.get().getSnackProducts().productsProperty());
				dinnerList.itemsProperty().bindBidirectional(menuSelected.get().getDinnerProducts().productsProperty());
			}
		});
	}

	@FXML
	void onBreakfastAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getBreakfastProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getBreakfastProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onBreakfastRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Desayuno",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getBreakfastProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onMidMorningAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getMidMorningProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getMidMorningProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onMidMorningRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de Media-Mañana",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getMidMorningProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onLunchAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getLunchProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getLunchProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onLunchRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Almuerzo",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getLunchProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onSnackAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getSnackProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getSnackProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onSnackRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Merienda",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getSnackProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onDinnerAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getDinnerProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getDinnerProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onDinnerRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Cena",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getDinnerProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	@FXML
	void onNextButtonAction(ActionEvent event) {
//		Weekday nextDay = Weekday.next(menuSelected.get().getWeekday().getId());
//		if (diet.getMenu().size() > 1) {			
//			Menu menu = Utils.searchMatchesInMenu(diet.getMenu(), nextDay);
//			menuSelected.set(menu != null ? menu : new Menu(nextDay));
//			System.out.println(menuSelected);
//		} else {
//			Menu menu = new Menu(nextDay);
//			diet.getMenu().add(menu);
//			menuSelected.set(menu);
//			System.out.println(menuSelected);
//		}
	}

	@FXML
	void onPreviousButtonAction(ActionEvent event) {
//		if (diet.getMenu().size() > 1) {
//			Weekday previousDay = Weekday.previous(menuSelected.get().getWeekday().getId());
//			Menu menu = Utils.searchMatchesInMenu(diet.getMenu(), previousDay);
//			menuSelected.set(menu != null ? menu : new Menu(previousDay));
//			System.out.println(menuSelected);
//		} else {
//			Menu menu = new Menu(Weekday.previous(menuSelected.get().getWeekday().getId()));
//			diet.getMenu().add(menu);
//			menuSelected.set(menu);
//			System.out.println(menuSelected);
//		}
	}

	@FXML
	void onSaveDietButtonAction(ActionEvent event) {
		if (diet.get().getId() != 0) {
			DietService.updateDiet(diet.get());
			ManageDietController.loadDietsAndMenus();
			ManageDietController.getModificateStage().close();
		} else {
			DietService.insertDiet(diet.get());
			ManageDietController.loadDietsAndMenus();
			ChoiceController.getCreateDietCustomStage().close();
		}
	}

	@FXML
	void onLoadExistingMenusButtonAction(ActionEvent event) {
		try {

			Stage loadAllMenuStage = new Stage();
			loadAllMenuStage.setTitle("Listas de Menús");
			loadAllMenuStage.resizableProperty().setValue(Boolean.FALSE);
			loadAllMenuStage.getIcons().add(new Image("/images/logo.png"));
			loadAllMenuStage.initModality(Modality.WINDOW_MODAL);
			loadAllMenuStage.initOwner(ManageDietController.getModificateStage());

			LoadAllMenuController loadAllMenuController = new LoadAllMenuController(loadAllMenuStage);
			Scene scene = new Scene(loadAllMenuController.getView());

			loadAllMenuStage.setScene(scene);
			loadAllMenuStage.showAndWait();

			if (loadAllMenuController.getMenuAccepted() != null) {
				overrideDailyMenu(loadAllMenuController.getMenuAccepted());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void overrideDailyMenu(Menu menu) {
		if (diet.get().getDailyMenu().size() != 0) {
			boolean matches = false;
			int count = 0;
			do {
				if (diet.get().getDailyMenu().get(count).getWeekday() == actualWeekday.get()) {
					diet.get().getDailyMenu().get(count).setMenu(menu);
					menuSelected.set(menu);
					matches = true;
				}
				count++;
			} while (!matches && count < diet.get().getDailyMenu().size());
		} else {
			diet.get().getDailyMenu().add(new DailyMenu(actualWeekday.get(), menu));
			menuSelected.set(menu);
		}
		nutritionalValues.get().clear();
		loadTotals();		
	}

	private void loadActualWeekday() {
		List<DailyMenu> dailyMenu = this.diet.get().getDailyMenu();
		if (dailyMenu.size() != 0) {
			actualWeekday.set(dailyMenu.get(0).getWeekday());
		}
	}

	private void loadMenuWeekday() {
		List<DailyMenu> dailyMenus = diet.get().getDailyMenu();
		if (dailyMenus.size() != 0) {
			menuSelected.set(Utils.searchMatchesInMenu(dailyMenus, actualWeekday.get()));
		}
	}

	private void loadTotals() {
		NutritionalValues totalsBreaksfast = loadTotalsForMomentDay(
				menuSelected.get().getBreakfastProducts().getProducts());
		NutritionalValues totalsMidMorning = loadTotalsForMomentDay(
				menuSelected.get().getMidMorningProducts().getProducts());
		NutritionalValues totalsLunch = loadTotalsForMomentDay(menuSelected.get().getLunchProducts().getProducts());
		NutritionalValues totalsSnack = loadTotalsForMomentDay(menuSelected.get().getSnackProducts().getProducts());
		NutritionalValues totalsDinner = loadTotalsForMomentDay(menuSelected.get().getDinnerProducts().getProducts());

		nutritionalValues.get().setKcalsTotals(totalsBreaksfast.getKcalsTotals() + totalsMidMorning.getKcalsTotals()
				+ totalsLunch.getKcalsTotals() + totalsSnack.getKcalsTotals() + totalsDinner.getKcalsTotals());

		nutritionalValues.get().proteinsTotalsProperty()
				.set(totalsBreaksfast.getProteinsTotals() + totalsMidMorning.getProteinsTotals()
						+ totalsLunch.getProteinsTotals() + totalsSnack.getProteinsTotals()
						+ totalsDinner.getProteinsTotals());

		nutritionalValues.get().hydratesTotalsProperty()
				.set(totalsBreaksfast.getHydratesTotals() + totalsMidMorning.getHydratesTotals()
						+ totalsLunch.getHydratesTotals() + totalsSnack.getHydratesTotals()
						+ totalsDinner.getHydratesTotals());

		nutritionalValues.get().fatsTotalsProperty()
				.set(totalsBreaksfast.getFatsTotals() + totalsMidMorning.getFatsTotals() + totalsLunch.getFatsTotals()
						+ totalsSnack.getFatsTotals() + totalsDinner.getFatsTotals());

		nutritionalValues.get().fibresTotalsProperty()
				.set(totalsBreaksfast.getFibresTotals() + totalsMidMorning.getFibresTotals()
						+ totalsLunch.getFibresTotals() + totalsSnack.getFibresTotals()
						+ totalsDinner.getFibresTotals());

		getPieChart();
	}

	private NutritionalValues loadTotalsForMomentDay(List<Product> products) {
		int kcals = 0, proteins = 0, hydrates = 0, fats = 0, fibres = 0;
		for (Product product : products) {
			kcals += this.nutritionalValues.get().kcalsTotalsProperty().add(product.kcalProperty()).intValue();
			proteins += this.nutritionalValues.get().proteinsTotalsProperty().add(product.proteinProperty()).intValue();
			hydrates += this.nutritionalValues.get().hydratesTotalsProperty().add(product.hydratesProperty())
					.intValue();
			fats += this.nutritionalValues.get().fatsTotalsProperty().add(product.fatsProperty()).intValue();
			fibres += this.nutritionalValues.get().fibresTotalsProperty().add(product.fibresProperty()).intValue();
		}
		return new NutritionalValues(kcals, proteins, hydrates, fats, fibres);
	}

	private void newSceneProduct(ProductMomentDay productMomentDay) {
		Stage choice = ChoiceController.getCreateDietCustomStage();
		Stage manage = ManageDietController.getModificateStage();
		
		Stage stage = choice != null ? choice : manage;
		try {
			Stage productStage = new Stage();
			productStage.setMinWidth(750);
			productStage.setMinHeight(450);
			productStage.setTitle("Productos");
			productStage.getIcons().add(new Image("/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(stage);

			productController = new ProductController(productMomentDay);
			Scene scene = new Scene(productController.getView());

			productStage.setScene(scene);
			productStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getPieChart() {
		// PieChart.Data kcal = new PieChart.Data("kcal",
		// nutritionalValues.get().getKcalsTotals());
		PieChart.Data proteins = new PieChart.Data("Proteinas", nutritionalValues.get().getProteinsTotals());
		PieChart.Data hydrates = new PieChart.Data("Hidratos", nutritionalValues.get().getHydratesTotals());
		PieChart.Data fats = new PieChart.Data("Grasas", nutritionalValues.get().getFatsTotals());
		PieChart.Data fibres = new PieChart.Data("Fibra", nutritionalValues.get().getFibresTotals());

		// menuChart.getData().setAll(kcal);
		menuChart.getData().setAll(proteins);
		menuChart.getData().add(hydrates);
		menuChart.getData().add(fats);
		menuChart.getData().add(fibres);

		menuChart.setClockwise(true);
		menuChart.setLabelsVisible(true);
		menuChart.setLabelLineLength(20);

		menuChart.getData().forEach(this::installTooltip);
		// installTooltip(kcal);
		installTooltip(proteins);
		installTooltip(hydrates);
		installTooltip(fats);
		installTooltip(fibres);
	}

	public void installTooltip(PieChart.Data d) {
		String msg = String.format("%s : %s", d.getName(), d.getPieValue());

		Tooltip tooltip = new Tooltip(msg);
		tooltip.setStyle("-fx-background-color: violet; -fx-text-fill: whitesmoke;");

		Tooltip.install(d.getNode(), tooltip);
	}

	public static Stage getLoadAllMenuStage() {
		return loadAllMenuStage;
	}

	public HBox getView() {
		return view;
	}
}
