package softwareEngineering;
import java.sql.*;
import java.util.*;

public class ConnectionManager {
    private Connection con = null;

    public ConnectionManager() {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

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

    public void close(){
        try{
            con.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
