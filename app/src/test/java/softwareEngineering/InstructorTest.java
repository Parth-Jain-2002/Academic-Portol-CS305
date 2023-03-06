package softwareEngineering;
import com.opencsv.CSVWriter;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.*;

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
            person = new Instructor("faculty1");
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

    @Test
    public void courseOfferingTest1(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS305\n5\nn\n2\nCS305\n8\n";
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

        assertTrue(output.contains(expectedOutput));

        expectedOutput = "Course offering removed successfully!\n";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest2(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS305\n5\ny\n2020CEB\nGE\n2\nCS305\n8\n";
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
        assertTrue(output.contains(expectedOutput));

        expectedOutput = "Course offering removed successfully!\n";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest3(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS305\n5\ny\n2020CSB\nGE\n8\n";
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
        String expectedOutput = "Error: Course CS305 is already added as program core";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest4(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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
        String expectedOutput = "Course offering is not open";
        assertTrue(output.contains(expectedOutput));
    }

    @Test
    public void courseOfferingTest5(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS305\n\nn\n2\nCS305\n8\n";
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

        assertTrue(output.contains(expectedOutput));

        expectedOutput = "Course offering removed successfully!\n";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest6(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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

    @Test public void courseOfferingTest7(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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
        String expectedOutput = "Course is already offered by you";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest8(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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
        String expectedOutput = "Course is already offered by another instructor";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest9(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "1\nCS305\n5\ny\n2020CSE\nGE\n8\n";
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
        String expectedOutput = "Error: Batch 2020CSE does not exist";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest10(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
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
        String expectedOutput = "Course offering is not open";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void courseOfferingTest11(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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

    @Test public void courseOfferingTest12(){
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
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

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
        String expectedOutput = "Course is not offered by you";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest1(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\nMA101\n\n\n\n8\n";
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
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
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
        String expectedOutput = "Course is not offered by you";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void viewGradesTest3(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
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

    @Test public void viewGradesTest4(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\nMA101\n2020\n\n\n8\n";
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

    @Test public void viewGradesTest5(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\nMA101\n\n1\n\n8\n";
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

    @Test public void viewGradesTest6(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "3\nMA101\n\n\nstudent1\n8\n";
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
    

    @Test public void viewPreviousCoursesTest(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
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
        String expectedOutput = "Previous courses:";
        assertTrue(output.contains(expectedOutput));

        // Also will add the courses name

    }

    @Test public void viewCurrentCoursesTest(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
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
        String expectedOutput = "Courses offered by you in";
        assertTrue(output.contains(expectedOutput));

        // Also will add the courses name

    }

    @Test public void updateGradesTest1(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nMA101\ny\n8\n";
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

    @Test public void updateGradesTest2(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nCS101\ny\n8\n";
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
        String expectedOutput = "No students enrolled in the course";
        assertTrue(output.contains(expectedOutput));
    }
    
    @Test public void updateGradesTest3(){
        String query = "UPDATE currentinfo set value=1 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
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
        String expectedOutput = "Grade Submission is not open";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest4(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nCS601\n8\n";
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

    @Test public void updateGradesTest5(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        
        String input = "6\nCS304\n8\n";
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
        String expectedOutput = "Course is not offered by you";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest6(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{
            
            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        String filePath = "C:\\Users\\Public\\Documents\\update_grades_fake.csv";
        File file = new File(filePath);
        try{
            file.createNewFile();
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            String[] data1 = {"entry_no","course_id","year","semester","grade"};
            String[] data2 = {"student6","MA101","2023","1","F-"};
            writer.writeNext(data1);
            writer.writeNext(data2);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        String input = "6\nMA101\ny\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.cm = cm;
        person.con = con;
        person.filePath = filePath;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades not updated for entry no: student6. Grade is not correct";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest7(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{

            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        String filePath = "C:\\Users\\Public\\Documents\\update_grades_fake.csv";
        File file = new File(filePath);
        try{
            file.createNewFile();
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            String[] data1 = {"entry_no","course_id","year","semester","grade"};
            String[] data2 = {"student7","MA101","2023","1","E"};
            writer.writeNext(data1);
            writer.writeNext(data2);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        String input = "6\nMA101\ny\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.cm = cm;
        person.con = con;
        person.filePath = filePath;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades not updated for entry no: student7. Entry not found in enrolledcourse table";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest8(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{

            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        String filePath = "C:\\Users\\Public\\Documents\\update_grades_fake.csv";
        File file = new File(filePath);
        try{
            file.createNewFile();
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            String[] data1 = {"entry_no","course_id","year","semester","grade"};
            String[] data2 = {"student6","MA103","2023","1","E"};
            writer.writeNext(data1);
            writer.writeNext(data2);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        String input = "6\nMA101\ny\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.cm = cm;
        person.con = con;
        person.filePath = filePath;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades not updated for entry no: student6. Course id is not correct";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest9(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{

            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }
        String filePath = "C:\\Users\\Public\\Documents\\update_grades_fake.csv";
        File file = new File(filePath);
        try{
            file.createNewFile();
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            String[] data1 = {"entry_no","course_id","year","semester","grade"};
            String[] data2 = {"student6","MA101","2023"};
            writer.writeNext(data1);
            writer.writeNext(data2);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        String input = "6\nMA101\ny\n8\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        person.sc = new Scanner(System.in);
        person.cm = cm;
        person.con = con;
        person.filePath = filePath;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        person.manager();

        String output = outputStream.toString();
        String expectedOutput = "Grades not updated. Data in csv file is not correct";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateGradesTest10(){
        String query = "UPDATE currentinfo set value=5 where field='current_event_id'";
        try{

            cm.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        Instructor person = null;
        try{
            person = new Instructor("faculty1");
        }
        catch(Exception e){
            System.out.println("Exception");
            return;
        }

        String input = "6\nMA101\nn\n8\n";
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
        String expectedOutput = "Grades not updated";
        assertTrue(output.contains(expectedOutput));
    }

    @Test public void updateProfileTest1(){
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
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
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
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
        Instructor person = null;
        try{
            person = new Instructor("faculty1");
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


