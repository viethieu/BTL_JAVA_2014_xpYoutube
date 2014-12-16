package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.ParseJsonYoutube;
import controller.Controller_Search;
import controller.Item;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Controller_Main implements Initializable {
	private boolean endOfMedia = false;
	private String str, name;
	private int size;
	private URL url;
	public MediaPlayer player = null;
	private Duration duration;
	private double volume;
	String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private Stage stageSearch = null;
	private Stage primaryStage;
	private Scene scene;
	private Pane pane;
	Image image1 = new Image("/image/play.png");
	Image image2 = new Image("/image/pause.png");
	Image image3 = new Image("/image/muted.png");
	Image image4 = new Image("/image/volume.png");

	/***********************************************************
	 * 
	 * 1. Hàm khởi tạo
	 * 
	 **********************************************************/
	public Controller_Main() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/giaoDien/layoutMain.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(
					getClass().getResource("/css/Off.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launch(String str1, String str2) {
		if (str2 != null) {
			URL url = null;
			try {
				url = new URL(str2);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Media media = new Media(url.toString());
			player = new MediaPlayer(media);

			final DoubleProperty width = idMediaView.fitWidthProperty();
			final DoubleProperty height = idMediaView.fitHeightProperty();

			width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
					"width"));
			height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
					"height"));

			idMediaView.setPreserveRatio(true);

			idMediaView.setMediaPlayer(player);
			player.setVolume(0.5);
			idVolume.setValue(50);
		}
		primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/giaoDien/iconMovie.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(str2);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				if (player != null)
					player.stop();
			}
		});
	}

	/*****************************************************************
	 * 
	 * 2. Lập trình sự kiện
	 * 
	 ****************************************************************/
	@FXML
	private TextField idSearchText;
	@FXML
	private ImageView idSearch;
	@FXML
	private ImageView idPlay;
	@FXML
	private MediaView idMediaView;
	@FXML
	private ImageView idStop;
	@FXML
	private ImageView idMute;
	@FXML
	private ImageView idFullScreen;
	@FXML
	private Slider idSliderTime;
	@FXML
	private Slider idVolume;
	@FXML
	private Label idTime;
	@FXML
	private Pane idPaneMedia;

	/************************************************************************************
	 * 
	 * 3. CONTROL MENU
	 * 
	 *************************************************************************************/
	@FXML
	private MenuItem idMOpen;
	@FXML
	private MenuItem idMClose;
	@FXML
	private Menu idMRecent;

	@FXML
	private MenuItem idMRecent1;
	@FXML
	private MenuItem idMRecent2;
	@FXML
	private MenuItem idMRecent3;
	@FXML
	private MenuItem idMRecent4;
	@FXML
	private MenuItem idMRecent5;
	@FXML
	private MenuItem idMRecent6;
	@FXML
	private MenuItem idMRecent7;
	@FXML
	private MenuItem idMClearRecent;

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

	/**********************************************************************
	 * 
	 * khai bao cac ham duoc tao trong sence builder
	 * 
	 **********************************************************************/
	@FXML
	private MenuItem idMAbout;

	@FXML
	private void mStop() {
		player.stop();
		idPlay.setImage(image1);
	}

	@FXML
	private void mPlay() {
		player.play();
		idPlay.setImage(image2);

		player.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateTime();
			}
		});
	}

	@FXML
	private void mPause() {
		player.pause();
		idPlay.setImage(image1);
	}

	@FXML
	private void mVolumeDown() {
		volume = player.getVolume() - 0.05;
		player.setVolume(volume);
		idVolume.setValue(idVolume.getValue() - 5);
	}

	@FXML
	private void mVolumeUp() {
		volume = player.getVolume() + 0.05;
		player.setVolume(volume);
		idVolume.setValue(idVolume.getValue() + 5);
	}

	@FXML
	private void mVolumeMute() {
		if (player.isMute() == true) {
			player.setMute(false);
			idMute.setImage(image4);
		} else {
			player.setMute(true);
			idMute.setImage(image3);
		}
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
			root = FXMLLoader.load(getClass()
					.getResource("/giaoDien/Team.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Imformation");
			primaryStage.setScene(scene);
			Image iconSoftWare = new Image("/image/title.png");
			primaryStage.getIcons().add(iconSoftWare);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**********************************************************************************
	 * 
	 * 5. CÁC CONTROL KHÁC ĐƯỢC XỬ LÝ TRONG HÀM KHỞI TẠO initialize()
	 * 
	 **********************************************************************************/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// sự kiện khi ấn enter
		idSearchText.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					str = idSearchText.getText();
					System.out.println(str);
					ParseJsonYoutube parser = new ParseJsonYoutube(str);
					list = parser.ParseYoutube();
					System.out.println(list);

					Controller_Search ctrl = new Controller_Search(list);
					if (stageSearch != null)
						stageSearch.close();
					stageSearch = ctrl.launch();
				}
			}
		});

		/*****************************************************************************
		 * 
		 * 5. Lập trình sự kiện cho nút search Xử lý lấy địa chỉ link video ở
		 * đây
		 * 
		 *****************************************************************************/
		idSearch.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				/* Cach lay video tren mang */
				// final String MEDIA_URL =
				// "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
				// pick = new Media(MEDIA_URL);

				/* Cach lay video trong may tinh */
				// str = idSearchText.getText();
				// str = "/application/" + str + ".mp3";
				// URL resource = getClass().getResource(str);
				// pick = new Media(resource.toString());

				// player = new MediaPlayer(pick);
				// idMediaView.setMediaPlayer(player);
				//
				// player.setVolume(0.5);
				// idPIn.setProgress(0.5);

				str = idSearchText.getText();
				System.out.println(str);
				ParseJsonYoutube parser = new ParseJsonYoutube(str);
				list = parser.ParseYoutube();
				System.out.println(list);

				Controller_Search ctrl = new Controller_Search(list);
				if (stageSearch != null)
					stageSearch.close();
				stageSearch = ctrl.launch();
			}
		});

		// Su kien nut Play/Pause
		idPlay.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {

				Status status = player.getStatus();
				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED || status == Status.READY
						|| status == Status.STOPPED) {
					player.play();

					player.setCycleCount(10);
				} else {
					player.pause();
				}

				player.setOnPaused(new Runnable() {
					public void run() {
						idPlay.setImage(image1);
					}
				});

				player.setOnPlaying(new Runnable() {
					public void run() {
						idPlay.setImage(image2);
					}
				});

				// lap trinh cho thoi gian chay khi xem phim
				duration = player.getMedia().getDuration();
				player.currentTimeProperty().addListener(
						new InvalidationListener() {
							public void invalidated(Observable ov) {
								updateTime();
							}
						});
			}
		});

		// Su kien khi click vao man hinh
		idMediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {
				Status status = player.getStatus();
				if (status == Status.PAUSED || status == Status.READY
						|| status == Status.STOPPED) {
					if (endOfMedia) {
						player.seek(player.getStartTime()); // lấy thời gian tại
															// lúc dừng
						endOfMedia = false;
					}
					player.play();
				} else {
					player.pause();
				}
				if (click.getClickCount() == 2) {
					fullScreen ctl = new fullScreen();
					ctl.lauch(getNameMeida(name), player.getCurrentTime()
							.toSeconds());
				}
			}

		});

		// Su kien nut Stop
		idStop.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				player.stop();
				idPlay.setImage(image1);

			}
		});

		// su kien nut thu phong man hinh
		idFullScreen.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				fullScreen ctl = new fullScreen();
				ctl.lauch(getNameMeida(name), player.getCurrentTime()
						.toSeconds());
			}
		});

		// Su kien khi keo thanh thoi gian
		idSliderTime.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				updateTime();
				if (idSliderTime.isValueChanging()) {
					player.seek(duration.multiply(idSliderTime.getValue() / 100.0));
				}
			}
		});

		/******************************************************************************
		 * 
		 * 6. Setup Volume
		 *
		 ******************************************************************************/
		idVolume.setOnMousePressed(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				player.setVolume(idVolume.getValue() / 100);
				System.out.println(idVolume.getValue());

			}
		});
		idVolume.setOnMouseMoved(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				player.setVolume(idVolume.getValue() / 100);
				System.out.println(idVolume.getValue());
			}
		});

		idMute.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				if (player.isMute() == true) {
					player.setMute(false);
					idMute.setImage(image4);
				} else {
					player.setMute(true);
					idMute.setImage(image3);
				}
			}
		});

		idMOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				final FileChooser fileChooser = new FileChooser();
				Stage stage = new Stage();
				File file = fileChooser.showOpenDialog(stage);
				name = file.getAbsolutePath();

				if (file != null) {
					try {
						File filename = new File("log_offline.txt");
						FileWriter fw = new FileWriter(filename, true);
						fw.write(name + "\n");// appends the
												// string to the
												// file
						fw.close();
					} catch (IOException ioe) {
						System.err.println("IOException: " + ioe.getMessage());
					}

					if (player != null) {
						player.stop();
						idPlay.setImage(image1);
					}

					Media media = new Media(getNameMeida(name).toString());
					player = new MediaPlayer(media);

					final DoubleProperty width = idMediaView.fitWidthProperty();
					final DoubleProperty height = idMediaView
							.fitHeightProperty();

					width.bind(Bindings.selectDouble(
							idMediaView.sceneProperty(), "width"));
					height.bind(Bindings.selectDouble(
							idMediaView.sceneProperty(), "height"));

					idMediaView.setPreserveRatio(true);

					idMediaView.setMediaPlayer(player);
					player.setVolume(0.5);
					idVolume.setValue(50);
				}
			}
		});

		idMRecent.setOnShowing(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				BufferedReader br = null;
				ArrayList<String> lines = new ArrayList<String>();
				try {
					br = new BufferedReader(new FileReader("log_offline.txt"));
					String tmp = "";
					while ((tmp = br.readLine()) != null) {
						lines.add(tmp);
					}
					br.close();
				} catch (Exception e) {
					System.out.println("Exception caught : " + e);
				}

				size = lines.size();

				if (size == 0) {
					idMRecent1.setVisible(false);
					idMRecent2.setVisible(false);
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				}
				if (size == 1) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setVisible(false);
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 2) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 3) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setText(lines.get(size - 3));
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 4) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setText(lines.get(size - 3));
					idMRecent4.setText(lines.get(size - 4));
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 5) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setText(lines.get(size - 3));
					idMRecent4.setText(lines.get(size - 4));
					idMRecent5.setText(lines.get(size - 5));
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 6) {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setText(lines.get(size - 3));
					idMRecent4.setText(lines.get(size - 4));
					idMRecent5.setText(lines.get(size - 5));
					idMRecent6.setText(lines.get(size - 6));
					idMRecent7.setVisible(false);
				} else {
					idMRecent1.setText(lines.get(size - 1));
					idMRecent2.setText(lines.get(size - 2));
					idMRecent3.setText(lines.get(size - 3));
					idMRecent4.setText(lines.get(size - 4));
					idMRecent5.setText(lines.get(size - 5));
					idMRecent6.setText(lines.get(size - 6));
					idMRecent7.setText(lines.get(size - 7));
				}

			}
		});

		idMRecent1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}
				name = idMRecent1.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent1.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent2.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent2.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent3.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent3.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent4.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent4.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent5.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent5.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent6.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent6.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setImage(image1);
				}

				name = idMRecent7.getText();
				Media media = new Media(getNameMeida(name).toString());
				player = new MediaPlayer(media);

				FileWriter fw;
				try {
					fw = new FileWriter("log_offline.txt", true);
					fw.write(idMRecent7.getText() + "\n");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(),
						"height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idVolume.setValue(50);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});

		idMClearRecent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					PrintWriter out = new PrintWriter(new FileWriter(
							"log_offline.txt"));
					out.print("");
				} catch (IOException e) {
					e.printStackTrace();
				}

				size = 0;
				idMRecent1.setVisible(false);
				idMRecent2.setVisible(false);
				idMRecent3.setVisible(false);
				idMRecent4.setVisible(false);
				idMRecent5.setVisible(false);
				idMRecent6.setVisible(false);
				idMRecent7.setVisible(false);
			}
		});

	}

	/*****************************************************************************
	 * 
	 * 7. Các hàm sử dụng cho việc update và hiển thị thời gian của video
	 * 
	 *****************************************************************************/
	protected void updateTime() {
		if (idTime != null && idSliderTime != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Duration currentTime = player.getCurrentTime();
					idTime.setText(setLabelTime(currentTime, duration));
					idSliderTime.setDisable(duration.isUnknown());
					if (!idSliderTime.isDisable()
							&& duration.greaterThan(Duration.ZERO)
							&& !idSliderTime.isValueChanging()) {
						idSliderTime.setValue(currentTime.divide(duration)
								.toMillis() * 100);
					}
				}
			});
		}
	}

	private String setLabelTime(Duration count, Duration length) {
		int intCount = (int) Math.floor(count.toSeconds());
		int intCountH = intCount / 3600;
		int intCountM = (intCount - intCountH * 3600) / 60;
		int intCountS = intCount - intCountH * 3600 - intCountM * 60;

		int intLength = (int) Math.floor(length.toSeconds());
		int intLengthH = intLength / 3600;
		int intLengthM = (intLength - intLengthH * 3600) / 60;
		int intLengthS = intLength - intLengthH * 3600 - intLengthM * 60;

		if (intLengthH > 0)
			return String.format("%02d:%02d:%02d/%02d:%02d:%02d", intCountH,
					intCountM, intCountS, intLengthH, intLengthM, intLengthS);
		else
			return String.format("%02d:%02d/%02d:%02d", intCountM, intCountS,
					intLengthM, intLengthS);
	}

	private URL getNameMeida(String name) {
		String fix = "file:///" + name.replaceAll(" ", "%20");
		int cuoi = name.lastIndexOf(".");
		int dau = name.lastIndexOf("\\");
		String title = name.substring(dau + 1, cuoi);
		primaryStage.setTitle(title + " - Xem phim Offline");
		try {
			url = new URL(fix);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
