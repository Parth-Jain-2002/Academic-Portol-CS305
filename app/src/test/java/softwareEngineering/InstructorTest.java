package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class InstructorTest {
    Connection con;
    ConnectionManager cm;
    
    public InstructorTest(){
        cm = ConnectionManager.getCM("academicsystemtest");
        con = cm.getConnection();
    }

    @Before public void setUp(){
        // Run a sql script to reset the database
        cm.runScript("../Sql_files/addDataTest.sql");
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
            cm.executeUpdate(query);
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

        String input = "1\nCS301\n5\ny\n2020CSE\n2\nCS301\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.cm = cm;
        person.con = con;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Course offering added successfully!";
        assertEquals(output, expectedOutput);
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
            
            cm.executeUpdate(query);
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
        person.cm = cm;
        person.con = con;
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades updated successfully!";
        assertTrue(output.contains(expectedOutput));
    }

}
