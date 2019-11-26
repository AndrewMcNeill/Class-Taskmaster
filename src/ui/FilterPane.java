package ui;

import database.Database;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Task;
import sample.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilterPane extends VBox {
    private static FilterPane instance;
    public static FilterTagButton f = new FilterTagButton();
    private static ScrollPane s = new ScrollPane(f);
    private Button CompletedTasksButton = new Button("Completed Tasks");
    private Button UncompletedTasksButton = new Button("Uncompleted Tasks");
    private Button BothTasksButton = new Button("All Tasks (Filtered) *");
    private Button AllTasksButton = new Button("All Tasks");

    public static FilterPane getInstance() {
        if (FilterPane.instance == null)
            FilterPane.instance = new FilterPane();
        return FilterPane.instance;
    }

    private FilterPane() {
        this.setStyle("-fx-background-color: blueviolet");
        this.getChildren().add(new Text("Filter"));

        CompletedTasksButton.setOnMouseClicked(e -> {
                SummaryList.getInstance().setDisplayMode("completed");
                SummaryList.getInstance().refresh();
        });

        UncompletedTasksButton.setOnMouseClicked(e -> {
            SummaryList.getInstance().setDisplayMode("uncompleted");
            SummaryList.getInstance().refresh();
        });

        BothTasksButton.setOnMouseClicked(e -> {
            SummaryList.getInstance().setDisplayMode("");
            SummaryList.getInstance().refresh();
        });

        AllTasksButton.setOnMouseClicked(e -> {
            SummaryList.getInstance().setDisplayMode("");
            Main.taskCollection.clear();
            Database.getInstance().grabAllTasks();
            SummaryList.getInstance().refresh();
        });

            //Formats today's, tomorrow's and a week from now's date's into a string to be used in SQL query
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String week = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        this.getChildren().addAll(
                new FilterButton("Today","date = '" + today + "'"),
                new FilterButton("Tomorrow","date = '" + tomorrow + "'"),
                new FilterButton("Next 7 Days", "date BETWEEN '" + today + "' AND '" + week + "'"),
                AllTasksButton,UncompletedTasksButton,BothTasksButton,CompletedTasksButton,s);
    }
}
