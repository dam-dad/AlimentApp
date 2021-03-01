package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.utils.Utils;
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

/**
 * Esta clase "ProductController" la utilizaremos para añadir o quitar productos de un momento del dia de un menu.
 * @author Antonio
 *
 */
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
	private ObjectProperty<ProductMomentDay> productMomentDay = new SimpleObjectProperty<>();
	private ListProperty<Product> defaultProducts = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Product> productSelected = new SimpleObjectProperty<>();
	private ObjectProperty<Product> productMenuSelected = new SimpleObjectProperty<>();

	public ProductController(ProductMomentDay productMomentDay) throws IOException {
		this.productMomentDay.set(productMomentDay);
		filterProduct();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Products.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDINGS
		selectedProductList.itemsProperty().bindBidirectional(productMomentDay.get().productsProperty());
		momentDayLabel.textProperty().bind(productMomentDay.get().momentDayProperty().asString());

		productSelected.bind(productList.getSelectionModel().selectedItemProperty());
		productList.itemsProperty().bindBidirectional(defaultProducts);

		selectedProductList.itemsProperty().bindBidirectional(productMomentDay.get().productsProperty());
		productMenuSelected.bind(selectedProductList.getSelectionModel().selectedItemProperty());

		// BUTTONS
		removeButton.disableProperty().bind(selectedProductList.getSelectionModel().selectedItemProperty().isNull());
		addButton.disableProperty().bind(productList.getSelectionModel().selectedItemProperty().isNull());

		// LISTENERS
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
	}
	/**
	 * El metodo "onAddButtonAction" se encarga de añadir productos a la lista del momento del dia 
	 * y eliminamos de la lista de productos ese producto para que no hayan duplicados.
	 * @param event
	 */
	@FXML
	void onAddButtonAction(ActionEvent event) {
		productMomentDay.get().getProducts().add(productSelected.get());
		defaultProducts.remove(productSelected.get());
		Utils.popup("Se ha guardado el producto correctamente");
	}
	/**
	 * El metodo "onRemoveButtonAction" se encarga de eliminar productos a la lista del momento del dia 
	 * y añadir a la lista de productos ese producto para que vuelva estar disponible.
	 * @param event
	 */
	@FXML
	void onRemoveButtonAction(ActionEvent event) {
		defaultProducts.add(productMenuSelected.get());
		productMomentDay.get().getProducts().remove(productMenuSelected.get());
		Utils.popup("Se ha eliminado el producto correctamente");
	}

	/**
	 * El metodo "filterProduct" se encarga de filtrar la lsita de productos a
	 * visualizar, para que los productos ya seleccionados de un momento del dia no
	 * aparezcan duplicados.
	 */
	private void filterProduct() {
		List<Product> allProduct = ProfileController.getProducts();
		allProduct.removeAll(productMomentDay.get().getProducts());
		defaultProducts.setAll(allProduct);
	}

	public HBox getView() {
		return view;
	}

	public final ObjectProperty<ProductMomentDay> productMomentDayProperty() {
		return this.productMomentDay;
	}

	/**
	 * El metodo "getProductMomentDay" se encarga de obtener una lista de todos los
	 * productos seleccionados para un momento del dia.
	 * 
	 * @return retornara una lista de productos para un moemento del dia.
	 */
	public final ProductMomentDay getProductMomentDay() {
		return this.productMomentDayProperty().get();
	}
}
