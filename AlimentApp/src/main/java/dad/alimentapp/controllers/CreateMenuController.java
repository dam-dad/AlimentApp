package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.models.Menu;
import dad.alimentapp.models.NutritionalValues;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.service.MenuService;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Esta clase "CreateMenuController" la utilizaremos para crear nuevos menús en
 * nuestra app.
 * 
 * @author Antonio
 *
 */
public class CreateMenuController implements Initializable {

	// VIEW
	@FXML
	private HBox view;

	@FXML
	private Button breakfastAddButton;

	@FXML
	private Button breakfastRemoveButton;

	@FXML
	private Button midMorningAddButton;

	@FXML
	private Button midMorningRemoveButton;

	@FXML
	private Button lunchAddButton;

	@FXML
	private Button lunchRemoveButton;

	@FXML
	private Button snackAddButton;

	@FXML
	private Button snackRemoveButton;

	@FXML
	private Button dinnerAddButton;

	@FXML
	private Button dinnerRemoveButton;

	@FXML
	private ListView<Product> breakfastList;

	@FXML
	private ListView<Product> midMorningList;

	@FXML
	private ListView<Product> lunchList;

	@FXML
	private ListView<Product> snackList;

	@FXML
	private ListView<Product> dinnerList;

	@FXML
	private TextField nameMenuText;

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
	private Button saveMenuButton;

	// CONTROLLERS
	private ProductController productController;

	// MODEL
	private ObjectProperty<Menu> menuSelected = new SimpleObjectProperty<>();
	private ObjectProperty<NutritionalValues> nutritionalValues = new SimpleObjectProperty<>(new NutritionalValues());

	// STAGE
	private static Stage loadAllMenuStage;

	public CreateMenuController(Menu menu) throws IOException {
		this.menuSelected.set(menu);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTotals();

		// BINDINGS
		nameMenuText.textProperty().bindBidirectional(menuSelected.get().nameProperty());

		// MOMENTS OF DAY
		breakfastList.itemsProperty().bindBidirectional(menuSelected.get().getBreakfastProducts().productsProperty());
		midMorningList.itemsProperty().bindBidirectional(menuSelected.get().getMidMorningProducts().productsProperty());
		lunchList.itemsProperty().bindBidirectional(menuSelected.get().getLunchProducts().productsProperty());
		snackList.itemsProperty().bindBidirectional(menuSelected.get().getSnackProducts().productsProperty());
		dinnerList.itemsProperty().bindBidirectional(menuSelected.get().getDinnerProducts().productsProperty());

		// BUTTONS
		saveMenuButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getBreakfastProducts().getProducts()).isEqualTo(0)
						.and(Bindings.size(menuSelected.get().getMidMorningProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getLunchProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getSnackProducts().getProducts()).isEqualTo(0))
						.and(Bindings.size(menuSelected.get().getDinnerProducts().getProducts()).isEqualTo(0)));

		breakfastRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getBreakfastProducts().getProducts()).isEqualTo(0));
		midMorningRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getMidMorningProducts().getProducts()).isEqualTo(0));
		lunchRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getLunchProducts().getProducts()).isEqualTo(0));
		snackRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getSnackProducts().getProducts()).isEqualTo(0));
		dinnerRemoveButton.disableProperty()
				.bind(Bindings.size(menuSelected.get().getDinnerProducts().getProducts()).isEqualTo(0));

		kcalTotLabel.textProperty().bindBidirectional(nutritionalValues.get().kcalsTotalsProperty(),
				new NumberStringConverter());
		proteinTotLabel.textProperty().bindBidirectional(nutritionalValues.get().proteinsTotalsProperty(),
				new NumberStringConverter());
		hydratesTotLabel.textProperty().bindBidirectional(nutritionalValues.get().hydratesTotalsProperty(),
				new NumberStringConverter());
		fatsTotLabel.textProperty().bindBidirectional(nutritionalValues.get().fatsTotalsProperty(),
				new NumberStringConverter());
		fibresTotLabel.textProperty().bindBidirectional(nutritionalValues.get().fibresTotalsProperty(),
				new NumberStringConverter());
	}

	/**
	 * El metodo "onBreakfastAddButtonAction" lo utilizamos para lanzar la vista de
	 * productos, en el cual le asignaremos una lista de productos al desayuno.
	 * 
	 * @param event
	 */
	@FXML
	void onBreakfastAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getBreakfastProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getBreakfastProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onBreakfastRemoveButtonAction", lo utilizaremos para borrar la
	 * lista de productos del desayuno.
	 * 
	 * @param event
	 */
	@FXML
	void onBreakfastRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Desayuno",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getBreakfastProducts().getProducts().setAll(new ArrayList<>());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onMidMorningAddButtonAction" lo utilizamos para lanzar la vista de
	 * productos, en el cual le asignaremos una lista de productos a media-mañana.
	 * 
	 * @param event
	 */
	@FXML
	void onMidMorningAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getMidMorningProducts());

		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getMidMorningProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onMidMorningRemoveButtonAction", lo utilizaremos para borrar la
	 * lista de productos de media-mañana.
	 * 
	 * @param event
	 */
	@FXML
	void onMidMorningRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de Media-Mañana",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getMidMorningProducts().getProducts().setAll(new ArrayList<>());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onLunchAddButtonAction" lo utilizamos para lanzar la vista de
	 * productos, en el cual le asignaremos una lista de productos al almuerzo.
	 * 
	 * @param event
	 */
	@FXML
	void onLunchAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getLunchProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getLunchProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onLunchRemoveButtonAction", lo utilizaremos para borrar la lista
	 * de productos del almuerzo.
	 * 
	 * @param event
	 */
	@FXML
	void onLunchRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos del Almuerzo",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getLunchProducts().getProducts().setAll(new ArrayList<>());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onSnackAddButtonAction" lo utilizamos para lanzar la vista de
	 * productos, en el cual le asignaremos una lista de productos a la merienda.
	 * 
	 * @param event
	 */
	@FXML
	void onSnackAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getSnackProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getSnackProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onSnackRemoveButtonAction", lo utilizaremos para borrar la lista
	 * de productos de la merienda.
	 * 
	 * @param event
	 */
	@FXML
	void onSnackRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Merienda",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getSnackProducts().getProducts().setAll(new ArrayList<>());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onDinnerAddButtonAction" lo utilizamos para lanzar la vista de
	 * productos, en el cual le asignaremos una lista de productos a la cena.
	 * 
	 * @param event
	 */
	@FXML
	void onDinnerAddButtonAction(ActionEvent event) {
		newSceneProduct(menuSelected.get().getDinnerProducts());
		if (productController.getProductMomentDay().getProducts().size() != 0) {
			this.menuSelected.get().getDinnerProducts()
					.setProducts(productController.getProductMomentDay().getProducts());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "onDinnerRemoveButtonAction", lo utilizaremos para borrar la lista
	 * de productos de la cena.
	 * 
	 * @param event
	 */
	@FXML
	void onDinnerRemoveButtonAction(ActionEvent event) {
		Optional<ButtonType> result = Messages.confirmation("Borrar los productos de la Cena",
				"Estas seguro de querer borrar esta lista productos.");
		if (result.get() == ButtonType.OK) {
			menuSelected.get().getDinnerProducts().getProducts().setAll(new ArrayList<>());
			nutritionalValues.get().clear();
			loadTotals();
		}
	}

	/**
	 * El metodo "newSceneProduct" se encarga de lanzar una nueva vista donde
	 * podemos seleccionar de una lista de productos ya existente los que queremos
	 * añadir a nuestro menu o dieta.
	 * 
	 * @param productMomentDay Recibimos la lista de productos a cargar o rellenar y
	 *                         el momento del dia al que pertenecen.
	 */
	private void newSceneProduct(ProductMomentDay productMomentDay) {
		Stage choice = ChoiceController.getCreateDietCustomStage();
		Stage manage = ManageDietController.getModificateStage();

		Stage stage = choice != null ? choice : manage;
		try {
			Stage productStage = new Stage();
			productStage.setMinWidth(800);
			productStage.setMinHeight(500);
			productStage.setTitle("Productos");
			productStage.getIcons().add(new Image("/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(stage);

			productController = new ProductController(productMomentDay);
			Scene scene = new Scene(productController.getView());
			scene.getStylesheets().add(MainController.getStyleSheetActual());

			productStage.setScene(scene);
			productStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * El metodo "onSaveMenuButtonAction" se encarga de guardar o actualizar un
	 * menú.
	 * 
	 * @param event
	 */
	@FXML
	void onSaveMenuButtonAction(ActionEvent event) {
		menuSelected.get().setName(menuSelected.get().getName().trim());
		if (menuSelected.get().getId() == 0) {
			boolean isDuplicate = Utils.isMatchMenuName(menuSelected.get());
			if (isDuplicate) {
				Messages.error("Error al guardar el menú", "No se pueden guardar menús con el mismo nombre.");
			} else {
				MenuService.insertMenu(menuSelected.get());
				ManageDietController.loadDietsAndMenus();
				ChoiceController.getCreateDietCustomStage().close();
			}
		} else {
			MenuService.updateMenu(menuSelected.get());
			ManageDietController.loadDietsAndMenus();
			ManageDietController.getModificateStage().close();
		}
	}

	/**
	 * El metodo "loadTotals" se encarga de asignar los valores nutricionales
	 * totales a cada una de las properties correspondientes y cargar la grafica.
	 */
	private void loadTotals() {		
		if (!menuSelected.get().isMomentsDayEmpty()) {
			NutritionalValues totalsBreaksfast = loadTotalsForMomentDay(
					menuSelected.get().getBreakfastProducts().getProducts());
			NutritionalValues totalsMidMorning = loadTotalsForMomentDay(
					menuSelected.get().getMidMorningProducts().getProducts());
			NutritionalValues totalsLunch = loadTotalsForMomentDay(menuSelected.get().getLunchProducts().getProducts());
			NutritionalValues totalsSnack = loadTotalsForMomentDay(menuSelected.get().getSnackProducts().getProducts());
			NutritionalValues totalsDinner = loadTotalsForMomentDay(menuSelected.get().getDinnerProducts().getProducts());
			
			nutritionalValues.get().setKcalsTotals(totalsBreaksfast.getKcalsTotals() + totalsMidMorning.getKcalsTotals()
					+ totalsLunch.getKcalsTotals() + totalsSnack.getKcalsTotals() + totalsDinner.getKcalsTotals());

			nutritionalValues.get().proteinsTotalsProperty()
					.set(totalsBreaksfast.getProteinsTotals() + totalsMidMorning.getProteinsTotals()
							+ totalsLunch.getProteinsTotals() + totalsSnack.getProteinsTotals()
							+ totalsDinner.getProteinsTotals());

			nutritionalValues.get().hydratesTotalsProperty()
					.set(totalsBreaksfast.getHydratesTotals() + totalsMidMorning.getHydratesTotals()
							+ totalsLunch.getHydratesTotals() + totalsSnack.getHydratesTotals()
							+ totalsDinner.getHydratesTotals());

			nutritionalValues.get().fatsTotalsProperty()
					.set(totalsBreaksfast.getFatsTotals() + totalsMidMorning.getFatsTotals()
							+ totalsLunch.getFatsTotals() + totalsSnack.getFatsTotals() + totalsDinner.getFatsTotals());

			nutritionalValues.get().fibresTotalsProperty()
					.set(totalsBreaksfast.getFibresTotals() + totalsMidMorning.getFibresTotals()
							+ totalsLunch.getFibresTotals() + totalsSnack.getFibresTotals()
							+ totalsDinner.getFibresTotals());

			getPieChart();
		} else {
			menuChart.getData().clear();
		}
	}

	/**
	 * El metodo "loadTotalsForMomentDay" se encarga de calcular los valores
	 * nutricionales totales para un momento del dia.
	 * 
	 * @param products Recibe la lista de productos correspondiente a un momento del
	 *                 dia.
	 * @return
	 */
	private NutritionalValues loadTotalsForMomentDay(List<Product> products) {
		int kcals = 0, proteins = 0, hydrates = 0, fats = 0, fibres = 0;
		for (Product product : products) {
			kcals += this.nutritionalValues.get().kcalsTotalsProperty().add(product.kcalProperty()).intValue();
			proteins += this.nutritionalValues.get().proteinsTotalsProperty().add(product.proteinProperty()).intValue();
			hydrates += this.nutritionalValues.get().hydratesTotalsProperty().add(product.hydratesProperty())
					.intValue();
			fats += this.nutritionalValues.get().fatsTotalsProperty().add(product.fatsProperty()).intValue();
			fibres += this.nutritionalValues.get().fibresTotalsProperty().add(product.fibresProperty()).intValue();
		}
		return new NutritionalValues(kcals, proteins, hydrates, fats, fibres);
	}

	/**
	 * El metodo "getPieChart" crea la grafica basandose en los datos de los valores
	 * nutricionales.
	 */
	private void getPieChart() {
		if (!nutritionalValues.get().isEmpty()) {
			Integer nutricionalsTotals = nutritionalValues.get().getProteinsTotals()
					+ nutritionalValues.get().getHydratesTotals() + nutritionalValues.get().getFatsTotals()
					+ nutritionalValues.get().getFibresTotals();
			Integer proteinsValue = Math
					.round((nutritionalValues.get().getProteinsTotals() * 100) / nutricionalsTotals);
			Integer hydratesValue = Math
					.round((nutritionalValues.get().getHydratesTotals() * 100) / nutricionalsTotals);
			Integer fatsValue = Math.round((nutritionalValues.get().getFatsTotals() * 100) / nutricionalsTotals);
			Integer fibresValue = Math.round((nutritionalValues.get().getFibresTotals() * 100) / nutricionalsTotals);

			PieChart.Data proteins = new PieChart.Data("Proteinas", proteinsValue);
			PieChart.Data hydrates = new PieChart.Data("Hidratos", hydratesValue);
			PieChart.Data fats = new PieChart.Data("Grasas", fatsValue);
			PieChart.Data fibres = new PieChart.Data("Fibra", fibresValue);

			menuChart.getData().setAll(proteins);
			menuChart.getData().add(hydrates);
			menuChart.getData().add(fats);
			menuChart.getData().add(fibres);

			menuChart.setClockwise(true);
			menuChart.setLabelsVisible(true);
			menuChart.setLabelLineLength(20);

			menuChart.getData().forEach(this::installTooltip);
			installTooltip(proteins);
			installTooltip(hydrates);
			installTooltip(fats);
			installTooltip(fibres);
		}
	}

	/**
	 * El metodo "installTooltip" nos muestra un tooltip sobre cada valor de la
	 * grafica.
	 * 
	 * @param d le pasamos el valor del porcentaje de cada nodo.
	 */
	public void installTooltip(PieChart.Data d) {

		String msg = String.format("%s : %s", d.getName(), d.getPieValue() + "%");

		Tooltip tooltip = new Tooltip(msg);
		tooltip.setStyle("-fx-background-color: #061f33; -fx-text-fill: #FFFFFF; -fx-font-family: Segoe UI Light;");

		Tooltip.install(d.getNode(), tooltip);
	}

	public static Stage getLoadAllMenuStage() {
		return loadAllMenuStage;
	}

	public HBox getView() {
		return view;
	}
}
