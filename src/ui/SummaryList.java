package ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Task;
import sample.Main;

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

        refresh();
    }

    /**
     * Clear the task summaries from the screen and replace with what's in Main.taskCollection
     */
    public void refresh() {
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setStyle("-fx-padding: 10px");
        for (int i = 0; i < Main.taskCollection.size(); i++) {
            vb.getChildren().add(new TaskSummary(Main.taskCollection.get(i)));
        }

        this.setContent(vb);
    }
}
