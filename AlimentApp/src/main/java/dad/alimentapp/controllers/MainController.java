package dad.alimentapp.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

	// View

	@FXML
	private BorderPane view;

	@FXML
	private MenuItem exitMenu;

	@FXML
	private MenuItem changeThemeMenu;

	@FXML
	private MenuItem showUserGuidesMenu;

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

	// Controllers
	MyMenusController myMenusController = new MyMenusController();
	DataController dataController = new DataController();
	InfoController infoController = new InfoController();
	AboutAppController aboutAppController;
	
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
	}

	// Funciones menu

	@FXML
	void onExitMenuAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Salir de la aplicación",
				"¿Está seguro de que desea salir de la aplicación?");
		if (result.get() == ButtonType.OK) {
			App.getPrimaryStage().close();
		}
	}

	@FXML
	void onChangeThemeMenuAction(ActionEvent event) {

	}

	@FXML
	void onAboutAppMenuAction(ActionEvent event) {
		try {
			aboutAppController = new AboutAppController();

			Stage secondaryStage = new Stage();
			Scene scene = new Scene(aboutAppController.getView());

			secondaryStage.setScene(scene);
			secondaryStage.setTitle("Acerca de AlimentApp");
			secondaryStage.resizableProperty().setValue(Boolean.FALSE);
			// secondaryStage.getIcons().add(new Image("/images/"));
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.initOwner(App.getPrimaryStage());
			secondaryStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void onShowUserGuidesMenuAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/dam-dad/AlimentApp/blob/main/README.md"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}

}
