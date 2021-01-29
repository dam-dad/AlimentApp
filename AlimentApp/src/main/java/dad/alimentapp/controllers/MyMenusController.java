package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyMenusController implements Initializable {

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
	private Button generateDietButton;

	@FXML
	private Button saveDietButton;

	@FXML
	private Button generateMenuButton;

	@FXML
	private Button generateDietButton;

	GenerateMenuController menuController;
	GenerateDietController dietController = new GenerateDietController();
	private Button saveMenuButton;

	// CONTROLLERS
	private ProductController productController;

	public MyMenusController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MyMenus.fxml"));
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
	
	private void newSceneProduct() {
		try {
			productController = new ProductController();

			Stage secondaryStage = new Stage();
			Scene escena = new Scene(productController.getView());

			secondaryStage.setScene(escena);
			secondaryStage.setTitle("AlimentApp");
			// secondaryStage.getIcons().add(new Image("/images/"));
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.initOwner(App.getPrimaryStage());
			secondaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void onBreakfastButtonAction(ActionEvent event) {
		newSceneProduct();
	}

	@FXML
	void onDinnerButtonAction(ActionEvent event) {
		newSceneProduct();
	}

	@FXML
	void onGenerateDietButtonAction(ActionEvent event) throws IOException {
		newSceneGenerateDiet();
	}

	@FXML
	void onGenerateMenuButtonAction(ActionEvent event) throws IOException {
		newSceneGenerateMenu();
	}

	@FXML
	void onLaunchButtonAction(ActionEvent event) {
		newSceneProduct();
	}

	@FXML
	void onMidMorningButtonAction(ActionEvent event) {
		newSceneProduct();
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


	private void newSceneGenerateMenu() {
		try {
			menuController = new GenerateMenuController();

			Stage secondaryStage = new Stage();
			Scene escena = new Scene(menuController.getView());

			secondaryStage.setScene(escena);
			secondaryStage.setTitle("Generar Menu");
			// secondaryStage.getIcons().add(new Image("/images/"));
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.initOwner(App.getPrimaryStage());
			secondaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void newSceneGenerateDiet() {
		try {
			dietController = new GenerateDietController();

			Stage secondaryStage = new Stage();
			Scene escena = new Scene(dietController.getView());

			secondaryStage.setScene(escena);
			secondaryStage.setTitle("Generar Dieta");
			// secondaryStage.getIcons().add(new Image("/images/"));
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.initOwner(App.getPrimaryStage());
			secondaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	@FXML
	void onSnackButtonAction(ActionEvent event) {
		newSceneProduct();
	}

	public HBox getView() {
		return view;
	}

}
