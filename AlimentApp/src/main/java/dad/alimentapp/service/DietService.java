package dad.alimentapp.service;

public class DietService {

	// MENU

//	public static Menu getMenu(Integer id) {
//		Menu menu = null;
//		try {
//			String sql = "SELECT name FROM menu WHERE id = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setInt(1, id);
//			ResultSet result = query.executeQuery();
//			while (result.next()) {
//				menu = new Menu(id, result.getString(1));
//			}
//			menu.setBreakfastProducts(getAllProductsToMenuOfMomentDay(id, MomentDay.DESAYUNO));
//			menu.setMidMorningProducts(getAllProductsToMenuOfMomentDay(id, MomentDay.MEDIA_MAÑANA));
//			menu.setLunchProducts(getAllProductsToMenuOfMomentDay(id, MomentDay.ALMUERZO));
//			menu.setSnackProducts(getAllProductsToMenuOfMomentDay(id, MomentDay.MERIENDA));
//			menu.setDinnerProducts(getAllProductsToMenuOfMomentDay(id, MomentDay.CENA));
//		} catch (SQLException e) {
//			Messages.error("Error al obtenner el menu selecionado", e.getMessage());
//		}
//		return menu;
//	}

//	/**
//	 * La función deleteMenuProduct la usamos para eliminar un producto en un menu
//	 * en un momento del dia especificado.
//	 * 
//	 * @param id_menu       pasamos el id del menu.
//	 * @param id_product    pasamos el id del producto.
//	 * @param id_moment_day pasamos el id del moment day.
//	 */
//	public static void deleteMenuProduct(Integer idMenu, Integer idProduct, MomentDay momentDay) {
//		try {
//			String sql = "DELETE FROM menu_product WHERE id_menu = ? AND id_product = ? AND id_moment_day = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setInt(1, idMenu);
//			query.setInt(2, idProduct);
//			query.setInt(3, momentDay.getId());
//			query.execute();
//		} catch (Exception e) {
//			Messages.error("Error: No se pudo eliminar el producto seleccionado de dicho menu", e.getMessage());
//		}
//	}

	// DIETAS

//	public static List<Diet> getAllDiets(Profile profile) {
//		List<Diet> dietsList = new ArrayList<>();
//		try {
//			String sql = "SELECT * FROM diets WHERE profile_id = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setInt(1, profile.getId());
//			ResultSet result = query.executeQuery();
//			while (result.next()) {
//				Diet diet = new Diet(result.getInt(1), result.getString(2), profile);
//				dietsList.add(diet);
//			}
//		} catch (SQLException e) {
//			Messages.error("Error al cargar todas las dietas", e.getMessage());
//		}
//		return dietsList;
//	}
//
//	public static List<Menu> getAllMenusForDiet(Diet diet) {
//		List<Menu> menus = new ArrayList<>();
//		try {
//			String sql = "SELECT id_menu FROM diets_menus WHERE id_diets = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setInt(1, diet.getId());
//			ResultSet result = query.executeQuery();
//			while (result.next()) {
//				menus.add(getMenu(result.getInt(1)));
//			}
//		} catch (SQLException e) {
//			Messages.error("Error al obtener todos los menus de la dieta indicada", e.getMessage());
//		}
//		return menus;
//	}
//	public static Diet getDiet(Integer id) {
//		Diet diet = null;
//		try {
//			String sql = "SELECT id, name, profile_id FROM diets WHERE id = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setInt(1, id);
//			ResultSet result = query.executeQuery();
//			while (result.next()) {
//				Profile profile = QueryService.getProfile(result.getInt(3));
//				diet = new Diet(result.getInt(1), result.getString(2), profile);
//			}
//		} catch (SQLException e) {
//			Messages.error("Error al obtenner el menu selecionado", e.getMessage());
//		}
//		return diet;
//	}
//
//	public static int insertDiet(Diet diet) {
//		int idResult = 0;
//		try {
//			String sql = "INSERT INTO diets (name, profile_id) VALUES (?, ?)";
//			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			query.setString(1, diet.getName());
//			query.setInt(2, diet.getProfile().getId());
//			query.execute();
//			ResultSet generatedKeys = query.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				idResult = generatedKeys.getInt(1);
//			}
//		} catch (SQLException e) {
//			Messages.error("Error al insertar la nueva dieta", e.getMessage());
//		}
//		return idResult;
//	}
//
//	public static void updateDiet(Diet diet) {
//		try {
//			String sql = "UPDATE diets SET name = ? WHERE id = ?";
//			PreparedStatement query = App.connection.prepareStatement(sql);
//			query.setString(1, diet.getName());
//			query.setInt(2, diet.getId());
//			query.execute();
//		} catch (SQLException e) {
//			Messages.error("Error al modificar esta dieta", e.getMessage());
//		}
//	}
//
//	public static int insertDietMenu(Integer dietId, Integer menuId) {
//		int idResult = 0;
//		try {
//			String sql = "INSERT INTO diets_menus VALUES (?, ?)";
//			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			query.setInt(1, dietId);
//			query.setInt(2, menuId);
//			query.execute();
//			ResultSet generatedKeys = query.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				idResult = generatedKeys.getInt(1);
//			}
//		} catch (SQLException e) {
//			Messages.error("Error al insertar el nuevo menú en la dieta", e.getMessage());
//		}
//		return idResult;
//	}

}
