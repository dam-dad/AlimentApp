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

/**
 * Esta clase "MainController" la utilizaremos para lanzar los diferentes
 * controladores de la app.
 * 
 * @author Antonio y David
 *
 */
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
	private Tab profileTab;

	@FXML
	private Tab myDataTab;

	@FXML
	private Tab manageDietsTab;

	// TRANSITION
	private FadeTransition transicion;

	// CONTROLLERS
	DataController dataController = new DataController();
	ProfileController profileController = new ProfileController();
	ManageDietController manageDietController = new ManageDietController();

	// PROFILE
	private static Profile profileSelected;

	// VARIABLES
	private static String styleSheetsActual;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myDataTab.setContent(dataController.getView());
		profileTab.setContent(profileController.getView());
		manageDietsTab.setContent(manageDietController.getView());

		myDataTab.setDisable(true);
		manageDietsTab.setDisable(true);
	}

	@FXML
	void onGenerateReportMenuAction(ActionEvent event) {

	}

	/**
	 * El metodo "onExitMenuAction" se encarga de cerrar la app, lanzando previamente un alert de confirmacion.
	 * @param event
	 */
	@FXML
	void onExitMenuAction(ActionEvent event) {
		Utils.closeApp();
	}

	/**
	 * El metodo "onLightThemeMenuAction" se encargar de cargar la hoja de estilos con un tema claro.
	 * @param event
	 */
	@FXML
	void onLightThemeMenuAction(ActionEvent event) {
		App.getAppScene().getStylesheets().clear();
		styleSheetsActual = "/css/style-light.css";
		App.getAppScene().getStylesheets().add(styleSheetsActual);
	}

	/**
	 * El metodo "onDarkThemeMenuAction" se encargar de cargar la hoja de estilos con un tema oscuro.
	 * @param event
	 */
	@FXML
	void onDarkThemeMenuAction(ActionEvent event) {
		App.getAppScene().getStylesheets().clear();
		styleSheetsActual = "/css/style-dark.css";
		App.getAppScene().getStylesheets().add(styleSheetsActual);
	}

	/**
	 * El metodo "onAboutAppMenuAction" se encarga de lanzar una nueva vista con informacion acerca de la app y sus desarrolladores.
	 * @param event
	 */
	@FXML
	void onAboutAppMenuAction(ActionEvent event) {
		try {
			AboutAppController aboutAppController = new AboutAppController();

			Stage aboutStage = new Stage();
			aboutStage.setTitle("Acerca de AlimentApp");
			aboutStage.resizableProperty().setValue(Boolean.FALSE);
			aboutStage.getIcons().add(new Image("/images/logo.png"));
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.initOwner(App.getPrimaryStage());
			
			Scene scene = new Scene(aboutAppController.getView());
			scene.getStylesheets().add(MainController.getStyleSheetActual());
			
			aboutStage.setScene(scene);
			aboutStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * El metodo "onShowUserGuidesMenuAction" se encarga de abrir navegador y visualizarnos la guia de usuario de la app.
	 * @param event
	 */
	@FXML
	void onShowUserGuidesMenuAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/dam-dad/AlimentApp/blob/main/README.md"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// Transition

	public void playTransition() {
		transicion = new FadeTransition();
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

	/**
	 * El metodo "getProfileSelected" nos permite obtener el perfil del usuario que este cargado.
	 * @return retornamos el perfil del usuario seleccionado.
	 */
	public static Profile getProfileSelected() {
		return profileSelected;
	}

	/**
	 * El metodo "setProfileSelected" nos permite asignarle el perfil del usuario que se seleccione en la vista principal.
	 * @param profileSelected Le pasamos por parametro el perfil del usuario seleccionado.
	 */
	public static void setProfileSelected(Profile profileSelected) {
		MainController.profileSelected = profileSelected;
	}

	/**
	 * El metodo "getStyleSheetActual" nos permite obtener el fichero de estilo que esta siendo usado por la app.
	 * @return retornamos el fichero de estilo que esta siendo usado por la app.
	 */
	public static String getStyleSheetActual() {
		return styleSheetsActual;
	}

	/**
	 * El metodo "setStyleSheetActual" nos permite definir una hoja de estilos y que toda la app tenga acceso.
	 * @param style le pasamos por parametros la ruta del fichero CSS que se va usando en la app.
	 */
	public static void setStyleSheetActual(String style) {
		styleSheetsActual = style;
	}

	public BorderPane getView() {
		return view;
	}

	public Tab getMyData() {
		return myDataTab;
	}

	public Tab getManageDietsTab() {
		return manageDietsTab;
	}

	public TabPane getDataTab() {
		return alimentAppTabPane;
	}
}
