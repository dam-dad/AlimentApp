package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import dad.alimentapp.models.DietsMenu;
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

	private GenerateMenuController menuController;
	private GenerateDietController dietController = new GenerateDietController();

	// VARIABLE
	private ControlDietMenu controlDietMenu;
	private static final String TITLE = "Crear ";
		
	//MODEL
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
		//BINDINGS
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
			createDietController = new CreateDietController(new DietsMenu(), this.controlDietMenu);

			Stage createDietStage = new Stage();
			createDietStage.setMinWidth(800);
			createDietStage.setMinHeight(500);
			Scene scene = new Scene(createDietController.getView());
			
			createDietStage.setScene(scene);
			createDietStage.setTitle(controlDietMenu.name());
			createDietStage.getIcons().add(new Image("/images/logo.png"));
			createDietStage.initModality(Modality.WINDOW_MODAL);
			createDietStage.initOwner(App.getPrimaryStage());
			createDietStage.show();
			ManageDietController.getChoiceStage().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void controlChoiceLabel() {
		title.set(TITLE + this.controlDietMenu);
	}
	public VBox getView() {
		return view;
	}

}
