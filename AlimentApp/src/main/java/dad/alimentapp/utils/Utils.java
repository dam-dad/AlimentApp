package dad.alimentapp.utils;

import java.util.List;
import java.util.Optional;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Weekday;
import javafx.scene.control.ButtonType;
/**
 * La clase "Util" clase para hacer funciones que se puedan reutilizar en distintas partes del proyecto.
 * @author Antonio
 *
 */
public class Utils {
	/**
	 * Esta funcion "closeApp" la utilizamos para sacar un alert de confimacion a la hora de intentar salir de nuestra aplicación.
	 */
	public static void closeApp() {
		Optional<ButtonType> result = Messages.confirmation("Salir de la aplicación",
				"¿Está seguro de que desea salir de la aplicación?");
		if (result.get() == ButtonType.OK) {
			App.getPrimaryStage().close();
		}
	}
	
	public static void replaceMatchesInMenu(List<Menu> menuList, Menu menu) {
		boolean matches = false;
		int count = 0;
		do {
			if (menuList.get(count).getWeekday() == menu.getWeekday()) {
				menuList.set(count, menu);
				matches = true;
			}
			count++;
		} while (!matches && count < menuList.size());		
		
		if(!matches) {
			menuList.add(menu);
		}
	}
	
	public static Menu searchMatchesInMenu(List<Menu> menuList, Weekday weekday) {
		Menu menuResult = null;
		boolean matches = false;
		int count = 0;
		do {
			if (menuList.get(count).getWeekday() == weekday) {
				menuResult = menuList.get(count);
				matches = true;
			}
			count++;
		} while (!matches && count < menuList.size());		
		return menuResult;
	}
}
