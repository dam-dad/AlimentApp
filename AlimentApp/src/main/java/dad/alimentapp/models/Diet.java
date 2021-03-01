package dad.alimentapp.models;

import dad.alimentapp.controllers.MainController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La clase "Diet" la utilizamos para almacenar la informacion de una dieta
 * junto con la lista de menus que posee.
 * 
 * @author Antonio
 *
 */
public class Diet {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Profile> profile = new SimpleObjectProperty<>();
	private ListProperty<DailyMenu> dailyMenus = new SimpleListProperty<>(FXCollections.observableArrayList());

	public Diet() {
		this.setName("Nueva Dieta");
		this.setProfile(MainController.getProfileSelected());
	}

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

	public final ListProperty<DailyMenu> dailyMenusProperty() {
		return this.dailyMenus;
	}

	public final ObservableList<DailyMenu> getDailyMenus() {
		return this.dailyMenusProperty().get();
	}

	public final void setDailyMenus(final ObservableList<DailyMenu> dailyMenus) {
		this.dailyMenusProperty().set(dailyMenus);
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean isEqualTo(Diet diet) {
		return name.get().equals(diet.getName());
	}

	public void clear() {
		for (int i = dailyMenus.size() - 1; i >= 0; i--) {
			if (dailyMenus.get(i).getMenu().isMomentsDayEmpty()) {
				dailyMenus.removeAll(dailyMenus.get(i));
			}
		}
	}
}
