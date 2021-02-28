package dad.alimentapp.main;

import java.sql.Connection;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.utils.ConnectionDB;
import dad.alimentapp.utils.Utils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

	private static Stage primaryStage;

	private static MainController controller;

	// Almacenamos e iniciamos la conexion con la BD.
	public static Connection connection = ConnectionDB.connection();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Creacion inicial de la BD. Descomentar esta linea si tienes que volver a
		// crear la BD.
		//ConnectionDB.createDB();
		App.primaryStage = primaryStage;
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Utils.closeApp();
				event.consume();
			}
		});

		controller = new MainController();

		Scene scene = new Scene(controller.getView(), 950, 700);
		
		scene.getStylesheets().add("/css/MainStyle.css");

		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("AlimentApp");
		primaryStage.getIcons().add(new Image("/images/logo.png"));
		primaryStage.show();
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);

	}

	public static MainController getMainController() {
		return controller;

	}
}
