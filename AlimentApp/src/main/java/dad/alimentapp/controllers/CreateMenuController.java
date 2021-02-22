package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.MenuProduct;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.utils.Messages;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CreateMenuController implements Initializable {

	// VIEW
	@FXML
	private HBox view;

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
	private Button saveMenuButton;


	// CONTROLLERS
	private ProductController productController;

	// MODEL
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>();

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

	public CreateMenuController(Menu menu) throws IOException {
		this.menuSelected.set(menu);
		loadProductsMenu();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getPieChart();

		// BINDINGS
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

		// BINDINGS LABELS
		// TODO LISTENERS Y UNBINDINGS
		kcalTotLabel.textProperty().bindBidirectional(kcalTotales);
		proteinTotLabel.textProperty().bindBidirectional(proteinTotales);
		hydratesTotLabel.textProperty().bindBidirectional(hydratesTotales);
		fatsTotLabel.textProperty().bindBidirectional(fatsTotales);
		fibresTotLabel.textProperty().bindBidirectional(fibresTotales);
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
			breakfastProductList.setProduct(FXCollections.observableArrayList());
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
			midMorningProductList.setProduct(FXCollections.observableArrayList());
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
			lunchProductList.setProduct(FXCollections.observableArrayList());
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
			snackProductList.setProduct(FXCollections.observableArrayList());
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
			dinnerProductList.setProduct(FXCollections.observableArrayList());
		}
	}

	@FXML
	void onNextButtonAction(ActionEvent event) {
		menuSelected.get().setWeekday(Weekday.next(menuSelected.get().getWeekday().getId()));
	}

	@FXML
	void onPreviousButtonAction(ActionEvent event) {
		menuSelected.get().setWeekday(Weekday.previous(menuSelected.get().getWeekday().getId()));
	}

	@FXML
	void onSaveMenuButtonAction(ActionEvent event) {
		if (menuSelected.get().getId() != 0) {
			Menu.updateMenu(menuSelected.get());
			manageRemoveProductsInMenu();
			manageInsertProductsInMenu();
			ManageDietController.getModificateStage().close();
		} else {
			menuSelected.get().setId(Menu.insertMenu(menuSelected.get()));
			manageInsertProductsInMenu();
			ChoiceController.getCreateDietCustomStage().close();
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
}
