package ui;

import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class DescriptionPane extends VBox {
    private static DescriptionPane instance;

    public static DescriptionPane getInstance() {
        if (DescriptionPane.instance == null)
            DescriptionPane.instance = new DescriptionPane();
        return DescriptionPane.instance;
    }

    private DescriptionPane() {
        this.setStyle("-fx-background-color: aqua");
        this.getChildren().add(new Text("Description"));
    }
}
