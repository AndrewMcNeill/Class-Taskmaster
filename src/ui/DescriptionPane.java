package ui;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Task;
import sample.Main;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DescriptionPane extends VBox {
    private static DescriptionPane instance;
    private Task task;
    private HBox hbox                = new HBox();
    private VBox vbox                = new VBox();
    private TextField taskTitle      = new TextField();
    private DatePicker taskDate      = new DatePicker();
    private HBox tagsButtonBox       = new HBox();
    private TagsButton tagsButton    = new TagsButton();
    private TextArea taskDescription = new TextArea();
    private final Label noneSelected = new Label("Click on a Task to see its details");


    public static DescriptionPane getInstance() {
        if (DescriptionPane.instance == null)
            DescriptionPane.instance = new DescriptionPane();
        return DescriptionPane.instance;
    }

    private DescriptionPane() {
        HBox.setHgrow(taskTitle, Priority.ALWAYS);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        VBox.setVgrow(taskDescription, Priority.ALWAYS);
        //((Region) taskDescription.lookup(".content")).setStyle("-fx-background-color: red");
        hbox.getChildren().addAll(taskTitle, taskDate);
        tagsButtonBox.getChildren().add(tagsButton.tagsButton);
        tagsButtonBox.setAlignment(Pos.BASELINE_RIGHT);
        vbox.getChildren().addAll(hbox, new Region(), taskDescription, new Region(), tagsButtonBox);
        vbox.setVisible(false);

        this.getChildren().addAll(noneSelected, vbox);

        taskTitle.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.ENTER) {
                this.task.setTitle(taskTitle.getText());
                SummaryList.getInstance().refresh();
            }
        });

        taskDate.setEditable(false);
        taskDate.getEditor().setDisable(true);
        /* https://docs.oracle.com/javafx/2/api/javafx/beans/value/ChangeListener.html
         * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBoxBase.html#valueProperty
         * checks whether the values change in the node, use ChangeListener
         * to check whether the value was changed in the node, if the value is different,
         * update the values for the respective task
         */
        taskDate.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!(this.task.getDate().equals(newVal))) {
                this.task.setDate(taskDate.getValue());
                SummaryList.getInstance().refresh();
            }
        });


        tagsButton.tagsButton.textProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!(this.task.getTag().equals(newVal))) {
                this.task.setTag(tagsButton.tagsButton.getText());
                Main.taskCollection.clear();
                Database.getInstance().grabAllTasks(); //refresh the task list
            }
        });

        taskDescription.focusedProperty().addListener((obs, oldVal, newVal)->{
            if (!newVal) {
                this.task.setDescription(taskDescription.getText());
            }
        });
    }

    void updateDesc(Task task) {
        this.task = task;
        //noneSelected.setVisible(false);
        this.getChildren().remove(noneSelected);
        vbox.setVisible(true);
        taskTitle.setText(task.getTitle());
        taskDate.setValue(task.getDate());
        String tag = task.getTag().equals("") ? "No Tag!" : task.getTag();
        tagsButton.tagsButton.setText(tag);
        taskDescription.setText(task.getDescription());
    }





}
