package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Profile;
import dad.alimentapp.service.DietService;
import dad.alimentapp.service.MenuService;
import dad.alimentapp.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * Realizamos el controlador que permitirá implementar la interfaz de Gestionar
 * Dietas y darle toda la funcionalidad que necesita.
 * 
 * @author Andy y Antonio
 */
public class ManageDietController implements Initializable {

	@FXML
	private HBox view;

	@FXML
	private ListView<Menu> menuList;

	@FXML
	private Button createMenuButton;

	@FXML
	private Button viewMenuButton;

	@FXML
	private Button modifyMenuButton;

	@FXML
	private Button removeMenuButton;

	@FXML
	private ListView<Diet> dietList;

	@FXML
	private Button createDietButton;

	@FXML
	private Button modifyDietButton;

	@FXML
	private Button removeDietButton;

	@FXML
	private GridPane viewMenuDietsPane;

	@FXML
	private Label menuDietsNameLabel;

	@FXML
	private Label breakfastLabel;

	@FXML
	private Label midmorningLabel;

	@FXML
	private Label lunchLabel;

	@FXML
	private Label snackLabel;

	@FXML
	private Label dinnerLabel;

	@FXML
	private Label bonAppetiteLabel;

	@FXML
	private Label copyrightLabel;

	@FXML
	private ListView<Product> breakfastListView;

	@FXML
	private ListView<Product> midMorningListView;

	@FXML
	private ListView<Product> lunchListView;

	@FXML
	private ListView<Product> snackListView;

	@FXML
	private ListView<Product> dinnerListView;

	// CONTROLLERS
	private CreateDietController createDietController;
	private CreateMenuController createMenuController;
	private ChoiceController choiceController;

	// MODEL
	private static ListProperty<Menu> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>();

	private static ListProperty<Diet> diets = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Diet> dietSelected = new SimpleObjectProperty<>();

	// VARIABLE
	private static Stage choiceStage;
	private static Stage modifIcateStage;
	private ProductMomentDay breakfastProductList;
	private ProductMomentDay midMorningProductList;
	private ProductMomentDay lunchProductList;
	private ProductMomentDay snackProductList;
	private ProductMomentDay dinnerProductList;

	public ManageDietController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageDietView2.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		loadDietsAndMenus();

		menuList.itemsProperty().bindBidirectional(menus);
		menuList.setCellFactory(menusView -> new ListCell<Menu>() {
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(Menu menu, boolean empty) {
				super.updateItem(menu, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					Image image = new Image("images/myManageDietsTab/menus-512px.png");
					imageView.setImage(image);
					imageView.setFitWidth(30);
					imageView.setFitHeight(30);
					setText(menu.getName());
					setGraphic(imageView);
				}
			}
		});
		
		dietList.itemsProperty().bindBidirectional(diets);
		dietList.setCellFactory(dietsView -> new ListCell<Diet>() {
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(Diet diet, boolean empty) {
				super.updateItem(diet, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					Image image = new Image("images/myManageDietsTab/diets-512px.png");
					imageView.setImage(image);
					imageView.setFitWidth(30);
					imageView.setFitHeight(30);
					setText(diet.getName());
					setGraphic(imageView);
				}
			}
		});

		menuSelected.bind(menuList.getSelectionModel().selectedItemProperty());
		dietSelected.bind(dietList.getSelectionModel().selectedItemProperty());

		viewMenuButton.disableProperty().bind(menuList.getSelectionModel().selectedItemProperty().isNull());
		modifyMenuButton.disableProperty().bind(menuList.getSelectionModel().selectedItemProperty().isNull());
		removeMenuButton.disableProperty().bind(menuList.getSelectionModel().selectedItemProperty().isNull());

		modifyDietButton.disableProperty().bind(dietList.getSelectionModel().selectedItemProperty().isNull());
		removeDietButton.disableProperty().bind(dietList.getSelectionModel().selectedItemProperty().isNull());
	}

	public static void loadDietsAndMenus() {
		Profile profile = MainController.getProfileSelected();
		if(profile != null) {
			diets.setAll(DietService.getAllDiets(profile));
			menus.setAll(MenuService.getAllMenus(profile));
		}
	}

	@FXML
	void onCreateDietButtonAction(ActionEvent event) {
		try {
			choiceController = new ChoiceController(ControlDietMenu.Dieta);
			this.createChoiceStage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onCreateMenuButtonAction(ActionEvent event) {
		try {
			choiceController = new ChoiceController(ControlDietMenu.Menú);
			this.createChoiceStage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onModifyDietButtonAction(ActionEvent event) {
		ControlDietMenu controlDietMenu = ControlDietMenu.Dieta;
		try {
			createDietController = new CreateDietController(dietSelected.get());
			modifIcateStage(controlDietMenu, createDietController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onModifyMenuButtonAction(ActionEvent event) {
		ControlDietMenu controlDietMenu = ControlDietMenu.Menú;
		try {
			createMenuController = new CreateMenuController(menuSelected.get());
			modifIcateStage(controlDietMenu, createMenuController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onRemoveDietButtonAction(ActionEvent event) {
		DietService.deleteDiet(dietSelected.get());
		Profile profile = MainController.getProfileSelected();
		diets.setAll(DietService.getAllDiets(profile));
		ManageDietController.loadDietsAndMenus();
	}

	@FXML
	void onRemoveMenuButtonAction(ActionEvent event) {
		MenuService.deleteMenu(menuSelected.get());
		Profile profile = MainController.getProfileSelected();
		menus.setAll(MenuService.getAllMenus(profile));
		resetProductsMenu();
		ManageDietController.loadDietsAndMenus();
	}

	private void resetProductsMenu() {

		ObservableList<Product> clearList = FXCollections.observableArrayList();
		breakfastListView.setItems(clearList);
		midMorningListView.setItems(clearList);
		lunchListView.setItems(clearList);
		snackListView.setItems(clearList);
		dinnerListView.setItems(clearList);

	}

	@FXML
	void onViewMenuButtonAction(ActionEvent event) {

		loadProductsMenu();
		breakfastListView.setItems(breakfastProductList.getProducts());
		midMorningListView.setItems(midMorningProductList.getProducts());
		lunchListView.setItems(lunchProductList.getProducts());
		snackListView.setItems(snackProductList.getProducts());
		dinnerListView.setItems(dinnerProductList.getProducts());

	}

	private void loadProductsMenu() {
		breakfastProductList = ProductService.getAllProductsToMenuOfMomentDay(menuSelected.get().getId(),
				MomentDay.DESAYUNO);
		midMorningProductList = ProductService.getAllProductsToMenuOfMomentDay(menuSelected.get().getId(),
				MomentDay.MEDIA_MAÑANA);
		lunchProductList = ProductService.getAllProductsToMenuOfMomentDay(menuSelected.get().getId(),
				MomentDay.ALMUERZO);
		snackProductList = ProductService.getAllProductsToMenuOfMomentDay(menuSelected.get().getId(),
				MomentDay.MERIENDA);
		dinnerProductList = ProductService.getAllProductsToMenuOfMomentDay(menuSelected.get().getId(), MomentDay.CENA);
	}

	private void createChoiceStage() {
		choiceStage = new Stage();
		Scene scene = new Scene(choiceController.getView());
		scene.getStylesheets().add(MainController.getStyleSheetActual());

		choiceStage.setScene(scene);
		choiceStage.setTitle("Elección");
		choiceStage.resizableProperty().setValue(Boolean.FALSE);
		choiceStage.getIcons().add(new Image("/images/logo.png"));
		choiceStage.initModality(Modality.WINDOW_MODAL);
		choiceStage.initOwner(App.getPrimaryStage());
		choiceStage.showAndWait();
	}

	private void modifIcateStage(ControlDietMenu controlDiet, HBox view) {
		ChoiceController.setCreateDietCustomStage(null);
		modifIcateStage = new Stage();
		Scene scene = new Scene(view);
		scene.getStylesheets().add(MainController.getStyleSheetActual());

		modifIcateStage.setScene(scene);
		modifIcateStage.setTitle(controlDiet.name());
		modifIcateStage.getIcons().add(new Image("/images/logo.png"));
		modifIcateStage.initModality(Modality.WINDOW_MODAL);
		modifIcateStage.initOwner(App.getPrimaryStage());
		modifIcateStage.show();
	}

	public static Stage getChoiceStage() {
		return choiceStage;
	}

	public static Stage getModificateStage() {
		return modifIcateStage;
	}

	public static void setModificateStage(Stage stage) {
		modifIcateStage = stage;
	}

	public HBox getView() {
		return view;
	}

}
