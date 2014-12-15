package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class fullScreen implements Initializable {
	private Stage stage = new Stage();
	boolean flag = true;

	public fullScreen() {
	}

	public void lauch(URL url, double time) {
		try {
			Group root = new Group();
			Media media = new Media(url.toString());
			final MediaPlayer mediaPlayer = new MediaPlayer(media);

			MediaView mediaView = new MediaView(mediaPlayer);
			mediaView.setFitWidth(1366);
			mediaView.setFitHeight(768);
			root.getChildren().add(mediaView);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.show();
			mediaPlayer.setStartTime(Duration.seconds(time));
			mediaPlayer.play();
			mediaPlayer.setVolume(0);

			mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent click) {
					if (click.getClickCount() == 2) {
						mediaPlayer.pause();
						stage.close();
					}
				}

			});
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
					}
					System.out.println(event.getCode());
					mediaPlayer.pause();
					stage.close();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
