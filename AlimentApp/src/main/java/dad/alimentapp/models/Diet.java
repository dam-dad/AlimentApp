
package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private ObjectProperty<User> user = new SimpleObjectProperty<>();
		
	public Diet() {}
	
	public Diet(String name) {
		this.setName(name);
	}
	
	public Diet(Integer id, String name, User user) {
		this.setId(id);
		this.setName(name);
		this.setUser(user);
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

	public final ObjectProperty<User> userProperty() {
		return this.user;
	}	

	public final User getUser() {
		return this.userProperty().get();
	}	

	public final void setUser(final User user) {
		this.userProperty().set(user);
	}	
	
	public static Diet getDiet(Integer id) {
		Diet diet = null;
		try {
			String sql = "SELECT id, name, user_id FROM diets WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				User user = User.getUser(result.getInt(3));
				diet = new Diet(result.getInt(1), result.getString(2), user);
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el menu selecionado",  e.getMessage());
		}
		return diet;
	}
}
