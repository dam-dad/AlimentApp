package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class DataController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private Button editarButton;

	@FXML
	private Button guardarButton;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField apellidosText;

	@FXML
	private TextField edadText;

	@FXML
	private RadioButton hombreRButton;

	@FXML
	private RadioButton mujerRButton;

	@FXML
	private Button cambiarButton;

	@FXML
	private TextField pesoText;

	@FXML
	private TextField alturaText;

	@FXML
	private Label resultLabel;

	public DataController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DataView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public BorderPane getView() {
		return view;
	}

}
