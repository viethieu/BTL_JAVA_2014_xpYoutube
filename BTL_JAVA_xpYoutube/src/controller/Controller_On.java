package controller;

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
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
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
			webView.getEngine()
					.loadContent(
							"<iframe width=\"640\" height=\"380\" src=\"http://www.youtube.com/embed/"+link+"\" frameborder=\"0\" allowfullscreen></iframe>");
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
	}

}
