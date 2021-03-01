package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.models.DailyMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.NutritionalValues;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.service.DietService;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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

/**
 * Esta clase "CreateDietController" la utilizaremos para crear o modificar dietas en nuestra app.
 * @author Antonio
 *
 */
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
	private ListProperty<DailyMenu> dailyMenusModificate = new SimpleListProperty<>(
			FXCollections.observableArrayList());
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>(new Menu());
	private ObjectProperty<NutritionalValues> nutritionalValues = new SimpleObjectProperty<>(new NutritionalValues());

	// STAGE
	private static Stage loadAllMenuStage;

	public CreateDietController(Diet diet) throws IOException {
		this.diet.set(diet);
		loadActualWeekday();
		loadMenuWeekday();
		dailyMenusModificate.setAll(this.diet.get().getDailyMenus());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTotals();
		// BINDINGS
		nameDietText.textProperty().bindBidirectional(diet.get().nameProperty());
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

		//LISTENERS
		menuSelected.addListener((o, ov, nv) -> {
			if (ov != null) {
				nameMenuText.textProperty().unbindBidirectional(ov.nameProperty());
				breakfastList.itemsProperty().unbindBidirectional(ov.getBreakfastProducts().productsProperty());
				midMorningList.itemsProperty().unbindBidirectional(ov.getMidMorningProducts().productsProperty());
				lunchList.itemsProperty().unbindBidirectional(ov.getLunchProducts().productsProperty());
				snackList.itemsProperty().unbindBidirectional(ov.getSnackProducts().productsProperty());
				dinnerList.itemsProperty().unbindBidirectional(ov.getDinnerProducts().productsProperty());
			}

			if (nv != null) {
				nameMenuText.textProperty().bindBidirectional(nv.nameProperty());
				breakfastList.itemsProperty().bindBidirectional(nv.getBreakfastProducts().productsProperty());
				midMorningList.itemsProperty().bindBidirectional(nv.getMidMorningProducts().productsProperty());
				lunchList.itemsProperty().bindBidirectional(nv.getLunchProducts().productsProperty());
				snackList.itemsProperty().bindBidirectional(nv.getSnackProducts().productsProperty());
				dinnerList.itemsProperty().bindBidirectional(nv.getDinnerProducts().productsProperty());

				breakfastRemoveButton.disableProperty()
						.bind(Bindings.size(nv.getBreakfastProducts().getProducts()).isEqualTo(0));
				midMorningRemoveButton.disableProperty()
						.bind(Bindings.size(nv.getMidMorningProducts().getProducts()).isEqualTo(0));
				lunchRemoveButton.disableProperty()
						.bind(Bindings.size(nv.getLunchProducts().getProducts()).isEqualTo(0));
				snackRemoveButton.disableProperty()
						.bind(Bindings.size(nv.getSnackProducts().getProducts()).isEqualTo(0));
				dinnerRemoveButton.disableProperty()
						.bind(Bindings.size(nv.getDinnerProducts().getProducts()).isEqualTo(0));

				saveDietButton.disableProperty().bind(Bindings
						.size(menuSelected.get().getBreakfastProducts().getProducts()).isEqualTo(0)
						.and(Bindings.size(menuSelected.get().getMidMorningProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getLunchProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getSnackProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getDinnerProducts().getProducts()).isEqualTo(0)));
			}
		});
	}
	
	/**
	 * El metodo "onBreakfastAddButtonAction" lo utilizamos para lanzar la vista de productos,
	 *  en el cual le asignaremos una lista de productos al desayuno.
	 * @param event
	 */
	@FXML
	void onBreakfastAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getBreakfastProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getBreakfastProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onBreakfastRemoveButtonAction", lo utilizaremos para borrar la lista de productos del desayuno.
	 * @param event
	 */
	@FXML
	void onBreakfastRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Desayuno",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getBreakfastProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onMidMorningAddButtonAction" lo utilizamos para lanzar la vista de productos,
	 *  en el cual le asignaremos una lista de productos a media-mañana.
	 * @param event
	 */
	@FXML
	void onMidMorningAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getMidMorningProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getMidMorningProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onMidMorningRemoveButtonAction", lo utilizaremos para borrar la lista de productos de media-mañana.
	 * @param event
	 */
	@FXML
	void onMidMorningRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de Media-Mañana",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getMidMorningProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onLunchAddButtonAction" lo utilizamos para lanzar la vista de productos,
	 *  en el cual le asignaremos una lista de productos al almuerzo.
	 * @param event
	 */
	@FXML
	void onLunchAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getLunchProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getLunchProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onLunchRemoveButtonAction", lo utilizaremos para borrar la lista de productos del almuerzo.
	 * @param event
	 */
	@FXML
	void onLunchRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Almuerzo",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getLunchProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onSnackAddButtonAction" lo utilizamos para lanzar la vista de productos,
	 *  en el cual le asignaremos una lista de productos a la merienda.
	 * @param event
	 */
	@FXML
	void onSnackAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getSnackProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getSnackProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onSnackRemoveButtonAction", lo utilizaremos para borrar la lista de productos de la merienda.
	 * @param event
	 */
	@FXML
	void onSnackRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Merienda",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getSnackProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onDinnerAddButtonAction" lo utilizamos para lanzar la vista de productos,
	 *  en el cual le asignaremos una lista de productos a la cena.
	 * @param event
	 */
	@FXML
	void onDinnerAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getDinnerProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getDinnerProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			overrideDailyMenu(this.menuSelected.get());
		}
	}
	
	/**
	 * El metodo "onDinnerRemoveButtonAction", lo utilizaremos para borrar la lista de productos de la cena.
	 * @param event
	 */
	@FXML
	void onDinnerRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Cena",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getDinnerProducts().getProducts().setAll(new ArrayList<>());
			overrideDailyMenu(this.menuSelected.get());
		}
	}

	/**
	 * El metodo "onNextButtonAction", para cambiar al siguiente dia de la semana y visualizar el menu que le corresponda.
	 * @param event
	 */
	@FXML
	void onNextButtonAction(ActionEvent event) {
		Weekday nextDay = Weekday.next(actualWeekday.get().getId());
		actualWeekday.set(nextDay);
		Menu menu = Utils.searchMatchesInMenu(dailyMenusModificate, nextDay);
		if (menu != null) {
			menuSelected.set(menu);
		} else {
			Menu newMenu = new Menu();
			dailyMenusModificate.add(new DailyMenu(nextDay, newMenu));
			menuSelected.set(newMenu);
		}
	}

	/**
	 * El metodo "onPreviousButtonAction", para cambiar al dia anterior de la semana y visualizar el menu que le corresponda.
	 * @param event
	 */
	@FXML
	void onPreviousButtonAction(ActionEvent event) {
		Weekday previousDay = Weekday.previous(actualWeekday.get().getId());
		actualWeekday.set(previousDay);
		Menu menu = Utils.searchMatchesInMenu(dailyMenusModificate, previousDay);
		if (menu != null) {
			menuSelected.set(menu);
		} else {
			Menu newMenu = new Menu();
			dailyMenusModificate.add(new DailyMenu(previousDay, newMenu));
			menuSelected.set(newMenu);
		}
	}

	/**
	 * El metodo "onSaveDietButtonAction" se encarga de guardar o actualizar una dieta y sus menus correspondientes.
	 * @param event
	 */
	@FXML
	void onSaveDietButtonAction(ActionEvent event) {
		this.diet.get().setName(this.diet.get().getName().trim());
		this.diet.get().setDailyMenus(dailyMenusModificate);
		this.diet.get().clear();
		if (diet.get().getId() == 0) {
			boolean isDuplicate = Utils.isMatchDietName(this.diet.get());
			if (isDuplicate) {
				Messages.error("Error al guardar la dieta", "No se pueden guardar dietas con el mismo nombre.");
			} else {
				DietService.insertDiet(diet.get());
				ManageDietController.loadDietsAndMenus();
				ChoiceController.getCreateDietCustomStage().close();
			}
		} else {
			DietService.updateDiet(diet.get());
			ManageDietController.loadDietsAndMenus();
			ManageDietController.getModificateStage().close();
		}
	}

	/**
	 * El metodo "onLoadExistingMenusButtonAction" se encarga de lanzar una nueva vista donde podemos seleccionar 
	 * de una lista un menu ya existente para usarlo en nuestra dieta.
	 * @param event
	 */
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
			scene.getStylesheets().add(MainController.getStyleSheetActual());

			loadAllMenuStage.setScene(scene);
			loadAllMenuStage.showAndWait();

			if (loadAllMenuController.getMenuAccepted() != null) {
				overrideDailyMenu(loadAllMenuController.getMenuAccepted());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * El metodo "overrideDailyMenu" de sobreescribir o añadir un menu a la dieta.
	 * @param menu Le pasamos el menu por parametro.
	 */
	private void overrideDailyMenu(Menu menu) {
		if (dailyMenusModificate.size() != 0) {
			boolean matches = false;
			int count = 0;
			do {
				if (dailyMenusModificate.get(count).getWeekday() == actualWeekday.get()) {
					dailyMenusModificate.get(count).setMenu(menu);
					menuSelected.set(menu);
					matches = true;
				}
				count++;
			} while (!matches && count < dailyMenusModificate.size());
		} else {
			dailyMenusModificate.add(new DailyMenu(actualWeekday.get(), menu));
			menuSelected.set(menu);
		}
		nutritionalValues.get().clear();
		loadTotals();
	}
	
	/**
	 * El metodo "loadActualWeekday" se utiliza para cargar inicialmente el dia de la semana que se visualizara primero.
	 */
	private void loadActualWeekday() {
		List<DailyMenu> dailyMenu = this.diet.get().getDailyMenus();
		if (dailyMenu.size() != 0) {
			actualWeekday.set(dailyMenu.get(0).getWeekday());
		}
	}

	/**
	 * El metodo "loadMenuWeekday" se utiliza para cargar inicialmente el menu que se visualizara primero en funcion 
	 * del dia de la semana que se haya definido como actual.
	 */
	private void loadMenuWeekday() {
		List<DailyMenu> dailyMenus = diet.get().getDailyMenus();
		if (dailyMenus.size() != 0) {
			menuSelected.set(Utils.searchMatchesInMenu(dailyMenus, actualWeekday.get()));
		}
	}

	/**
	 * El metodo "loadTotals" se encarga de asignar los valores nutricionales totales a cada una de las properties 
	 * correspondientes y cargar la grafica.
	 */
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

	/**
	 * El metodo "loadTotalsForMomentDay" se encarga de calcular los valores nutricionales totales para un momento del dia.
	 * @param products Recibe la lista de productos correspondiente a un momento del dia.
	 * @return
	 */
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

	/**
	 * El metodo "newSceneProduct" se encarga de lanzar una nueva vista donde podemos seleccionar 
	 * de una lista de productos ya existente los que queremos añadir a nuestro menu o dieta.
	 * @param productMomentDay Recibimos la lista de productos a cargar o rellenar y el momento del dia al que pertenecen.
	 */
	private void newSceneProduct(ProductMomentDay productMomentDay) {
		Stage choice = ChoiceController.getCreateDietCustomStage();
		Stage manage = ManageDietController.getModificateStage();

		Stage stage = choice != null ? choice : manage;
		try {
			Stage productStage = new Stage();
			productStage.setMinWidth(800);
			productStage.setMinHeight(500);
			productStage.setTitle("Productos");
			productStage.getIcons().add(new Image("/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(stage);

			productController = new ProductController(productMomentDay);
			Scene scene = new Scene(productController.getView());
			scene.getStylesheets().add(MainController.getStyleSheetActual());

			productStage.setScene(scene);
			productStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * El metodo "getPieChart" crea la grafica basandose en los datos de los valores nutricionales.
	 */
	private void getPieChart() {
		if (!nutritionalValues.get().isEmpty()) {
			Integer nutricionalsTotals = nutritionalValues.get().getProteinsTotals()
					+ nutritionalValues.get().getHydratesTotals() + nutritionalValues.get().getFatsTotals()
					+ nutritionalValues.get().getFibresTotals();
			Integer proteinsValue = Math
					.round((nutritionalValues.get().getProteinsTotals() * 100) / nutricionalsTotals);
			Integer hydratesValue = Math
					.round((nutritionalValues.get().getHydratesTotals() * 100) / nutricionalsTotals);
			Integer fatsValue = Math.round((nutritionalValues.get().getFatsTotals() * 100) / nutricionalsTotals);
			Integer fibresValue = Math.round((nutritionalValues.get().getFibresTotals() * 100) / nutricionalsTotals);

			PieChart.Data proteins = new PieChart.Data("Proteinas", proteinsValue);
			PieChart.Data hydrates = new PieChart.Data("Hidratos", hydratesValue);
			PieChart.Data fats = new PieChart.Data("Grasas", fatsValue);
			PieChart.Data fibres = new PieChart.Data("Fibra", fibresValue);

			menuChart.getData().setAll(proteins);
			menuChart.getData().add(hydrates);
			menuChart.getData().add(fats);
			menuChart.getData().add(fibres);

			menuChart.setClockwise(true);
			menuChart.setLabelsVisible(true);
			menuChart.setLabelLineLength(20);

			menuChart.getData().forEach(this::installTooltip);
			installTooltip(proteins);
			installTooltip(hydrates);
			installTooltip(fats);
			installTooltip(fibres);
		}
	}

	/**
	 * El metodo "installTooltip" nos muestra un tooltip sobre cada valor de la grafica.
	 * @param d le pasamos el valor del porcentaje de cada nodo.
	 */
	public void installTooltip(PieChart.Data d) {
		String msg = String.format("%s : %s", d.getName(), d.getPieValue() + "%");

		Tooltip tooltip = new Tooltip(msg);
		tooltip.setStyle("-fx-background-color: #061f33; -fx-text-fill: #FFFFFF; -fx-font-family: Segoe UI Light;");

		Tooltip.install(d.getNode(), tooltip);
	}
	
	/**
	 * Creamos un getter para poder acceder al stage de cargar la lista de todos los menus existentes desde cualquier parte de la app.
	 * @return retornamos el stage de cargar la lista de todos los menus existentes
	 */
	public static Stage getLoadAllMenuStage() {
		return loadAllMenuStage;
	}

	public HBox getView() {
		return view;
	}
}
