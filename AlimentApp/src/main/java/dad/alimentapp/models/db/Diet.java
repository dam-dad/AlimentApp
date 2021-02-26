
package dad.alimentapp.models.db;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.models.Profile;
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
		
	@Override
	public String toString() {
		return getName();
	}
}
