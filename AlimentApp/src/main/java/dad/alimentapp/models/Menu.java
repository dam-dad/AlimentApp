package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representamos la tabla menu con un clase y generamos sus getter and setters.
 * Para poder obtener toda la información. Además de algunos metodos.
 * 
 * @author Antonio
 *
 */
public class Menu {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Weekday> weekday = new SimpleObjectProperty<>();
	private ObjectProperty<Profile> profile = new SimpleObjectProperty<>();

	public Menu() {
		this.setName("Nuevo Menú");
		this.setWeekday(Weekday.LUNES);
	}

	public Menu(Integer id, String name, Weekday weekday) {
		this.setId(id);
		this.setName(name);
		this.setWeekday(weekday);
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

	public final ObjectProperty<Weekday> weekdayProperty() {
		return this.weekday;
	}

	public final Weekday getWeekday() {
		return this.weekdayProperty().get();
	}

	public final void setWeekday(final Weekday weekday) {
		this.weekdayProperty().set(weekday);
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

	/**
	 * Esta función getMenu, nos de vuelve un menu del id especificado.
	 * 
	 * @param id en este parametro especificaremos el id del menu.
	 * @return retornaremos un menu con dicho id.
	 */
	public static Menu getMenu(Integer id) {
		Menu menu = null;
		try {
			String sql = "SELECT id, name, id_weekday FROM menu WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				menu = new Menu(result.getInt(1), result.getString(2), Weekday.valueOf(result.getInt(3)));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el menu selecionado", e.getMessage());
		}
		return menu;
	}

	public static int insertMenu(String name, Weekday weekday) {
		int idResult = 0;
		try {
			String sql = "INSERT INTO menu (name, id_weekday) VALUES (?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setString(1, name);
			query.setInt(2, weekday.getId());
			query.execute();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				idResult = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			Messages.error("Error al insertar el nuevo menú", e.getMessage());
		}
		return idResult;
	}
}
