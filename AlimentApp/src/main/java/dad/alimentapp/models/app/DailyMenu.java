package dad.alimentapp.models.app;

import dad.alimentapp.models.Weekday;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DailyMenu {

	private ObjectProperty<Weekday> weekday = new SimpleObjectProperty<>();
	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();

	public DailyMenu() {
		this.setWeekday(Weekday.LUNES);
		this.setMenu(new Menu());
	}

	public DailyMenu(Weekday weekday, Menu menu) {
		this.setWeekday(weekday);
		this.setMenu(menu);
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

	public final ObjectProperty<Menu> menuProperty() {
		return this.menu;
	}

	public final Menu getMenu() {
		return this.menuProperty().get();
	}

	public final void setMenu(final Menu menu) {
		this.menuProperty().set(menu);
	}

	@Override
	public String toString() {
		return "[" + getMenu() + " - " + getWeekday() + "]";
	}
}
