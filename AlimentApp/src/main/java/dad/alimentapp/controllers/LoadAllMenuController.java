package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Profile;
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

/**
 * Esta clase "LoadAllMenuController" la utilizaremos para visualizar una lista con todos los menus del usuario.
 * @author Antonio
 *
 */
public class LoadAllMenuController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private ListView<Menu> allMenuList;

	@FXML
	private Button loadAllMenuButton;

	// MODEL
	private ListProperty<Menu> allMenus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();
	private Menu menuAccepted = null;	
	
	//VARIABLE
	private Stage stage;

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

		//BUTTON
		loadAllMenuButton.disableProperty().bind(allMenuList.getSelectionModel().selectedItemProperty().isNull());
	}

	/**
	 * El metodo "onLoadAllMenuButtonAction" se encarga almacenar el menu seleccionado para cargar en 
	 * la variable "menuAccepted" y de cerrar la ventana.
	 * @param event
	 */
	@FXML
	void onLoadAllMenuButtonAction(ActionEvent event) {
		menuAccepted = menu.get();
		stage.close();
	}

	/**
	 * El metodo "loadMenus" se encarga de cargar la lista de todos los menus existentes de un usuario.
	 */
	private void loadMenus() {
		Profile profile = MainController.getProfileSelected();
		allMenus.addAll(MenuService.getAllMenus(profile));
	}

	public VBox getView() {
		return view;
	}

	/**
	 * El metodo "getMenuAccepted" se encarga de devolver el menu seleccionado para cargar.
	 * @return retornamos el menu a cargar.
	 */
	public Menu getMenuAccepted() {
		return menuAccepted;
	}
}
