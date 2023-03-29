package softwareEngineering;
import java.io.*;
import java.sql.*;
import java.util.*;


public class ConnectionManager {
    private static ConnectionManager builder = null;
    private String dbName;
    private Connection con = null;

    // Have used the singleton pattern to create a single connection object
    private ConnectionManager(String databaseName) {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url + databaseName, username, password);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

    // This method will return the same connection object if the database name is same
    public static ConnectionManager getCM(String databaseName){
        if(builder == null || !builder.dbName.equals(databaseName)){
            builder = new ConnectionManager(databaseName);
            builder.dbName = databaseName;
        }
        return builder;
    }

    // This method will return the connection object
    public Connection getConnection(){
        return con;
    }

    public ResultSet executeQuery(String query){
        ResultSet rs = null;
        try{
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        return rs;
    }

    public void executeUpdate(String query){
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void runScript(String scriptPath){
        try{
            java.util.Scanner sc = new java.util.Scanner(new File(scriptPath));
            String query = "";
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
                if(line.contains("--")){
                    continue;
                }
                else if(line.contains(";")){
                    query += line;
                    executeUpdate(query);
                    query = "";
                }
                else{
                    query += line;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void close(){
        try{
            con.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
