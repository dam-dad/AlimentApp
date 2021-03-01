package dad.alimentapp.utils;

import java.util.Optional;

import dad.alimentapp.main.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * La clase "Messages" nos permite crear dialogos o alerts de distintos
 * tipos en cualquier parte del proyecto para el control de errores, infomación,
 * confimación.
 * 
 * @author Antonio
 *
 */
public class Messages {
	/**
	 * El metodo "confirmation" nos permite crear un alert personalizado de tipo
	 * confirmación.
	 * 
	 * @param title   debera definir el titulo del alert.
	 * @param content debera definir el contenido de dicho alert.
	 * @return retornara el resultado.
	 */
	public static Optional<ButtonType> confirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(content);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	/**
	 * El metodo "info" nos permite crear un alert personalizado de tipo
	 * información.
	 * 
	 * @param header   debera definir el titulo del alert.
	 * @param content debera definir el contenido de dicho alert.
	 */

	public static void info(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(header);
		alert.setContentText(content);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		alert.showAndWait();
	}

	/**
	 * El metodo "error" nos permite crear un alert personalizado de tipo error.
	 * 
	 * @param header   debera definir el titulo del alert.
	 * @param content debera definir el contenido de dicho alert.
	 */
	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		alert.showAndWait();
	}
}
