package ui;

import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FilterPane extends VBox {
    private static FilterPane instance;
    public static FilterTagButton f = new FilterTagButton();
    private static ScrollPane s = new ScrollPane(f);

    public static FilterPane getInstance() {
        if (FilterPane.instance == null)
            FilterPane.instance = new FilterPane();
        return FilterPane.instance;
    }

    private FilterPane() {
        this.setStyle("-fx-background-color: blueviolet");
        this.getChildren().add(new Text("Filter"));

        this.getChildren().add(s);
    }
}
