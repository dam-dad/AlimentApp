package dad.alimentapp.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Representamos la tabla Dietas menu con un clase y generamos sus getter and setters. Para poder obtener toda la información. 
 * @author Antonio
 *
 */
public class DietsMenu {
	
	private ObjectProperty<Diet> diets = new SimpleObjectProperty<>();
	private ListProperty<Menu> menu = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Weekday> weekday = new SimpleObjectProperty<>();
	
	public final ObjectProperty<Diet> dietsProperty() {
		return this.diets;
	}
	
	public final Diet getDiets() {
		return this.dietsProperty().get();
	}
	
	public final void setDiets(final Diet diets) {
		this.dietsProperty().set(diets);
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

	public final ListProperty<Menu> menuProperty() {
		return this.menu;
	}	

	public final ObservableList<Menu> getMenu() {
		return this.menuProperty().get();
	}	

	public final void setMenu(final ObservableList<Menu> menu) {
		this.menuProperty().set(menu);
	}
}
