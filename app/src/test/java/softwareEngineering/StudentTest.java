package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;
import java.util.*;


public class StudentTest {

    Connection con;
    ConnectionManager cm;
    
    public StudentTest(){
        cm = ConnectionManager.getCM("academicsystemtest");
        con = cm.getConnection();
    }

    @Before public void setUp(){
        cm.runScript("../Sql_files/addDataTest.sql");
    }

    @Test public void courseRegisterationTest(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS202\n2\nCS202\n7\n";
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
        String expectedOutput = "You have been registered for the course";
        assertTrue(output.contains(expectedOutput));

        output = outputStream.toString();
        expectedOutput = "You have been deregistered from the course";     
        assertTrue(output.contains(expectedOutput));
        
    }

    @Test public void getCourseInfoTest(){
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "3\nCS201\n7\n";
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
        String expectedOutput = "Course name: Computer Networks";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewEnrolledCoursesTest(){
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "4\n7\n";
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
        String expectedOutput = "You are registered for the following courses in the current semester:";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCompletedCoursesTest(){
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "5\n7\n";
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
        String expectedOutput = "You have completed the following courses:";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest(){
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "6\n7\n";
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
        String expectedOutput = "Your CGPA is: 8.25";
        assertTrue(output.contains(expectedOutput));
    }
        
    
}
