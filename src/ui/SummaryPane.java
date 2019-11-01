package ui;

import javafx.scene.layout.Pane;

public class SummaryPane extends Pane {
    private static SummaryPane instance;

    public static SummaryPane getInstance() {
        if (SummaryPane.instance == null)
            SummaryPane.instance = new SummaryPane();
        return SummaryPane.instance;
    }

    private SummaryPane() {

    }
}
