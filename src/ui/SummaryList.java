package ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SummaryList extends ScrollPane {
    private static SummaryList instance;

    public static SummaryList getInstance() {
        if (SummaryList.instance == null)
            SummaryList.instance = new SummaryList();
        return SummaryList.instance;
    }

    private SummaryList() {
        //this.getChildren().add(new Text("Hello"));
        this.fitToWidthProperty().set(true);
        this.getStyleClass().add("edge-to-edge");
        this.setStyle("-fx-background: transparent");

        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setStyle("-fx-padding: 10px");
        for (int i = 0; i < 100; i++) {
            vb.getChildren().add(new TaskSummary());
        }

        this.setContent(vb);
    }
}
