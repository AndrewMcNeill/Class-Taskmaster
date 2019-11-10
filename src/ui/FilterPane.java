package ui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FilterPane extends VBox {
    private static FilterPane instance;

    public static FilterPane getInstance() {
        if (FilterPane.instance == null)
            FilterPane.instance = new FilterPane();
        return FilterPane.instance;
    }

    private FilterPane() {
        this.setStyle("-fx-background-color: blueviolet");
        this.getChildren().add(new Text("Filter"));
    }
}
