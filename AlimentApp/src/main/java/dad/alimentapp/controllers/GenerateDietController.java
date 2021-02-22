package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import dad.alimentapp.models.Weekday;
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

public class GenerateDietController implements Initializable {

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
	private VBox dietTypeVBox;

	@FXML
	private HBox dietTypeBox;

	@FXML
	private ComboBox<String> dietTypeCombo;

	@FXML
	private Label descriptionLabel;

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
	private CheckComboBox<Weekday> daysCheckCombo;

	private String item1 = "DIETA HIPERCALÓRICA", item2 = "DIETA HIPOCALÓRICA", item3 = "DIETA HIPERPROTEICA",
			item4 = "DIETA VEGANA", item5 = "DIETA ESTÁNDAR";

	ObservableList<String> dietsList = FXCollections.observableArrayList(item1, item2, item3, item4, item5);

	public GenerateDietController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dietTypeCombo.setValue(item1);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(item2);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(item3);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(item4);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(item5);
		dietTypeCombo.setItems(dietsList);

		ObservableList<Weekday> dayList = FXCollections.observableArrayList();
		for (int i = 0; i <= 0; i++) {
			dayList.addAll(Weekday.values());
		}
		daysCheckCombo.getItems().addAll(dayList);
		daysCheckCombo.getCheckModel().check(0);
		daysCheckCombo.getCheckModel().check(2);
		daysCheckCombo.getCheckModel().check(4);
		daysCheckCombo.getCheckModel().check(6);

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

		dietTypeBox.setDisable(true);
		personalizedPane.setDisable(true);

		defaultRadio.setSelected(true);

		descriptionLabel.setText("Una dieta equilibrada, basada en los valores orientativos de consumo \n"
				+ "de nutrientes para cada persona.");

		configureRadio.selectedProperty().addListener((o, ov, nv) -> {
			if (configureRadio.isSelected()) {
				dietTypeBox.setDisable(false);
				personalizedPane.setDisable(false);
				dietTypeCombo.getSelectionModel().clearSelection();
				kcalSlider.setValue(0);
				hydrartsSlider.setValue(0);
				proteinSlider.setValue(0);
				fatsSlider.setValue(0);
				descriptionLabel.setText(" ");
				daysCheckCombo.getCheckModel().clearChecks();
			} else if (defaultRadio.isSelected()) {
				dietTypeBox.setDisable(true);
				personalizedPane.setDisable(true);
				dietTypeCombo.getSelectionModel().select(4);
				kcalSlider.setValue(2000);
				hydrartsSlider.setValue(150);
				proteinSlider.setValue(40);
				fatsSlider.setValue(55);
				daysCheckCombo.getCheckModel().check(0);
				daysCheckCombo.getCheckModel().check(2);
				daysCheckCombo.getCheckModel().check(4);
				daysCheckCombo.getCheckModel().check(6);
				descriptionLabel.setText("Una dieta equilibrada, basada en los valores orientativos de consumo \n"
						+ "de nutrientes para cada persona.");
			}
		});

		dietTypeCombo.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> {
			showDescription();
		});

		dietTypeCombo.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> {
			recommendsLabel();
		});
	}

	private void showDescription() {
		try {
			if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 0) {
				descriptionLabel.setText("Dieta destinada a la ganancia de peso, para  personas cuyo IMC \n"
						+ "es inferior a 18 o simplemente para personas que se ven demasiado \n"
						+ "delgadas y quisieran subir" + "de peso de manera equilibrada y \ncomiendo sano");
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 1) {
				descriptionLabel.setText("Dieta destinada a la pérdida de peso , recomendada para personas cuyo \n"
						+ "IMC es superior a 25, consistente en una reducción en el número de \n" + "kcal a consumir");
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 2) {
				descriptionLabel.setText("Dieta destinada al aumento de la masa muscular a través de un mayor \n"
						+ "consumo de proteínas y una reducción en la ingesta de hidratos de carbono.\n"
						+ "Ideal para personas que realizan actividad física intensa y para personas que \n"
						+ "quieran aumentar su masa muscular.");
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 3) {
				descriptionLabel.setText("Dieta destinada a personas que hayan renunciado a tomar todo tipo \n"
						+ "de alimentos de origen animal, por lo tanto, los alimentos de esta dieta deben \n"
						+ "ser todos de origen vegetal, buscando un equilibrio que logre aportar todos los \n"
						+ "nutrientes necesarios para el día a día.");
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 4) {
				descriptionLabel.setText("Una dieta equilibrada, basada en los valores orientativos de consumo \n"
						+ "de nutrientes para cada persona.");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void recommendsLabel() {
		try {
			if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 0) {
				recomend1Label.setText("2300");
				recomend3Label.setText("80");
				recomend2Label.setText("185");
				recomend4Label.setText("65");
				kcalSlider.setValue(2300);
				hydrartsSlider.setValue(185);
				proteinSlider.setValue(80);
				fatsSlider.setValue(65);
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 1) {
				recomend1Label.setText("1800");
				recomend3Label.setText("30");
				recomend2Label.setText("120");
				recomend4Label.setText("40");
				kcalSlider.setValue(1800);
				hydrartsSlider.setValue(120);
				proteinSlider.setValue(30);
				fatsSlider.setValue(40);
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 2) {
				recomend1Label.setText("1900");
				recomend3Label.setText("90");
				recomend2Label.setText("140");
				recomend4Label.setText("52");
				kcalSlider.setValue(1900);
				hydrartsSlider.setValue(140);
				proteinSlider.setValue(90);
				fatsSlider.setValue(52);
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 3) {
				recomend1Label.setText("2350");
				recomend3Label.setText("19");
				recomend2Label.setText("137");
				recomend4Label.setText("40");
				kcalSlider.setValue(2350);
				hydrartsSlider.setValue(137);
				proteinSlider.setValue(19);
				fatsSlider.setValue(40);
			} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 4) {
				recomend1Label.setText("2000");
				recomend3Label.setText("40");
				recomend2Label.setText("150");
				recomend4Label.setText("55");
				kcalSlider.setValue(2000);
				hydrartsSlider.setValue(150);
				proteinSlider.setValue(40);
				fatsSlider.setValue(55);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
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
