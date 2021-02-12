package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.ControlDietMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class ManageDietController implements Initializable {
	
	@FXML
    private HBox view;

    @FXML
    private GridPane menusDietsPane;

    @FXML
    private Label dietsLabel;

    @FXML
    private Label menuLabel;

    @FXML
    private Button modifyMenuButton;

    @FXML
    private Button removeMenuButton;

    @FXML
    private Button createMenuButton;

    @FXML
    private Button viewMenuButton;

    @FXML
    private Button removeDietsButton;

    @FXML
    private Button modifyDietsButton;

    @FXML
    private Button viewDietsButton;

    @FXML
    private Button createDietsButton;

    @FXML
    private Label menuName1Label;

    @FXML
    private Label menuName2Label;

    @FXML
    private Label menuName3Label;

    @FXML
    private Label dietsName1Label;

    @FXML
    private Label dietsName2Label;

    @FXML
    private Label dietsName3Label;

    @FXML
    private Button menu1ImageButton;

    @FXML
    private ImageView menu1ImageView;

    @FXML
    private Button menu2ImageButton;

    @FXML
    private ImageView menu2ImageView;

    @FXML
    private Button menu3ImageButton;

    @FXML
    private ImageView menu3ImageView;

    @FXML
    private Button diets1ImageButton;

    @FXML
    private ImageView diets1ImageView;

    @FXML
    private Button diets2ImageButton;

    @FXML
    private ImageView diets2ImageView;

    @FXML
    private Button diets3ImageButton;

    @FXML
    private ImageView diets3ImageView;

    @FXML
    private GridPane viewMenuDietsPane;

    @FXML
    private Label menuDietsNameLabel;

    @FXML
    private Label breakfastLabel;

    @FXML
    private Label midmorningLabel;

    @FXML
    private Label lunchLabel;

    @FXML
    private Label snackLabel;

    @FXML
    private Label dinnerLabel;

    @FXML
    private Label bonAppetiteLabel;

    @FXML
    private Label copyrightLabel;

    @FXML
    private ListView<String> breakfastListView;

    @FXML
    private ListView<String> midMorningListView;

    @FXML
    private ListView<String> lunchListView;

    @FXML
    private ListView<String> snackListView;

    @FXML
    private ListView<String> dinnerListView;
    
    //CONTROLLERS
    ChoiceController choiceController;
    
    //VARIABLE
	private static Stage choiceStage;
	    
		public ManageDietController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageDietView.fxml"));
			loader.setController(this);
			loader.load();
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub

		}

		@FXML
	    void onCreateDietsButtonAction(ActionEvent event) {
			try {
				choiceController = new ChoiceController(ControlDietMenu.Dieta);
				this.createChoiceStage();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @FXML
	    void onCreateMenuButtonAction(ActionEvent event) {
	    	try {
				choiceController = new ChoiceController(ControlDietMenu.Menú);
				this.createChoiceStage();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @FXML
	    void onDiets1ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onDiets2ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onDiets3ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onMenu1ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onMenu2ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onMenu3ImageButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onModifyDietsButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onModifyMenuButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onRemoveDietsButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onRemoveMenuButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onViewDietsButtonAction(ActionEvent event) {

	    }

	    @FXML
	    void onViewMenuButtonAction(ActionEvent event) {

	    }
	    
	    private void createChoiceStage() {
	    	choiceStage = new Stage();
			Scene scene = new Scene(choiceController.getView());
			
			choiceStage.setScene(scene);
			choiceStage.setTitle("Elección");
			choiceStage.resizableProperty().setValue(Boolean.FALSE);
			choiceStage.getIcons().add(new Image("/images/logo.png"));
			choiceStage.initModality(Modality.WINDOW_MODAL);
			choiceStage.initOwner(App.getPrimaryStage());
			choiceStage.showAndWait();
	    }
	    
	    public static Stage getChoiceStage() {
			return choiceStage;
		}
	    
	    public HBox getView() {
	    	return view;
	    }
	    
}
