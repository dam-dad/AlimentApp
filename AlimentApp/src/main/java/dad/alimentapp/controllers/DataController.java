package dad.alimentapp.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import dad.alimentapp.main.App;
import dad.alimentapp.models.Gender;
import dad.alimentapp.models.db.Profile;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.controllers.InfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
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
	    private TextField nameText;

	    @FXML
	    private TextField surnameText;

	    @FXML
	    private TextField ageText;

	    @FXML
	    private RadioButton manRadio;

	    @FXML
	    private ToggleGroup gender;

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
	    private Button saveButton;

	    @FXML
	    private BarChart<?, ?> historicChart;

	    @FXML
	    private Label idealWeightLabel;

	    @FXML
	    private Label idealDietLabel;

	    @FXML
	    private Label exerciseLabel;

	    @FXML
	    private ImageView imcImageView;

	    @FXML
	    private Label indeximcLabel;

	// model
	private StringProperty pesoStringProperty = new SimpleStringProperty();
	private DoubleProperty pesoProperty = new SimpleDoubleProperty();

	private StringProperty alturaStringProperty = new SimpleStringProperty();
	private DoubleProperty alturaProperty = new SimpleDoubleProperty();

	private StringProperty imcStringProperty = new SimpleStringProperty();
	private DoubleProperty imcProperty = new SimpleDoubleProperty();

	private StringProperty resProperty = new SimpleStringProperty();
	
	private static ObjectProperty<Profile> profile= new SimpleObjectProperty<>();
	
	


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
		
		
		profile.addListener((o,ov,nv)->onProfileChanged(o,ov,nv));
		
		
		
	
		
		
		saveButton.setOnAction(e->onSaveButtonAction(e));

		

	}
	


	private void onProfileChanged(ObservableValue<? extends Profile> o, Profile ov, Profile nv) {
		
		System.out.println("ov: "+ov);
		System.out.println("nv: "+nv);
		if(ov!=null) {
		
			/*profile.unbindBidirectional(InfoController.getProfile());
			nameText.textProperty().bind(profile.get().nameProperty());
			surnameText.textProperty().bind(profile.get().surNameProperty());
			Bindings.bindBidirectional(ageText.textProperty(),profile.get().ageProperty(),new NumberStringConverter());
			Bindings.bindBidirectional(weightText.textProperty(),profile.get().weightProperty(),new NumberStringConverter());
			Bindings.bindBidirectional(heighText.textProperty(),profile.get().heightProperty(),new NumberStringConverter());
			*/
		}
		if(nv!=null) {
			profile.bindBidirectional(InfoController.getProfile());
			nameText.textProperty().bind(profile.get().nameProperty());
			surnameText.textProperty().bind(profile.get().surNameProperty());
			Bindings.bindBidirectional(ageText.textProperty(),profile.get().ageProperty(),new NumberStringConverter());
			Bindings.bindBidirectional(weightText.textProperty(),profile.get().weightProperty(),new NumberStringConverter());
			Bindings.bindBidirectional(heighText.textProperty(),profile.get().heightProperty(),new NumberStringConverter());
			
			if(profile.get().genderProperty().get().getId()==1) {
				manRadio.setSelected(true);
			}else {
				womanRadio.setSelected(true);
			}
			
		}
		
	}

	private void onSaveButtonAction(ActionEvent e) {
		
	//Dialog para perfil	
		
	//Recogemos los datos 	
	String name= nameText.getText();
	String surname= surnameText.getText();
	int age= Integer.parseInt( ageText.getText());
	
	/*
	try {
		String sql = "insert into Profile (name,surname,age) values()";
		PreparedStatement query = App.connection.prepareStatement(sql);
		query.setInt(1, id);
		ResultSet result = query.executeQuery();
		while (result.next()) {
			profile = new Profile(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),
					result.getInt(5), result.getInt(6), result.getDouble(7), Gender.valueOf(result.getInt(7)));
		}
	} catch (SQLException e) {
		Messages.error("Error al obtenner el perfil selecionado", e.getMessage());
	}
	*/
	
		
		
	//InfoController.getProfileList().add();
		
		
		
		
		
	}

	private void onCalculoRes() {
		if (imcProperty.get() == 0) {
			imcStringProperty.set("(peso * altura^ 2)");
			resProperty.set("Bajo peso | Normal | Sobrepeso | Obeso");
			imcImageView.setImage(new Image("images/myDataTab/no_weight.png"));
		} else {
			imcStringProperty.set(" "+imcProperty.get());
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
	
	public static void setProfile(Profile e) {
		profile.set(e);
	}

	public BorderPane getView() {
		return view;
	}

}