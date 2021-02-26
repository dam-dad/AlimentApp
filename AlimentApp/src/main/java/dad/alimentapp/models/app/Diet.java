package dad.alimentapp.models.app;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.models.db.Profile;
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

public class Diet {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Profile> profile = new SimpleObjectProperty<>();
	private ListProperty<DailyMenu> dailyMenu = new SimpleListProperty<>(FXCollections.observableArrayList());

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

	public final ListProperty<DailyMenu> dailyMenuProperty() {
		return this.dailyMenu;
	}

	public final ObservableList<DailyMenu> getDailyMenu() {
		return this.dailyMenuProperty().get();
	}

	public final void setDailyMenu(final ObservableList<DailyMenu> dailyMenu) {
		this.dailyMenuProperty().set(dailyMenu);
	}
}
