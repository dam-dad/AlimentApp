package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
	void onGenerateDietButtonAction(ActionEvent event) {

	}

	@FXML
	void onGenerateMenuButtonAction(ActionEvent event) {

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

	public HBox getView() {
		return view;
	}

}
