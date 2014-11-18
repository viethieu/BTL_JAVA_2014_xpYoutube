package application;

import java.io.IOException;

import controller.Controller_Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage)  {
		Controller_Main ctrl = new Controller_Main();
		ctrl.launch("Xem phim youtube", null);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}