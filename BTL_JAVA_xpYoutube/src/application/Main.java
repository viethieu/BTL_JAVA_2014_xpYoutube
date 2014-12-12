package application;

import java.io.IOException;

import controller.Controller_Chose;
import controller.Controller_Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private Parent pane;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage)  {
		Controller_Chose ctrl = new Controller_Chose();
		ctrl.launch();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}