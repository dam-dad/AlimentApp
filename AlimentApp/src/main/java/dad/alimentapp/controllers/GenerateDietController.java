package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.DailyMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.service.DietService;
import dad.alimentapp.service.MenuService;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
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

/**
 * Esta pestaña creara una dieta por defecto, seleccionandolo en un combobox. Se
 * visualizará en una lista los productos y en unos label las cantidades de
 * kilocalorias, hidratos, proteinas, grasas y fibras
 * 
 * @author Daniel
 *
 */
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

	private ObjectProperty<Diet> diet = new SimpleObjectProperty<>();
	private ObjectProperty<Weekday> actualWeekday = new SimpleObjectProperty<>(Weekday.LUNES);
	private ListProperty<DailyMenu> dailyMenusModificate = new SimpleListProperty<>(
			FXCollections.observableArrayList());

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
		
		dietNameText.textProperty().bindBidirectional(diet.get().nameProperty());
	}

	/**
	 * Esta función hara que cierre la pestaña abierta, si no desea realizar ninguna
	 * operación en ella.
	 * 
	 * @param event
	 */
	@FXML
	void onCancelDietButtonAction(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Guardará el menú en la lista de dietas en ManageDietController
	 * 
	 * @param event
	 */
	@FXML
	void onSaveDietButtonAction(ActionEvent event) {
		this.diet.get().setName(this.diet.get().getName().trim());
		this.diet.get().setDailyMenus(dailyMenusModificate);
		this.diet.get().clear();
		if (diet.get().getId() == 0) {
			boolean isDuplicate = Utils.isMatchDietName(this.diet.get());
			if (isDuplicate) {
				Messages.error("Error al guardar la dieta", "No se pueden guardar dietas con el mismo nombre.");
			} else {
				DietService.insertDiet(diet.get());
				ManageDietController.loadDietsAndMenus();
				ChoiceController.getCreateDietCustomStage().close();
			}
		} else {
			DietService.updateDiet(diet.get());
			ManageDietController.loadDietsAndMenus();
			ManageDietController.getModificateStage().close();
		}
	}

	public VBox getView() {
		return view;
	}

}
