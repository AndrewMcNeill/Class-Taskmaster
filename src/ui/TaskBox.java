package ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Task;
import sample.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TaskBox extends HBox {

    private final String FORMAT = "dd/MM/yyyy";

    private HashMap<String, MenuItem> tagList = new HashMap<>();
    private String date;

    TextField taskTitle;
    DatePicker taskDate;
    MenuButton tagsButton;

    TaskBox() {

        taskTitle = new TextField();
        taskDate = new DatePicker();
        tagsButton = new MenuButton("Tags");
        Button addTask = new Button("Add Task");

        taskTitle.setPromptText("Add A Task...");
        taskDate.setPromptText("Pick a Date...");
        taskDate.setOnAction(e -> {
           date = taskDate.getValue().format(DateTimeFormatter.ofPattern(FORMAT));

           System.out.println(date);
        });

        TextField newTag = new TextField();
        newTag.setPromptText("+ New Tag");

        //this will need cleanup at some point
        newTag.setOnAction(e->{
            if (!tagList.containsKey(newTag.getText())){
                if (!newTag.getText().equals("")) {
                    MenuItem item = new MenuItem(newTag.getText());
                    item.setOnAction(f->{tagsButton.setText(item.getText());});
                    tagList.put(newTag.getText(), item);
                    tagsButton.getItems().add(item);
                    tagsButton.setText(item.getText());
                    newTag.clear();
                }
            }
        });

        addTask.setOnMouseClicked(e->{
            String tag = tagsButton.getText().equals("Tags") ? "" : tagsButton.getText();               //          default to no tag if isnt chosen
            date = date == null ? LocalDate.now().format(DateTimeFormatter.ofPattern(FORMAT)) : date;   //default to todays date if no date entered
            Task task = new Task(0, taskTitle.getText(), date, tag, false);
            Main.addTask(task);
            clearUI();
        });

        CustomMenuItem addTag = new CustomMenuItem(newTag, false);

        tagsButton.getItems().add(addTag);

        this.getChildren().addAll(taskTitle, taskDate, tagsButton, addTask);

    }

    private void clearUI() {
        tagsButton.setText("Tags");
        taskDate.getEditor().clear();
        taskTitle.clear();
    }

}

