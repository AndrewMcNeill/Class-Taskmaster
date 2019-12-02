package ui;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import sample.Main;

public class StatsPane extends BorderPane {

    private static StatsPane instance;

    public static StatsPane getInstance() {
        if (StatsPane.instance == null)
            StatsPane.instance = new StatsPane();
        return StatsPane.instance;
    }

    private StatsPane() {
        Label l = new Label("statistics pane");
        this.setCenter(l);
        Button backButton = new Button("Back to tasks");
        backButton.setOnMouseClicked(e->{
            Main.changeScene(MainPane.getInstance());
        });
    }

}
