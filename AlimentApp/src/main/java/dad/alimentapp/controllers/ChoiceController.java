package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.app.Diet;
import dad.alimentapp.models.app.Menu;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoiceController implements Initializable {

	// VIEW
	@FXML
	private VBox view;

	@FXML
	private Label titleChoiceLabel;

	@FXML
	private Button defaultButton;

	@FXML
	private Button personalizedButton;

	// CONTROLLERS
	private CreateDietController createDietController;
	private CreateMenuController createMenuController;

	private GenerateMenuController menuController;
	private GenerateDietController dietController;

	// VARIABLE
	private static Stage createCustomStage; 
	private ControlDietMenu controlDietMenu;
	private static final String TITLE = "Crear ";

	// MODEL
	private StringProperty title = new SimpleStringProperty();

	public ChoiceController(ControlDietMenu controlDietMenu) throws IOException {
		this.controlDietMenu = controlDietMenu;
		this.controlChoiceLabel();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChoiceView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		titleChoiceLabel.textProperty().bindBidirectional(title);
	}

	@FXML
	void onDefaultButtonAction(ActionEvent event) {
		try {
			if (controlDietMenu.equals(ControlDietMenu.Dieta)) {

				dietController = new GenerateDietController();

				Stage secondaryStage = new Stage();
				Scene escena = new Scene(dietController.getView());

				secondaryStage.setScene(escena);
				secondaryStage.setTitle("Generar Dieta");
				secondaryStage.getIcons().add(new Image("/images/logo.png"));
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				secondaryStage.initOwner(App.getPrimaryStage());
				secondaryStage.show();

			} else {
				menuController = new GenerateMenuController();

				Stage secondaryStage = new Stage();
				Scene escena = new Scene(menuController.getView());

				secondaryStage.setScene(escena);
				secondaryStage.setTitle("Generar Menu");
				secondaryStage.getIcons().add(new Image("/images/logo.png"));
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				secondaryStage.initOwner(App.getPrimaryStage());
				secondaryStage.show();
			}
			ManageDietController.getChoiceStage().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onPersonalizedButtonAction(ActionEvent event) {
		try {
			if (controlDietMenu.equals(ControlDietMenu.Dieta)) {
				createDietController = new CreateDietController(new Diet());
				createStage(createDietController.getView());
			} else {
				createMenuController = new CreateMenuController(new Menu());
				createStage(createMenuController.getView());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Stage getCreateDietCustomStage() {
		return createCustomStage;
	}
	
	public static void setCreateDietCustomStage(Stage stage) {
		createCustomStage = stage;
	}

	public void controlChoiceLabel() {
		title.set(TITLE + this.controlDietMenu);
	}

	private void createStage(HBox view) {
		ManageDietController.setModificateStage(null);
		createCustomStage = new Stage();
		Scene scene = new Scene(view);

		createCustomStage.setScene(scene);
		createCustomStage.setTitle(controlDietMenu.name());
		createCustomStage.getIcons().add(new Image("/images/logo.png"));
		createCustomStage.initModality(Modality.WINDOW_MODAL);
		createCustomStage.initOwner(App.getPrimaryStage());
		createCustomStage.show();
		ManageDietController.getChoiceStage().close();
	}

	public VBox getView() {
		return view;
	}

}
