package dad.alimentapp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.main.App;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Origin;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Type;
import dad.alimentapp.utils.Messages;

/**
 * En esta clase tenemos almacenadas todas las consultas a la base de datos, en
 * referencia a la tabla Product.
 * 
 * @author Antonio
 *
 */
public class ProductService {
	/**
	 * Esta funcion la utilizamos para obtener un producto con un id especifico.
	 * 
	 * @param id paseremos el id del producto por parametros
	 * @return retornaremos el producto
	 */
	private static Product getProduct(Integer id) {
		Product product = null;
		try {
			String sql = "SELECT nombre, kcal, hydrates, fats, protein, fibres, image, id_origin, id_type FROM product WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Origin origin = Origin.valueOf(result.getInt(8));
				Type type = Type.valueOf(result.getInt(9));
				product = new Product(id, result.getString(1), result.getInt(2), result.getInt(3), result.getInt(4),
						result.getInt(5), result.getInt(6), result.getString(7), origin, type);
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al obtener el producto seleccionado");
		}
		return product;
	}

	/**
	 * Esta funcion la utilizaremos para obtener todos los productos.
	 * 
	 * @return nos retornara una lista de productos.
	 */
	public static List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		try {
			String sql = "SELECT id, nombre, kcal, hydrates, fats, protein, fibres, image, id_origin, id_type FROM product";
			PreparedStatement query = App.connection.prepareStatement(sql);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Origin origin = Origin.valueOf(result.getInt(9));
				Type type = Type.valueOf(result.getInt(10));
				Product product = new Product(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4),
						result.getInt(5), result.getInt(6), result.getInt(7), result.getString(8), origin, type);
				products.add(product);
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al cargar la lista de todos los productos");
		}
		return products;
	}

	/**
	 * El metodo "getAllProductsToMenuOfMomentDay" obtenemos todos los productos de
	 * ese menu en un momento del dia.
	 * 
	 * @param idMenu      pasamos por parametros el menu.
	 * @param momentDay pasamos por parametros el momento del dia.
	 * @return retornamos todos los productos del menu para ese momento del dia.
	 */

	public static ProductMomentDay getAllProductsToMenuOfMomentDay(Integer idMenu, MomentDay momentDay) {
		ProductMomentDay productMomentDay = new ProductMomentDay(momentDay);
		try {
			String sql = "SELECT id_product FROM menu_product WHERE id_menu = ? AND id_moment_day = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, idMenu);
			query.setInt(2, momentDay.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				productMomentDay.getProducts().add(getProduct(result.getInt(1)));
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al obtener todos los productos del menu indicado para " + momentDay);
		}
		return productMomentDay;
	}
}
