
package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Representamos la tabla Dietas con un clase y generamos sus getter and setters. Para poder obtener toda la información. 
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
}
