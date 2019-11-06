package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Task;
import ui.MainPane;
import ui.SummaryList;
import ui.SummaryPane;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application {

    public static ArrayList<Task> taskCollection = new ArrayList<Task>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Taskmaster");
        primaryStage.setScene(new Scene(MainPane.getInstance(), 800, 600));
        primaryStage.show();
    }

    public static void addTask(Task task) {
        Main.taskCollection.add(task);
        SummaryList.getInstance().refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
