package softwareEngineering;
import java.sql.*;
import java.io.*;

// TODO: Graduation check function
// TODO: Addition of program core and program elective courses


public class AcademicSection extends Person{
    Scanner sc;
    Connection con;

    public AcademicSection(String userName){
        this.username = userName;
        this.role = "academic";

        sc = new Scanner();
        ConnectionManager cm = new ConnectionManager();
        con = cm.getConnection();

    }

    public void manager(){
        int choice = 0;
        while(choice != 8){
            displayOptions();
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    addCourse();
                    break;
                case 2:
                    dropCourse();
                    break;
                case 3:
                    viewGrades();
                    break;
                case 4:
                    generateTranscripts();
                    break;
                case 5:
                    viewCourses();
                    break;
                case 6:
                    analyzePerformance();
                    break;
                case 7:
                    changeCurrentInfo();
                    break;
                case 8:
                    System.out.println("Thank you for using the system. Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void displayOptions(){
        String options = "\n\n1. Add a new course to the system\n";
        options += "2. Drop a course from the system\n";
        options += "3. View grades of all students\n";
        options += "4. Generate transcripts for all students\n";
        options += "5. View all courses\n";
        options += "6. Analyze student performance\n";
        options += "7. Change Current Info\n";
        options += "8. Exit\n";

        System.out.println(options);
    }

    public String getEventInfo(){
        // 1. Get event info from CurrentInfo(field, value) table
        // 2. Return the event value

        // Step 1: Get event info from CurrentInfo(field, value) table
        String query = "SELECT value FROM CurrentInfo WHERE field = 'current_event_id';";
        String event = "";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                event = rs.getString("value");
            }
            else{
                return "Error: Event not found";
            }
        }
        catch(Exception e){
            return "Error: " + e;
        }
        return event;
    }

    private void addCourse(){
        // 1. Check if course catalog modification is open
        // 2. Get course details
        // 3. Insert into table Course(id, name, l, t, p, credits, department)

        // Step 1: Check if course catalog modification is open
        String event = getEventInfo();

        if(event.equals("Error: Event not found")){
            System.out.println(event);
            return;
        }
        else if(!event.equals("1")){
            System.out.println("Error: Course Catalog Modification is not open");
            return;
        }

        // Step 2: Get course details
        System.out.println("Enter course details: ");
        System.out.println("Course ID: ");
        String id = sc.nextLine();
        System.out.println("Course name: ");
        String name = sc.nextLine();
        System.out.println("Lecture hours: ");
        int l = sc.nextInt();
        System.out.println("Tutorial hours: ");
        int t = sc.nextInt();
        System.out.println("Practical hours: ");
        int p = sc.nextInt();
        System.out.println("Credits: ");
        int credits = sc.nextInt();
        System.out.println("Department: ");
        String department = sc.nextLine();

        // Step 3: Insert into table Course(id, name, l, t, p, credits, department)
        String query = "INSERT INTO Course(id, name, l, t, p, credits, department) VALUES( '" + id + "', '" + name + "', " + l + ", " + t + ", " + p + ", " + credits + ", '" + department + "');";
        try{
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Course added successfully");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

    private void dropCourse(){
        
        // 1. Check if course catalog modification is open
        // 2. Get course id
        // 3. Display course details
        // 4. Delete from table Course(id, name, l, t, p, credits, department)

        // Step 1: Check if course catalog modification is open
        String event = getEventInfo();

        if(event.equals("Error: Event not found")){
            System.out.println(event);
            return;
        }
        else if(!event.equals("1")){
            System.out.println("Error: Course Catalog Modification is not open");
            return;
        }

        // Step 2: Get course id
        System.out.println("Enter course id: ");
        String id = sc.nextLine();

        // Step 3: Display course details
        String query = "SELECT * FROM Course WHERE id = '" + id + "';";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                System.out.println("Course details: ");
                System.out.println("Course ID: " + rs.getString("id"));
                System.out.println("Course name: " + rs.getString("name"));
                System.out.println("Lecture hours: " + rs.getInt("l"));
                System.out.println("Tutorial hours: " + rs.getInt("t"));
                System.out.println("Practical hours: " + rs.getInt("p"));
                System.out.println("Credits: " + rs.getInt("credits"));
                System.out.println("Department: " + rs.getString("department"));
            }
            else{
                System.out.println("Course not found");
                return;
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }

        // Step 4: Delete from table Course(id, name, l, t, p, credits, department)
        query = "DELETE FROM Course WHERE id = '" + id + "';";
        try{
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Course deleted successfully");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    private void viewGrades(){
        // 1. Take user input and apply filters(course_id, year, semester,entry_no) if any
        // 2. Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)

        // Step 1: Take user input and apply filters(course_id, year, semester,entry_no) if any
        String query = "SELECT * FROM CompletedCourse";
        System.out.println("Enter course id: ");
        String course_id = sc.nextLine();
        if(!course_id.equals("")){
            query += " WHERE course_id = '" + course_id + "'";
        }
        System.out.println("Enter year: ");
        int year = sc.nextInt();
        if(year != 0){
            if(course_id.equals("")){
                query += " WHERE year = " + year;
            }
            else{
                query += " AND year = " + year;
            }
        }
        System.out.println("Enter semester: ");
        int semester = sc.nextInt();
        if(semester != 0){
            if(course_id.equals("") && year == 0){
                query += " WHERE semester = " + semester;
            }
            else{
                query += " AND semester = " + semester;
            }
        }
        System.out.println("Enter entry number: ");
        String entry_no = sc.nextLine();
        if(!entry_no.equals("")){
            if(course_id.equals("") && year == 0 && semester == 0){
                query += " WHERE entry_no = '" + entry_no + "'";
            }
            else{
                query += " AND entry_no = '" + entry_no + "'";
            }
        }

        // Order by course_id, semester , year, entry_no
        query += " ORDER BY course_id, semester, year, entry_no;";

        // Step 2: Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("Grades: ");
            System.out.println("Entry number\tCourse ID\tYear\tSemester\tGrade");
            while(rs.next()){
                String entry = rs.getString("entry_no");
                String course = rs.getString("course_id");
                int yr = rs.getInt("year");
                int sem = rs.getInt("semester");
                String grade = rs.getString("grade");
                System.out.format("%s\t\t%s\t\t%d\t%d\t\t%s\n", entry, course, yr, sem, grade); 
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    
    }

    private void generateTranscripts(){
        // 1. Generate transcript for all students for all semesters, one folder per semester per student
        // 2. Store in Transcripts folder

        // Step 1: Generate transcript for all students for all semesters, one folder per semester per student from CompletedCourse(entry_no, course_id, year, semester, grade)
        String query = "SELECT * FROM CompletedCourse ORDER BY entry_no, semester, year;";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            String entry_no = "";
            int semester = 0;
            int year = 0;
            String folder = "";
            String file = "";
            while(rs.next()){
                String entry = rs.getString("entry_no");
                String course = rs.getString("course_id");
                int yr = rs.getInt("year");
                int sem = rs.getInt("semester");
                String grade = rs.getString("grade");

                int credits = 0;
                String courseName = "";
                String query2 = "SELECT * FROM Course WHERE id = '" + course + "';";
                try{
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(query2);
                    if(rs2.next()){
                        credits = rs2.getInt("credits");
                        courseName = rs2.getString("name");
                    }
                    else{
                        System.out.println("Error: Course not found");
                        return;
                    }
                }
                catch(Exception e){
                    System.out.println("Error1: " + e);
                }

                if(!entry.equals(entry_no) || sem != semester || yr != year){
                    // Create new folder
                    entry_no = entry;
                    semester = sem;
                    year = yr;
                    folder = "Transcripts/" + entry_no + "/" + year + "_" + semester + "/";

                    // Create folder
                    File f = new File(folder);
                    f.mkdirs();

                    // Create file
                    file = folder + entry_no + ".txt";
                    f = new File(file);
                    f.createNewFile();

                    // Write to file
                    FileWriter fw = new FileWriter(file);
                    fw.write("Transcript for " + entry_no + " for " + year + "_" + semester + " semester is: \n\n");
                    fw.write(String.format("%-10s\t%-50s\t%-10s\t%-10s\n", "Course ID", "Course Name", "Credits", "Grade"));
                    fw.write(String.format("%-10s\t%-50s\t%-10d\t%-10s\n", course, courseName, credits, grade));
                    fw.close();
                }
                else{
                    // Write to file
                    FileWriter fw = new FileWriter(file,true);
                    // Write in file with proper formatting and uniform spacing
                    fw.write(String.format("%-10s\t%-50s\t%-10d\t%-10s\n", course, courseName, credits, grade));
                    fw.close();
                }
    
            }
        }
        catch(Exception e){
            System.out.println("Error2: " + e);
        }
    }

    private void viewCourses(){
        // 1. Take user input and apply filters if any
        // 2. Display course details

        // Step 1: Take user input and apply filters (department, credits) if any
        String query = "SELECT * FROM Course";
        System.out.println("Enter department: (Press enter to skip)");
        String department = sc.nextLine();
        if(!department.equals("")){
            query += " WHERE department = '" + department + "'";
        }
        System.out.println("Enter credits: (Press 0 to skip)");
        int credits = sc.nextInt();
        if(credits != 0){
            if(department.equals("")){
                query += " WHERE credits = " + credits;
            }
            else{
                query += " AND credits = " + credits;
            }
        }
        
        // Step 2: Display course details
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("Course details: ");
            System.out.format("%-10s\t%-50s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n", "Course ID", "Course Name", "L", "T", "P", "Credits", "Department");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int l = rs.getInt("l");
                int t = rs.getInt("t");
                int p = rs.getInt("p");
                int cred = rs.getInt("credits");
                String dept = rs.getString("department");
                System.out.format("%-10s\t%-50s\t%-10d\t%-10d\t%-10d\t%-10d\t%-10s\n", id, name, l, t, p, cred, dept);
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

    private void analyzePerformance(){

    }

    private void changeCurrentInfo(){
        // 1. Take user input regarding which field to change
        // 2. Check if the field exists in CurrentInfo(field, value)
        // 3. Take user input for new value
        // 4. Update the database

        // Step 1: Take user input regarding which field to change
        System.out.println("Enter field to change: ");
        String field = sc.nextLine();

        // Step 2: Check if the field exists in CurrentInfo(field, value)
        String query = "SELECT * FROM CurrentInfo WHERE field = '" + field + "';";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(!rs.next()){
                System.out.println("Error: Field does not exist");
                return;
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

        // Step 3: Take user input for new value
        System.out.println("Enter new value: ");
        String value = sc.nextLine();

        // Step 4: Update the database
        query = "UPDATE CurrentInfo SET value = '" + value + "' WHERE field = '" + field + "';";
        try{
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Updated successfully");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    private void graduationCheck(){
        // 1. Take user input for entry number
        // 2. Check if the student has completed all the program cores for his/her batch
        // 3. Check if the student has completed minimum elective credits for his/her batch
    }
}
