package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;


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

    @Test public void managerTest(){
        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "9\n8\n";
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
        String expectedOutput = "Invalid choice";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest1(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS101\n2\nCS101\n8\n";
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

    @Test public void addCourseTest2(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS201\n8\n";
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
        String expectedOutput = "You have not completed the prerequisites for this course";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest3(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS601\n8\n";
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
        String expectedOutput = "Course does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest4(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS305\n8\n";
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
        String expectedOutput = "Course is not offered in the current semester";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest5(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student3");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "2\nCS201\n1\nCS201\n8\n";
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

    @Test public void addCourseTest6(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student3");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\n8\n";
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
        String expectedOutput = "Course registeration/deregisteration is not open";
        assertTrue(output.contains(expectedOutput));

    }

    @Test public void addCourseTest7(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS101\n8\n";
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
        String expectedOutput = "You have already done this course";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest8(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nMA101\n8\n";
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
        String expectedOutput = "You are already registered for this course";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest9(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student3");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nCS202\n8\n";
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
        String expectedOutput = "You cannot register for this course as it will exceed the maximum number of credits";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addCourseTest10(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "1\nHS102\n8\n";
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
        String expectedOutput = "You do not have the required cgpa for this course";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void deregisterCourseTest1(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "2\n8\n";
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
        String expectedOutput = "Course registeration/deregisteration is not open";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void deregisterCourseTest2(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "2\nCS201\n8\n";
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
        String expectedOutput = "You are not registered for this course";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void deregisterCourseTest3(){
        String query = "UPDATE currentinfo set value=3 where field='current_event_id'";
        cm.executeUpdate(query);

        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "2\nCS601\n8\n";
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
        String expectedOutput = "Course does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void getCourseInfoTest1(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "3\nCS201\n8\n";
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
        String expectedOutput = "Course name: Data Structures";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void getCourseInfoTest2(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "3\nCS305\n8\n";
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
        String expectedOutput = "Course is not offered in the current semester";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void getCourseInfoTest3(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "3\nCS601\n8\n";
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
        String expectedOutput = "Course does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewEnrolledCoursesTest1(){
        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "4\n8\n";
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

    @Test public void viewEnrolledCoursesTest2(){
        Student person = null;
        try{
            person = new Student("student3");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "4\n8\n";
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
        String expectedOutput = "You are not registered for any courses in the current semester";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCompletedCoursesTest1(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "5\n8\n";
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

    @Test public void viewCompletedCoursesTest2(){
        Student person = null;
        try{
            person = new Student("student5");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "5\n8\n";
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
        String expectedOutput = "You have not completed any courses";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest(){
        Student person = null;
        try{
            person = new Student("student3");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;
        
        String input = "6\n8\n";
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
        String expectedOutput = "Your CGPA is: 6.275";
        assertTrue(output.contains(expectedOutput));
    }
        
    @Test public void updateProfileTest1(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "7\nemail\nstudentAlpha\n7\nemail\nstudent1\n8\n";
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
        String expectedOutput = "Email updated successfully";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateProfileTest2(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "7\nphone\n8888888888\n7\nphone\n8888888888\n8\n";
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
        String expectedOutput = "Phone number updated successfully";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateProfileTest3(){
        Student person = null;
        try{
            person = new Student("student1");
        }
        catch(Exception e){
            System.out.println("Exception");
        }

        if(person == null) return;

        String input = "7\njoining\n8\n";
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
        String expectedOutput = "Invalid choice";
        assertTrue(output.contains(expectedOutput));
    }

}
