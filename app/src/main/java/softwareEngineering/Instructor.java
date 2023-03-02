package softwareEngineering;
import java.io.*;
import java.sql.*;

// TODO: Giving the option to add a course offering and decide the program elective or not

public class Instructor extends Person{
    Scanner sc;
    Connection con;
    ConnectionManager cm;

    public Instructor(String userName){
        this.username = userName;
        this.role = "instructor";

        sc = new Scanner();
        cm = ConnectionManager.getCM("academicsystem");
        con = cm.getConnection();
    }

    private void displayOptions(){
        String options = "\n\n1. Add a course offering \n";
        options += "2. Remove a course offering \n";
        options += "3. View grades of all students of a course\n";
        options += "4. View all previous courses offering \n";
        options += "5. View all current courses offering \n";
        options += "6. Update grades of all student \n";
        options += "7. Exit \n";
        System.out.println(options);
    }    

    private String getEventInfo() throws Exception{
        // 1. Get event info from CurrentInfo(field, value) table
        // 2. Return the event value

        // Step 1: Get event info from CurrentInfo(field, value) table
        String query = "SELECT value FROM CurrentInfo WHERE field = 'current_event_id';";
        String event = "";
        ResultSet rs = cm.executeQuery(query);
        if(rs.next()){
            event = rs.getString("value");
        }
        else{
            return "Error: Event not found";
        }
        return event;
    }

   public void manager(){
        try{
            int choice = 0;
            while(choice != 7){
                displayOptions();
                System.out.println("Enter your choice: ");
                choice = sc.nextInt();
                switch(choice){
                    case 1:
                        addCourseOffering();
                        break;
                    case 2:
                        removeCourseOffering();
                        break;
                    case 3:
                        viewGrades();
                        break;
                    case 4:
                        viewPreviousCourses();
                        break;
                    case 5:
                        viewCurrentCourses();
                        break;
                    case 6:
                        updateGrades();
                        break;
                    case 7:
                        System.out.println("Thank you for using the system. Have a nice day!\n");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }

            cm.close();
            sc.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    private String courseExist(String course_id) throws Exception{
        String query = "SELECT * FROM course WHERE id = '" + course_id + "'";
        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("Course does not exist");
            return "NOT EXIST";
        }
        else{
            return "EXIST";
        }
    }

    private void addCourseOffering() throws Exception{
        // 1. Check if the course offering is open
        // 2. Get the course id
        // 3. Check if the course exists
        // 4. Chcek if the course is already offered in current semester and year
        // 5. Set the cgpa criteria
        // 6. If not, add the course offering

        // Step 1: Check if the course offering is open
        String event = getEventInfo();
        if(event.equals("Error: Event not found")){
            return;
        }
        else if(!event.equals("2")){
            System.out.println("Course offering is not open");
            return;
        }

        // Step 2: Get the course id
        System.out.println("Enter the course id: ");
        String courseId = sc.nextLine();

        // Step 3: Check if the course exists in Course(id, name, l, t, p, credits, department) table
        if(courseExist(courseId).equals("NOT EXIST")){
            return;
        }

        // Step 4: Check if the course is already offered in CourseOffering(course_id, semester, year, section, instructor_id,cgpa) table in current semester and year
        String currentSem = "";
        String currentYear = "";

        // Get year and semester from CurrentInfo table
        ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
        // Values in currentinfo are of the form (field, value)
        while(rs.next()){
            if(rs.getString("field").equals("current_year")){
                currentYear = rs.getString("value");
            }
            else if(rs.getString("field").equals("current_semester")){
                currentSem = rs.getString("value");
            }
        }


        String query = "SELECT * FROM courseoffering WHERE course_id = '" + courseId + "' AND semester = '" + currentSem + "' AND year = '" + currentYear + "'";
        rs = cm.executeQuery(query);
        if(rs.next()){
            if(rs.getString("instructor").equals(username)){
                System.out.println("Course is already offered by you");
                return;
            }
            else{
                System.out.println("Course is already offered by another instructor");
                return;
            }
        }


        // Step 5: Set the cgpa criteria else set it to 0
        System.out.println("Enter the cgpa criteria: ");
        String cgpa = sc.nextLine();
        if(cgpa.equals("")){
            cgpa = "0";
        }

        System.out.println("Add the course as program elective for different batches? (y/n)");
        String choice = sc.nextLine();

        if(choice.equals("y")){
            System.out.println("Enter batch ids: ");
            String batchIds = sc.nextLine();
            String[] batchIdsArray = batchIds.split(" ");
            for(int i = 0; i < batchIdsArray.length; i++){
                // Check if batch exists in table Batch(id, semester, year, department)
                query = "SELECT * FROM Batch WHERE id = '" + batchIdsArray[i] + "';";
                rs = cm.executeQuery(query);
                if(!rs.next()){
                    System.out.println("Error: Batch " + batchIdsArray[i] + " does not exist");
                    return;
                }

                // Check if the course is already added as program core for the batch
                query = "SELECT * FROM ProgramCore WHERE course_id = '" + courseId + "' AND batch_id = '" + batchIdsArray[i] + "';";
                rs = cm.executeQuery(query);
                if(rs.next()){
                    System.out.println("Error: Course " + courseId + " is already added as program core for batch " + batchIdsArray[i]);
                    return;
                }

                query = "INSERT INTO ProgramElective(course_id, batch_id, semester, year) VALUES('" + courseId + "', '" + batchIdsArray[i] + "', '" + currentSem + "', '" + currentYear + "')";
                cm.executeUpdate(query);
            }
        }

        // Step 6: If not, add the course offering
        // Insert into CourseOffering(course_id, year, semester, instructor, cgpa) table
        query = "INSERT INTO courseoffering VALUES('" + courseId + "', '" + currentYear + "', '" + currentSem + "', '" + username + "', '" + cgpa + "')";

        cm.executeUpdate(query);
        System.out.println("Course offering added successfully!\n");
    }

    private void removeCourseOffering() throws Exception{
        // 1. Check if the course offering is open
        // 2. Get the course id
        // 3. Check if the course exists
        // 4. Check if the course is offered by the instructor
        // 5. If yes, remove the course offering

        // Step 1: Check if the course offering is open
        String event = getEventInfo();
        if(event.equals("Error: Event not found")){
            return;
        }
        else if(!event.equals("2")){
            System.out.println("Course offering is not open");
            return;
        }

        // Step 2: Get the course id
        System.out.println("Enter the course id: ");
        String courseId = sc.nextLine();

        // Step 3: Check if the course exists in Course(id, name, l, t, p, credits, department) table
        if(courseExist(courseId).equals("NOT EXIST")){
            return;
        }

        // Step 4: Check if the course is offered by the instructor in CourseOffering(course_id, year, semester, instructor,cpga) table in current semester and year
        String currentSem = "";
        String currentYear = "";

        // Get year and semester from CurrentInfo table
            ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
                else if(rs.getString("field").equals("current_semester")){
                    currentSem = rs.getString("value");
                }
            }

        String query = "SELECT * FROM courseoffering WHERE course_id = '" + courseId + "' AND semester = '" + currentSem + "' AND year = '" + currentYear + "' AND instructor = '" + username + "'";
        rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("Course is not offered by you");
            return;
        }

        query = "DELETE FROM ProgramElective WHERE course_id = '" + courseId + "' AND semester = '" + currentSem + "' AND year = '" + currentYear + "'";
        cm.executeUpdate(query);

        // Step 5: If yes, remove the course offering
        query = "DELETE FROM courseoffering WHERE course_id = '" + courseId + "' AND semester = '" + currentSem + "' AND year = '" + currentYear + "'";
        cm.executeUpdate(query);
        System.out.println("Course offering removed successfully!\n");

    }

    private void viewGrades() throws Exception{
        // 1. Get the course id
        // 2. Check if the course exists
        // 3. Check if the course is offered by the instructor
        // 4. Take user input and apply filters(course_id, year, semester,entry_no) if any
        // 5. Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)

        // Step 1: Get the course id
        System.out.println("Enter the course id: ");
        String courseId = sc.nextLine();

        // Step 2: Check if the course exists in Course(id, name, l, t, p, credits, department) table
        if(courseExist(courseId).equals("NOT EXIST")){
            return;
        }

        // Step 3: Check if the course is offered by the instructor in CourseOffering(course_id, year, semester, instructor,cgpa) table
        String query = "SELECT * FROM courseoffering WHERE course_id = '" + courseId + "' AND instructor = '" + username + "'";
            ResultSet rs = cm.executeQuery(query);
            if(!rs.next()){
                System.out.println("Course is not offered by you");
                return;
            }
        
        // Step 4: Take user input and apply filters(year, semester, entry_no) if any
        query = "SELECT * FROM completedcourse WHERE course_id = '" + courseId + "'";
        System.out.println("Enter the year: (Filter, leave blank for all years)");
        String year = sc.nextLine();
        if(!year.equals("")){
            query += " AND year = " + year;
        }
        System.out.println("Enter the semester: (Filter, leave blank for all semesters)");
        String semester = sc.nextLine();
        if(!semester.equals("")){
            query += " AND semester = " + semester;
        }
        System.out.println("Enter the entry number: (Filter, leave blank for all students)");
        String entryNo = sc.nextLine();
        if(!entryNo.equals("")){
            query += " AND entry_no = '" + entryNo + "'";
        }

        // Order by course_id, semester , year, entry_no
        query += " ORDER BY course_id, semester, year, entry_no;";

        // Step 5: Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)
            rs = cm.executeQuery(query);
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

    private void viewPreviousCourses() throws Exception{
        // 1. Get the current semester and year from CurrentInfo table
        // 2. Get the previous offering by the instructor except the current semester and year from CourseOffering(course_id, year, semester, instructor) table

        // Step 1: Get the current semester and year from CurrentInfo table
        String currentSem = "";
        String currentYear = "";

            ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
                else if(rs.getString("field").equals("current_semester")){
                    currentSem = rs.getString("value");
                }
            }


        // Step 2: Get the previous offering by the instructor except the current semester and year from CourseOffering(course_id, year, semester, instructor,cgpa) table
        String query = "SELECT * FROM courseoffering WHERE instructor = '" + username + "' AND ( year != '" + currentYear + "' OR semester != '" + currentSem + "')";

        rs = cm.executeQuery(query);
        System.out.println("Previous courses: ");
        System.out.println("Course ID\tCourse Name\t\t\t\t\tSemester\tYear");
        while(rs.next()){
            String courseId = rs.getString("course_id");
            String semester = rs.getString("semester");
            String year = rs.getString("year");
            String courseName = "";
            // Get the course name from Course table
            query = "SELECT * FROM course WHERE id = '" + courseId + "'";
                ResultSet rs2 = cm.executeQuery(query);
                if(rs2.next()){
                    courseName = rs2.getString("name");
                }

            System.out.format("%-15s\t%-45s\t%-15s\t%-15s\n", courseId, courseName, semester, year);
        } 
    }

    private void viewCurrentCourses() throws Exception{
        // 1. Get the current semester and year from CurrentInfo table
        // 2. Get the current offering by the instructor from CourseOffering(course_id, year, semester, instructor, cgpa) table

        // Step 1: Get the current semester and year from CurrentInfo table
        String currentSem = "";
        String currentYear = "";

            ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
                else if(rs.getString("field").equals("current_semester")){
                    currentSem = rs.getString("value");
                }
            }


        // Step 2: Get the current offering by the instructor from CourseOffering(course_id, year, semester, instructor, cgpa) table
        String query = "SELECT * FROM courseoffering WHERE instructor = '" + username + "' AND year = '" + currentYear + "' AND semester = '" + currentSem + "'";
        rs = cm.executeQuery(query);
        System.out.println("Courses offered by you in semester " + currentSem + " and year " + currentYear + ": ");
        System.out.println("Course ID\tCourse Name\t\t\t\t\tSemester\tYear");
        while(rs.next()){
            String courseId = rs.getString("course_id");
            String semester = rs.getString("semester");
            String year = rs.getString("year");
            String courseName = "";
            // Get the course name from Course table
            query = "SELECT * FROM course WHERE id = '" + courseId + "'";
            ResultSet rs2 = cm.executeQuery(query);
            if(rs2.next()){
                courseName = rs2.getString("name");
            }

            System.out.format("%-15s\t%-45s\t%-15s\t%-15s\n", courseId, courseName, semester, year);
        }
    }

    private void updateGrades() throws Exception{
        // 1. Check if the grade submission is open
        // 2. Get the course id
        // 3. Check if the course exists
        // 4. Check if the course is offered by the instructor
        // 5. If yes, get all the students enrolled in the course from EnrolledCourse(entry_no, course_id, year, semester)
        // 6. Save this file in csv format

        // Step 1: Check if the grade submission is open
            String event = getEventInfo();
            if(event.equals("Error: Event not found")){
                return;
            }
            else if(!event.equals("5")){
                System.out.println("Grade Submission is not open");
                return;
            }

            // Step 1: Get the course id
            System.out.println("Enter the course id: ");
            String courseId = sc.nextLine();

            // Step 2: Check if the course exists in Course(id, name, l, t, p, credits, department) table
            if(courseExist(courseId).equals("NOT EXIST")){
                return;
            }

            // Step 3: Check if the course is offered by the instructor in CourseOffering(course_id, year, semester, instructor,cgpa) table
            String query = "SELECT * FROM courseoffering WHERE course_id = '" + courseId + "' AND instructor = '" + username + "'";
            ResultSet rs = cm.executeQuery(query);
            if(!rs.next()){
                System.out.println("Course is not offered by you");
                return;
            }

            

            // Step 4: If yes, get all the students enrolled in the course from EnrolledCourse(entry_no, course_id, year, semester) using copy command
            query = "SELECT * FROM enrolledcourse WHERE course_id = '" + courseId + "'";
            rs = cm.executeQuery(query);
            if(!rs.next()){
                System.out.println("No students enrolled in the course");
                return;
            }
            else{
                // Save the result in a csv file in same directory using copy command
                String filePath = "C:\\Users\\Public\\Documents\\update_grades.csv";
                query = "COPY (" + query + ") TO '" + filePath + "' DELIMITER ',' CSV HEADER";
                // System.out.println(query);
                cm.executeUpdate(query);
                System.out.println("File saved successfully in " + filePath);
            }
            

            // Step 5: Ask the user to update the grades in the csv file and take confirmation
            System.out.println("Have you updated the grades in the csv file? (y/n)");
            String choice = sc.nextLine();
            if(choice.equals("y")){
                // 1. Read the csv file
                // 2. Check whether only the grades are updated and not the other fields
                // 3. Update the grades in the enrolledcourse table

                // Step 1: Read the csv file
                String line = "";
                String cvsSplitBy = ",";
                String filePath = "C:\\Users\\Public\\Documents\\update_grades.csv";
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    // Skip the first line
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        // use comma as separator
                        String[] data = line.split(cvsSplitBy);
                        System.out.println(data.length);
                        if(!(data.length==5 || data.length==4)){
                            System.out.println("Grades not updated. Data in csv file is not correct");
                            continue;
                        }

                        String entryNo = data[0];
                        String course_id = data[1];
                        String year = data[2];
                        String semester = data[3];
                        String grade = "";

                        if(data.length==5){
                            grade = data[4];
                        }

                        grade = grade.toUpperCase();
                        if(grade.length()>2) grade = grade.substring(0,2);

                        // If grade is empty, skip the entry
                        if(grade.equals("")){
                            System.out.println("Grades not provided for entry no: " + entryNo);
                            continue;
                        }

                        // Grade can be A, A-, B+, B, B-, C+, C, C-, D+, D, D-, E, F
                        if(!grade.equals("A") && !grade.equals("A-") && !grade.equals("B+") && !grade.equals("B") && !grade.equals("B-") && !grade.equals("C+") && !grade.equals("C") && !grade.equals("C-") && !grade.equals("D+") && !grade.equals("D") && !grade.equals("D-") && !grade.equals("E") && !grade.equals("F")){
                            System.out.println("Grades not updated for entry no: " + entryNo + " Grade is not correct");
                            continue;
                        }
                        
                        // Check if the courseId and course_id are same
                        if(!courseId.equals(course_id)){
                            System.out.println("Grades not updated for entry no: " + entryNo + " Course id is not correct");
                            continue;
                        }

                        // Step 2: Check whether only the grades are updated and not the other fields, check whether there is a entry in enrolledcourse table for these fields
                        query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + entryNo + "' AND course_id = '" + course_id + "' AND year = '" + year + "' AND semester = '" + semester + "'";
                        rs = cm.executeQuery(query);
                        if(!rs.next()){
                            System.out.println("Grades not updated for entry no: " + entryNo + " Entry not found in enrolledcourse table");
                            continue;
                        }
                        
                        // Step 3: Update the grades in the enrolledcourse table
                        query = "UPDATE enrolledcourse SET grade = '" + grade + "' WHERE entry_no = '" + entryNo + "' AND course_id = '" + course_id + "' AND year = '" + year + "' AND semester = '" + semester + "'";
                        cm.executeUpdate(query);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Grades updated successfully!");
            }
            else{
                System.out.println("Grades not updated");
            }
    }

}
