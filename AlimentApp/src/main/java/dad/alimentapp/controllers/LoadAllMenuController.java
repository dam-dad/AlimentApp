package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Profile;
import dad.alimentapp.utils.Utils;
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

public class LoadAllMenuController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private ListView<Menu> allMenuList;

	@FXML
	private Button loadAllMenuButton;

	// MODEL
	private ListProperty<Menu> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>();

	// VARIABLE
	private List<Menu> menuList;

	public LoadAllMenuController(List<Menu> menus) throws IOException {
		this.menuList = menus;
		loadMenus();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadAllMenus.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		menuSelected.bind(allMenuList.getSelectionModel().selectedItemProperty());
		allMenuList.itemsProperty().bindBidirectional(menus);

		loadAllMenuButton.disableProperty().bind(allMenuList.getSelectionModel().selectedItemProperty().isNull());
	}

	@FXML
	void onLoadAllMenuButtonAction(ActionEvent event) {
		Utils.replaceMatchesInMenu(menuList, menuSelected.get());		
		CreateDietController.getLoadAllMenuStage().close();
	}

	private void loadMenus() {
		Profile profile = MainController.getProfileSelected();
		menus.addAll(Menu.getAllMenus(profile));
	}

	public VBox getView() {
		return view;
	}

}
