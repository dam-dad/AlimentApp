package dad.alimentapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class InfoController implements Initializable{
	

    @FXML
    private BorderPane view;

    @FXML
    private ImageView adviceImageView;

    @FXML
    private Button historicButton;

    @FXML
    private GridPane dietPane;

    @FXML
    private Label dietLabel;
	
	public InfoController() throws IOException  {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InfoView.fxml"));
		loader.setController(this);
		loader.load();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
public BorderPane getView() {
	return view;
}	
	

}
