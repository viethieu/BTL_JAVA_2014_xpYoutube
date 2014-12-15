package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.ParseJsonYoutube;

public class Controller_On implements Initializable {

	private Pane pane;
	private Scene scene;
	private Stage stage;
	private String str;
	private String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private ArrayList<String> lines = new ArrayList<String>();
	private int size;
	private Stage primaryStage;

	@FXML
	private ImageView idSearch;

	@FXML
	private TextField idTextSearch;

	@FXML
	private MenuItem idMOpen, idMAbout;
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
	private WebView idWebView;

	public Controller_On() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/giaodien/ONLINE.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/css/Onl.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launch(String str1, String str2) {
		link = str2;
		if (link != null) {
			final WebView webView = idWebView;
			webView.getEngine().loadContent(
					"<iframe width=\"1366\" height=\"768\" src=\"http://www.youtube.com/embed/"
							+ link
							+ "\" frameborder=\"0\" allowfullscreen></iframe>");
		}

		primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/image/title.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
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
					if (stage != null)
						stage.close();
					stage = ctrl.launch();
				}
			}
		});
		idSearch.setOnMouseClicked(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				str = idTextSearch.getText();
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
		idMAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				AnchorPane root;
				try {
					root = FXMLLoader.load(getClass().getResource(
							"/giaodien/Team.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = new Stage();
					primaryStage.setTitle("About YourTube");
					Image iconSoftWare = new Image("/image/title.png");
					primaryStage.getIcons().add(iconSoftWare);
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
		
		idMRecent.setOnShowing(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader("log_online.txt"));
					String tmp = "";
					while ((tmp = br.readLine()) != null) {
						lines.add(tmp);
					}
					br.close();
				} catch (Exception e) {
					System.out.println("Exception caught : " + e);
				}

				size = lines.size();
				System.out.println (size);

				if (size == 0) {
					idMRecent1.setVisible(false);
					idMRecent2.setVisible(false);
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				}
				if (size == 2) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setVisible(false);
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 4) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setVisible(false);
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 6) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setText(lines.get(size - 6));
					idMRecent4.setVisible(false);
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 8) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setText(lines.get(size - 6));
					idMRecent4.setText(lines.get(size - 8));
					idMRecent5.setVisible(false);
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 10) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setText(lines.get(size - 6));
					idMRecent4.setText(lines.get(size - 8));
					idMRecent5.setText(lines.get(size - 10));
					idMRecent6.setVisible(false);
					idMRecent7.setVisible(false);
				} else if (size == 12) {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setText(lines.get(size - 6));
					idMRecent4.setText(lines.get(size - 8));
					idMRecent5.setText(lines.get(size - 10));
					idMRecent6.setText(lines.get(size - 12));
					idMRecent7.setVisible(false);
				} else {
					idMRecent1.setText(lines.get(size - 2));
					idMRecent2.setText(lines.get(size - 4));
					idMRecent3.setText(lines.get(size - 6));
					idMRecent4.setText(lines.get(size - 8));
					idMRecent5.setText(lines.get(size - 10));
					idMRecent6.setText(lines.get(size - 12));
					idMRecent7.setText(lines.get(size - 14));
				}

			}
		});

		idMRecent1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent1.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-1))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent1.getText() + "\n");
					fw.write(lines.get(size-1) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});
		idMRecent2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent2.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-3))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent2.getText() + "\n");
					fw.write(lines.get(size-3) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});
		
		idMRecent3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent3.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-5))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent3.getText() + "\n");
					fw.write(lines.get(size-5) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});
		
		idMRecent4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent4.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-7))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent4.getText() + "\n");
					fw.write(lines.get(size-7) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});
		idMRecent5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent5.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-9))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent5.getText() + "\n");
					fw.write(lines.get(size-9) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});
		idMRecent6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent6.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-11))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent6.getText() + "\n");
					fw.write(lines.get(size-11) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
			
		});
		
		idMRecent7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(idMRecent7.getText());
				idWebView.getEngine().loadContent(
						"<iframe width=\"1360\" height=\"768\" src=\"http://www.youtube.com/embed/" + getLink(lines.get(size-13))
								+ "\" frameborder=\"0\" allowfullscreen></iframe>");
				try {
					String filename = "log_online.txt";
					FileWriter fw = new FileWriter(filename, true);
					fw.write(idMRecent7.getText() + "\n");
					fw.write(lines.get(size-13) + "\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		});

		idMClearRecent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					PrintWriter out= new PrintWriter(new FileWriter("log_online.txt"));
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
	
	private String getLink (String link){
		String id = null;
		for (int i = 0; i < link.length(); i++) {
			if (link.charAt(i) == 'v') {
				for (int j = i + 2; j < link.length(); j++) {
					id += link.charAt(j);
					if (link.charAt(j + 1) == '&')
						break;
				}
			}
		}
		return id;
	}

}
