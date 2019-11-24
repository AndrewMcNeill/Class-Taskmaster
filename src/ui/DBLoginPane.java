package ui;

import database.Const;
import database.Database;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DBLoginPane extends VBox {

    private static DBLoginPane instance;
    private TextField dbName   = new TextField();
    private TextField dbUser   = new TextField();
    private TextField dbPass   = new TextField();
    private TextField dbUrl    = new TextField();
    private Button login       = new Button("Login");
    private Label errorMessage = new Label("Please log in");

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
                Const.dbLoginData.put("name", dbName.getText());
                Const.dbLoginData.put("username", dbUser.getText());
                Const.dbLoginData.put("password", dbPass.getText());
                Const.dbLoginData.put("url", dbUrl.getText());
                //TODO: run DB test, change to main application panes
                try {
                    if (dbTest()) {
                        Database.getInstance().setupTables();
                        Database.getInstance().grabAllTasks();
                        Database.getInstance().grabTags(Main.tagList);
                        MainPane.getInstance().switchPane();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.getChildren().addAll(errorMessage, dbUrl, dbName, dbUser, dbPass, login);
    }

    private boolean dbTest() throws SQLException {
        boolean passed = false;
        HashMap<String, Boolean> tests = new HashMap<>();
        tests.put("create", false);
        tests.put("write", false);
        tests.put("read", false);
        tests.put("modify", false);
        tests.put("delete", false);


        ResultSet result = Database.getInstance().md.getTables(null, null, "testclasstaskmaster", null);
        if (result.next()) {
            Database.getInstance().sqlQuery("DROP TABLE testclasstaskmaster;", true);
            System.out.println("Table already exists, possibly due to failed test. Deleting table");
        }
        if (Database.getInstance().dbTestQuery("CREATE TABLE testclasstaskmaster( testcolid INT AUTO_INCREMENT PRIMARY KEY, testcol VARCHAR(99));")) {
            tests.put("create", true);
        }

        if (Database.getInstance().dbTestQuery("INSERT INTO testclasstaskmaster VALUES(0,'this is a db test')")) {
            tests.put("write", true);
        }

        if (Database.getInstance().dbTestQuery("SELECT * FROM testclasstaskmaster")) {
            tests.put("read", true);
        }

        if (Database.getInstance().dbTestQuery("UPDATE testclasstaskmaster SET testcol = 'different text in here' WHERE testcolid = 1")) {
            tests.put("modify", true);
        }

        if (Database.getInstance().dbTestQuery("DELETE FROM testclasstaskmaster WHERE testcolid = 1")) {
            tests.put("delete", true);
        }

        if (tests.containsValue(false)){
            System.out.println("[Test Failure] : \n create = " + tests.get("create")
                    + "\n write = " + tests.get("write") + "\n read = " + tests.get("read")
                    +"\n modify = " + tests.get("modify") + "\n delete = " + tests.get("delete") + "\n");
            errorMessage.setText("Something went wrong, check the console!");
        } else {
            System.out.println("All tests OK");
            Database.getInstance().sqlQuery("DROP TABLE testclasstaskmaster;", true);
            System.out.println("cleaning up db");
            passed = true;
        }
        return passed;
    }
}
