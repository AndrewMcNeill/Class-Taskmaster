package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class DBLoginPane extends VBox {

    private static DBLoginPane instance;
    private TextField dbName = new TextField();
    private TextField dbUser = new TextField();
    private TextField dbPass = new TextField();
    private TextField dbUrl  = new TextField();
    private Button login     = new Button("Login");

    public HashMap<String, String> dbLoginData = new HashMap<>();

    public static DBLoginPane getInstance() {
        if (DBLoginPane.instance == null) {
            DBLoginPane.instance = new DBLoginPane();
        }
        return DBLoginPane.instance;
    }

    private DBLoginPane() {
        this.setStyle("-fx-background-color: honeydew");
        dbName.setPromptText("Database name");
        dbUser.setPromptText("Username");
        dbPass.setPromptText("Password");
        dbUrl.setPromptText("Database URL");

        login.setOnMouseClicked(e->{
            if (!dbName.getText().equals("") && !dbUser.getText().equals("") &&
            !dbPass.getText().equals("") && !dbUrl.getText().equals("")){
                dbLoginData.put("name", dbName.getText());
                dbLoginData.put("username", dbUser.getText());
                dbLoginData.put("password", dbPass.getText());
                dbLoginData.put("url", dbName.getText());
                //TODO: run DB test, change to main application panes
            }
        });

        this.getChildren().addAll(dbUrl, dbName, dbUser, dbPass, login);
    }

}
