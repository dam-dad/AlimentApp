package dad.alimentapp.models.db;

import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Representamos la tabla menu product con un clase y generamos sus getter and setters. Para poder obtener toda la información. 
 * Además de algunos metodos.
 * @author Antonio
 *
 */
public class MenuProduct {

	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();
	private ListProperty<Product> product = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<MomentDay> momentDay = new SimpleObjectProperty<>();
	
	public MenuProduct(Menu menu, MomentDay momentDay) {
		this.setMenu(menu);
		this.setMomentDay(momentDay);
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
	
	public final ObjectProperty<MomentDay> momentDayProperty() {
		return this.momentDay;
	}
	
	public final MomentDay getMomentDay() {
		return this.momentDayProperty().get();
	}
	
	public final void setMomentDay(final MomentDay momentDay) {
		this.momentDayProperty().set(momentDay);
	}

	public final ListProperty<Product> productProperty() {
		return this.product;
	}

	public final ObservableList<Product> getProduct() {
		return this.productProperty().get();
	}

	public final void setProduct(final ObservableList<Product> product) {
		this.productProperty().set(product);
	}
	
	
	@Override
	public String toString() {
		return "MenuProduct [menu=" + menu + ", product=" + product + ", momentDay=" + momentDay + "]";
	}
}
