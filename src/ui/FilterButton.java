package ui;

import database.Database;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import models.Task;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilterButton extends HBox {
    private String where;

    public FilterButton(String text, String where) {
        this.where = where;
        Button button = new Button(text);
        getChildren().add(button);

        button.setOnMouseClicked(e -> {
            Main.taskCollection.clear();
            grabTasks();
        });
    }

    public void grabTasks() {
        try {
            Database db = Database.getInstance();
            ResultSet allTasks = db.sqlQuery("SELECT * from tasks WHERE " + where + " ORDER BY date ASC;", false);
            while (allTasks.next()) {
                //Grab taskid
                int taskid = (allTasks.getInt("taskid"));

                //Grab tags
                String tagsQuery = "SELECT tagname FROM tags WHERE tagid = " +
                        "(SELECT tagid FROM tagstaskrelational WHERE taskid = '" + taskid + "' LIMIT 1);";

                ResultSet tagSet = db.sqlQuery(tagsQuery, false);
                //Extract tags
                tagSet.next();
                String tag = (tagSet.getString("tagname"));
                //Grab description
                String descriptionQuery = "SELECT description FROM descriptions WHERE taskid = '" +
                        taskid + "' LIMIT 1;";

                ResultSet descriptionSet = db.sqlQuery(descriptionQuery,false);
                //Extract description
                descriptionSet.next();
                String description = (descriptionSet.getString("description"));
                //Extract task data
                String tasktitle = (allTasks.getString("title"));
                LocalDate date = (allTasks.getDate("date").toLocalDate());
                Boolean completed = (allTasks.getBoolean("completed"));


                Task task = new Task(taskid, tasktitle, date,description,tag, completed);
                Main.addTask(task);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        SummaryList.getInstance().refresh();
    }
}
