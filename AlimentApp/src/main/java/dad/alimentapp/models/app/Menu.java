package dad.alimentapp.models.app;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Profile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Menu {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Profile> profile = new SimpleObjectProperty<>();
	private ObjectProperty<ProductMomentDay> breakfastProducts = new SimpleObjectProperty<>(
			new ProductMomentDay(MomentDay.DESAYUNO));
	private ObjectProperty<ProductMomentDay> midMorningProducts = new SimpleObjectProperty<>(
			new ProductMomentDay(MomentDay.MEDIA_MAÑANA));
	private ObjectProperty<ProductMomentDay> lunchProducts = new SimpleObjectProperty<>(
			new ProductMomentDay(MomentDay.ALMUERZO));
	private ObjectProperty<ProductMomentDay> snackProducts = new SimpleObjectProperty<>(
			new ProductMomentDay(MomentDay.MERIENDA));
	private ObjectProperty<ProductMomentDay> dinnerProducts = new SimpleObjectProperty<>(
			new ProductMomentDay(MomentDay.CENA));

	public Menu() {
		this.setName("Nuevo Menú");
		this.setProfile(MainController.getProfileSelected());
	}

	public Menu(Integer id, String name) {
		this.setId(id);
		this.setName(name);
		this.setProfile(MainController.getProfileSelected());
	}

	public Menu(Integer id, String name, Profile profile) {
		this.setId(id);
		this.setName(name);
		this.setProfile(profile);
	}

	public Menu(Integer id, String name, Profile profile, ProductMomentDay breakfastProducts,
			ProductMomentDay midMorningProducts, ProductMomentDay lunchProducts, ProductMomentDay snackProducts,
			ProductMomentDay dinnerProducts) {
		this.setId(id);
		this.setName(name);
		this.setProfile(profile);
		this.setBreakfastProducts(breakfastProducts);
		this.setMidMorningProducts(midMorningProducts);
		this.setLunchProducts(lunchProducts);
		this.setSnackProducts(snackProducts);
		this.setDinnerProducts(dinnerProducts);
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

	public final ObjectProperty<ProductMomentDay> breakfastProductsProperty() {
		return this.breakfastProducts;
	}

	public final ProductMomentDay getBreakfastProducts() {
		return this.breakfastProductsProperty().get();
	}

	public final void setBreakfastProducts(final ProductMomentDay breakfastProducts) {
		this.breakfastProductsProperty().set(breakfastProducts);
	}

	public final ObjectProperty<ProductMomentDay> midMorningProductsProperty() {
		return this.midMorningProducts;
	}

	public final ProductMomentDay getMidMorningProducts() {
		return this.midMorningProductsProperty().get();
	}

	public final void setMidMorningProducts(final ProductMomentDay midMorningProducts) {
		this.midMorningProductsProperty().set(midMorningProducts);
	}

	public final ObjectProperty<ProductMomentDay> lunchProductsProperty() {
		return this.lunchProducts;
	}

	public final ProductMomentDay getLunchProducts() {
		return this.lunchProductsProperty().get();
	}

	public final void setLunchProducts(final ProductMomentDay lunchProducts) {
		this.lunchProductsProperty().set(lunchProducts);
	}

	public final ObjectProperty<ProductMomentDay> snackProductsProperty() {
		return this.snackProducts;
	}

	public final ProductMomentDay getSnackProducts() {
		return this.snackProductsProperty().get();
	}

	public final void setSnackProducts(final ProductMomentDay snackProducts) {
		this.snackProductsProperty().set(snackProducts);
	}

	public final ObjectProperty<ProductMomentDay> dinnerProductsProperty() {
		return this.dinnerProducts;
	}

	public final ProductMomentDay getDinnerProducts() {
		return this.dinnerProductsProperty().get();
	}

	public final void setDinnerProducts(final ProductMomentDay dinnerProducts) {
		this.dinnerProductsProperty().set(dinnerProducts);
	}

	@Override
	public String toString() {
		return getName();
	}
}
