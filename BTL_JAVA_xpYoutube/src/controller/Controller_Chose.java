package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Chose implements Initializable {
	@FXML
	private Button idOFFLINE, idONLINE;
	private Pane pane;
	private Scene scene;

	public Controller_Chose() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/giaodien/CHOSE.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/css/Choose.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void launch() {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("CHƯƠNNG TRÌNH XEM PHIM");
		Image iconSoftWare = new Image("/image/title.png");
		primaryStage.getIcons().add(iconSoftWare);	
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idOFFLINE.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
//				final FileChooser fileChooser = new FileChooser();
//				Stage stage= new Stage();
//				File file = fileChooser.showOpenDialog(stage);
//				if (file != null) {
//					Controller_Main ctrl= new Controller_Main();
//					try {
//						Writer
//						//PrintWriter log_off = new PrintWriter(new FileWriter("log_offline.txt"));
//						PrintWriter log_off= new PrintWriter(filename,true);
//						log_off.println("file:///" + file.getAbsolutePath());
//						log_off.flush();
//
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				Controller_Main ctrl= new Controller_Main();
				ctrl.launch("Xem phim offline", null);
				
				
//						
//					try
//				     {
//						String filename="log_offline.txt";
//						FileWriter fw = new FileWriter(filename,true);
//				         fw.write(file.getAbsolutePath()+"\n");//appends the string to the file
//				         fw.close();
//				     }
//				     catch(IOException ioe)
//				     {
//				         System.err.println("IOException: " + ioe.getMessage());
//				     }
//					ctrl.launch("Test", "file:///"+file.getAbsolutePath());
//				}
			}
		});
		idONLINE.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Controller_On ctrl= new Controller_On();
				ctrl.launch("Youtube",null);
			}
		});
	}
	public static void openFile(File file) {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(Controller_Chose.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

}
