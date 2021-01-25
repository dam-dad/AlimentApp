package dad.alimentapp.controllers;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InicioSesionController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private HBox userBox;

	@FXML
	private HBox passwordBox;

	@FXML
	private TextField userText;

	@FXML
	private TextField passText;

	@FXML
	private HBox buttonBox;

	@FXML
	private Button signinButton;

	@FXML
	private Button cancelButton;

	public InicioSesionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioSesionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onCacnelButtonAction(ActionEvent event) {

	}

	@FXML
	void onSinginButtonAction(ActionEvent event) {

	}
}
