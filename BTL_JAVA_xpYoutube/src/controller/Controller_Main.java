package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.ParseJsonYoutube;

public class Controller_Main implements Initializable {
	private boolean endOfMedia = false;
	private Media pick;
	public MediaPlayer player;
	private Duration duration;
	private double volume;
	String str;
	String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private Stage stage = null;
	private Scene scene;
	private Pane pane;

	/***********************************************************
	 * 
	 * 1. Ham khoi tao
	 * 
	 **********************************************************/
	public Controller_Main () {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/LayoutMain.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/application/aMain.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void launch(String str1, String str2) {
		link = str2;
		if (link != null) {
			final WebEngine eng = idWebView.getEngine();
			eng.load("https://www.youtube.com/v/8TBPdJHKZYo?version=3&f=videos&app=youtube_gdata");
		}
		Stage primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/layout/iconMovie.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(str2);
	}

	/*****************************************************************
	 * 
	 * 2. Lap trinh su kien
	 * 
	 ****************************************************************/
	@FXML
	private TextField idSearchText;
	@FXML
	private Button idSearch;
	@FXML
	private Button idPlay;
	@FXML
	private MediaView idMediaView;
	@FXML
	private Button idStop;
	@FXML
	private Button idVolumeDown;
	@FXML
	private Button idVolumeUp;
	@FXML
	private Button idMute;
	@FXML
	private ProgressIndicator idPIn;
	@FXML
	private Button idFullScreen;
	@FXML
	private Slider idSliderTime;
	@FXML
	private Label idTime;
	@FXML
	private Pane idPaneMedia;
	@FXML
	private WebView idWebView;

	/************************************************************************************
	 * 
	 * 3. CONTROL MENU
	 * 
	 *************************************************************************************/
	@FXML
	private MenuItem idMClose;
	@FXML
	private MenuItem idMStop;
	@FXML
	private MenuItem idMPlay;
	@FXML
	private MenuItem idMPause;
	@FXML
	private MenuItem idMVolumeDown;
	@FXML
	private MenuItem idMVolumeUp;
	@FXML
	private MenuItem idMVolumeMute;
	@FXML
	private MenuItem idMBalance1;
	@FXML
	private MenuItem idMBalance2;
	@FXML
	private MenuItem idMBalance3;
	@FXML
	private MenuItem idMBalance4;
	@FXML
	private MenuItem idMBalance5;
	@FXML
	private MenuItem idMAbout;

	@FXML
	private void mStop() {
		player.stop();
		idPlay.setText(">");
	}

	@FXML
	private void mPlay() {
		player.play();
		idPlay.setText("||");

		player.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateTime();
			}
		});
	}

	@FXML
	private void mPause() {
		player.pause();
		idPlay.setText(">");
	}

	@FXML
	private void mVolumeDown() {
		volume = player.getVolume() - 0.05;
		player.setVolume(volume);
		idPIn.setProgress(volume);
	}

	@FXML
	private void mVolumeUp() {
		volume = player.getVolume() + 0.05;
		player.setVolume(volume);
		idPIn.setProgress(volume);
	}

	@FXML
	private void mVolumeMute() {
		if (player.isMute() == true)
			player.setMute(false);
		else
			player.setMute(true);
	}

	@FXML
	private void mCloseScene() {
		Platform.exit();
	}

	@FXML
	private void mBalance1() {
		player.setBalance(1.0);
	}

	@FXML
	private void mBalance2() {
		player.setBalance(0.5);
	}

	@FXML
	private void mBalance3() {
		player.setBalance(0.0);
	}

	@FXML
	private void mBalance4() {
		player.setBalance(-0.5);
	}

	@FXML
	private void mBalance5() {
		player.setBalance(-1.0);
	}

	@FXML
	private void mAboutTeam() {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("/layout/Team.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Imformation");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// viet vao day

}
