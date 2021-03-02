package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dad.alimentapp.models.DailyMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.service.DietService;
import dad.alimentapp.service.MenuService;
import dad.alimentapp.service.ProductService;
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

	private String diet1 = "DIETA HIPERCALÓRICA", diet2 = "DIETA HIPOCALÓRICA";

	ObservableList<String> dietsList = FXCollections.observableArrayList(diet1, diet2);

	private Product product1B = ProductService.getProduct(3), product2B = ProductService.getProduct(4),
			product3B = ProductService.getProduct(7), product4B = ProductService.getProduct(10),
			product5B = ProductService.getProduct(8), product6B = ProductService.getProduct(11),
			product7B = ProductService.getProduct(3), product8B = ProductService.getProduct(4),
			product9B = ProductService.getProduct(9), product1MM = ProductService.getProduct(13),
			product2MM = ProductService.getProduct(17), product3MM = ProductService.getProduct(19),
			product4MM = ProductService.getProduct(12), product5MM = ProductService.getProduct(14),
			product1L = ProductService.getProduct(28), product2L = ProductService.getProduct(27),
			product3L = ProductService.getProduct(21), product4L = ProductService.getProduct(26),
			product5L = ProductService.getProduct(22), product6L = ProductService.getProduct(50),
			product1S = ProductService.getProduct(29), product2S = ProductService.getProduct(30),
			product3S = ProductService.getProduct(32), product4S = ProductService.getProduct(31),
			product1D = ProductService.getProduct(39), product2D = ProductService.getProduct(37),
			product3D = ProductService.getProduct(41), product4D = ProductService.getProduct(35),
			product5D = ProductService.getProduct(38), product6D = ProductService.getProduct(46);

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

		breakfastListView.getItems().clear();
		midMorningListView.getItems().clear();
		lunchListView.getItems().clear();
		snackListView.getItems().clear();
		dinnerListView.getItems().clear();
		breakfastListView.getItems().add(product4B);
		breakfastListView.getItems().add(product5B);
		breakfastListView.getItems().add(product6B);
		midMorningListView.getItems().add(product1MM);
		midMorningListView.getItems().add(product2MM);
		midMorningListView.getItems().add(product4MM);
		lunchListView.getItems().add(product5L);
		lunchListView.getItems().add(product2L);
		lunchListView.getItems().add(product4L);
		snackListView.getItems().add(product1S);
		snackListView.getItems().add(product2S);
		snackListView.getItems().add(product3S);
		dinnerListView.getItems().add(product1D);
		dinnerListView.getItems().add(product4D);
		dinnerListView.getItems().add(product5D);
		kcalLabel.setText("2801");
		hydratsLabel.setText("157,11");
		proteinLabel.setText("201.27");
		fatsLabel.setText("181.62");
		fiberLabel.setText("19.4");
		
		dietTypeCombo.getSelectionModel().selectFirst();

		dietTypeCombo.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> {
			listProductShow();
		});
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
		Diet dietName = new Diet();
		dietName.setName(dietNameText.getText());

		DietService.insertDiet(dietName);
		Messages.info("Menú generado", "Se ha generado el menú correctamente.");
		ManageDietController.loadDietsAndMenus();
		Stage stage = (Stage) this.saveButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * La función mostrará listas de productos, según lo que escogas en el combobox
	 */
	private void listProductShow() {
		if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 0) {
			breakfastListView.getItems().clear();
			midMorningListView.getItems().clear();
			lunchListView.getItems().clear();
			snackListView.getItems().clear();
			dinnerListView.getItems().clear();
			breakfastListView.getItems().add(product4B);
			breakfastListView.getItems().add(product5B);
			breakfastListView.getItems().add(product6B);
			midMorningListView.getItems().add(product1MM);
			midMorningListView.getItems().add(product2MM);
			midMorningListView.getItems().add(product4MM);
			lunchListView.getItems().add(product5L);
			lunchListView.getItems().add(product2L);
			lunchListView.getItems().add(product4L);
			snackListView.getItems().add(product1S);
			snackListView.getItems().add(product2S);
			snackListView.getItems().add(product3S);
			dinnerListView.getItems().add(product1D);
			dinnerListView.getItems().add(product4D);
			dinnerListView.getItems().add(product5D);
			kcalLabel.setText("2801");
			hydratsLabel.setText("157,11");
			proteinLabel.setText("201.27");
			fatsLabel.setText("181.62");
			fiberLabel.setText("19.4");
		} else if (dietTypeCombo.getSelectionModel().getSelectedIndex() == 1) {
			breakfastListView.getItems().clear();
			midMorningListView.getItems().clear();
			lunchListView.getItems().clear();
			snackListView.getItems().clear();
			dinnerListView.getItems().clear();
			breakfastListView.getItems().add(product1B);
			breakfastListView.getItems().add(product2B);
			midMorningListView.getItems().add(product5MM);
			lunchListView.getItems().add(product1L);
			lunchListView.getItems().add(product2L);
			snackListView.getItems().add(product4S);
			dinnerListView.getItems().add(product3D);
			dinnerListView.getItems().add(product2D);
			kcalLabel.setText("590");
			hydratsLabel.setText("87.23");
			proteinLabel.setText("46.52");
			fatsLabel.setText("6.92");
			fiberLabel.setText("15.9");
		}
	}

	/**
	 * Devuelve la vista de la pestaña
	 * 
	 * @return
	 */
	public VBox getView() {
		return view;
	}

}
