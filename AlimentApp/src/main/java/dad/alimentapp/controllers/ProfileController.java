package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Gender;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.Profile;
import dad.alimentapp.service.ProductService;
import dad.alimentapp.service.ProfileService;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Clase que muestra el conjunto de perfiles almacenados, permite acceder a ellos y borrarlos, así como acceder a la creación de un nuevo perfil
 * @author David_Diaz
 *
 */
public class ProfileController implements Initializable {

	// view
	@FXML
	private BorderPane view;

	@FXML
	private  ListView<Profile> profileView;

	@FXML
	private Button newProfileButton;

	@FXML
	private Button entryButton;

	@FXML
	private Button deleteButton;

	@FXML
	private ImageView adviceImageView;

	// model
	public static ListProperty<Profile> profileList = new SimpleListProperty<>(
			FXCollections.observableArrayList(new ArrayList<>()));
	private static ObjectProperty<Profile> profileSelected = new SimpleObjectProperty<>();	

	//DATA
	private static List<Product> products;

	public ProfileController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InfoView.fxml"));
		loader.setController(this);
		loader.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		ProfileService.loadProfiles();
		
		profileView.itemsProperty().bind(profileList);

			profileView.setCellFactory(profileView -> new ListCell<Profile>() {
		    private ImageView imageView = new ImageView();
		    @Override
		    public void updateItem(Profile profile, boolean empty) {
		        super.updateItem(profile, empty);
		        if (empty) {
		            setText(null);
		            setGraphic(null);
		        } else {
		            Image image = new Image("images/infoTab/icon_listView.png");
		            imageView.setImage(image);
		            imageView.setFitWidth(40);
		            imageView.setFitHeight(40);
		            setText(profile.toString());
		            setGraphic(imageView);
		        }
		    }
		});
		


			MainController.setProfileSelected(ProfileService.getProfile(1)); // TODO PROVISIONAL
			products = ProductService.getAllProducts();
			loadMainImage();
		
		entryButton.setOnAction(e->onEntryButtonAction());
		deleteButton.setOnAction(e->onDeleteButtonAction());
		newProfileButton.setOnAction(e->onNewProfileButtonAction());
		
		newProfileButton.disableProperty().bind(profileView.getSelectionModel().selectedItemProperty().isNotNull());
		entryButton.disableProperty().bind(profileView.getSelectionModel().selectedItemProperty().isNull());
		deleteButton.disableProperty().bind(profileView.getSelectionModel().selectedItemProperty().isNull());
	}




/**
 * Método para cargar la información de un nuevo perfil
 */
private void onNewProfileButtonAction() {
	
	
	App.getMainController().getDataTab().getSelectionModel().select(1);
	App.getMainController().playTransition();
	App.getMainController().getMyData().setDisable(false);
	App.getMainController().getManageDietsTab().setDisable(true);
	MainController.setProfileSelected(new Profile("","","",0,0,0,0.0,Gender.HOMBRE));
	profileSelected.set(new Profile("","","",0,0,0,0.0,Gender.HOMBRE));
	
	
		
	
	}

/**
 * Método para borrar el perfil seleccionado
 */
private void onDeleteButtonAction() {
		profileSelected.set(profileView.getSelectionModel().getSelectedItem());
		
		Optional <ButtonType> result= Messages.confirmation("Borrar un perfil", "¿Seguro que desea borrar este perfil?");
		
		if(result.get() == ButtonType.OK) {
			
		int id= profileSelected.get().getId();
		
		try {
			String sql = "delete FROM profile WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			query.executeUpdate();
			Messages.info("Perfil borrado", "Se ha borrado el perfil correctamente");
			profileList.clear();
			ProfileService.loadProfiles();
			App.getMainController().getMyData().setDisable(true);
			App.getMainController().getManageDietsTab().setDisable(true);
		}catch(Exception e) {
			Messages.error("Ha ocurrido un error", "No se ha podido borrar el usuario seleccionado.");
			
		}
		
		
		}
	}


/**
 * Método que carga los datos del perfil seleccionado y los muestra en la pestaña correspondiente
 */
private void onEntryButtonAction() {
	
	
	profileSelected.set( profileView.getSelectionModel().getSelectedItem());
	MainController.setProfileSelected(profileSelected.get());
	App.getMainController().getDataTab().getSelectionModel().select(1);
	App.getMainController().getMyData().setDisable(false);
	App.getMainController().getManageDietsTab().setDisable(false);
	App.getMainController().playTransition();
	
	
}





/**
 * Método que carga aleatoriamente una imagen en la pestaña Informacion cada vez que la aplicación se inicia	
 */
	private void loadMainImage() {

		int nPhoto = (int) (Math.random() * (10 - 1)) + 1;
		adviceImageView.setImage(new Image("images/infoTab/" + nPhoto + ".png"));
	}

	



	
	
	
	public static ObjectProperty<Profile> getProfile() {
		return profileSelected;
	}

	public static ListProperty<Profile> getProfileList() {
		return profileList;
	}
	
	public static List<Product> getProducts() {
		return products;
	}

	public BorderPane getView() {
		return view;
	}

}
