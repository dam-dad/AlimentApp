package dad.alimentapp.main;

import dad.alimentapp.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage primaryStage;
	MainController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {

		App.primaryStage = primaryStage;

		controller = new MainController();

		Scene escena = new Scene(controller.getView(), 900, 750);

		primaryStage.setScene(escena);
		primaryStage.setTitle("AlimentApp");
		primaryStage.getIcons().add(new Image("/images/icon.png"));
		primaryStage.show();

	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
