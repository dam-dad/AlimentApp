package dad.alimentapp.models.db;

import dad.alimentapp.models.Origin;
import dad.alimentapp.models.Type;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
/**
 * Representamos la tabla Product con un clase y generamos sus getter and setters. Para poder obtener toda la información.
 *  Además de algunos metodos.
 * @author Antonio
 *
 */
public class Product {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private IntegerProperty kcal = new SimpleIntegerProperty();
	private IntegerProperty hydrates = new SimpleIntegerProperty();
	private IntegerProperty fats = new SimpleIntegerProperty();
	private IntegerProperty protein = new SimpleIntegerProperty();
	private IntegerProperty fibres = new SimpleIntegerProperty();
	private ObjectProperty<Origin> origin = new SimpleObjectProperty<>();
	private ObjectProperty<Type> type = new SimpleObjectProperty<>();
	private ObjectProperty<Image> image = new SimpleObjectProperty<>();
	
	/**
	 * Creamos una funcion para asignar un producto por defecto.
	 * @return retorna el producto por defecto.
	 */
	public static Product productDefault() {
		return new Product("Selecciona un producto", 0, 0, 0, 0, 0,
				new Image(Product.class.getResource("/images/createDietTab/products/food-128px.png").toExternalForm()),
				Origin.INDEFINIDO, Type.INDEFINIDO);
	}

	public Product() {
	};

	public Product(Integer id, String name, Integer kcal, Integer hydrates, Integer fats, Integer protein,
			Integer fibres, String image, Origin origin, Type type) {
		super();
		this.setId(id);
		this.setName(name);
		this.setKcal(kcal);
		this.setHydrates(hydrates);
		this.setFats(fats);
		this.setProtein(protein);
		this.setFibres(fibres);
		this.setImage(convertStringToImage(image));
		this.setOrigin(origin);
		this.setType(type);
	}

	public Product(String name, Integer kcal, Integer hydrates, Integer fats, Integer protein, Integer fibres,
			Image image, Origin origin, Type type) {
		super();
		this.setName(name);
		this.setKcal(kcal);
		this.setHydrates(hydrates);
		this.setFats(fats);
		this.setProtein(protein);
		this.setFibres(fibres);
		this.setImage(image);
		this.setOrigin(origin);
		this.setType(type);
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

	public final IntegerProperty kcalProperty() {
		return this.kcal;
	}

	public final int getKcal() {
		return this.kcalProperty().get();
	}

	public final void setKcal(final int kcal) {
		this.kcalProperty().set(kcal);
	}

	public final IntegerProperty hydratesProperty() {
		return this.hydrates;
	}

	public final int getHydrates() {
		return this.hydratesProperty().get();
	}

	public final void setHydrates(final int hydrates) {
		this.hydratesProperty().set(hydrates);
	}

	public final IntegerProperty fatsProperty() {
		return this.fats;
	}

	public final int getFats() {
		return this.fatsProperty().get();
	}

	public final void setFats(final int fats) {
		this.fatsProperty().set(fats);
	}

	public final IntegerProperty proteinProperty() {
		return this.protein;
	}

	public final int getProtein() {
		return this.proteinProperty().get();
	}

	public final void setProtein(final int protein) {
		this.proteinProperty().set(protein);
	}

	public final IntegerProperty fibresProperty() {
		return this.fibres;
	}

	public final int getFibres() {
		return this.fibresProperty().get();
	}

	public final void setFibres(final int fibres) {
		this.fibresProperty().set(fibres);
	}

	public final ObjectProperty<Origin> originProperty() {
		return this.origin;
	}

	public final Origin getOrigin() {
		return this.originProperty().get();
	}

	public final void setOrigin(final Origin origin) {
		this.originProperty().set(origin);
	}

	public final ObjectProperty<Type> typeProperty() {
		return this.type;
	}

	public final Type getType() {
		return this.typeProperty().get();
	}

	public final void setType(final Type type) {
		this.typeProperty().set(type);
	}

	public final ObjectProperty<Image> imageProperty() {
		return this.image;
	}

	public final Image getImage() {
		return this.imageProperty().get();
	}

	public final void setImage(final Image image) {
		this.imageProperty().set(image);
	}

	private Image convertStringToImage(String urlImage) {
		return new Image(urlImage);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		final Product other = (Product) obj;
		if (this.getId() != other.getId()) {
			return false;
		}

		return true;
	}
}
