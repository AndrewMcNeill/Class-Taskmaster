package ui;

import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class MainPane extends BorderPane {
    private static MainPane instance;

    public static MainPane getInstance() {
        if (MainPane.instance == null)
            MainPane.instance = new MainPane();
        return MainPane.instance;
    }

    private MainPane() {

        this.setCenter(DBLoginPane.getInstance());
    }

    public void switchPane() {
        this.setLeft(FilterPane.getInstance());
        this.setCenter(SummaryPane.getInstance());
        this.setRight(DescriptionPane.getInstance());
    }
}
