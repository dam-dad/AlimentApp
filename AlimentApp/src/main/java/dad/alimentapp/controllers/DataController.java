package dad.alimentapp.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import dad.alimentapp.main.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

public class DataController implements Initializable {

	// view
	@FXML
	private BorderPane view;

	@FXML
	private Button editButton;

	@FXML
	private Button saveButton;

	@FXML
	private TextField nameText;

	@FXML
	private TextField surnameText;

	@FXML
	private TextField ageText;

	@FXML
	private RadioButton manRadio;

	@FXML
	private RadioButton womanRadio;
	
    @FXML
    private ToggleGroup gender;

	@FXML
	private ImageView avatarImageView;

	@FXML
	private Button changeButton;

	@FXML
	private TextField weightText;

	@FXML
	private TextField heighText;

	@FXML
	private Label imcLabel;

	@FXML
	private Label indeximcLabel;
	
    @FXML
    private Label idealWeightLabel;

    @FXML
    private Label idealDietLabel;

    @FXML
    private Label exerciseLabel;

	@FXML
	private Button saveRegisterButton;

	@FXML
	private Button historicButton;

	@FXML
	private ProgressBar imcProgress;

	@FXML
	private ImageView imcImageView;

	// model
	private StringProperty pesoStringProperty = new SimpleStringProperty();
	private DoubleProperty pesoProperty = new SimpleDoubleProperty();

	private StringProperty alturaStringProperty = new SimpleStringProperty();
	private DoubleProperty alturaProperty = new SimpleDoubleProperty();

	private StringProperty imcStringProperty = new SimpleStringProperty();
	private DoubleProperty imcProperty = new SimpleDoubleProperty();

	private StringProperty resProperty = new SimpleStringProperty();
	
	


	public DataController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DataView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gender= new ToggleGroup();
		
		gender.getToggles().add(manRadio);
		gender.getToggles().add(womanRadio);

		
		

	
		
		
		imcImageView.setImage(new Image("images/myDataTab/no_weight.png"));

		changeButton.setOnAction(e -> onChangeButtonAction());

		pesoStringProperty.bindBidirectional(weightText.textProperty());
		Bindings.bindBidirectional(pesoStringProperty, pesoProperty, new NumberStringConverter());
		pesoProperty.addListener((o, ov, nv) -> onCalculoIndice());

		alturaStringProperty.bindBidirectional(heighText.textProperty());
		Bindings.bindBidirectional(alturaStringProperty, alturaProperty, new NumberStringConverter());
		alturaProperty.addListener((o, ov, nv) -> onCalculoIndice());

		imcStringProperty.bindBidirectional(imcLabel.textProperty());
		resProperty.bindBidirectional(indeximcLabel.textProperty());
		imcProperty.addListener((o, ov, nv) -> onCalculoRes());

		

	}
	





		
		
	


	private void onCalculoRes() {
		if (imcProperty.get() == 0) {
			imcStringProperty.set("(peso * altura^ 2)");
			resProperty.set("Bajo peso | Normal | Sobrepeso | Obeso");
			imcImageView.setImage(new Image("images/myDataTab/no_weight.png"));
		} else {
			imcStringProperty.set("IMC: " + imcProperty.get());
			if (imcProperty.get() < 18.5) {
				resProperty.set("Bajo peso");
				idealDietLabel.setText("Te recomendamos una dieta hipercalórica");
				exerciseLabel.setText("Realiza ejercicio de manera poco intensa," + "\n"+ "de modo que no incluya un gasto calórico excesivo");
				imcImageView.setImage(new Image("images/myDataTab/under_weight.png"));
			} else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25.0) {
				resProperty.set("Normal");
				idealDietLabel.setText("Sigue una dieta equilibrada");
				exerciseLabel.setText("El ejercicio físico moderado te vendrá bien");
				imcImageView.setImage(new Image("images/myDataTab/normal_weight.png"));

			} else if (imcProperty.get() >= 25.0 && imcProperty.get() < 30.0) {
				resProperty.set("Sobrepeso");
				idealDietLabel.setText("Te recomendamos seguir una dieta hipocalórica");
				exerciseLabel.setText("Lleva a cabo aquel ejercicio que te ayude a quemar"+"\n"+"más calorías de las que consumes");
				imcImageView.setImage(new Image("images/myDataTab/over_weight.png"));
			} else {
				resProperty.set("Obeso");
				imcImageView.setImage(new Image("images/myDataTab/obese_weight.png"));
				idealDietLabel.setText("Consulta con un especialista para que te ayude" +"\n"+ "a bajar peso de forma considerable");
				exerciseLabel.setText("Realiza ejercicio físico aeróbico");
			}
		}

	}

	private void onCalculoIndice() {
	

		if ((pesoProperty.get() == 0) || (alturaProperty.get() == 0)) 
			imcProperty.set(0);	
		else {

			Double imc = (pesoProperty.get() / (Math.pow(alturaProperty.get(), 2))*10000);
			imcProperty.set(Math.round((imc*100.00)/100.00));

		}
		
	
		
		

		

	}

	// MÉTODO PARA CAMBIAR EL AVATAR DEL USUARIO
	private void onChangeButtonAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Cambiar de avatar");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Imagen .png (*.PNG)", "*.PNG"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Imagen .jpg (*.JPG)", "*.JPG"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
		File imageFile = fileChooser.showOpenDialog(App.getPrimaryStage());

		if (imageFile != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(imageFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				avatarImageView.setImage(image);
			} catch (IOException ex) {
				// TODO implementar alert
			}
		}

	}

	public BorderPane getView() {
		return view;
	}

}