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

    @Test public void getEventInfoTest() {
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        AcademicSection person = null;
        try{
            person = new AcademicSection("admin1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        assertEquals(person.getEventInfo(),"1");
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

        // Get last line of output
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        output = lines[lines.length - 1];
        output = output.trim();

        String expectedOutput = "Thank you for using the system. Have a nice day!";
        assertEquals(expectedOutput, output);
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

        String input = "1\nCS601\nAdvanced Research\n4\n1\n0\n4\nCSE\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();

        input = "2\nCS601\n8\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.manager();
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

        person.manager();
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
}
