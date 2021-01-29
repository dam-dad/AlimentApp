package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GenerateMenuController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private TextField nameText;

	@FXML
	private RadioButton forTypeRadio;

	@FXML
	private ToggleGroup type;

	@FXML
	private RadioButton personalizedRadio;

	@FXML
	private Button generateButton;

	@FXML
	private Button cancelButton;

	@FXML
	private VBox menuTypeVBox;

	@FXML
	private HBox menuTypeBox;

	@FXML
	private ComboBox<?> menuTypeCombo;

	@FXML
	private VBox personalizedVBox;

	@FXML
	private GridPane personalizedPane;

	@FXML
	private HBox kcalBox;

	@FXML
	private Slider kcalSlider;

	@FXML
	private Label recomend1Label;

	@FXML
	private Label recomend2Label;

	@FXML
	private Slider hydrartsSlider;

	@FXML
	private Slider proteinSlider;

	@FXML
	private Label recomend3Label;

	@FXML
	private Label recomend4Label;

	@FXML
	private Slider fatsSlider;

	@FXML
	private RadioButton eats3Radio;

	@FXML
	private ToggleGroup food;

	@FXML
	private RadioButton eats5Radio;

	@FXML
	private CheckBox vegetalCheck;

	public GenerateMenuController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateMenuView.fxml"));
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
	void onGenerateButtonAction(ActionEvent event) {

	}

	public BorderPane getView() {
		return view;
	}

}