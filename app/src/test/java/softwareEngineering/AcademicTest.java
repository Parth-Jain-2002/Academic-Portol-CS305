package softwareEngineering;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;

public class AcademicTest {
    Connection con;
    ConnectionManager cm;
    
    public AcademicTest(){
        cm = ConnectionManager.getCM("academicsystemtest");
        con = cm.getConnection();
    }

    @Before public void setUp(){
        cm.runScript("../Sql_files/addDataTest.sql");
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
        String expectedOutput = "Thank you for using the system. Have a nice day!";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addDropCourseTest1(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            cm.executeUpdate(query);
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

        String input = "2\nCS601\nAdvanced Research\n4\n1\n0\n3\n4\nCS\ny\n2020CSB 2020CSE\n8\n";
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
        String expectedOutput = "Course added successfully";
        assertTrue(output.contains(expectedOutput));

        expectedOutput = "Error: Batch 2020CSE does not exist";
        assertTrue(output.contains(expectedOutput));

        query = "DELETE FROM course WHERE course_id='CS601'";
        try{
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

    @Test public void addDropCourseTest2(){
        String query = "UPDATE currentinfo set value=2 where field='current_event_id'";
        try{
            cm.executeUpdate(query);
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
        String expectedOutput = "Error: Course Catalog Modification is not open";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void addDropCourseTest3(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            cm.executeUpdate(query);
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

        String input = "2\nCS601\nAdvanced Research\n4\n1\n0\n3\n4\nCS\nn\n8\n";
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
        String expectedOutput = "Course added successfully";
        assertTrue(output.contains(expectedOutput));

        query = "DELETE FROM course WHERE course_id='CS601'";
        try{
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

    // Test all possible combinations of input for viewGradesTest in different functions
    @Test public void viewGradesTest1(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "3\nCS101\n2022\n1\n\n8\n";
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
        String expectedOutput = "Grades: ";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest2(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "3\n\n2022\n0\n\n8\n";
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
        String expectedOutput = "Grades: ";
        //assertEquals(output, expectedOutput);
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest3(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "3\n\n0\n1\nstudent1\n8\n";
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
        String expectedOutput = "Grades: ";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest4(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "3\n\n0\n0\nstudent1\n8\n";
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
        String expectedOutput = "Grades: ";
        assertTrue(output.contains(expectedOutput));
    }
    

    @Test public void generateTranscriptsTest1(){
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
        person.cm = cm;
        person.con = con;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Transcripts generated successfully";

        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCoursesTest1(){
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
        person.cm = cm;
        person.con = con;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Course details: ";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCoursesTest2(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "5\nCS\n0\n8\n";
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
        String expectedOutput = "Course details: ";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCoursesTest3(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "5\n\n4\n8\n";
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
        String expectedOutput = "Course details: ";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewCoursesTest4(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("admin2");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "5\nCS\n4\n8\n";
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
        String expectedOutput = "Course details: ";
        assertTrue(output.contains(expectedOutput));
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
        
        String input = "1\ncurrent_year\n2024\n1\ncurrent_year\n2023\n1\ncurrent_time\n8\n";
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
        String expectedOutput = "Updated successfully";
        assertTrue(output.contains(expectedOutput));

        output = outputStream.toString();
        expectedOutput = "Error: Field does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void graduationCheckTest1(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nstudent4\n8\n";
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
        String expectedOutput = "Student is eligible for graduation";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void graduationCheckTest2(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "6\nstudent1\n8\n";
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
        String expectedOutput = "Student has not completed all the program cores for his/her batch, hence cannot graduate";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void graduationCheckTest3(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "6\nstudent3\n8\n";
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
        String expectedOutput = "Student is not eligible for graduation";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void graduationCheckTest4(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "6\nstudent11\n8\n";
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
        String expectedOutput = "Error: Entry number does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateProfileTest1(){
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        if(person == null) return;

        String input = "7\nemail\nstudentAlpha\n8\nemail\nstudent1\n8\n";
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
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        if(person == null) return;

        String input = "7\nphone\n8888888888\n8\nphone\n8888888888\n8\n";
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
        AcademicSection person = null;
        try{
            person = new AcademicSection("academic1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
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


