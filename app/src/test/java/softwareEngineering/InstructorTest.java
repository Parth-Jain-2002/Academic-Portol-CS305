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


    @Test public void getEventInfoTest() {
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Student person = null;
        try{
            person = new Student("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        assertEquals(person.getEventInfo(),"1");
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

        // Get last line of output
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        output = lines[lines.length - 2];

        String expectedOutput = "Thank you for using the system. Have a nice day!";
        assertEquals(expectedOutput, output);
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
        
        assertEquals(person.viewCurrentCourses(), "Success");
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
        
        assertEquals(person.viewPreviousCourses(), "Success");
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

        //assertEquals(outputStream,"Success");
    }

    @Test public void addDropCourseOfferingTest(){
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

        String input = "1\nCS204\n\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "2\nCS204\n7\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();
    }
}
