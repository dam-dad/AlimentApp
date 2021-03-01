package dad.alimentapp.models;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductMomentDay {
	private ObjectProperty<MomentDay> momentDay = new SimpleObjectProperty<>();
	private ListProperty<Product> products = new SimpleListProperty<>(FXCollections.observableArrayList());
		
	public ProductMomentDay(MomentDay momentDay) {
		this.setMomentDay(momentDay);
	}
	
	public ProductMomentDay(MomentDay momentDay, List<Product> products) {
		this.setMomentDay(momentDay);
		this.getProducts().setAll(products);
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
	
	public final ListProperty<Product> productsProperty() {
		return this.products;
	}
	
	public final ObservableList<Product> getProducts() {
		return this.productsProperty().get();
	}
	
	public final void setProducts(final ObservableList<Product> products) {
		this.productsProperty().set(products);
	}	
}
