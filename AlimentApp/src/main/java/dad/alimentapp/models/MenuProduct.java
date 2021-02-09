package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
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
	
	/**
	 * La función getAllProductsToMenuOfMomentDay obtenemos todos los productos de ese menu en un momento del dia.
	 * @param menu pasamos por parametros el menu.
	 * @param momentDay pasamos por parametros el momentDay.
	 * @return retornamos todos los productos de ese menu en ese momento del dia.
	 */
	
	public static MenuProduct getAllProductsToMenuOfMomentDay(Menu menu, MomentDay momentDay) {
		MenuProduct menuProduct = new MenuProduct(menu, momentDay);
		try {
			String sql = "SELECT id_product FROM menu_product WHERE id_menu = ? AND id_moment_day = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, menu.getId());
			query.setInt(2, momentDay.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				menuProduct.product.add(Product.getProduct(result.getInt(1)));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtener todos los productos del menu indicado",  e.getMessage());
		}
		return menuProduct;
	}
	/**
	 * La función insertMenuProduct la usamos para insertar un producto en un menu en un momento del dia especificado.
	 * @param id_menu pasamos el id del menu.
	 * @param id_product pasamos el id del producto.
	 * @param id_moment_day pasamos el id del moment day.
	 * @return retorna true o false dependiendo si inserta o no.
	 */
	public static boolean insertMenuProduct(Integer id_menu, Integer id_product, Integer id_moment_day) {
		boolean resultado = true;
		try {
			String sql = "INSERT INTO menu_product(id_menu,id_product, id_moment_day) VALUES (?,?,?)";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id_menu);
			query.setInt(2, id_product);
			query.setInt(3, id_moment_day);
			resultado = query.execute();
			
		} catch (SQLException e) {
			Messages.error("Error al insertar el nuevo producto en el menu", e.getMessage());
		}
		return resultado;
	}
	/**
	 * La función deleteMenuProduct la usamos para eliminar un producto en un menu en un momento del dia especificado.
	 * @param id_menu pasamos el id del menu.
	 * @param id_product pasamos el id del producto.
	 * @param id_moment_day pasamos el id del moment day.
	 */
	public static void deleteMenuProduct(Integer id_menu, Integer id_product, Integer id_moment_day) {
		try {
			String sql = "DELETE FROM menu_product WHERE id_menu = ? AND id_product = ? AND id_moment_day = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id_menu);
			query.setInt(2, id_product);
			query.setInt(3, id_moment_day);
			query.execute();
		} catch (Exception e) {
			Messages.error("Error: No se pudo eliminar el producto seleccionado de dicho menu", e.getMessage());
		}
	}
}
