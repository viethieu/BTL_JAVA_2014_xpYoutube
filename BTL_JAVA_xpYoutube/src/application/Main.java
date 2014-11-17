package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage)  {
	
		try {
				Pane root = FXMLLoader.load(getClass().getResource("/giaoDien/layoutMain.fxml"));
				Scene scene = new Scene(root,600,400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("Xem phim Youtube");
				Image iconSoftWare = new Image("/giaoDien/iconMovie.png");
				primaryStage.getIcons().add(iconSoftWare);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}