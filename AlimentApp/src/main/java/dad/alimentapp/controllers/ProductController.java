package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dad.alimentapp.models.MenuProduct;
import dad.alimentapp.models.Product;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class ProductController implements Initializable {

	// VIEW
	@FXML
	private HBox view;

	@FXML
	private Label productNameLabel;

	@FXML
	private ImageView productImageView;

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
	private ListView<Product> productList;

	@FXML
	private Button addButton;

	@FXML
	private Button removeButton;

	@FXML
	private Label momentDayLabel;

	@FXML
	private ListView<Product> selectedProductList;

	// MODEL
	private MenuProduct menuProduct;
	private ListProperty<Product> defaultProductList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Product> productSelected = new SimpleObjectProperty<>();
	private ObjectProperty<Product> productMenuSelected = new SimpleObjectProperty<>();

	public ProductController(MenuProduct menuProduct) throws IOException {
		this.menuProduct = menuProduct;
		filterProduct();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Products.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		selectedProductList.itemsProperty().bindBidirectional(menuProduct.productProperty());

		productSelected.bind(productList.getSelectionModel().selectedItemProperty());
		productList.itemsProperty().bindBidirectional(defaultProductList);

		removeButton.disableProperty().bind(selectedProductList.getSelectionModel().selectedItemProperty().isNull());
		addButton.disableProperty().bind(productList.getSelectionModel().selectedItemProperty().isNull());

		selectedProductList.itemsProperty().bindBidirectional(menuProduct.productProperty());
		productMenuSelected.bind(selectedProductList.getSelectionModel().selectedItemProperty());

		productSelected.addListener((o, ov, nv) -> {
			if (ov != null) {
				kcalProductLabel.textProperty().unbindBidirectional(ov.kcalProperty());
				hydratesProductLabel.textProperty().unbindBidirectional(ov.hydratesProperty());
				fatsProductLabel.textProperty().unbindBidirectional(ov.fatsProperty());
				proteinProductLabel.textProperty().unbindBidirectional(ov.proteinProperty());
				fibresProductLabel.textProperty().unbindBidirectional(ov.fibresProperty());
				originProductLabel.textProperty().unbindBidirectional(ov.originProperty().asString());
				typeProductLabel.textProperty().unbindBidirectional(ov.typeProperty().asString());
				productImageView.imageProperty().unbindBidirectional(ov.imageProperty());
				productNameLabel.textProperty().unbindBidirectional(ov.nameProperty());
			}
			
			if (nv == null) {
				nv = Product.productDefault();
			}

			if (nv != null) {
				kcalProductLabel.textProperty().bindBidirectional(nv.kcalProperty(), new NumberStringConverter());
				hydratesProductLabel.textProperty().bindBidirectional(nv.hydratesProperty(),
						new NumberStringConverter());
				fatsProductLabel.textProperty().bindBidirectional(nv.fatsProperty(), new NumberStringConverter());
				proteinProductLabel.textProperty().bindBidirectional(nv.proteinProperty(), new NumberStringConverter());
				fibresProductLabel.textProperty().bindBidirectional(nv.fibresProperty(), new NumberStringConverter());
				originProductLabel.textProperty().bind(nv.originProperty().asString());
				typeProductLabel.textProperty().bind(nv.typeProperty().asString());
				productImageView.imageProperty().bindBidirectional(nv.imageProperty());
				productNameLabel.textProperty().bindBidirectional(nv.nameProperty());
			}			
		});

		momentDayLabel.textProperty().bind(menuProduct.momentDayProperty().asString());
	}

	@FXML
	void onAddButtonAction(ActionEvent event) {
		menuProduct.getProduct().add(productSelected.get());
		MenuProduct.insertMenuProduct(menuProduct.getMenu().getId(), productSelected.get().getId(),
				menuProduct.getMomentDay().getId());
		defaultProductList.remove(productSelected.get());
	}

	@FXML
	void onRemoveButtonAction(ActionEvent event) {
		defaultProductList.add(productMenuSelected.get());
		MenuProduct.deleteMenuProduct(menuProduct.getMenu().getId(), productMenuSelected.get().getId(),
				menuProduct.getMomentDay().getId());
		menuProduct.getProduct().remove(productMenuSelected.get());

	}

	private void filterProduct() {
		List<Product> allProduct = Product.getAllProducts();
		allProduct.removeAll(menuProduct.getProduct());
		defaultProductList.setAll(allProduct);
	}

	public HBox getView() {
		return view;
	}
}
