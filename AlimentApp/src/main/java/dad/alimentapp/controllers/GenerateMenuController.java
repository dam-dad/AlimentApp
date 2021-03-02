package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.models.Type;
import dad.alimentapp.models.Menu;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Product;
import dad.alimentapp.service.MenuService;
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
 * Esta pestaña creara un menu por defecto, seleccionandolo en un combobox. Se
 * visualizará en una lista los productos y en unos label las cantidades de
 * kilocalorias, hidratos, proteinas, grasas y fibras
 * 
 * @author Daniel
 *
 */
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
	private Label fiberLabel;

	@FXML
	private ComboBox<String> menuTypeCombo;

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
	private TextField menuNameText;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private String menu1 = "MENU PROTEICO", menu2 = "MENU VEGANO", menu3 = "MENU FÍBRICO", menu4 = "MENU VEGETARIANO",
			menu5 = "MENU ESTÁNDAR";

	private String product1B = "LECHE CON MIEL", product2B = "TOSTADAS CON JAMON", product3B = "YOGUR CON FRUTAS",
			product4B = "TOSTADAS CON QUESO DE UNTAR", product5B = "SANDWICH PAVO",
			product6B = "REQUESÓN CON MIEL Y NUECES", product1MM = "TORTITAS DE ARROZ", product2MM = "YOGUR NATURAL",
			product3MM = "ZUMO DE NARANJA", product4MM = "FRUTOS SECOS", product1L = "ARROZ",
			product2L = "PECHUGA DE PAVO", product3L = "ENSALADA", product4L = "POLLO A LA PLANCHA", product5L = "ATUN",
			product1S = "YOGUR CON ALMENDRAS", product2S = "HUEVOS DUROS", product3S = "TOSTADAS CON AGUACATE",
			product4S = "BROCHETAS DE FRUTAS", product1D = "PECHUGA DE POLLA AL LIMON", product2D = "GUISANTES",
			product3D = "CALAMARES", product4D = "SOPA DE PESCADO", product5D = "SANDWICH DE TORTILLA FRANCESA";

	ObservableList<String> menuList = FXCollections.observableArrayList(menu1, menu2, menu3, menu4, menu5);

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

		menuTypeCombo.getSelectionModel().selectFirst();
		System.out.println(menuTypeCombo.getSelectionModel().getSelectedIndex());

		menuTypeCombo.selectionModelProperty().addListener((o, ov, nv) -> {
			listProductList();
		});
	}

	/**
	 * Esta función hara que cierre la pestaña abierta, si no desea realizar ninguna
	 * operación en ella.
	 * 
	 * @param event
	 */
	@FXML
	void onCancelMenuButtonAction(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onGenerateButtonAction(ActionEvent event) {
		
	}

	void onSaveMenuButtonAction(ActionEvent event) {
		/*Menu menuName = new Menu(menuNameText.getText());
		MenuService.insertMenu(menuName);*/
	}

	private void listProductList() {
		/*if (menuTypeCombo.getSelectionModel().isSelected(0)) {
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
		} else if (menuTypeCombo.getSelectionModel().isSelected(4)) {
			breakfastListView.getItems().add(product1B);
			breakfastListView.getItems().add(product2B);
			breakfastListView.getItems().add(product3B);
			midMorningListView.getItems().add(product1MM);
			midMorningListView.getItems().add(product2MM);
			midMorningListView.getItems().add(product3MM);
			lunchListView.getItems().add(product1L);
			lunchListView.getItems().add(product2L);
			lunchListView.getItems().add(product3L);
			snackListView.getItems().add(product1S);
			snackListView.getItems().add(product2S);
			snackListView.getItems().add(product3S);
			dinnerListView.getItems().add(product1D);
			dinnerListView.getItems().add(product2D);
			dinnerListView.getItems().add(product3D);
		}*/
	}

	public VBox getView() {
		return view;
	}

}
