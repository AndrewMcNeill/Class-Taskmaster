package ui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SummaryPane extends VBox {
    private static SummaryPane instance;

    public static SummaryPane getInstance() {
        if (SummaryPane.instance == null)
            SummaryPane.instance = new SummaryPane();
        return SummaryPane.instance;
    }

    private SummaryPane() {
        this.setStyle("-fx-background-color: bisque");
        this.getChildren().add(new Text("Summary"));
    }
}
