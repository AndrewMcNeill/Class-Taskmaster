package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Task;
import ui.MainPane;
import ui.SummaryList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main extends Application {

    public static ArrayList<Task> taskCollection = new ArrayList<Task>();
    public static Set<String> tagList = new HashSet<>();

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Taskmaster");
        primaryStage.setScene(new Scene(MainPane.getInstance(), 960, 540 ));
        primaryStage.show();
    }

    public static void addTask(Task task) {
        Main.taskCollection.add(task);
        SummaryList.getInstance().refresh();
    }

    public static void changeScene(Pane pane) {
        primaryStage.getScene().setRoot(pane);
    }

    public static void main(String[] args) { launch(args); }
}
