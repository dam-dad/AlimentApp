package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProductController implements Initializable {

	// VIEW
	@FXML
	private HBox view;

	@FXML
	private Label productNameLabel;

	@FXML
	private ImageView productImageLabel;

	@FXML
	private Label kcalProductLabel;

	@FXML
	private Label hydratesProductLabel;

	@FXML
	private Label fatsProductLabel;

	@FXML
	private Label proteinProductLabel;

	@FXML
	private Label fibresProductLabel;

	@FXML
	private Label originProductLabel;

	@FXML
	private Label typeProductLabel;

	@FXML
	private ListView<?> productList;

	@FXML
	private Button addButton;

	@FXML
	private Button removeButton;

	@FXML
	private Label momentDayLabel;

	@FXML
	private ListView<?> selectedProductList;

	@FXML
	private Button saveProductButton;

	public ProductController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Products.fxml"));
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
	void onRemoveButtonAction(ActionEvent event) {

	}

	@FXML
	void onSaveProductButtonAction(ActionEvent event) {

	}

	public HBox getView() {
		return view;
	}
}
