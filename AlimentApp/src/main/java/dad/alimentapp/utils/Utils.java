package dad.alimentapp.utils;

import java.util.List;
import java.util.Optional;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Weekday;
import dad.alimentapp.models.app.DailyMenu;
import dad.alimentapp.models.app.Menu;
import javafx.animation.PauseTransition;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.util.Duration;

/**
 * La clase "Util" clase para hacer funciones que se puedan reutilizar en
 * distintas partes del proyecto.
 * 
 * @author Antonio
 *
 */
public class Utils {
	/**
	 * Esta funcion "closeApp" la utilizamos para sacar un alert de confimacion a la
	 * hora de intentar salir de nuestra aplicación.
	 */
	public static void closeApp() {
		Optional<ButtonType> result = Messages.confirmation("Salir de la aplicación",
				"¿Está seguro de que desea salir de la aplicación?");
		if (result.get() == ButtonType.OK) {
			App.getPrimaryStage().close();
		}
	}

	/**
	 * Reemplaza las coincidencias que encuentra en la lista con el dia de la semana
	 * que recibe en el menu el cual le pasamos por parametro.
	 * 
	 * @param dailyMenu le pasamos una lista de menus.
	 * @param menu      le pasamos un menu.
	 */
	// TODO corregir con la version de fran
	public static void replaceMatchesInMenu(List<DailyMenu> dailyMenu) {
//		boolean matches = false;
//		int count = 0;
//		do {
//			if (dailyMenu.get(count).getWeekday() == menu.getWeekday()) {
//				dailyMenu.set(count, menu);
//				matches = true;
//			}
//			count++;
//		} while (!matches && count < dailyMenu.size());
//
//		if (!matches) {
//			dailyMenu.add(menu);
//		}
	}

	/**
	 * Buscas en la lista un menu que coincida con el dia de la semana que recibe
	 * por parametros.
	 * 
	 * @param menuList le pasamos una lista de menus.
	 * @param weekday  le pasamos el objeto del dia de la semana.
	 * @return si encuentra coincidencias retornamos el menu con ese dia de la
	 *         semana y sino devuelve null
	 */
	public static Menu searchMatchesInMenu(List<DailyMenu> menuList, Weekday weekday) {
		Menu menus = null;
		boolean matches = false;
		int count = 0;
		do {
			if (menuList.get(count).getWeekday() == weekday) {
				menus = menuList.get(count).getMenu();
				matches = true;
			}
			count++;
		} while (!matches && count < menuList.size());
		return menus;
	}

	public static void popup(String message) {
		Popup popup = new Popup();
		popup.setAutoHide(true);
		popup.setAutoFix(true);
		Label popupLabel = new Label(message);
		popup.getContent().add(popupLabel);
		popupLabel.setStyle("-fx-background-color:black;" + " -fx-text-fill: white;" + " -fx-font-size:15px;"
				+ " -fx-padding: 10px;" + " -fx-background-radius: 6;");

		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e -> popup.hide());

		popup.show(App.getPrimaryStage());
		delay.play();
	}
}
