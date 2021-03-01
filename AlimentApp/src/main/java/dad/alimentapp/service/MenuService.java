package dad.alimentapp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Profile;
import dad.alimentapp.utils.Messages;

/**
 * En esta clase tenemos almacenadas todas las consultas a la base de datos, en
 * referencia a la tabla Menu.
 * 
 * @author Antonio y Andy
 *
 */
public class MenuService {
	/**
	 * @author Antonio El metodo "getAllMenus" lo utilizamos para obtener una lista
	 *         de menus del perfil indicado.
	 *
	 * @param profile le pasamos el objeto profile para realizar la busqueda de los
	 *                menus que le pertenecen.
	 * @return retornamos la lista de menus.
	 */
	public static List<Menu> getAllMenus(Profile profile) {
		List<Menu> menuList = new ArrayList<>();
		try {
			String sql = "SELECT id, name FROM menu WHERE profile_id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, profile.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Integer id = result.getInt(1);
				String name = result.getString(2);
				Menu menu = new Menu(id, name, profile);
				menu.setBreakfastProducts(ProductService.getAllProductsToMenuOfMomentDay(id, MomentDay.DESAYUNO));
				menu.setMidMorningProducts(ProductService.getAllProductsToMenuOfMomentDay(id, MomentDay.MEDIA_MAÑANA));
				menu.setLunchProducts(ProductService.getAllProductsToMenuOfMomentDay(id, MomentDay.ALMUERZO));
				menu.setSnackProducts(ProductService.getAllProductsToMenuOfMomentDay(id, MomentDay.MERIENDA));
				menu.setDinnerProducts(ProductService.getAllProductsToMenuOfMomentDay(id, MomentDay.CENA));
				menuList.add(menu);
			}
		} catch (SQLException e) {
			Messages.error("Error",
					"Error al cargar la lista con todos los menus del perfil " + profile.getNameProfile());
		}
		return menuList;
	}

	/**
	 * @author Antonio 
	 * El metodo "getMenu" lo utilizamos para obtener un menu a
	 *         través de su identificador el cual recibimos por parametros.
	 * 
	 * @param menu_id le pasamos el identificador del menu.
	 * @return retornamos el menu resultante.
	 */
	public static Menu getMenu(Integer menu_id) {
		Menu menu = null;
		try {
			String sql = "SELECT name, profile_id FROM menu WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, menu_id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				String name = result.getString(1);
				Profile profile = ProfileService.getProfile(result.getInt(2));
				menu = new Menu(menu_id, name, profile);
				menu.setBreakfastProducts(ProductService.getAllProductsToMenuOfMomentDay(menu_id, MomentDay.DESAYUNO));
				menu.setMidMorningProducts(
						ProductService.getAllProductsToMenuOfMomentDay(menu_id, MomentDay.MEDIA_MAÑANA));
				menu.setLunchProducts(ProductService.getAllProductsToMenuOfMomentDay(menu_id, MomentDay.ALMUERZO));
				menu.setSnackProducts(ProductService.getAllProductsToMenuOfMomentDay(menu_id, MomentDay.MERIENDA));
				menu.setDinnerProducts(ProductService.getAllProductsToMenuOfMomentDay(menu_id, MomentDay.CENA));
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al obtenner el menu selecionado");
		}
		return menu;
	}

	/**
	 * @author Antonio El metodo "updateMenu" lo utilizamos para actualizar la
	 *         informacion de un menu que recibimos por parametros.
	 * 
	 * @param menu recibimos el menu que vamos a actualizar.
	 */
	public static void updateMenu(Menu menu) {
		try {
			String sql = "UPDATE menu SET name = ? WHERE profile_id = ? AND id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setString(1, menu.getName());
			query.setInt(2, menu.getProfile().getId());
			query.setInt(3, menu.getId());
			query.execute();
			deleteAllProductsForMenuAllMomentDay(menu.getId());
			insertAllProductsInMenuForAllMomentDay(menu);
		} catch (SQLException e) {
			Messages.error("Error", "Error al modificar el menú: " + menu.getName());
		}
	}

	/**
	 * @author Antonio El metodo "insertAllProductsInMenuForAllMomentDay" se encarga
	 *         de insertar cada menu en sus respectivo momento del dia.
	 * @param menu
	 */
	private static void insertAllProductsInMenuForAllMomentDay(Menu menu) {
		insertProductsInMenuForMomentDay(menu.getId(), menu.getBreakfastProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getMidMorningProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getLunchProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getSnackProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getDinnerProducts());
	}

	/**
	 * @author Antonio El metodo "insertProductsInMenuForMomentDay" se encarga de
	 *         insertar todos los productos de un menu para un momento del dia.
	 * @param idMenu
	 * @param productMomentDay
	 */
	private static void insertProductsInMenuForMomentDay(Integer idMenu, ProductMomentDay productMomentDay) {
		for (Product product : productMomentDay.getProducts()) {
			insertMenuProduct(idMenu, product.getId(), productMomentDay.getMomentDay().getId());
		}
	}

	/**
	 * @author Antonio El metodo "insertMenuProduct" se encarga de insertar un
	 *         productos de un menu para un momento del dia.
	 * @param id_menu
	 * @param id_product
	 * @param id_moment_day
	 * @return
	 */
	private static boolean insertMenuProduct(Integer id_menu, Integer id_product, Integer id_moment_day) {
		boolean resultado = true;
		try {
			String sql = "INSERT INTO menu_product(id_menu,id_product, id_moment_day) VALUES (?,?,?)";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id_menu);
			query.setInt(2, id_product);
			query.setInt(3, id_moment_day);
			resultado = query.execute();

		} catch (SQLException e) {
			Messages.error("Error", "Error al insertar el nuevo producto en el menu");
		}
		return resultado;
	}

	/**
	 * @author Antonio El metodo "deleteAllProductsForMenuAllMomentDay" se encarga
	 *         de eliminar todos los productos de un menu para un momento del dia.
	 * @param menuId
	 */
	private static void deleteAllProductsForMenuAllMomentDay(Integer menuId) {
		deleteAllProductsForMomentDay(menuId, MomentDay.DESAYUNO);
		deleteAllProductsForMomentDay(menuId, MomentDay.MEDIA_MAÑANA);
		deleteAllProductsForMomentDay(menuId, MomentDay.ALMUERZO);
		deleteAllProductsForMomentDay(menuId, MomentDay.MERIENDA);
		deleteAllProductsForMomentDay(menuId, MomentDay.CENA);
	}

	/**
	 * @author Antonio El metodo "deleteAllProductsForMomentDay" lo utilizamnos para
	 *         eliminar todos los productos de un momento del dia.
	 * 
	 * @param idMenu    recibimos el id del menu al cual le vamos a borrar los
	 *                  productos.
	 * @param momentDay le indicamos el momento del dia en el que queremos eliminar
	 *                  los productos.
	 */
	public static void deleteAllProductsForMomentDay(Integer idMenu, MomentDay momentDay) {
		try {
			String sql = "DELETE FROM menu_product WHERE id_menu = ? AND id_moment_day = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, idMenu);
			query.setInt(2, momentDay.getId());
			query.execute();
		} catch (Exception e) {
			Messages.error("Error", "Error: No se pudo eliminar el producto seleccionado de dicho menu");
		}
	}

	/**
	 * @author Antonio El metodo "insertMenu" lo utilizaremos para insertar un menu
	 *         y todos sus productos relacionados.
	 * 
	 * @param menu le pasaremos el menu a insertar.
	 */
	public static void insertMenu(Menu menu) {
		try {
			String sql = "INSERT INTO menu (name, profile_id) VALUES (?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setString(1, menu.getName());
			query.setInt(2, menu.getProfile().getId());
			query.execute();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				menu.setId(generatedKeys.getInt(1));
			}
			insertAllProductsInMenuForAllMomentDay(menu);
		} catch (SQLException e) {
			Messages.error("Error", "Error al insertar el nuevo menú");
		}
	}

	/**
	 * Esta función deleteMenu, nos permite eliminar un menú y sus claves foráneas
	 * 
	 * @param menu, es el menú que se eliminará
	 */
	public static void deleteMenu(Menu menu) {
		try {
			String sql = "SELECT id_diets FROM diets_menus WHERE id_menu = ?";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, menu.getId());
			ResultSet rs = query.executeQuery();
		    while(rs.next())
		            {
		                if(rs.getInt(2) == menu.getId()) {
		                int idDieta = rs.getInt(1);
		                sql = "DELETE FROM diets WHERE id_diet = ?";
		    			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    			query.setInt(1, idDieta);
		    			query.execute();
		                }
		            }
			sql = "DELETE FROM diets_menus WHERE id_menu = ?";
			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, menu.getId());
			query.execute();
			sql = "DELETE FROM menu_product WHERE id_menu = ?";
			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, menu.getId());
			query.execute();
			sql = "DELETE FROM menu WHERE id = ?";
			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, menu.getId());
			query.execute();
			Messages.info("Menú eliminado", "El menú ha sido eliminado correctamente.");
		} catch (SQLException e) {
			Messages.error("Error al eliminar el menú", "Asegúrese de eliminar primero la dieta en la que se encuentra el menú seleccionado para poder eliminar posteriormente el menú.");
		}
	}

}
