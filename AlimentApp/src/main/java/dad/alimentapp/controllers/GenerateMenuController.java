package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;

public class GenerateMenuController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private TextField nameText;

	@FXML
	private Button generateButton;

	@FXML
	private Button cancelButton;

	@FXML
	private RadioButton defaultRadio;

	@FXML
	private ToggleGroup configure;

	@FXML
	private RadioButton configureRadio;

	@FXML
	private VBox menuTypeVBox;

	@FXML
	private HBox menuTypeBox;

	@FXML
	private ComboBox<Type> productTypeCombo;

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
	private Label numberCalLabel;

	@FXML
	private Slider hydrartsSlider;

	@FXML
	private Label numberHydrLabel;

	@FXML
	private Slider proteinSlider;

	@FXML
	private Label recomend3Label;

	@FXML
	private Label numberProtLabel;

	@FXML
	private Label numberFatsLabel;

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

	public GenerateMenuController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Type> productList = FXCollections.observableArrayList();
		for (int i = 0; i <= 0; i++) {
			productList.addAll(Type.values());
		}
		productTypeCombo.getItems().addAll(productList);

		kcalSlider.setMax(3000);
		kcalSlider.setMin(0);
		kcalSlider.setValue(2000);
		numberCalLabel.setText(String.valueOf(kcalSlider.getValue()));

		kcalSlider.valueProperty().addListener((o, ov, nv) -> {
			numberCalLabel.setText(String.format("%.0f", nv));
		});

		hydrartsSlider.setMax(200);
		hydrartsSlider.setMin(0);
		hydrartsSlider.setValue(150);
		numberHydrLabel.setText(String.valueOf(hydrartsSlider.getValue()));

		hydrartsSlider.valueProperty().addListener((o, ov, nv) -> {
			numberHydrLabel.setText(String.format("%.0f", nv));
		});

		proteinSlider.setMax(100);
		proteinSlider.setMin(0);
		proteinSlider.setValue(40);
		numberProtLabel.setText(String.valueOf(proteinSlider.getValue()));

		proteinSlider.valueProperty().addListener((o, ov, nv) -> {
			numberProtLabel.setText(String.format("%.0f", nv));
		});

		fatsSlider.setMax(100);
		fatsSlider.setMin(0);
		fatsSlider.setValue(55);
		numberFatsLabel.setText(String.valueOf(fatsSlider.getValue()));

		fatsSlider.valueProperty().addListener((o, ov, nv) -> {
			numberFatsLabel.setText(String.format("%.0f", nv));
		});

		menuTypeBox.setDisable(true);
		personalizedPane.setDisable(true);
		
		productTypeCombo.getSelectionModel().select(4);

		defaultRadio.setSelected(true);

		eats3Radio.setSelected(true);

		configureRadio.selectedProperty().addListener((o, ov, nv) -> {
			if (configureRadio.isSelected()) {
				menuTypeBox.setDisable(false);
				personalizedPane.setDisable(false);
				productTypeCombo.getSelectionModel().selectFirst();
				kcalSlider.setValue(2000);
				hydrartsSlider.setValue(150);
				proteinSlider.setValue(40);
				fatsSlider.setValue(55);
				eats3Radio.setSelected(false);
				productTypeCombo.setPromptText("Selecciona el tipo");
			} else if (defaultRadio.isSelected()) {
				menuTypeBox.setDisable(true);
				personalizedPane.setDisable(true);
				productTypeCombo.getSelectionModel().select(4);
				kcalSlider.setValue(2000);
				hydrartsSlider.setValue(150);
				proteinSlider.setValue(40);
				fatsSlider.setValue(55);
				eats3Radio.setSelected(true);
			}
		});
	}

	@FXML
	void onCancelButtonAction(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onGenerateButtonAction(ActionEvent event) {

	}

	public BorderPane getView() {
		return view;
	}

}
