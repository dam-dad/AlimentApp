package dad.alimentapp.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Profile;
import dad.alimentapp.utils.Utils;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {

	// View
	@FXML
	private BorderPane view;

	@FXML
	private MenuItem generateReportMenu;

	@FXML
	private MenuItem exitMenu;

	@FXML
	private MenuItem lightThemeMenu;

	@FXML
	private MenuItem darkThemeMenu;

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
	private Tab manageDietsTab;
	
	//Transicion
	private FadeTransition transicion;

	// Controllers
	DataController dataController = new DataController();
	InfoController infoController = new InfoController();
	AboutAppController aboutAppController;
	ManageDietController manageDietController = new ManageDietController();

	// Profile
	private static Profile profileSelected;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myDataTab.setContent(dataController.getView());
		informationTab.setContent(infoController.getView());
		manageDietsTab.setContent(manageDietController.getView());
	}

	// Funciones menu

	@FXML
	void onGenerateReportMenuAction(ActionEvent event) {

	}

	@FXML
	void onExitMenuAction(ActionEvent event) {
		Utils.closeApp();
	}

	@FXML
	void onLightThemeMenuAction(ActionEvent event) {

	}

	@FXML
	void onDarkThemeMenuAction(ActionEvent event) {

	}

	@FXML
	void onAboutAppMenuAction(ActionEvent event) {
		try {
			aboutAppController = new AboutAppController();

			Stage aboutStage = new Stage();
			Scene scene = new Scene(aboutAppController.getView());

			aboutStage.setScene(scene);
			aboutStage.setTitle("Acerca de AlimentApp");
			aboutStage.resizableProperty().setValue(Boolean.FALSE);
			aboutStage.getIcons().add(new Image("/images/logo.png"));
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.initOwner(App.getPrimaryStage());
			aboutStage.showAndWait();
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

	
	//Transition
	
	public  void playTransition() {
		transicion= new FadeTransition();
		transicion.setAutoReverse(true);
		transicion.setDelay(Duration.ZERO);
		transicion.setDuration(Duration.seconds(3));
		transicion.setFromValue(0.0);
		transicion.setToValue(1.0);
		transicion.setRate(2.0);
		transicion.setNode(dataController.getView());
		transicion.setInterpolator(Interpolator.LINEAR);
		transicion.play();
		
	}
	
	
	
	
	public static Profile getProfileSelected() {
		return profileSelected;
	}

	public static void setProfileSelected(Profile profileSelected) {
		MainController.profileSelected = profileSelected;
	}

	public BorderPane getView() {
		return view;
	}

	public TabPane getDataTab() {
		return alimentAppTabPane;
	}
}
