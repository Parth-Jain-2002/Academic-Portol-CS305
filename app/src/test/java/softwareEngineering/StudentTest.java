package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;
import java.util.*;


public class StudentTest {

    Connection con;
    
    public StudentTest(){
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
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        assertEquals(person.getEventInfo(),"1");
    }

    @Test public void getBatchInfoTest() {
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        assertEquals(person.getBatchInfo(),"2020CSE");
    }

    @Test public void getSemesterInfoTest() {
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        assertEquals(person.getSemesterInfo(),"5");
    }

    @Test public void computeCGPATest() {
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        // computeCGPA() returns a double value
        assertEquals(8.333333333333334, person.computeCGPA(false), 0.2);
        assertEquals(8.333333333333334, person.computeCGPA(true), 0.2);
    }

    @Test public void viewCompletedCoursesTest(){
        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        assertEquals(person.viewCompletedCourses(),"Completed courses printed");
    }

    @Test public void courseRegisterationTest() throws Exception{
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Student person = null;
        try{
            person = new Student("admin");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "CS202\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        assertEquals(person.deregisterForCourse(),"You have been deregistered from the course");

        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        assertEquals(person.registerForCourse(),"You have been registered for the course");
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
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();
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
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

    }
}
