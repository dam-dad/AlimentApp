package dad.alimentapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MenuProduct {

	private ObjectProperty<Menu> menu = new SimpleObjectProperty<>();
	private ObjectProperty<Product> product = new SimpleObjectProperty<>();
	private ObjectProperty<MomentDay> momentDay = new SimpleObjectProperty<>();
	
	public final ObjectProperty<Menu> menuProperty() {
		return this.menu;
	}
	
	public final Menu getMenu() {
		return this.menuProperty().get();
	}
	
	public final void setMenu(final Menu menu) {
		this.menuProperty().set(menu);
	}
	
	public final ObjectProperty<Product> productProperty() {
		return this.product;
	}
	
	public final Product getProduct() {
		return this.productProperty().get();
	}
	
	public final void setProduct(final Product product) {
		this.productProperty().set(product);
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
}
