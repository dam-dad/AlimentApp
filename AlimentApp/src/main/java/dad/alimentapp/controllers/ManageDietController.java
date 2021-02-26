package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.Profile;
import dad.alimentapp.models.app.Diet;
import dad.alimentapp.models.app.Menu;
import dad.alimentapp.service.DietService;
import dad.alimentapp.service.MenuService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
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

public class ManageDietController implements Initializable {
	
	/**
	 * Realizamos el controlador que permitirá implementar la interfaz de Gestionar Dietas y darle toda la funcionalidad que necesita. 
	 * @author Andy
	 */
	
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
	private MenuProduct breakfastProductList;
	private MenuProduct midMorningProductList;
	private MenuProduct lunchProductList;
	private MenuProduct snackProductList;
	private MenuProduct dinnerProductList;

	public ManageDietController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		loadDietsAndMenus();
		
		menuList.itemsProperty().bindBidirectional(menus);
		dietList.itemsProperty().bindBidirectional(diets);
		
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
		diets.setAll(DietService.getAllDiets(profile));
		menus.setAll(MenuService.getAllMenus(profile));
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
		
		Diet.deleteDiet(dietSelected.get());
		Profile profile = MainController.getProfileSelected();	
		diets.setAll(Diet.getAllDiets(profile));

	}

	@FXML
	void onRemoveMenuButtonAction(ActionEvent event) {
		
		Menu.deleteMenu(menuSelected.get());
		Profile profile = MainController.getProfileSelected();		
		menus.setAll(Menu.getAllMenus(profile));
		resetProductsMenu();

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
		breakfastListView.setItems(breakfastProductList.getProduct());
		midMorningListView.setItems(midMorningProductList.getProduct());
		lunchListView.setItems(lunchProductList.getProduct());
		snackListView.setItems(snackProductList.getProduct());
		dinnerListView.setItems(dinnerProductList.getProduct());

	}
	
	private void loadProductsMenu() {
		breakfastProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.DESAYUNO);
		midMorningProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MEDIA_MAÑANA);
		lunchProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.ALMUERZO);
		snackProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.MERIENDA);
		dinnerProductList = MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected.get(), MomentDay.CENA);
	}

	private void createChoiceStage() {
		choiceStage = new Stage();
		Scene scene = new Scene(choiceController.getView());

		choiceStage.setScene(scene);
		choiceStage.setTitle("Elección");
		choiceStage.resizableProperty().setValue(Boolean.FALSE);
		choiceStage.getIcons().add(new Image("/images/logo.png"));
		choiceStage.initModality(Modality.WINDOW_MODAL);
		choiceStage.initOwner(App.getPrimaryStage());
		choiceStage.showAndWait();
	}

	private void modifIcateStage(ControlDietMenu controlDiet, HBox view) {
		modifIcateStage = new Stage();
		modifIcateStage.setMinWidth(800);
		modifIcateStage.setMinHeight(500);
		Scene scene = new Scene(view);

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

	public HBox getView() {
		return view;
	}

}
