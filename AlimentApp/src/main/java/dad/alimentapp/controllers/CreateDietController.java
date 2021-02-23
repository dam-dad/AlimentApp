package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.DietsMenu;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.MenuProduct;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

	@FXML
	private Button deleteCurrentMenuButton;

	// CONTROLLERS
	private ProductController productController;
	private LoadAllMenuController loadAllMenuController;

	// MODEL
	private DietsMenu dietsMenu;
	private ObjectProperty<Menu> menuSelected;

	private StringProperty kcalTotales = new SimpleStringProperty();
	private StringProperty proteinTotales = new SimpleStringProperty();
	private StringProperty hydratesTotales = new SimpleStringProperty();
	private StringProperty fatsTotales = new SimpleStringProperty();
	private StringProperty fibresTotales = new SimpleStringProperty();

	// STAGE
	private static Stage loadAllMenuStage;

	// VARIABLES
	private MenuProduct breakfastProductList;
	private MenuProduct midMorningProductList;
	private MenuProduct lunchProductList;
	private MenuProduct snackProductList;
	private MenuProduct dinnerProductList;
 

	public CreateDietController(DietsMenu dietsMenu) throws IOException {
		this.dietsMenu = dietsMenu;
		List<Menu> menuList = this.dietsMenu.getMenu();
		if (menuList.size() == 0) {
			menuList.add(new Menu());
		}
		this.menuSelected = new SimpleObjectProperty<>(menuList.get(0));
		loadProductsMenu();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getPieChart();
		// BINDINGS
		nameDietText.textProperty().bindBidirectional(dietsMenu.getDiets().nameProperty());

		// BINDINGS LABELS
		// TODO LISTENERS Y UNBINDINGS
		kcalTotLabel.textProperty().bindBidirectional(kcalTotales);
		proteinTotLabel.textProperty().bindBidirectional(proteinTotales);
		hydratesTotLabel.textProperty().bindBidirectional(hydratesTotales);
		fatsTotLabel.textProperty().bindBidirectional(fatsTotales);
		fibresTotLabel.textProperty().bindBidirectional(fibresTotales);

		// BUTTONS
		saveDietButton.disableProperty()
				.bind(Bindings.size(breakfastProductList.getProduct()).isEqualTo(0)
						.and(Bindings.size(midMorningProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(lunchProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(snackProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(breakfastProductList.getProduct()).isEqualTo(0)));

		deleteCurrentMenuButton.disableProperty()
				.bind(Bindings.size(breakfastProductList.getProduct()).isEqualTo(0)
						.and(Bindings.size(midMorningProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(lunchProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(snackProductList.getProduct()).isEqualTo(0))
						.and(Bindings.size(breakfastProductList.getProduct()).isEqualTo(0)));

		breakfastRemoveButton.disableProperty().bind(Bindings.size(breakfastProductList.getProduct()).isEqualTo(0));
		midMorningRemoveButton.disableProperty().bind(Bindings.size(midMorningProductList.getProduct()).isEqualTo(0));
		lunchRemoveButton.disableProperty().bind(Bindings.size(lunchProductList.getProduct()).isEqualTo(0));
		snackRemoveButton.disableProperty().bind(Bindings.size(snackProductList.getProduct()).isEqualTo(0));
		dinnerRemoveButton.disableProperty().bind(Bindings.size(dinnerProductList.getProduct()).isEqualTo(0));

		// TODO
		
		nameMenuText.textProperty().bindBidirectional(menuSelected.get().nameProperty());
		Bindings.bindBidirectional(weekdayLabel.textProperty(), menuSelected.get().weekdayProperty(),
				new StringConverter<Weekday>() {
					@Override
					public String toString(Weekday weekday) {
						return weekday.name();
					}

					@Override
					public Weekday fromString(String string) {
						return Weekday.valueOf(string);
					}
				});

		breakfastList.itemsProperty().bindBidirectional(breakfastProductList.productProperty());
		midMorningList.itemsProperty().bindBidirectional(midMorningProductList.productProperty());
		lunchList.itemsProperty().bindBidirectional(lunchProductList.productProperty());
		snackList.itemsProperty().bindBidirectional(snackProductList.productProperty());
		dinnerList.itemsProperty().bindBidirectional(dinnerProductList.productProperty());

		
		
		
//		dietsMenu.menuProperty().addListener((o, ov, nv) -> manageBindDietsMenu(o, ov, nv));
//		menuSelected.addListener((o, ov, nv) -> manageBindMenuSelected(o, ov, nv));

	}



	@FXML
	void onBreakfastAddButtonAction(ActionEvent event) {
		newSceneProduct(breakfastProductList);
	}

	@FXML
	void onBreakfastRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Desayuno",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			List<Product> products = breakfastProductList.getProduct();
			breakfastProductList.getProduct().removeAll(products);
		}
	}

	@FXML
	void onMidMorningAddButtonAction(ActionEvent event) {
		newSceneProduct(midMorningProductList);
	}

	@FXML
	void onMidMorningRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de Media-Mañana",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			List<Product> products = midMorningProductList.getProduct();
			midMorningProductList.getProduct().removeAll(products);
		}
	}

	@FXML
	void onLunchAddButtonAction(ActionEvent event) {
		newSceneProduct(lunchProductList);
	}

	@FXML
	void onLunchRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Almuerzo",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			List<Product> products = lunchProductList.getProduct();
			lunchProductList.getProduct().removeAll(products);
		}
	}

	@FXML
	void onSnackAddButtonAction(ActionEvent event) {
		newSceneProduct(snackProductList);
	}

	@FXML
	void onSnackRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Merienda",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			List<Product> products = snackProductList.getProduct();
			snackProductList.getProduct().removeAll(products);
		}
	}

	@FXML
	void onDinnerAddButtonAction(ActionEvent event) {
		newSceneProduct(dinnerProductList);
	}

	@FXML
	void onDinnerRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Cena",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			List<Product> products = dinnerProductList.getProduct();
			dinnerProductList.getProduct().removeAll(products);
		}
	}

	@FXML
	void onNextButtonAction(ActionEvent event) {
		if (dietsMenu.getMenu().size() > 1) {
			Weekday nextDay = Weekday.next(menuSelected.get().getWeekday().getId());
			Menu menu = Utils.searchMatchesInMenu(dietsMenu.getMenu(), nextDay);
			menuSelected.set(menu != null ? menu : new Menu(nextDay));
			System.out.println(menuSelected);
		} else {
			Menu menu = new Menu(Weekday.next(menuSelected.get().getWeekday().getId()));
			dietsMenu.getMenu().add(menu);
			menuSelected.set(menu);
			System.out.println(menuSelected);
		}
	}

	@FXML
	void onPreviousButtonAction(ActionEvent event) {
		if (dietsMenu.getMenu().size() > 1) {
			Weekday previousDay = Weekday.previous(menuSelected.get().getWeekday().getId());
			Menu menu = Utils.searchMatchesInMenu(dietsMenu.getMenu(), previousDay);
			menuSelected.set(menu != null ? menu : new Menu(previousDay));
			System.out.println(menuSelected);
		} else {
			Menu menu = new Menu(Weekday.previous(menuSelected.get().getWeekday().getId()));
			dietsMenu.getMenu().add(menu);
			menuSelected.set(menu);
			System.out.println(menuSelected);
		}
	}

	@FXML
	void onSaveDietButtonAction(ActionEvent event) {
		if (dietsMenu.getDiets().getId() != 0) {
			Diet.updateDiet(dietsMenu.getDiets());
			for (Menu menu : dietsMenu.getMenu()) {
				menuSelected.set(menu);
				Menu.updateMenu(menuSelected.get());
				manageRemoveProductsInMenu();
				manageInsertProductsInMenu();
			}
			ManageDietController.loadDietsAndMenus();
			ManageDietController.getModificateStage().close();
		} else {
			dietsMenu.getDiets().setId(Diet.insertDiet(dietsMenu.getDiets()));
			for (Menu menu : dietsMenu.getMenu()) {
				menu.setId(Menu.insertMenu(menuSelected.get()));
				DietsMenu.insertDietMenu(dietsMenu.getDiets().getId(), menu.getId());
				menuSelected.set(menu);
				manageInsertProductsInMenu();
			}
			ManageDietController.loadDietsAndMenus();
			ChoiceController.getCreateDietCustomStage().close();
		}
	}

	@FXML
	void onDeleteCurrentMenuButtonAction(ActionEvent event) {
		// TODO
	}

	@FXML
	void onLoadExistingMenusButtonAction(ActionEvent event) {
		try {
			loadAllMenuController = new LoadAllMenuController(dietsMenu.getMenu());

			loadAllMenuStage = new Stage();
			Scene scene = new Scene(loadAllMenuController.getView());
			loadAllMenuStage.setScene(scene);
			loadAllMenuStage.setTitle("Listas de Menús");
			loadAllMenuStage.resizableProperty().setValue(Boolean.FALSE);
			loadAllMenuStage.getIcons().add(new Image("/images/logo.png"));
			loadAllMenuStage.initModality(Modality.WINDOW_MODAL);
			loadAllMenuStage.initOwner(App.getPrimaryStage());
			loadAllMenuStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void manageRemoveProductsInMenu() {
		removeProductsInMenu(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.DESAYUNO));
		removeProductsInMenu(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MEDIA_MAÑANA));
		removeProductsInMenu(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.ALMUERZO));
		removeProductsInMenu(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MERIENDA));
		removeProductsInMenu(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.CENA));
	}

	private void removeProductsInMenu(MenuProduct menuProduct) {
		for (Product product : menuProduct.getProduct()) {
			MenuProduct.deleteMenuProduct(menuProduct.getMenu().getId(), product.getId(),
					menuProduct.getMomentDay().getId());
		}
	}

	private void manageInsertProductsInMenu() {
		insertProductsInMenu(breakfastProductList);
		insertProductsInMenu(midMorningProductList);
		insertProductsInMenu(lunchProductList);
		insertProductsInMenu(snackProductList);
		insertProductsInMenu(dinnerProductList);
	}

	private void insertProductsInMenu(MenuProduct menuProduct) {
		for (Product product : menuProduct.getProduct()) {
			MenuProduct.insertMenuProduct(menuProduct.getMenu().getId(), product.getId(),
					menuProduct.getMomentDay().getId());
		}
	}

	private void newSceneProduct(MenuProduct menuProduct) {
		try {
			productController = new ProductController(menuProduct);

			Stage productStage = new Stage();
			Scene scene = new Scene(productController.getView());
			productStage.setMinWidth(750);
			productStage.setMinHeight(450);
			productStage.setScene(scene);
			productStage.setTitle("Productos");
			productStage.getIcons().add(new Image("/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(App.getPrimaryStage());
			productStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadProductsMenu() {
		breakfastProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.DESAYUNO);
		midMorningProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MEDIA_MAÑANA);
		lunchProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.ALMUERZO);
		snackProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MERIENDA);
		dinnerProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.CENA);
	}

	private void getPieChart() {
		// Ejemplo provicional
		PieChart.Data queso1 = new PieChart.Data("kcal", 40);
		PieChart.Data queso2 = new PieChart.Data("Proteinas", 20);
		PieChart.Data queso3 = new PieChart.Data("Hidratos", 10);
		PieChart.Data queso4 = new PieChart.Data("Grasas", 10);
		PieChart.Data queso5 = new PieChart.Data("Fibra", 20);

		menuChart.getData().add(queso1);
		menuChart.getData().add(queso2);
		menuChart.getData().add(queso3);
		menuChart.getData().add(queso4);
		menuChart.getData().add(queso5);

		menuChart.setLabelsVisible(true);
		menuChart.setLabelLineLength(20);
//		menuChart.setLegendSide(Side.LEFT);
	}

	public static Stage getLoadAllMenuStage() {
		return loadAllMenuStage;
	}

	public HBox getView() {
		return view;
	}
//	private void refreshBinding() {
//	nameMenuText.textProperty().bindBidirectional(menuSelected.get().nameProperty());
//	Bindings.bindBidirectional(weekdayLabel.textProperty(), menuSelected.get().weekdayProperty(),
//			new StringConverter<Weekday>() {
//				@Override
//				public String toString(Weekday weekday) {
//					return weekday.name();
//				}
//
//				@Override
//				public Weekday fromString(String string) {
//					return Weekday.valueOf(string);
//				}
//			});
//
//	breakfastList.itemsProperty().bindBidirectional(breakfastProductList.productProperty());
//	midMorningList.itemsProperty().bindBidirectional(midMorningProductList.productProperty());
//	lunchList.itemsProperty().bindBidirectional(lunchProductList.productProperty());
//	snackList.itemsProperty().bindBidirectional(snackProductList.productProperty());
//	dinnerList.itemsProperty().bindBidirectional(dinnerProductList.productProperty());
//}
//
//private void refreshUnBinding() {
//	nameMenuText.textProperty().unbindBidirectional(menuSelected.get().nameProperty());
//	weekdayLabel.textProperty().unbindBidirectional(menuSelected.get().weekdayProperty());
//
//	breakfastList.itemsProperty().unbindBidirectional(breakfastProductList.productProperty());
//	midMorningList.itemsProperty().unbindBidirectional(midMorningProductList.productProperty());
//	lunchList.itemsProperty().unbindBidirectional(lunchProductList.productProperty());
//	snackList.itemsProperty().unbindBidirectional(snackProductList.productProperty());
//	dinnerList.itemsProperty().unbindBidirectional(dinnerProductList.productProperty());
//}
//
//private void manageBindMenuSelected(ObservableValue<? extends Menu> o, Menu ov, Menu nv) {
//	if (ov != null) {
//		refreshUnBinding();
//	}
//
//	if (nv != null) {
//		refreshBinding();
//	}
//}
//
//private void manageBindDietsMenu(ObservableValue<? extends ObservableList<Menu>> o, ObservableList<Menu> ov,
//		ObservableList<Menu> nv) {
//	if (ov != null) {
//		refreshUnBinding();
//	}
//
//	if (nv != null) {
//		refreshBinding();
//	}
//}
}
