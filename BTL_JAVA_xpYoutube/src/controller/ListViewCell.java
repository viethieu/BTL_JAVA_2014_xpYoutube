package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ListViewCell extends ListCell<Item> {
	HBox hbox1 = new HBox();
	HBox hbox2 = new HBox();
	ImageView imageView;
	VBox vbox = new VBox();
	Label label1 = new Label("(empty)");
	Label label2 = new Label("(empty)");
	Label label3 = new Label("(empty)");
	Label label4 = new Label("(empty)");
	Hyperlink hyperlink = new Hyperlink();
	Pane pane = new Pane();
	Image image;
	String link, title;

	public ListViewCell() {
		super();
		hbox2.getChildren().setAll(label2, pane, label3);
		vbox.getChildren().setAll(label1, hbox2, label4, hyperlink);

		HBox.setHgrow(pane, Priority.ALWAYS);
		hbox1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					Controller_Main ctrl = new Controller_Main();
					String id = "";
					int i, j;
					for (i = 0; i < link.length(); i++) {
						if (link.charAt(i) == 'v') {
							for (j = i + 2; j < link.length(); j++) {
								id += link.charAt(j);
								if (link.charAt(j + 1) == '&')
									break;
							}
						}

					}
					ctrl.launch(title, id);
				}
					
			}
		});
	}

	@Override
	public void updateItem(Item it, boolean empty) {
		super.updateItem(it, empty);
		setText(null);
		if (!empty) {

			link = it.getLink();
			title = it.getTitle();
			label1.setText("  " + it.getTitle());
			label1.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-padding: 0 0 10 0;");
			label2.setText("  " + it.getDate());
			label3.setText(it.getDuration());
			label4.setText("  " + "Lượt xem: " + it.getViewCount());
			image = new Image(it.geticon());
			imageView = new ImageView(image);
			hyperlink.setText(it.getLink());

			hbox1.getChildren().setAll(imageView, vbox);
			setGraphic(hbox1);
		} else {
			setGraphic(null);
		}
	}
}