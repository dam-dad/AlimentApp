package dad.alimentapp.main;

import java.sql.Connection;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.utils.ConnectionDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage primaryStage;
	private MainController controller;
	
	//Almacenamos e iniciamos la conexion con la BD.
	public static Connection connection = ConnectionDB.connection();

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Creacion inicial de la BD. Descomentar esta linea si tienes que volver a crear la BD.
		//ConnectionDB.createDB();
		App.primaryStage = primaryStage;

		controller = new MainController();

		Scene scene = new Scene(controller.getView(), 800, 700);

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
}
