package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.Task;
import ui.MainPane;
import ui.SummaryList;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static ArrayList<Task> taskCollection = new ArrayList<Task>();
    public static HashMap<String, MenuItem> tagList = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Taskmaster");
        primaryStage.setScene(new Scene(MainPane.getInstance(), 960, 540 ));
        primaryStage.show();
    }

    public static void addTask(Task task) {
        Main.taskCollection.add(task);
        SummaryList.getInstance().refresh();
    }

    public static void main(String[] args) { launch(args); }
}
