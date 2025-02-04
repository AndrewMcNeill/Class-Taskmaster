package ui;

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
        this.getChildren().add(new TaskBox());
        this.getChildren().add(SummaryList.getInstance());
    }
}
