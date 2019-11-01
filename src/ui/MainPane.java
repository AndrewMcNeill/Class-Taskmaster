package ui;

import javafx.scene.layout.BorderPane;

public class MainPane extends BorderPane {
    private static MainPane instance;

    public static MainPane getInstance() {
        if (MainPane.instance == null)
            MainPane.instance = new MainPane();
        return MainPane.instance;
    }

    private MainPane() {
        
    }
}
