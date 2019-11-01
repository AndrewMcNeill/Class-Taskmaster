package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.MainPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Taskmaster");
        primaryStage.setScene(new Scene(MainPane.getInstance(), 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
