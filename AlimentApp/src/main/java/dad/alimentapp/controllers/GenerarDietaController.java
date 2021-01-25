package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class GenerarDietaController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private ComboBox<?> typeDietCombo;

	@FXML
	private Button acceptButton;

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<?> dayCombo;

	public GenerarDietaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerarDietaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onCancelButtonAction(ActionEvent event) {

	}

	@FXML
	void onAcceptButtonAction(ActionEvent event) {

	}
}
