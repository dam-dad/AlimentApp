
package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Representamos la tabla Dietas con una clase y generamos sus getter and setters. Para poder obtener toda la información. 
 * @author Antonio
 *
 */
public class Diet {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Profile> profile = new SimpleObjectProperty<>();
		
	public Diet() {}
	
	public Diet(String name) {
		this.setName(name);
		this.setProfile(MainController.getProfileSelected());
	}
	
	public Diet(Integer id, String name, Profile profile) {
		this.setId(id);
		this.setName(name);
		this.setProfile(profile);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ObjectProperty<Profile> profileProperty() {
		return this.profile;
	}	

	public final Profile getProfile() {
		return this.profileProperty().get();
	}	

	public final void setProfile(final Profile profile) {
		this.profileProperty().set(profile);
	}	
		
	@Override
	public String toString() {
		return getName();
	}

	public static Diet getDiet(Integer id) {
		Diet diet = null;
		try {
			String sql = "SELECT id, name, profile_id FROM diets WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Profile profile = Profile.getProfile(result.getInt(3));
				diet = new Diet(result.getInt(1), result.getString(2), profile);
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el menu selecionado",  e.getMessage());
		}
		return diet;
	}
	
	public static int insertDiet(Diet diet) {
		int idResult = 0;
		try {
			String sql = "INSERT INTO diets (name, profile_id) VALUES (?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setString(1, diet.getName());
			query.setInt(2, diet.getProfile().getId());
			query.execute();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				idResult = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			Messages.error("Error al insertar la nueva dieta", e.getMessage());
		}
		return idResult;
	}
	
	public static void updateDiet(Diet diet) {
		try {
			String sql = "UPDATE diets SET name = ? WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setString(1, diet.getName());
			query.setInt(2, diet.getId());
			query.execute();
		} catch (SQLException e) {
			Messages.error("Error al modificar esta dieta", e.getMessage());
		}
	}
	
	
	/**
	 * Esta función deleteDiet, nos permite eliminar una dieta
	 * 
	 * @param diet, es la dieta que se eliminará
	 */
	public static void deleteDiet(Diet diet) {
		try {
			String sql = "DELETE FROM diets_menus WHERE id_diets = ?";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, diet.getId());
			query.execute();
			sql = "DELETE FROM diets WHERE id = ?";
			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, diet.getId());
			query.execute();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Éxito en la eliminación");
			alert.setHeaderText("Se ha eliminado el menú correctamente.");
			alert.show();
		} catch (SQLException e) {
			Messages.error("Error al eliminar la dieta", e.getMessage());
		}
	}
	
	public static List<Diet> getAllDiets(Profile profile) {
		List<Diet> dietsList = new ArrayList<>();
		try {
			String sql = "SELECT * FROM diets WHERE profile_id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, profile.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Diet diet = new Diet(result.getInt(1), result.getString(2), profile);
				dietsList.add(diet);
			}
		} catch (SQLException e) {
			Messages.error("Error al cargar todas las dietas", e.getMessage());
		}
		return dietsList;		
	}
}
