package dad.alimentapp.utils;

import java.util.Optional;

import dad.alimentapp.main.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Messages {
	public static Optional<ButtonType> confirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(content);

		// PONER ICONO DEL PADRE
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}
	
	public static void info(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(header);
		alert.setContentText(content);

		// PONER ICONO DEL PADRE
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		alert.showAndWait();
	}
	
	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);

		// PONER ICONO DEL PADRE
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(App.getPrimaryStage().getIcons());

		alert.showAndWait();
	}
}
