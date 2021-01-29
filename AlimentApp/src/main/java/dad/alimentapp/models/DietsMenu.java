package dad.alimentapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DietsMenu {
	
	private ObjectProperty<Diet> diets = new SimpleObjectProperty<>();
	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();
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
	
	public final ObjectProperty<Menu> menuProperty() {
		return this.menu;
	}
	
	public final Menu getMenu() {
		return this.menuProperty().get();
	}
	
	public final void setMenu(final Menu menu) {
		this.menuProperty().set(menu);
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
}
