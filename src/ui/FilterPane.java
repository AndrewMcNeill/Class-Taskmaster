package ui;

import javafx.scene.layout.Pane;

public class FilterPane extends Pane {
    private static FilterPane instance;

    public static FilterPane getInstance() {
        if (FilterPane.instance == null)
            FilterPane.instance = new FilterPane();
        return FilterPane.instance;
    }

    private FilterPane() {

    }
}
