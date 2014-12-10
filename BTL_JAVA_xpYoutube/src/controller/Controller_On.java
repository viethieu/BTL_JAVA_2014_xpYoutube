package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.ParseJsonYoutube;

public class Controller_On implements Initializable {

	private Pane pane;
	private Scene scene;
	String str;
	String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();

	@FXML
	private Button idSearch;

	@FXML
	private TextField idTextSearch;

	@FXML
	private MenuItem idMOpen, idMAbout;

	@FXML
	private WebView idWebView;

	public Controller_On() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/layout/Online.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launch(String str1, String str2) {
		link = str2;
		if (link != null) {
			final WebView webView = idWebView;
			webView.getEngine().loadContent(
					"<iframe width=\"640\" height=\"380\" src=\"http://www.youtube.com/embed/"
							+ link
							+ "\" frameborder=\"0\" allowfullscreen></iframe>");
		}

		Stage primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/layout/iconMovie.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(str2);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idTextSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					str = idTextSearch.getText();
					System.out.println(str);
					ParseJsonYoutube parser = new ParseJsonYoutube(str);
					list = parser.ParseYoutube();
					System.out.println(list);

					Controller_Search ctrl = new Controller_Search(list);
					ctrl.launch();
				}
			}
		});
		idSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				str = idTextSearch.getText();
				System.out.println(str);
				ParseJsonYoutube parser = new ParseJsonYoutube(str);
				list = parser.ParseYoutube();
				System.out.println(list);

				Controller_Search ctrl = new Controller_Search(list);
				ctrl.launch();
			}
		});
		idMAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				AnchorPane root;
				try {
					root = FXMLLoader.load(getClass().getResource(
							"/layout/Team.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = new Stage();
					primaryStage.setTitle("About YourTube");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		idMOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				final FileChooser fileChooser = new FileChooser();
				Stage stage = new Stage();
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					// System.out.println(file.getAbsolutePath());
					// openFile(file);
					// Controller_Main ctrl = new Controller_Main();
					// try {
					// Writer
					// //PrintWriter log_off = new PrintWriter(new
					// FileWriter("log_offline.txt"));
					// PrintWriter log_off= new PrintWriter(filename,true);
					// log_off.println("file:///" + file.getAbsolutePath());
					// log_off.flush();
					//
					// } catch (IOException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

					// player.stop();
					try {
						String filename = "log_offline.txt";
						FileWriter fw = new FileWriter(filename, true);
						fw.write(file.getAbsolutePath() + "\n");// appends the
																// string to the
																// file
						fw.close();
					} catch (IOException ioe) {
						System.err.println("IOException: " + ioe.getMessage());
					}
					Controller_Main ctrl= new Controller_Main();
					ctrl.launch("Test", "file:///"+file.getAbsolutePath());
				}
			}
		});
	}

}
