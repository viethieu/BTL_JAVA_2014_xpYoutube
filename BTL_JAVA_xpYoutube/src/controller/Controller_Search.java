package controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.ParseJsonYoutube;

public class Controller_Search  {

	String str;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private Scene scene;
	private Pane pane;
	@FXML
	private TextField idTextSearch;
	@FXML
	private ImageView idSearch;
	@FXML
	private ListView<Item> idListView;

	public Controller_Search(List<Item> list) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/giaoDien/Search.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/css/Search.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.list = list;
		observableList.setAll(list);
		idListView.setItems(observableList);
		idListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

			@Override
			public ListCell<Item> call(ListView<Item> listView) {
				return new ListViewCell();
			}
		});
	}

	public Stage launch() {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Resutl Search");
		Image iconSoftWare = new Image("/giaoDien/iconSearch.jpg");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}

	@FXML
	private void actionSearch() {
		str = idTextSearch.getText();

		System.out.println(str);
		ParseJsonYoutube parser = new ParseJsonYoutube(str);
		list = parser.ParseYoutube();
		observableList.setAll(list);
		idListView.setItems(observableList);
		idListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

			@Override
			public ListCell<Item> call(ListView<Item> listView) {
				return new ListViewCell();
			}
		});

	}
	 
	@FXML
	private void pressEnter ()  {
		actionSearch();
	}

}
