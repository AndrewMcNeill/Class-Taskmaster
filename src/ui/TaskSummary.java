package ui;

import database.Database;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Task;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class TaskSummary extends GridPane {

    private Task ownTask;

    //TODO: Take in a Task instance
    public TaskSummary(Task task) {
        ownTask = task;
        this.setStyle("-fx-background-color: aquamarine");
        this.add(new Text(task.getTitle()), 0, 0, 2, 1);
        this.add(new Text(task.getTag()), 0, 1, 1, 1);
        this.add(new Text(task.getStringDate()), 1, 1, 1, 1);
        Button done = new Button("✔");
        Button delete = new Button("X");
        delete.setMinWidth(30);
        done.setMinWidth(30);
        delete.setStyle("-fx-background-color: red");
        done.setStyle("-fx-background-color: " + (ownTask.isCompleted() ? "green" : "yellow"));

        this.add(delete,3,0,1,2);
        this.add(done, 2, 0, 1, 2);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setFillWidth(true);
        col1.setPrefWidth(99999);
        this.getColumnConstraints().add(col1);

        this.setOnMouseEntered(e->{
            this.setStyle("-fx-background-color: cornflowerblue");
        });

        this.setOnMouseExited(e->{
            this.setStyle("-fx-background-color: aquamarine");
        });

        this.setOnMouseClicked(e-> {
            this.requestFocus();
            DescriptionPane.getInstance().updateDesc(ownTask);
        });

        done.setOnMouseClicked((MouseEvent e) -> {
            ownTask.setCompleted(!ownTask.isCompleted());
            done.setStyle("-fx-background-color: " + (ownTask.isCompleted() ? "green" : "yellow"));
        });

        //Event handler for delete button, deletes task
        delete.setOnMouseClicked((MouseEvent e) -> {
            Database.getInstance().deleteTask(ownTask.getId());
        });

    }



}
