package dad.alimentapp.models.db;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.models.Weekday;
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
		this.setProfile(MainController.getProfileSelected());
	}
	
	public Menu(Weekday weekday) {
		this.setName("Nuevo Menú");
		this.setWeekday(weekday);
		this.setProfile(MainController.getProfileSelected());
	}

	public Menu(Integer id, String name, Weekday weekday) {
		this.setId(id);
		this.setName(name);
		this.setWeekday(weekday);
	}
	
	public Menu(Integer id, String name, Weekday weekday, Profile profile) {
		this.setId(id);
		this.setName(name);
		this.setWeekday(weekday);
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
	
	@Override
	public String toString() {
		return getName()+ " - " + getWeekday();
	}

}
