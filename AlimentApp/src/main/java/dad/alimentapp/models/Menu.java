package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *  Representamos la tabla menu con un clase y generamos sus getter and setters. Para poder obtener toda la información.
 *  Además de algunos metodos. 
 * @author Antonio
 *
 */
public class Menu {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	
	public Menu(Integer id, String name) {
		this.setId(id);
		this.setName(name);
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
	/**
	 * Esta función getMenu, nos de vuelve un menu del id especificado.
	 * @param id en este parametro especificaremos el id del menu.
	 * @return retornaremos un menu con dicho id.
	 */
	public static Menu getMenu(Integer id) {
		Menu menu = null;
		try {
			String sql = "SELECT id, name FROM menu WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				menu = new Menu(result.getInt(1), result.getString(2));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el menu selecionado",  e.getMessage());
		}
		return menu;
	}
}
