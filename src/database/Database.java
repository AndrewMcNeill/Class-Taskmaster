package database;

import java.sql.*;

public class Database {
    public static Database instance;
    private Connection connection;

    public static Database getInstance() {
        if (Database.instance == null)
            Database.instance = new Database();
        return Database.instance;
    }

    private Database() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Const.DB_NAME + "?useSSL=false",
                        Const.DB_USER, Const.DB_PASS);
                System.out.println("Created Connection");
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            /*
            TODO: Create tables
            try {
                createTable(Const.TABLE_LOCATION,
                        Const.CREATE_TABLE_LOCATION;
                createTable(Const.TABLE_COIN,
                        Const.CREATE_TABLE_COIN);
                createTable(Const.TABLE_CONDITION,
                        Const.CREATE_TABLE_COIN_CONDITION;
                createTable(Const.TABLE_ITEM,
                        Const.CREATE_TABLE_ITEM);
            } catch (SQLException e) {
                e.printStackTrace();
            }
             */
        }
    }

    public void createTable(String tableName, String tableQuery) throws SQLException {

        //
        Statement sqlStatement;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet result = md.getTables(null, null, tableName, null);
        if (result.next()) {
            System.out.println(tableName + " table already exists");
        } else {
            sqlStatement = connection.createStatement();
            sqlStatement.execute(tableQuery);
            System.out.println("The "
                    + tableName + " table has been created");
        }
    }
}
