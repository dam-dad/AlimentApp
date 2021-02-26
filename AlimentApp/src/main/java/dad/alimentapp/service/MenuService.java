package dad.alimentapp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.main.App;
import dad.alimentapp.models.MomentDay;
import dad.alimentapp.models.app.Menu;
import dad.alimentapp.models.app.ProductMomentDay;
import dad.alimentapp.models.db.Product;
import dad.alimentapp.models.db.Profile;
import dad.alimentapp.utils.Messages;

public class MenuService {

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
			Messages.error("Error al cargar todos los menus", e.getMessage());
		}
		return menuList;
	}
	
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
			Messages.error("Error al modificar este menú", e.getMessage());
		}
	}
	
	private static void insertAllProductsInMenuForAllMomentDay(Menu menu) {
		insertProductsInMenuForMomentDay(menu.getId(), menu.getBreakfastProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getMidMorningProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getLunchProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getSnackProducts());
		insertProductsInMenuForMomentDay(menu.getId(), menu.getDinnerProducts());
	}
	
	private static void insertProductsInMenuForMomentDay(Integer idMenu, ProductMomentDay productMomentDay) {
		for (Product product : productMomentDay.getProducts()) {
			insertMenuProduct(idMenu, product.getId(), productMomentDay.getMomentDay().getId());
		}
	}
	
	/**
	 * La función insertMenuProduct la usamos para insertar un producto en un menu
	 * en un momento del dia especificado.
	 * 
	 * @param id_menu       pasamos el id del menu.
	 * @param id_product    pasamos el id del producto.
	 * @param id_moment_day pasamos el id del moment day.
	 * @return retorna true o false dependiendo si inserta o no.
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
			Messages.error("Error al insertar el nuevo producto en el menu", e.getMessage());
		}
		return resultado;
	}
	
	private static void deleteAllProductsForMenuAllMomentDay(Integer menuId) {
		deleteAllProductsForMomentDay(menuId, MomentDay.DESAYUNO);
		deleteAllProductsForMomentDay(menuId, MomentDay.MEDIA_MAÑANA);
		deleteAllProductsForMomentDay(menuId, MomentDay.ALMUERZO);
		deleteAllProductsForMomentDay(menuId, MomentDay.MERIENDA);
		deleteAllProductsForMomentDay(menuId, MomentDay.CENA);
	}
	
	public static void deleteAllProductsForMomentDay(Integer idMenu, MomentDay momentDay) {
		try {
			String sql = "DELETE FROM menu_product WHERE id_menu = ? AND id_moment_day = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, idMenu);
			query.setInt(2, momentDay.getId());
			query.execute();
		} catch (Exception e) {
			Messages.error("Error: No se pudo eliminar el producto seleccionado de dicho menu", e.getMessage());
		}
	}
		
	/**
	 * Esta función getMenu, nos de vuelve un menu del id especificado.
	 * 
	 * @param id en este parametro especificaremos el id del menu.
	 * @return retornaremos un menu con dicho id.
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
			Messages.error("Error al insertar el nuevo menú", e.getMessage());
		}
	}
}
