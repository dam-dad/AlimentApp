package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.DietsMenu;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Profile;
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
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

//NUESTRO CONTROLLER
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
	private Button viewDietButton;

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
	// parte de visualizacion
	@FXML
	private ListView<?> breakfastListView;

	@FXML
	private ListView<?> midMorningListView;

	@FXML
	private ListView<?> lunchListView;

	@FXML
	private ListView<?> snackListView;

	@FXML
	private ListView<?> dinnerListView;

	// CONTROLLERS
	private CreateDietController createDietController;
	private CreateMenuController createMenuController;
	private ChoiceController choiceController;

	// MODEL
	private ListProperty<Menu> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>();
	private ListProperty<Diet> diets = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Diet> dietSelected = new SimpleObjectProperty<>();

	// VARIABLE
	private static Stage choiceStage;
	private static Stage modifIcateStage;

	public ManageDietController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		loadDietsAndMenus();
		
//		diets.addListener((o, ov, nv) -> {
//			System.out.println(nv);
//		});
//		
//		menus.addListener((o, ov, nv) -> {
//			System.out.println(nv);
//		});

		menuList.itemsProperty().bindBidirectional(menus);
		dietList.itemsProperty().bindBidirectional(diets);
		
		menuSelected.bind(menuList.getSelectionModel().selectedItemProperty());
		dietSelected.bind(dietList.getSelectionModel().selectedItemProperty());
	}
	
	private void loadDietsAndMenus() {		
		Profile profile = MainController.getProfileSelected();		
		diets.addAll(Diet.getAllDiets(profile));
		menus.addAll(Menu.getAllMenus(profile));
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
			createDietController = new CreateDietController(DietsMenu.getAllMenusForDiet(dietSelected.get()));
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

	}

	@FXML
	void onRemoveMenuButtonAction(ActionEvent event) {

	}

	@FXML
	void onViewMenuButtonAction(ActionEvent event) {

	}

	@FXML
	void onViewDietButtonAction(ActionEvent event) {

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
