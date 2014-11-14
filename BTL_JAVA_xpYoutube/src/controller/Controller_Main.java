package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {
	private boolean endOfMedia = false;
	private String str;
	private Media pick;
	public MediaPlayer player;
	private Duration duration;
	private double volume;

	@FXML
	private TextField idTF;
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

	/************************************************************************************
	 * 
	 * CONTROL MENU
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
	}

	@FXML
	private void mPlay() {
		player.play();
	}

	@FXML
	private void mPause() {
		player.pause();
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
	private void mAboutTeam()  {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		idSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				/* cách lấy dữ liệu video trên mạng */
				final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
				pick = new Media(MEDIA_URL);

				/* cách lấy dữ liệu trong máy tính 			
				str = idTF.getText();
				str = "/application/" + str + ".mp3";
				System.out.println(str);
				URL resource = getClass().getResource(str);
				pick = new Media(resource.toString());
				*/
				player = new MediaPlayer(pick);
				player.setAutoPlay(true);
				idMediaView.setMediaPlayer(player);

				player.setVolume(0.5);
				idPIn.setProgress(0.5);
			}
		});

		idPlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				Status status = player.getStatus();
				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					// rewind the movie if we're sitting at the end
					player.play();
				} else {
					player.pause();
				}
			}
		});
		
		idMediaView.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Status status = player.getStatus();
				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					player.play();
				} else {
					player.pause();
				}
			}
	
		});
		
		idStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				player.stop();
				endOfMedia = true;
			}
		});

		idFullScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
			 
			}
		});


		/******************************************************************************
		 * 
		 * Setup Volume
		 *
		 ******************************************************************************/
		idVolumeDown.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				volume = player.getVolume() - 0.05;
				player.setVolume(volume);
				idPIn.setProgress(volume);
			}
		});

		idVolumeUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				volume = player.getVolume() + 0.05;
				player.setVolume(volume);
				idPIn.setProgress(volume);
			}
		});

		idMute.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (player.isMute() == true)
					player.setMute(false);
				else
					player.setMute(true);
			}
		});
		
		

	}
}
