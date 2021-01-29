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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

public class DataController implements Initializable {
	
	
	//view
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
	    private Button saveRegisterButton;

	    @FXML
	    private Button historicButton;

	    @FXML
	    private ProgressBar imcProgress;
	    
	    @FXML
	    private ImageView imcImageView;
	    
	    
	    //model
	    private StringProperty pesoStringProperty = new SimpleStringProperty();
		private DoubleProperty pesoProperty = new SimpleDoubleProperty();
		
		private StringProperty alturaStringProperty = new SimpleStringProperty();
		private DoubleProperty alturaProperty = new SimpleDoubleProperty();
		
		private StringProperty imcStringProperty = new SimpleStringProperty();
		private DoubleProperty imcProperty = new SimpleDoubleProperty();
		
		private StringProperty resProperty= new SimpleStringProperty();
    
    
    public DataController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DataView.fxml"));
		loader.setController(this);
		loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		changeButton.setOnAction(e->onChangeButtonAction());
		
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
		} else {
			imcStringProperty.set("IMC: " + imcProperty.get());
			if (imcProperty.get() < 18.5) {
				resProperty.set("Bajo peso");
				imcImageView.setImage(new Image("images/imcWeight/under_weight.png"));
			}
			else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25.0 ) {
				resProperty.set("Normal");
				imcImageView.setImage(new Image("images/imcWeight/normal_weight.png"));
				
			}
			else if (imcProperty.get() >= 25.0 && imcProperty.get() < 30.0) {
				resProperty.set("Sobrepeso");
				imcImageView.setImage(new Image("images/imcWeight/over_weight.png"));
			}
			else {
				resProperty.set("Obeso");
				imcImageView.setImage(new Image("images/imcWeight/obese_weight.png"));
			}
		}
		
	}

	private void onCalculoIndice() {
		
		if ((pesoProperty.get() == 0) || (alturaProperty.get() == 0))
			imcProperty.set(0);
		else {
	
			Double imc = (pesoProperty.get() / (Math.pow(alturaProperty.get(), 2)));
			imcProperty.set(imc*10000);

	}
		
	}

	//MÉTODO PARA CAMBIAR EL  AVATAR DEL USUARIO
	private void onChangeButtonAction() {
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Cambiar de avatar");
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Imagen .png (*.PNG)", "*.PNG"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Imagen .jpg (*.JPG)", "*.JPG"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
    	File imageFile= fileChooser.showOpenDialog(App.getPrimaryStage());
    	
    	if(imageFile!=null) {
    		  try {
                  BufferedImage bufferedImage = ImageIO.read(imageFile);
                  Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                  avatarImageView.setImage(image);
              } catch (IOException ex) {
                  //TODO implementar alert
              }
    	}
    	
	
	}

	
	
	public BorderPane getView() {
		return view;
	}
	

}
