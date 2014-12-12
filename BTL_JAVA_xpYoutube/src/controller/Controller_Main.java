package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.util.Duration;

public class Controller_Main implements Initializable {
	private boolean endOfMedia = false;
	private String str;
	private Media pick;
	public MediaPlayer player;
	private Duration duration;
	private double volume;
	String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private Stage stage = null;
	private Scene scene;
	private Pane pane;

	/***********************************************************
	 * 
	 * 1. Hàm khởi tạo
	 * 
	 **********************************************************/
	public Controller_Main () {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/giaoDien/layoutMain.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/css/Off.css").toExternalForm());
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

			width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

			idMediaView.setPreserveRatio(true);

			idMediaView.setMediaPlayer(player);
			player.setVolume(0.5);
			idPIn.setProgress(0.5);
		}
		Stage primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/giaoDien/iconMovie.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(str2);
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
			root = FXMLLoader.load(getClass().getResource("/giaoDien/Team.fxml"));
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

	/**********************************************************************************
	 * 
	 * 5. CÁC CONTROL KHÁC ĐƯỢC XỬ LÝ TRONG HÀM KHỞI TẠO initialize()
	 * 
	 **********************************************************************************/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//sự kiện khi ấn enter
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
					if (stage != null)
						stage.close();
					stage = ctrl.launch();
				}
			}
		});
		
		/*****************************************************************************
		 * 
		 *5. Lập trình sự kiện cho nút search Xử lý lấy địa chỉ link video ở đây
		 * 
		 *****************************************************************************/
		idSearch.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				/* Cach lay video tren mang*/
//				final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
//				pick = new Media(MEDIA_URL);

				/* Cach lay video trong may tinh*/			
				//str = idSearchText.getText();
				//str = "/application/" + str + ".mp3";
				//URL resource = getClass().getResource(str);
				//pick = new Media(resource.toString());

//				player = new MediaPlayer(pick);
//				idMediaView.setMediaPlayer(player);
//
//				player.setVolume(0.5);
//				idPIn.setProgress(0.5);
				
				str = idSearchText.getText();
				System.out.println(str);
				ParseJsonYoutube parser = new ParseJsonYoutube(str);
				list = parser.ParseYoutube();
				System.out.println(list);

				Controller_Search ctrl = new Controller_Search(list);
				if (stage != null)
					stage.close();
				stage = ctrl.launch();
			}
		});

		//Su kien nut Play/Pause
		idPlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				Status status = player.getStatus();
				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					player.play();
				} else {
					player.pause();
				}
				
				player.setOnPaused(new Runnable() {
					public void run() {
						idPlay.setText(">");
					}
				});

				player.setOnPlaying(new Runnable() {
					public void run() {
						idPlay.setText("||");
					}
				});

				// lap trinh cho thoi gian chay khi xem phim
				duration = player.getMedia().getDuration();
				player.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateTime();
					}
				});
			}
		});
		
		//Su kien khi click vao man hinh
		idMediaView.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Status status = player.getStatus();
				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					if (endOfMedia) {
						player.seek(player.getStartTime()); // lấy thời gian tại
															// lúc dừng
						endOfMedia = false;
					}
					player.play();
				} else {
					player.pause();
				}
			}
	
		});
		
		//Su kien nut Stop
		idStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				player.stop();
				idPlay.setText(">");
			}
		});

		//su kien nut thu phong man hinh
		idFullScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
			 
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
						File filename = new File("log_offline.txt");
						FileWriter fw = new FileWriter(filename, true);
						fw.write(file.getAbsolutePath() + "\n");// appends the
																// string to the
																// file
						fw.close();
					} catch (IOException ioe) {
						System.err.println("IOException: " + ioe.getMessage());
					}

					URL url = null;
					try {
						url = new URL("file:///" + file.getAbsolutePath());
						System.out.println(url);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					
					if (player != null) {
						player.stop();
						idPlay.setText(">");
					}
					
					Media media = new Media(url.toString());
					player = new MediaPlayer(media);

					final DoubleProperty width = idMediaView.fitWidthProperty();
					final DoubleProperty height = idMediaView.fitHeightProperty();

					width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
					height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

					idMediaView.setPreserveRatio(true);

					idMediaView.setMediaPlayer(player);
					player.setVolume(0.5);
					idPIn.setProgress(0.5);

					// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
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

				int size = lines.size();

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
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent1.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent2.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent3.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent4.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent5.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent6.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
			}
		});
		idMRecent7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (player != null) {
					player.stop();
					idPlay.setText(">");
				}
				URL url = null;
				try {
					url = new URL("file:///" + idMRecent7.getText());
					System.out.println(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Media media = new Media(url.toString());
				player = new MediaPlayer(media);

				final DoubleProperty width = idMediaView.fitWidthProperty();
				final DoubleProperty height = idMediaView.fitHeightProperty();

				width.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "width"));
				height.bind(Bindings.selectDouble(idMediaView.sceneProperty(), "height"));

				idMediaView.setPreserveRatio(true);

				idMediaView.setMediaPlayer(player);
				player.setVolume(0.5);
				idPIn.setProgress(0.5);

				// ctrl.launch("Test", "file:///" + file.getAbsolutePath());
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
					if (!idSliderTime.isDisable() && duration.greaterThan(Duration.ZERO)
							&& !idSliderTime.isValueChanging()) {
						idSliderTime.setValue(currentTime.divide(duration).toMillis() * 100);
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
			return String.format("%02d:%02d:%02d/%02d:%02d:%02d", intCountH, intCountM, intCountS, intLengthH,
					intLengthM, intLengthS);
		else
			return String.format("%02d:%02d/%02d:%02d", intCountM, intCountS, intLengthM, intLengthS);
	}
}
