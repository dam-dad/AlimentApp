package dad.alimentapp.models.app;

import dad.alimentapp.models.Weekday;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DailyMenu {

	private ObjectProperty<Weekday> weekday = new SimpleObjectProperty<>();
	private ListProperty<Menu> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public final ObjectProperty<Weekday> weekdayProperty() {
		return this.weekday;
	}
	
	public final Weekday getWeekday() {
		return this.weekdayProperty().get();
	}
	
	public final void setWeekday(final Weekday weekday) {
		this.weekdayProperty().set(weekday);
	}
	
	public final ListProperty<Menu> menusProperty() {
		return this.menus;
	}
	
	public final ObservableList<Menu> getMenus() {
		return this.menusProperty().get();
	}
	
	public final void setMenus(final ObservableList<Menu> menus) {
		this.menusProperty().set(menus);
	}	
}
