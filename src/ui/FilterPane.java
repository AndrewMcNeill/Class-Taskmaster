package ui;

import database.Database;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilterPane extends VBox {
    private static FilterPane instance;
    public static TagFilters tagFilters = new TagFilters();
    private static ScrollPane tagFilterScrollPane = new ScrollPane(tagFilters);
    private Button completedTasksButton = new Button("Completed");
    private Button uncompletedTasksButton = new Button("Incomplete");
    private Button bothTasksButton = new Button("Both");
    private Button allTasksButton = new Button("All Tasks");

    public static FilterPane getInstance() {
        if (FilterPane.instance == null)
            FilterPane.instance = new FilterPane();
        return FilterPane.instance;
    }

    private FilterPane() {
        this.setId("FilterPane");
        this.getChildren().add(new Text("Filter"));

        completedTasksButton.setOnMouseClicked(e -> {
                SummaryList.getInstance().setDisplayMode("completed");
                SummaryList.getInstance().refresh();
        });

        uncompletedTasksButton.setOnMouseClicked(e -> {
            SummaryList.getInstance().setDisplayMode("uncompleted");
            SummaryList.getInstance().refresh();
        });

        bothTasksButton.setOnMouseClicked(e -> {
            SummaryList.getInstance().setDisplayMode("");
            SummaryList.getInstance().refresh();
        });

        allTasksButton.setOnMouseClicked(e -> {
            Main.taskCollection.clear();
            Database.getInstance().grabAllTasks();
            SummaryList.getInstance().refresh();
        });

        //Formats today's, tomorrow's and a week from now's date's into a string to be used in SQL query
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String week = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Button statspane = new Button("Statistics");
        statspane.setOnMouseClicked(e->{
            Main.changeScene(StatsPane.getInstance());
            StatsPane.getInstance().refresh();
        });

        this.getChildren().addAll(
                allTasksButton,
                new FilterButton("Today","date = '" + today + "'"),
                new FilterButton("Tomorrow","date = '" + tomorrow + "'"),
                new FilterButton("Next 7 Days", "date BETWEEN '" + today + "' AND '" + week + "'"),
                tagFilterScrollPane, uncompletedTasksButton, completedTasksButton, bothTasksButton, statspane);
    }
}
