package database;

import java.sql.*;

public class Database {
    public static Database instance;
    public DatabaseMetaData md;
    private Connection connection;

    public static Database getInstance() {
        if (Database.instance == null)
            Database.instance = new Database();
        return Database.instance;
    }

    private Database() {
        if(connection == null) {
            try {
                System.out.println(Const.dbLoginData);
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + Const.dbLoginData.get("url") + "/"
                                + Const.dbLoginData.get("name") + "?useSSL=false", Const.dbLoginData.get("username"), Const.dbLoginData.get("password"));
                System.out.println("Created Connection");
                md = connection.getMetaData();
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
        Statement sqlStatement;

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
}
