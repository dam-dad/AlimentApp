package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerateDietController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private GridPane dietGrid;

	@FXML
	private Label kcalLabel;

	@FXML
	private Label hydratsLabel;

	@FXML
	private Label proteinLabel;

	@FXML
	private Label fatsLabel;

	@FXML
	private Label fiberLabel;

	@FXML
	private ComboBox<String> dietTypeCombo;

	@FXML
	private ListView<Product> breakfastListView;

	@FXML
	private ListView<Product> midMorningListView;

	@FXML
	private ListView<Product> lunchListView;

	@FXML
	private ListView<Product> snackListView;

	@FXML
	private ListView<Product> dinnerListView;

	@FXML
	private TextField dietNameText;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private String diet1 = "DIETA HIPERCALÓRICA", diet2 = "DIETA HIPOCALÓRICA", diet3 = "DIETA HIPERPROTEICA",
			diet4 = "DIETA VEGANA", diet5 = "DIETA ESTÁNDAR";

	ObservableList<String> dietsList = FXCollections.observableArrayList(diet1, diet2, diet3, diet4, diet5);

	public GenerateDietController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dietTypeCombo.setValue(diet1);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(diet2);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(diet3);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(diet4);
		dietTypeCombo.setItems(dietsList);
		dietTypeCombo.setValue(diet5);
		dietTypeCombo.setItems(dietsList);
	}

	@FXML
	void onCancelDietButtonAction(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onSaveDietButtonAction(ActionEvent event) {

	}

	public VBox getView() {
		return view;
	}

}
