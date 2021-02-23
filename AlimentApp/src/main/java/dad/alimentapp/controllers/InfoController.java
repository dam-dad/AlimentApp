package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


import dad.alimentapp.main.App;
import dad.alimentapp.models.Gender;
import dad.alimentapp.models.Profile;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class InfoController implements Initializable{
		
		
		//view
	   @FXML
	    private BorderPane view;

	    @FXML
	    private ListView<Profile> profileView;

	    @FXML
	    private Button newProfileButton;

	    @FXML
	    private Button entryButton;

	    @FXML
	    private Button deleteButton;

	    @FXML
	    private ImageView adviceImageView;
	    
	//model
	    private static ListProperty<Profile> profileList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	    private static ObjectProperty<Profile> profileSelected= new SimpleObjectProperty<>();
	    
	
	public InfoController() throws IOException  {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InfoView.fxml"));
		loader.setController(this);
		loader.load();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		loadProfiles();
		profileView.itemsProperty().bind(profileList);
		
		MainController.setProfileSelected(Profile.getProfile(1)); //TODO PROVISIONAL
		loadMainImage();
		
		entryButton.setOnAction(e->onEntryButtonAction());
		deleteButton.setOnAction(e->onDeleteButtonAction());
	}

	

private void onDeleteButtonAction() {
		profileSelected.set(profileView.getSelectionModel().getSelectedItem());
		
		Optional result= Messages.confirmation("Borrar un perfil", "¿Seguro que desea borrar este perfil?");
		
		if(result.isPresent()) {
			
		int id= profileSelected.get().getId();
		
		try {
			String sql = "delete FROM profile WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet executeDelete = query.executeQuery();
			profileList.remove(profileSelected);	
		}catch(Exception e) {
			Messages.error("Ha ocurrido un error", "No se ha podido borrar el usuario seleccionado.");
		}
		
		
		}
	}

private void onEntryButtonAction() {
	
	//CARGAMOS LOS DATOS DEL PERFIL CORRESPONDIENTE
	profileSelected.set( profileView.getSelectionModel().getSelectedItem());
	DataController.setProfile(profileSelected.get());
	App.getMainController().getDataTab().getSelectionModel().select(1);
	
		
	}

//Método que carga aleatoriamente una imagen en la pestaña Informacion cada vez que la aplicación se inicia	
private void loadMainImage() {
		
	int nPhoto=(int)(Math.random() * (10 - 1)) + 1;
	adviceImageView.setImage(new Image("images/infoTab/"+nPhoto+".png"));
		
	}


	private void loadProfiles() {
	
	try {
		String sql = "SELECT * FROM profile";
		PreparedStatement query = App.connection.prepareStatement(sql);
		ResultSet result = query.executeQuery();
		while (result.next()) {
			profileList.add( new Profile(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),
					result.getInt(5), result.getInt(6), result.getDouble(7), Gender.valueOf(result.getInt(7))));
		}
	} catch (SQLException e) {
		Messages.error("Error al obtener la lista de perfiles", e.getMessage());
	}
	
}

public static  ObjectProperty <Profile> getProfile() {
	return profileSelected;
}

public static ListProperty<Profile> getProfileList() {
	return profileList;
}


public BorderPane getView() {
	return view;
}	


	

}
