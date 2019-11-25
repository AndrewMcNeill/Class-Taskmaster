package database;

import models.Task;
import sample.Main;
import ui.SummaryList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Set;

public class Database {
    public static Database instance;
    public DatabaseMetaData md;
    private Connection connection;

    public static Database getInstance() {
        if (Database.instance == null)
            Database.instance = new Database();
        if (instance.connection == null)
            instance = null;
        return Database.instance;
    }

    private Database() {
        login();
    }

    public void login() {
        if(connection == null) {
            try {
                System.out.println(Credentials.dbLoginData);
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + Credentials.dbLoginData.get("url") + "/"
                        + Credentials.dbLoginData.get("name") + "?useSSL=false", Credentials.dbLoginData.get("username"), Credentials.dbLoginData.get("password"));
                System.out.println("Created Connection");
                md = connection.getMetaData();
            }
            catch(Exception e) {
                e.printStackTrace();
                connection = null;
            }
        }
    }

    public void setupTables() {
        try {
            System.out.println("Creating tables..");
            createTable("tasks",
                    "CREATE TABLE `tasks` (" +
                            "taskid INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                            "completed BOOL, " +
                            "title VARCHAR(255)," +
                            "date DATE" +
                            ");");
            if (createTable("tags",
                    "CREATE TABLE `tags` (" +
                            "tagid INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                            "tagname varchar(255)" +
                            ");")) {
                sqlQuery("INSERT into `tags` (tagid, tagname) VALUES (0, 'No Tag!')", true);
            }

            createTable("tagstaskrelational",
                    "CREATE TABLE `tagstaskrelational` (" +
                            "taskid INT PRIMARY KEY, " +
                            "tagid INT" +
                            ");");
            createTable("descriptions",
                    "CREATE TABLE `descriptions` (" +
                            "taskid INT PRIMARY KEY, " +
                            "description TEXT" +
                            ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createTable(String tableName, String tableQuery) throws SQLException {
        Statement sqlStatement;

        ResultSet result = md.getTables(null, null, tableName, null);
        if (result.next()) {
            System.out.println(tableName + " table already exists");
            return false;
        } else {
            sqlStatement = connection.createStatement();
            sqlStatement.execute(tableQuery);
            System.out.println("The "
                    + tableName + " table has been created");
            return true;
        }

    }

    public boolean dbTestQuery(String sql) {
        boolean ok = false;
        try {
            Statement sqlStatement = connection.createStatement();
            sqlStatement.execute(sql);
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public ResultSet sqlQuery(String sql, boolean modify) {
        boolean ok = false;
        ResultSet rs = null;
        try {
            Statement sqlStatement = connection.createStatement();
            if (modify) {
                sqlStatement.executeUpdate(sql);
            } else {
                rs = sqlStatement.executeQuery(sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void insertTask(Task task){

        String addTask = "INSERT INTO `tasks` VALUES(0, false, '" + task.getTitle() + "', '" + task.getDate() +"');";



        String addTaskTagRelation = "INSERT INTO tagstaskrelational VALUES((SELECT LAST_INSERT_ID() FROM tasks LIMIT 1),(SELECT tagid FROM tags WHERE tagname = '" +
                task.getTag() + "' LIMIT 1));";



        String addDescription = "INSERT INTO `descriptions` (`taskid`,`description`)" +
                "VALUES" +
                "((SELECT LAST_INSERT_ID() as taskid from tasks LIMIT 1),'" +
                task.getDescription() + "');";

        sqlQuery(addTask, true);

        try {
            ResultSet rs = sqlQuery("SELECT LAST_INSERT_ID() as taskid from tasks LIMIT 1", false);
            rs.next();
            task.setId(rs.getInt("taskid"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sqlQuery(addTaskTagRelation, true);
        sqlQuery(addDescription, true);
    }

    public void insertTag(String tagName) {
        String addTag = "INSERT INTO `tags` VALUES(0, '" + tagName + "');";
        sqlQuery(addTag, true);
    }

    public void grabTags(Set<String> tagList){
        try {
            ResultSet rs = sqlQuery("SELECT * from tags", false);
            while (rs.next()) {
                String tagname = (rs.getString("tagname"));
                tagList.add(tagname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void grabAllTasks(){
        try {
            ResultSet allTasks = sqlQuery("SELECT * from tasks", false);
            while (allTasks.next()) {

                //Grab taskid
                int taskid = (allTasks.getInt("taskid"));

                //Grab tags
                String tagsQuery = "SELECT tagname FROM tags WHERE tagid = " +
                        "(SELECT tagid FROM tagstaskrelational WHERE taskid = '" + taskid + "' LIMIT 1);";

                ResultSet tagSet = sqlQuery(tagsQuery, false);
                //Extract tags
                tagSet.next();
                String tag = (tagSet.getString("tagname"));
                //Grab description
                String descriptionQuery = "SELECT description FROM descriptions WHERE taskid = '" +
                        taskid + "' LIMIT 1;";

                ResultSet descriptionSet = sqlQuery(descriptionQuery,false);
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
    }



    public void removeTag(String tagName) {
        sqlQuery("UPDATE tagstaskrelational SET tagid = 1 WHERE tagid = (SELECT tagid FROM tags WHERE tagname = '"+ tagName +"');", true);
        sqlQuery("DELETE FROM tags WHERE tagname = '"+ tagName + "';", true);
        Main.taskCollection.clear();
        grabAllTasks();
    }

    /**
     * Method deletes task when delete button is clicked
     * @param taskid
     */
    public void deleteTask(int taskid){
        //Queries to delete the task information from tasks and description
        //Not deleting from tags, as we want to keep the tag.

        String deleteTask = "DELETE FROM tasks WHERE taskid = " + taskid + ";";
        String deleteDesc = "DELETE FROM descriptions WHERE taskid = " + taskid + ";";
        String deleteTaskTagRelation = "DELETE FROM tagstaskrelational WHERE taskid = " + taskid + ";";

        //Run sqlQueries
        sqlQuery(deleteDesc,true);
        sqlQuery(deleteTaskTagRelation, true);
        sqlQuery(deleteTask, true);

        //Refresh tasks after deletion
        Main.taskCollection.clear();
        grabAllTasks();
        SummaryList.getInstance().refresh();
    }
}
