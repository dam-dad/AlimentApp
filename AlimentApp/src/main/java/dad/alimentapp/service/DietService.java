package dad.alimentapp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.main.App;
import dad.alimentapp.models.DailyMenu;
import dad.alimentapp.models.Diet;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Profile;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.utils.Messages;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * En esta clase tenemos almacenadas todas las consultas a la base de datos, en
 * referencia a la tabla Diet.
 * 
 * @author Antonio y Andy
 *
 */
public class DietService {
	/**
	 * @author Antonio 
	 * El metodo "getAllDiets" lo utilizamos para obtener una lista
	 *         de dietas del perfil indicado.
	 *
	 * @param profile le pasamos el objeto profile para realizar la busqueda de las
	 *                dietas que le pertenecen.
	 * @return retornamos la lista de dietas.
	 */
	public static List<Diet> getAllDiets(Profile profile) {
		List<Diet> dietsList = new ArrayList<>();
		try {
			String sql = "SELECT id, name FROM diets WHERE profile_id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, profile.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				Diet diet = new Diet(result.getInt(1), result.getString(2), profile);
				diet.getDailyMenus().setAll(getAllMenusForDiet(diet));
				dietsList.add(diet);
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al cargar la lista de todas las dietas");
		}
		return dietsList;
	}
	/**
	 *  @author Antonio
	 *  El metodo "getAllMenusForDiet" se encarga de cargar todos los menus para la dieta indicada.
	 * @param diet le pasamos la dieta por parametros.
	 * @return retornamos el menu con su dia de la semana correspondiente.
	 */
	private static List<DailyMenu> getAllMenusForDiet(Diet diet) {
		List<DailyMenu> dailyMenu = new ArrayList<>();
		try {
			String sql = "SELECT id_menu, id_weekday FROM diets_menus WHERE id_diets = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, diet.getId());
			ResultSet result = query.executeQuery();
			Weekday weekday = null;
			Menu menu = null;
			while (result.next()) {
				weekday = Weekday.valueOf(result.getInt(2));
				menu = MenuService.getMenu(result.getInt(1));
				dailyMenu.add(new DailyMenu(weekday, menu));
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al obtener todos los menus de la dieta: "+diet.getName());
		}
		return dailyMenu;
	}
	/**
	 *  @author Antonio
	 *  El metodo "insertDiet" se encarga de insertar una dieta.
	 * @param diet le pasamos la dieta por parametros.
	 */
	public static void insertDiet(Diet diet) {
		try {
			String sql = "INSERT INTO diets (name, profile_id) VALUES (?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setString(1, diet.getName());
			query.setInt(2, diet.getProfile().getId());
			query.execute();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				diet.setId(generatedKeys.getInt(1));
			}
			insertAllMenusForDiet(diet);
		} catch (SQLException e) {
			Messages.error("Error", "Error al insertar la nueva dieta");
		}
	}
	/**
	 * @author Antonio
	 * El metodo "updateDiet" se encarga de modificar una dieta.
	 * @param diet
	 */
	public static void updateDiet(Diet diet) {
		try {
			String sql = "UPDATE diets SET name = ? WHERE id = ? AND profile_id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setString(1, diet.getName());
			query.setInt(2, diet.getId());
			query.setInt(3, diet.getProfile().getId());
			query.execute();
			deleteAllMenusForDiet(diet.getId());
			insertAllMenusForDiet(diet);
		} catch (SQLException e) {
			Messages.error("Error", "Error al modificar la dieta: "+diet.getName());
		}
	}
	
	/**
	 * Esta función deleteDiet, nos permite eliminar una dieta y sus claves foráneas
	 * 
	 * @param diet, es la dieta que se eliminará
	 */
	public static void deleteDiet(Diet diet) {
		try {
			String sql = "DELETE FROM diets_menus WHERE id_diets = ?";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, diet.getId());
			query.execute();
			sql = "DELETE FROM diets WHERE id = ?";
			query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, diet.getId());
			query.execute();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Éxito en la eliminación");
			alert.setHeaderText("Se ha eliminado el menú correctamente.");
			alert.show();
		} catch (SQLException e) {
			Messages.error("Error al eliminar la dieta", e.getMessage());
		}
	}
	/**
	 *  @author Antonio
	 *  El metodo "insertDietMenu" se encarga de insertar un menu para la dieta indicada.
	 * @param dietId id de la dieta.
	 * @param menuId id del menu.
	 * @return
	 */
	public static int insertDietMenu(Integer dietId, Integer menuId) {
		int idResult = 0;
		try {
			String sql = "INSERT INTO diets_menus VALUES (?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, dietId);
			query.setInt(2, menuId);
			query.execute();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				idResult = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			Messages.error("Error", "Error al insertar el nuevo menú en la dieta.");
		}
		return idResult;
	}
	/**
	 *  @author Antonio
	 * El metodo "insertAllMenusForDiet" se encarga de insertar todos los menus para la dieta indicada.
	 * @param diet le pasamos por parametros la dieta.
	 */
	private static void insertAllMenusForDiet(Diet diet) {
		for (DailyMenu dailyMenu : diet.getDailyMenus()) {
			if (dailyMenu.getMenu().getId() == 0) {
				MenuService.insertMenu(dailyMenu.getMenu());
			} else {
				MenuService.updateMenu(dailyMenu.getMenu());
			}
			insertMenuForDietInWeekday(diet.getId(), dailyMenu);
		}
	}
	/**
	 *  @author Antonio
	 *  El metodo "insertMenuForDietInWeekday" se encarga de insertar un menu en una dieta para un dia de la semana.
	 * @param dietId le pasamos id de la dieta
	 * @param dailyMenu le pasamos el menu con su dia de la semana correspondiente
	 */
	private static void insertMenuForDietInWeekday(int dietId, DailyMenu dailyMenu) {
		try {
			String sql = "INSERT INTO diets_menus VALUES (?, ?, ?)";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, dietId);
			query.setInt(2, dailyMenu.getMenu().getId());
			query.setInt(3, dailyMenu.getWeekday().getId());
			query.execute();
		} catch (SQLException e) {
			Messages.error("Error","Error al insertar el nuevo menú en la dieta");
		}
	}
	/**
	 *  @author Antonio
	 *  El metodo "deleteAllMenusForDiet" se encarga de eliminar todos los menus de una dieta.
	 * @param idDiet le pasamos el id de la dieta.
	 */
	private static void deleteAllMenusForDiet(int idDiet) {
		try {
			String sql = "DELETE FROM diets_menus WHERE id_diets = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, idDiet);
			query.execute();
		} catch (Exception e) {
			Messages.error("Error", "Error: No se pudieron eliminar los menus para la dieta indicada");
		}
	}

}
