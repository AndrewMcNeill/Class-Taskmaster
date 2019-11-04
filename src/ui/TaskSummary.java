package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TaskSummary extends GridPane {

    //TODO: Take in a Task instance
    public TaskSummary() {
        this.setStyle("-fx-background-color: aquamarine");
        this.add(new Text("Task title text goes here. Task title text goes here. Task title text goes here. "), 0, 0, 2, 1);
        this.add(new Text("Tags go here"), 0, 1, 1, 1);
        this.add(new Text("Date goes here"), 1, 1, 1, 1);
        this.add(new Button("✔️"), 2, 0, 1, 2);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setFillWidth(true);
        col1.setPrefWidth(99999);
        this.getColumnConstraints().add(col1);
    }
}
