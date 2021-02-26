package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.Profile;
import dad.alimentapp.models.app.Menu;
import dad.alimentapp.service.MenuService;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadAllMenuController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private ListView<Menu> allMenuList;

	@FXML
	private Button loadAllMenuButton;
	
	//VARIABLE
	private Stage stage;

	// MODEL
	private ListProperty<Menu> allMenus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();
	private Menu menuAccepted = null;

	public LoadAllMenuController(Stage stage) throws IOException {
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadAllMenus.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadMenus();
		
		// BINDINGS
		menu.bind(allMenuList.getSelectionModel().selectedItemProperty());
		allMenuList.itemsProperty().bindBidirectional(allMenus);

		loadAllMenuButton.disableProperty().bind(allMenuList.getSelectionModel().selectedItemProperty().isNull());
	}

	@FXML
	void onLoadAllMenuButtonAction(ActionEvent event) {
		menuAccepted = menu.get();
		stage.close();
	}

	private void loadMenus() {
		Profile profile = MainController.getProfileSelected();
		allMenus.addAll(MenuService.getAllMenus(profile));
	}

	public VBox getView() {
		return view;
	}

	public Menu getMenuAccepted() {
		return menuAccepted;
	}
}
