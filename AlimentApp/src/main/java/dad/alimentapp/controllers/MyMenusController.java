package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyMenusController implements Initializable {

	// View

	@FXML
	private HBox view;

	@FXML
	private Label productNameLabel;

	@FXML
	private ImageView productImageView;

	@FXML
	private Label kcalLabel;

	@FXML
	private Label proteinsLabel;

	@FXML
	private Label hydratesLabel;

	@FXML
	private Label fatsLabel;

	@FXML
	private Label descriptionLabel;

	@FXML
	private ComboBox<?> mealsComboBox;

	@FXML
	private ListView<?> productsList;

	@FXML
	private Button addButton;

	@FXML
	private Button removeButton;

	@FXML
	private Button backButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label dayLabel;

	@FXML
	private Label kcalTotLabel;

	@FXML
	private Label proteinsTotLabel;

	@FXML
	private Label hydratesTotLabel;

	@FXML
	private Label fatsTotLabel;

	@FXML
	private Label productDLabel;

	@FXML
	private Label productD2Label;

	@FXML
	private Label productD3Label;

	@FXML
	private Label productMaLabel;

	@FXML
	private Label productMa2Label;

	@FXML
	private Label productMa3Label;

	@FXML
	private Label productALabel;

	@FXML
	private Label productA2Label;

	@FXML
	private Label productA3Label;

	@FXML
	private Label productMeLabel;

	@FXML
	private Label productMe2Label;

	@FXML
	private Label productMe3Label;

	@FXML
	private Label productCLabel;

	@FXML
	private Label productC2Label;

	@FXML
	private Label productC3Label;

	@FXML
	private Button saveMenuButton;

	@FXML
	private Button saveDietButton;

	@FXML
	private Button generateMenuButton;

	@FXML
	private Button generateDietButton;

	GenerateMenuController menuController;
	GenerateDietController dietController = new GenerateDietController();

	public MyMenusController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MyMenus.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void onAddButtonAction(ActionEvent event) {

	}

	@FXML
	void onBackButtonAction(ActionEvent event) {

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
	void onNextButtonAction(ActionEvent event) {

	}

	@FXML
	void onRemoveButtonAction(ActionEvent event) {

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
	}

	public HBox getView() {
		return view;
	}

}
