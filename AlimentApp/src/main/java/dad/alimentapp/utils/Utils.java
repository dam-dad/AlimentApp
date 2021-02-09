package dad.alimentapp.utils;

import java.util.Optional;

import dad.alimentapp.main.App;
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
}
