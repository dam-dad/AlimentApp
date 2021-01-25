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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class GenerarMenuController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private ComboBox<?> dayCombo;

	@FXML
	private Button acceptButton;

	@FXML
	private Button cancelButton;

	@FXML
	private RadioButton yesMeatRadio;

	@FXML
	private ToggleGroup meat;

	@FXML
	private RadioButton noMeatRadio;

	@FXML
	private ComboBox<?> momentCombo;

	@FXML
	private RadioButton yesVegetRadio;

	@FXML
	private ToggleGroup vegetable;

	@FXML
	private RadioButton noVegetRadio;

	public GenerarMenuController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerarMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onAcceptButtonAction(ActionEvent event) {

	}

	@FXML
	void onCancelButtonAction(ActionEvent event) {

	}
}
