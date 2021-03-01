package dad.alimentapp.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;


import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import dad.alimentapp.main.App;
import dad.alimentapp.models.Gender;
import dad.alimentapp.models.Profile;
import dad.alimentapp.service.ProfileService;
import dad.alimentapp.utils.Messages;
import dad.alimentapp.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

/**
 * Esta clase la utilizaremos para mostrar, gestionar y guardar los datos de los
 * usuarios
 * 
 * @author David_Diaz
 *
 */
public class DataController implements Initializable {

	// view
	@FXML
	private BorderPane view;

	@FXML
	private TextField profileText;

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
    private LineChart<String,Double> imcLineChart;
    
    @FXML
    private CategoryAxis weekAxis;

    @FXML
    private NumberAxis imcAxis;

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
	private StringProperty weightStringProperty = new SimpleStringProperty();
	private DoubleProperty weightProperty = new SimpleDoubleProperty();

	private StringProperty heightStringProperty = new SimpleStringProperty();
	private DoubleProperty heightProperty = new SimpleDoubleProperty();

	private StringProperty imcStringProperty = new SimpleStringProperty();
	private DoubleProperty imcProperty = new SimpleDoubleProperty();

	private StringProperty resProperty = new SimpleStringProperty();

	private static ObjectProperty<Profile> profile = new SimpleObjectProperty<>();

	public DataController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DataView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/**
		 * Valores iniciales de la vista al iniciarse la misma
		 */
		gender = new ToggleGroup();
		gender.getToggles().add(manRadio);
		gender.getToggles().add(womanRadio);

		manRadio.selectedProperty().set(true);

		imcImageView.setImage(new Image("images/myDataTab/no_weight.png"));

		changeButton.setOnAction(e -> onChangeButtonAction());
		weightStringProperty.bindBidirectional(weightText.textProperty());

		Bindings.bindBidirectional(weightStringProperty, weightProperty, new NumberStringConverter());
		weightProperty.addListener((o, ov, nv) -> onCalculateIndex());
		heightStringProperty.bindBidirectional(heighText.textProperty());

		Bindings.bindBidirectional(heightStringProperty, heightProperty, new NumberStringConverter());
		heightProperty.addListener((o, ov, nv) -> onCalculateIndex());
		imcStringProperty.bindBidirectional(imcLabel.textProperty());
		resProperty.bindBidirectional(indeximcLabel.textProperty());
		imcProperty.addListener((o, ov, nv) -> onCalculateIMC());

		womanRadio.selectedProperty().addListener((o, ov, nv) -> onCalculateIndex());
		manRadio.selectedProperty().addListener((o, ov, nv) -> onCalculateIndex());

		profile.bind(ProfileController.getProfile());
		profile.addListener((o, ov, nv) -> onProfileChanged(o, ov, nv));

		saveButton.setOnAction(e -> onSaveButtonAction());

		/**
		 * Validadores para los distintos datos que recogeremos del usuario
		 */
		Validator<String> stringValidator = (control, value) -> {
			boolean condition = value.isBlank();
			return ValidationResult.fromMessageIf(control, "Debe introducir algún carácter",
					org.controlsfx.validation.Severity.ERROR, condition);
		};

		Validator<String> integerValidator = (control, value) -> {
			boolean condition = !checkNumber(value);
			return ValidationResult.fromMessageIf(control, "Debe introducir un número",
					org.controlsfx.validation.Severity.ERROR, condition);
		};

		ValidationSupport support = new ValidationSupport();
		support.registerValidator(nameText, false, stringValidator);
		support.registerValidator(surnameText, false, stringValidator);
		support.registerValidator(profileText, false, stringValidator);
		support.registerValidator(ageText, false, integerValidator);
		support.registerValidator(weightText, false, integerValidator);
		support.registerValidator(heighText, false, integerValidator);

		saveButton.disableProperty().bind(support.invalidProperty());
		
		XYChart.Series<String,Double> serie= new XYChart.Series<>();
		serie.setName("IMC");
		
		serie.getData().add(new XYChart.Data<>("Semana 1",21.0));
		serie.getData().add(new XYChart.Data<>("Semana 2",16.0));
		serie.getData().add(new XYChart.Data<>("Semana 3",23.0));
		serie.getData().add(new XYChart.Data<>("Semana 4",14.0));
		serie.getData().add(new XYChart.Data<>("Semana 5",19.0));
		serie.getData().add(new XYChart.Data<>("Semana 6",28.0));
		
		imcAxis= new NumberAxis(10.0,30.0,1);
	
		imcLineChart.getData().add(serie);

	}

	/**
	 * Método que vincula y desvincula los datos del usuario según el perfil que se haya escogido
	 * @param o  Indica que es un valor de tipo observable
	 * @param ov Antiguo valor que tenía el parámetro
	 * @param nv Nuevo Valor que tiene el parámetro
	 */
	private void onProfileChanged(ObservableValue<? extends Profile> o, Profile ov, Profile nv) {
		if (ov != null) {
			
			profile.unbind();
			nameText.textProperty().unbind();
			surnameText.textProperty().unbindBidirectional(ov.ageProperty());
			Bindings.unbindBidirectional(ageText.textProperty(), ov.ageProperty());
			Bindings.unbindBidirectional(weightText.textProperty(), ov.weightProperty());
			Bindings.unbindBidirectional(heighText.textProperty(), ov.heightProperty());
			// avatarImageView.imageProperty().unbind();

		}
		if (nv != null) {
			
			profile.bind(ProfileController.getProfile());
			profileText.textProperty().bindBidirectional(nv.nameProfileProperty());
			nameText.textProperty().bindBidirectional(nv.nameProperty());
			surnameText.textProperty().bindBidirectional(nv.surNameProperty());
			Bindings.bindBidirectional(ageText.textProperty(), nv.ageProperty(), new NumberStringConverter());
			Bindings.bindBidirectional(weightText.textProperty(), nv.weightProperty(), new NumberStringConverter());
			Bindings.bindBidirectional(heighText.textProperty(), nv.heightProperty(), new NumberStringConverter());
			// avatarImageView.imageProperty().set(nv.imageProperty().get());

			if (nv.genderProperty().get().getId() == 1) {
				manRadio.setSelected(true);
			} else {
				womanRadio.setSelected(true);
			}

		}

	}

	/**
	 * Método que guarda un objeto de tipo Profile con los datos que el usuario ha introducido
	 * 
	 */
	private void onSaveButtonAction() {

		// Recogemos los datos
		String profileName = profileText.textProperty().get();
		String name = nameText.textProperty().get();
		String surname = surnameText.textProperty().get();
		int age = Integer.parseInt(ageText.textProperty().get());
		int weight = Integer.parseInt(weightText.textProperty().get());
		int height = Integer.parseInt(heighText.textProperty().get());
		double imc = imcProperty.get();
		int genderN = 1;
		String image = avatarImageView.getImage().getUrl();
		Gender genderG =  Gender.HOMBRE;

		if (manRadio.isSelected()) {
			genderN = 1;
			genderG = Gender.HOMBRE;

		} else if (womanRadio.isSelected()) {
			genderN = 2;
			genderG = Gender.MUJER;

		}

			
	
		try {
			String sql = "SELECT name_profile from Profile";
			PreparedStatement queryCheck = App.connection.prepareStatement(sql);
			ResultSet result = queryCheck.executeQuery();
			while (result.next()) {
				
			if(profileName.equals(result.getString(1))) {
				
				Optional <ButtonType> confirm= Messages.confirmation("Se sobreescribirán los datos", "Se sobreescribirán los datos del perfil seleccionado");
				if(confirm.get()==ButtonType.OK) {
					
					
					String updateSql= "UPDATE Profile set name=?,surname=?,age=?,weight=?,height=?,imc=?,gender=?,image_profile=? where name_profile=?";
					PreparedStatement queryUpdate = App.connection.prepareStatement(updateSql);
					queryUpdate.setString(1, name);
					queryUpdate.setString(2, surname);
					queryUpdate.setInt(3, age);
					queryUpdate.setInt(4, weight);
					queryUpdate.setInt(5, height);
					queryUpdate.setDouble(6, imc);
					queryUpdate.setInt(7, genderN);
					queryUpdate.setString(8, image);
					queryUpdate.setString(9, profileName);
					queryUpdate.executeUpdate();

					ProfileService.loadProfiles();
					Utils.popup("Se ha actualizado el perfil correctamente");
					App.getMainController().getManageDietsTab().setDisable(false);
					
				}
				
				
					
				}
			
			
			else {
				
				try {
					String sqlInsert = "insert into Profile (name_profile,name,surname,age,weight,height,imc,gender,image_profile) values(?,?,?,?,?,?,?,?,?)";
					PreparedStatement query = App.connection.prepareStatement(sqlInsert);
					query.setString(1, profileName);
					query.setString(2, name);
					query.setString(3, surname);
					query.setInt(4, age);
					query.setInt(5, weight);
					query.setInt(6, height);
					query.setDouble(7, imc);
					query.setInt(8, genderN);
					query.setString(9, image);
					query.executeUpdate();

					ProfileService.loadProfiles();
					Utils.popup("Se ha guardado el perfil correctamente");
					App.getMainController().getManageDietsTab().setDisable(false);

				} catch (SQLException ex) {
					Messages.error("Error al guardar el perfil ", "No se ha podido guardar el perfil seleccionado");
				}
				
			}
			
			}
			}
			
			
		 catch (SQLException exc) {
			Messages.error("Ha ocurrido un error", exc.getMessage());
		

		
		 }
			
		 }
	

	/**
	 * Método utilizado por el validador para comprobar si lo que está escrito en un TextField es un número o no
	 * @param text
	 * @return
	 */
	private boolean checkNumber(String text) {

		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	/**
	 * Método utilizado para calcular y mostrar el IMC del usuario
	 */
	private void onCalculateIMC() {
		if (imcProperty.get() == 0) {
			imcStringProperty.set("(peso * altura^ 2)");
			resProperty.set("Bajo peso | Normal | Sobrepeso | Obeso");
			imcImageView.setImage(new Image("images/myDataTab/no_weight.png"));
		} else {
			imcStringProperty.set(" " + imcProperty.get());
			if (imcProperty.get() < 18.5) {

				resProperty.set("Bajo peso");
				idealDietLabel.setText("Te recomendamos una dieta hipercalórica");
				exerciseLabel.setText("Realiza ejercicio de manera poco intensa," + "\n"
						+ "de modo que no incluya un gasto calórico excesivo");
				imcImageView.setImage(new Image("images/myDataTab/under_weight.png"));
			} else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25.0) {
				resProperty.set("Normal");
				idealDietLabel.setText("Sigue una dieta equilibrada");
				exerciseLabel.setText("El ejercicio físico moderado te vendrá bien");
				imcImageView.setImage(new Image("images/myDataTab/normal_weight.png"));

			} else if (imcProperty.get() >= 25.0 && imcProperty.get() < 30.0) {
				resProperty.set("Sobrepeso");
				idealDietLabel.setText("Te recomendamos seguir una dieta hipocalórica");
				exerciseLabel.setText("Lleva a cabo aquel ejercicio que te ayude a quemar" + "\n"
						+ "más calorías de las que consumes");
				imcImageView.setImage(new Image("images/myDataTab/over_weight.png"));
			} else {
				resProperty.set("Obeso");
				imcImageView.setImage(new Image("images/myDataTab/obese_weight.png"));
				idealDietLabel.setText(
						"Consulta con un especialista para que te ayude" + "\n" + "a bajar peso de forma considerable");
				exerciseLabel.setText("Realiza ejercicio físico aeróbico");
			}
		}

	}

	/**
	 * Método utilizado para mostrar la imagen y el peso ideal asociadas al IMC, altura y sexo del usuario
	 */
	private void onCalculateIndex() {
		Double idealWeight;

		if ((weightProperty.get() == 0) || (heightProperty.get() == 0))
			imcProperty.set(0);
		else {

			Double imc = (weightProperty.get() / (Math.pow(heightProperty.get(), 2)) * 10000);
			imcProperty.set(Math.round((imc * 100.00) / 100.00));

			if (manRadio.isSelected()) {
				idealWeight = Math.round(((0.75 * heightProperty.get()) - 62.5) * 100.00) / 100.00;
				idealWeightLabel.textProperty()
						.set("Tu peso ideal está entre " + idealWeight + "-" + (idealWeight + 5.00) + " kg");
			} else if (womanRadio.isSelected()) {
				idealWeight = Math.round(((0.675 * heightProperty.get()) - 56.25) * 100.00) / 100.00;
				idealWeightLabel.textProperty()
						.set("Tu peso ideal está entre " + idealWeight + "-" + (idealWeight + 5.00) + " kg");

			}
		}
	}

	/**
	 * Método para cambiar el avatar del usuario
	 */
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
				Messages.error("Error al cargar imagen", "No se ha podido cargar la imagen");
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