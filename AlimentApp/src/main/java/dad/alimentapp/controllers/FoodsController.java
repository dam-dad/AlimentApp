package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class FoodsController implements Initializable {
	
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
	    private Button viewMenuButton;

	    @FXML
	    private Button removeDietsButton;

	    @FXML
	    private Button modifyDietsButton;

	    @FXML
	    private Button viewDietsButton;

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
	    private ImageView menu1ImageView;

	    @FXML
	    private ImageView menu2ImageView;

	    @FXML
	    private ImageView menu3ImageView;

	    @FXML
	    private ImageView diets1ImageView;

	    @FXML
	    private ImageView diets2ImageView;

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
	    private Label breakfast1Label;

	    @FXML
	    private Label breakfast2Label;

	    @FXML
	    private Label breakfast3Label;

	    @FXML
	    private Label midmorning1Label;

	    @FXML
	    private Label midmorning2Label;

	    @FXML
	    private Label midmorning3Label;

	    @FXML
	    private Label lunch1Label;

	    @FXML
	    private Label lunch2Label;

	    @FXML
	    private Label lunch3Label;

	    @FXML
	    private Label snack1Label;

	    @FXML
	    private Label snack2Label;

	    @FXML
	    private Label snack3Label;

	    @FXML
	    private Label dinner1Label;

	    @FXML
	    private Label dinner2Label;

	    @FXML
	    private Label dinner3Label;

	    @FXML
	    private Label bonAppetiteLabel;

	    @FXML
	    private Label copyrightLabel;
	    
		public FoodsController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FoodsView.fxml"));
			loader.setController(this);
			loader.load();
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub

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
	    
	    public HBox getView() {
	    	return view;
	    }

}
