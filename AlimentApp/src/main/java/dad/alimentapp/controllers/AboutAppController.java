package dad.alimentapp.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Esta clase "AboutAppController" la utilizaremos para lanzar una vista con
 * informacion acerca de la app.
 * 
 * @author Antonio
 *
 */
public class AboutAppController implements Initializable {

	// VIEW
	@FXML
	private VBox view;

	@FXML
	private Label versionLabel;

	@FXML
	private Hyperlink davidLink;

	@FXML
	private Hyperlink antonioLink;

	@FXML
	private Hyperlink andyLink;

	@FXML
	private Hyperlink danielLink;

	@FXML
	private Hyperlink licenceLink;

	public AboutAppController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AboutAlimentApp.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * El metodo "onDavidLinkAction" utiliza el metedo link para redirigirnos al
	 * gitHub de David.
	 * 
	 * @param event
	 */
	@FXML
	void onDavidLinkAction(ActionEvent event) {
		link("https://github.com/David18888");
	}

	/**
	 * El metodo "onAntonioLinkAction" utiliza el metedo link para redirigirnos al
	 * gitHub de Antonio.
	 * 
	 * @param event
	 */
	@FXML
	void onAntonioLinkAction(ActionEvent event) {
		link("https://github.com/AntonioRodriguezLuis");
	}

	/**
	 * El metodo "onDanielLinkAction" utiliza el metedo link para redirigirnos al
	 * gitHub de Daniel.
	 * 
	 * @param event
	 */
	@FXML
	void onDanielLinkAction(ActionEvent event) {
		link("https://github.com/DanielPlasenciaOran2DAMA");
	}

	/**
	 * El metodo "onAndyLinkAction" utiliza el metedo link para redirigirnos al
	 * gitHub de Andy.
	 * 
	 * @param event
	 */
	@FXML
	void onAndyLinkAction(ActionEvent event) {
		link("https://github.com/AbsolutCode");
	}

	/**
	 * El metodo "onLicenceLinkAction" utiliza el metedo link para redirigirnos a la
	 * licencia de la app.
	 * 
	 * @param event
	 */
	@FXML
	void onLicenceLinkAction(ActionEvent event) {
		link("https://github.com/dam-dad/AlimentApp/blob/main/LICENSE");
	}

	/**
	 * El metodo "link" se encarga de abrir la url en nuestro navegador.
	 * 
	 * @param url
	 */
	private void link(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public VBox getView() {
		return view;
	}

}
