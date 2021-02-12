package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.DietsMenu;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.MenuProduct;
import dad.alimentapp.models.MomentDay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateDietController implements Initializable {

	// VIEW

	@FXML
	private HBox view;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label weekdayLabel;

	@FXML
	private Button breakfastButton;

	@FXML
	private Button midMorningButton;

	@FXML
	private Button lunchButton;

	@FXML
	private Button snackButton;

	@FXML
	private Button dinnerButton;

	@FXML
	private Label kcalTotLabel;

	@FXML
	private Label proteinTotLabel;

	@FXML
	private Label hydratesTotLabel;

	@FXML
	private Label fatsTotLabel;

	@FXML
	private Label fibresTotLabel;

	@FXML
	private PieChart menuChart;

	@FXML
	private Button saveDietButton;

	@FXML
	private Button saveMenuButton;

	// CONTROLLERS
	private ProductController productController;

	// MODEL
	private DietsMenu dietsMenu;
	private Menu menuSelected = Menu.getMenu(1);

	// VARIABLE
	private ControlDietMenu controlDietMenu;

	public CreateDietController(DietsMenu dietsMenu, ControlDietMenu controlDietMenu) throws IOException {
		this.dietsMenu = dietsMenu;
		this.controlDietMenu = controlDietMenu;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateDietView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Ejemplo provicional
		PieChart.Data queso1 = new PieChart.Data("kcal", 40);
		PieChart.Data queso2 = new PieChart.Data("Proteinas", 20);
		PieChart.Data queso3 = new PieChart.Data("Hidratos", 10);
		PieChart.Data queso4 = new PieChart.Data("Grasas", 10);
		PieChart.Data queso5 = new PieChart.Data("Fibra", 20);

		menuChart.getData().add(queso1);
		menuChart.getData().add(queso2);
		menuChart.getData().add(queso3);
		menuChart.getData().add(queso4);
		menuChart.getData().add(queso5);

		menuChart.setLabelsVisible(true);
		menuChart.setLabelLineLength(20);
		menuChart.setLegendSide(Side.LEFT);
	}

	private void newSceneProduct(MenuProduct menuProduct) {
		try {
			productController = new ProductController(menuProduct);

			Stage productStage = new Stage();
			Scene scene = new Scene(productController.getView());
			productStage.setMinWidth(750);
			productStage.setMinHeight(450);
			productStage.setScene(scene);
			productStage.setTitle("Productos");
			productStage.getIcons().add(new Image("/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(App.getPrimaryStage());
			productStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onBreakfastButtonAction(ActionEvent event) {
		newSceneProduct(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected, MomentDay.DESAYUNO));
	}

	@FXML
	void onDinnerButtonAction(ActionEvent event) {
		newSceneProduct(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected, MomentDay.CENA));
	}

	@FXML
	void onLaunchButtonAction(ActionEvent event) {
		newSceneProduct(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected, MomentDay.ALMUERZO));
	}

	@FXML
	void onMidMorningButtonAction(ActionEvent event) {
		newSceneProduct(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected, MomentDay.MEDIA_MAÑANA));
	}

	@FXML
	void onNextButtonAction(ActionEvent event) {

	}

	@FXML
	void onPreviousButtonAction(ActionEvent event) {

	}

	@FXML
	void onSaveDietButtonAction(ActionEvent event) {

	}

	@FXML
	void onSaveMenuButtonAction(ActionEvent event) {

	}

	@FXML
	void onSnackButtonAction(ActionEvent event) {
		newSceneProduct(MenuProduct.getAllProductsToMenuOfMomentDay(menuSelected, MomentDay.MERIENDA));
	}

	public HBox getView() {
		return view;
	}

}
