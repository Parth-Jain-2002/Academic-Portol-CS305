package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class InstructorTest {
    Connection con;
    
    public InstructorTest(){
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
    
    @Test public void managerTest(){
        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Thank you for using the system. Have a nice day!";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest(){
        String query = "UPDATE currentinfo set value=2 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }

        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS301\n5\n2\nCS301\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Course offering added successfully!";
        assertTrue(output.contains(expectedOutput));

        expectedOutput = "Course offering removed successfully!\n";
        assertTrue(output.contains(expectedOutput));
    }

    

    @Test public void viewGradesTest(){
        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\nCS103\n\n\n\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

    }

    @Test public void viewPreviousCoursesTest(){
        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "4\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Previous courses:";
        assertTrue(output.contains(expectedOutput));

        // Also will add the courses name

    }

    @Test public void viewCurrentCoursesTest(){
        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "5\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Courses offered by you in";
        assertTrue(output.contains(expectedOutput));

        // Also will add the courses name

    }

    @Test public void updateGradesTest(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nCS202\ny\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades updated successfully!";
        assertTrue(output.contains(expectedOutput));
    }

}
