package ui;

import database.Credentials;
import database.Database;
import javafx.scene.layout.BorderPane;
import sample.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPane extends BorderPane {
    private static MainPane instance;

    public static MainPane getInstance() {
        if (MainPane.instance == null)
            MainPane.instance = new MainPane();
        return MainPane.instance;
    }

    private MainPane() {
        Credentials.load();
        Database db = Database.getInstance();
        if (db == null)
            this.setCenter(DBLoginPane.getInstance());
        else {
            Database.getInstance().setupTables();
            Database.getInstance().grabAllTasks();
            Database.getInstance().grabTags(Main.tagList);
            switchPane();
        }
    }

    public void switchPane() {
        this.setLeft(FilterPane.getInstance());
        this.setCenter(SummaryPane.getInstance());
        this.setRight(DescriptionPane.getInstance());
    }
}
