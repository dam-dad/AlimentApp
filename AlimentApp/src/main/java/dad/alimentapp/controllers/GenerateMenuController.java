package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import dad.alimentapp.models.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerateMenuController implements Initializable {

	@FXML
	private VBox view;

	@FXML
	private GridPane menuGrid;

	@FXML
	private Label kcalLabel;

	@FXML
	private Label hydratsLabel;

	@FXML
	private Label proteinLabel;

	@FXML
	private Label fatsLabel;

	@FXML
	private Label productsLabel;

	@FXML
	private Label fiberLabel;

	@FXML
	private ComboBox<String> menuTypeCombo;

	@FXML
	private TextField menuNameText;

	@FXML
	private RadioButton defaultRadio;

	@FXML
	private ToggleGroup choose;

	@FXML
	private RadioButton configureRadio;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private String menu1 = "MENU PROTEICO", menu2 = "MENU VEGANO", menu3 = "MENU DE PESCADO",
			menu4 = "MENU VEGETARIANO", menu5 = "MENU ESTÁNDAR";

	ObservableList<String> menuList = FXCollections.observableArrayList(menu1, menu2, menu3, menu4, menu5);

	private Product products;

	public GenerateMenuController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuTypeCombo.getItems().addAll(menuList);
		menuTypeCombo.setValue(menu1);
		menuTypeCombo.setItems(menuList);
		menuTypeCombo.setValue(menu2);
		menuTypeCombo.setItems(menuList);
		menuTypeCombo.setValue(menu3);
		menuTypeCombo.setItems(menuList);
		menuTypeCombo.setValue(menu4);
		menuTypeCombo.setItems(menuList);
		menuTypeCombo.setValue(menu5);
		menuTypeCombo.setItems(menuList);

		menuGrid.setDisable(true);

		defaultRadio.setSelected(true);

		configureRadio.selectedProperty().addListener((o, ov, nv) -> {
			if (configureRadio.isSelected()) {
				menuGrid.setDisable(false);
				menuTypeCombo.getSelectionModel().clearSelection();
			} else if (defaultRadio.isSelected()) {
				menuGrid.setDisable(true);
				menuTypeCombo.getSelectionModel().select(4);
			}
		});

		menuTypeCombo.selectionModelProperty().addListener((o, ov, nv) -> {
			showDescriptionMenus();
			sumMenus();
		});
	}

	@FXML
	void onCancelMenuButtonAction(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onSaveMenuButtonAction(ActionEvent event) {

	}

	private void showDescriptionMenus() {
		try {
			if (menuTypeCombo.getSelectionModel().getSelectedIndex() == 0) {
				productsLabel.setText("Sandwich de pavo (desayuno), Tortitas de arroz (media-mañana), \n"
						+ "Pollo a la plancha (Almuerzo), Huevos duros (merienda), \n"
						+ "Pechuga de pollo al limón (cena)");
				kcalLabel.setText("739");
				hydratsLabel.setText("53.04");
				proteinLabel.setText("72.96");
				fatsLabel.setText("19.05");
				fiberLabel.setText("4");
			} else if (menuTypeCombo.getSelectionModel().getSelectedIndex() == 1) {
				productsLabel.setText("Tostadas con jamón y yogur con frutas. ");
				kcalLabel.setText("276");
				hydratsLabel.setText("30");
				proteinLabel.setText("12.4");
				fatsLabel.setText("8");
				fiberLabel.setText("2");
			} else if (menuTypeCombo.getSelectionModel().getSelectedIndex() == 2) {
				productsLabel.setText("Tortitas de arroz y zumo de naranja. ");
				kcalLabel.setText("224");
				hydratsLabel.setText("48.7");
				proteinLabel.setText("4.3");
				fatsLabel.setText("1.5");
				fiberLabel.setText("1");
			} else if (menuTypeCombo.getSelectionModel().getSelectedIndex() == 3) {
				productsLabel.setText("Frutos secos y yogur natural. ");
				kcalLabel.setText("563");
				hydratsLabel.setText("11.04");
				proteinLabel.setText("9.25");
				fatsLabel.setText("10.55");
				fiberLabel.setText("2");
			} else if (menuTypeCombo.getSelectionModel().getSelectedIndex() == 4) {
				productsLabel.setText("Pechuga de pavo y arroz. ");
				kcalLabel.setText("308");
				hydratsLabel.setText("48.2");
				proteinLabel.setText("21.9");
				fatsLabel.setText("2");
				fiberLabel.setText("1.1");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void sumMenus() {

	}

	public VBox getView() {
		return view;
	}

}
