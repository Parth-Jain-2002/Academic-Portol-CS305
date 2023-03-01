package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class AcademicTest {
    Connection con;
    
    public AcademicTest(){
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
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "8\n";
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

    @Test public void addDropCourseTest(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }


        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS601\nAdvanced Research\n4\n1\n0\n4\nCSE\ny\n2020CSE\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Course added successfully";
        assertTrue(output.contains(expectedOutput));

        input = "2\nCS601\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        output = outputStream.toString();
        expectedOutput = "Course deleted successfully";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\n\n0\n0\n\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        person.manager();

        input = "3\nCS103\n0\n0\n\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "3\nCS103\n2022\n0\n\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "3\n\n2022\n0\n\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "3\n\n0\n1\n\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();
    }

    @Test public void generateTranscriptsTest(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "4\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Transcripts generated successfully";

        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCoursesTest(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "5\n\n0\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "5\nCSE\n0\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        person.manager();

        input = "5\n\n4\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);

        person.manager();
    }

    @Test public void changeCurrentInfoTest(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "7\ncurrent_year\n2024\n7\ncurrent_year\n2023\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Updated successfully";
        assertTrue(output.contains(expectedOutput));
    }
}
