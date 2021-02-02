package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// View
	@FXML
	private BorderPane view;

	@FXML
	private MenuItem newMenu;

	@FXML
	private MenuItem openMenu;

	@FXML
	private MenuItem saveMenu;

	@FXML
	private MenuItem saveAsMenu;

	@FXML
	private MenuItem exitMenu;

	@FXML
	private MenuItem changeThemeMenu;

	@FXML
	private MenuItem aboutAppMenu;

	@FXML
	private TabPane alimentAppTabPane;

	@FXML
	private Tab informationTab;

	@FXML
	private Tab myDataTab;

	@FXML
	private Tab myMenusTab;

	@FXML
	private Tab myDietsTab;
	
	//Controllers
	MyMenusController myMenusController = new MyMenusController();
	DataController  dataController= new DataController();
	InfoController infoController = new InfoController();
	FoodsController foodController = new FoodsController();

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myMenusTab.setContent(myMenusController.getView());
		myDataTab.setContent(dataController.getView());
		informationTab.setContent(infoController.getView());
		myDietsTab.setContent(foodController.getView());
	}

	// Funciones menu
	@FXML
	void onAboutAppMenuAction(ActionEvent event) {

	}

	@FXML
	void onChangeThemeMenuAction(ActionEvent event) {

	}

	@FXML
	void onExitMenuAction(ActionEvent event) {

	}

	@FXML
	void onNewMenuAction(ActionEvent event) {

	}

	@FXML
	void onOpenMenuAction(ActionEvent event) {

	}

	@FXML
	void onSaveAsMenuAction(ActionEvent event) {

	}

	@FXML
	void onSaveMenuAction(ActionEvent event) {

	}

	public BorderPane getView() {
		return view;
	}

}
