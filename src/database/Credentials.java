package database;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Credentials {
    public static HashMap<String, String> dbLoginData = new HashMap<>();
    public static File path = new File(System.getProperty("user.home") + "/.taskmaster");

    public static void load() {
        try {
            path.createNewFile();
            ArrayList<String> credentials = new ArrayList<>();
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine())
                credentials.add(scanner.nextLine());
            if (credentials.size() > 3) {
                dbLoginData.put("url", credentials.get(0));
                dbLoginData.put("name", credentials.get(1));
                dbLoginData.put("username", credentials.get(2));
                dbLoginData.put("password", credentials.get(3));
            }
        } catch (Exception e) { }
    }

    public static void save() {
        if (!(  dbLoginData.containsKey("url") &&
                dbLoginData.containsKey("name") &&
                dbLoginData.containsKey("username") &&
                dbLoginData.containsKey("password")))
            return;
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(dbLoginData.get("url")+"\n");
            fileWriter.write(dbLoginData.get("name")+"\n");
            fileWriter.write(dbLoginData.get("username")+"\n");
            fileWriter.write(dbLoginData.get("password"));
            fileWriter.close();
        } catch (Exception e) { }
    }

}
