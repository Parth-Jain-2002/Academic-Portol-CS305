package softwareEngineering;
import java.sql.*;
import java.io.*;
import java.util.Map;

public class AcademicSection extends Person{
    Scanner sc;
    Connection con;
    ConnectionManager cm;

    public AcademicSection(String userName){
        this.username = userName;
        this.role = "academic";

        sc = new Scanner();
        cm = ConnectionManager.getCM("academicsystem");
        con = cm.getConnection();

    }

    // This method is the main entry point for the student and redirects to the appropriate method based on the choice
    public void manager(){
        try{
            int choice = 0;
            while(choice != 8){
                displayOptions();
                System.out.println("Enter your choice: ");
                choice = sc.nextInt();
                // This gives the user the option to choose from the menu and then perform the corresponding action
                switch(choice){
                    case 1:
                        changeCurrentInfo();
                        break;
                    case 2:
                        addCourse();
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
                        graduationCheck();
                        break;
                    case 7:
                        updateProfile();
                        break;
                    case 8:
                        System.out.println("Thank you for using the system. Have a nice day!");
                        break;           
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    // This method displays the menu for the student
    private void displayOptions(){
        String options = "\n============================================\n";
        options += "+++++++++++++++++++ MENU +++++++++++++++++++\n\n";
        options += "1. Change Current Info\n";
        options += "2. Add a new course to the system\n";
        options += "3. View grades of all students\n";
        options += "4. Generate transcripts for all students\n";
        options += "5. View all courses\n";
        options += "6. Check graduation eligibility\n";
        options += "7. Update Profile\n";
        options += "8. Exit\n";
        options += "\n============================================\n";

        System.out.println(options);
    }

    private String getEventInfo() throws Exception{
        // 1. Get event info from CurrentInfo(field, value) table
        // 2. Return the event value

        // Step 1: Get event info from CurrentInfo(field, value) table
        String query = "SELECT value FROM CurrentInfo WHERE field = 'current_event_id';";
        String event = "";
            
        ResultSet rs = cm.executeQuery(query);
        rs.next();
        event = rs.getString("value");
        return event;
    }

    private void addCourse() throws Exception{
        // 1. Check if course catalog modification is open
        // 2. Get course details
        // 3. Insert into table Course(id, name, l, t, p, credits, department)

        // Step 1: Check if course catalog modification is open
        String event = getEventInfo();

        if(!event.equals("1")){
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
        System.out.println("Total preparation hours: ");
        int prep = sc.nextInt();
        System.out.println("Credits: ");
        int credits = sc.nextInt();
        System.out.println("Department: ");
        String department = sc.nextLine();

        System.out.println("Add the course as program core for different batches? (y/n)");
        String choice = sc.nextLine();

        if(choice.equals("y")){
            System.out.println("Enter batch ids: ");
            String batchIds = sc.nextLine();
            String[] batchIdsArray = batchIds.split(" ");

            for(int i = 0; i < batchIdsArray.length; i++){
                // Check if batch exists in table Batch(id, semester, year, department)
                String query = "SELECT * FROM Batch WHERE id = '" + batchIdsArray[i] + "';";
                ResultSet rs = cm.executeQuery(query);
                if(!rs.next()){
                    System.out.println("Error: Batch " + batchIdsArray[i] + " does not exist");
                    continue;
                }

                query = "INSERT INTO ProgramCore(course_id, batch_id) VALUES('" + id + "', '" + batchIdsArray[i] + "');";
                cm.executeUpdate(query);
            }
        }

        // Step 3: Insert into table Course(id, name, l, t, p, credits, department)
        String query = "INSERT INTO Course(id, name, l, t, p, s, credits, department) VALUES( '" + id + "', '" + name + "', " + l + ", " + t + ", " + p + ", " + prep + ", " + credits + ", '" + department + "');";
        cm.executeUpdate(query);
        System.out.println("Course added successfully");
    }

    private void viewGrades() throws Exception{
        // 1. Take user input and apply filters(course_id, year, semester,entry_no) if any
        // 2. Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)

        // Step 1: Take user input and apply filters(course_id, year, semester,entry_no) if any
        String query = "SELECT * FROM CompletedCourse";
        System.out.println("Enter course id: (leave blank to view all)");
        String course_id = sc.nextLine();
        if(!course_id.equals("")){
            query += " WHERE course_id = '" + course_id + "'";
        }
        System.out.println("Enter year: (press 0 to view all)");
        int year = sc.nextInt();
        if(year != 0){
            if(course_id.equals("")){
                query += " WHERE year = " + year;
            }
            else{
                query += " AND year = " + year;
            }
        }
        System.out.println("Enter semester: (press 0 to view all)");
        int semester = sc.nextInt();
        if(semester != 0){
            if(course_id.equals("") && year == 0){
                query += " WHERE semester = " + semester;
            }
            else{
                query += " AND semester = " + semester;
            }
        }
        System.out.println("Enter entry number: (leave blank to view all)");
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
        query += " ORDER BY entry_no, course_id, semester;";

        // Step 2: Display grades from CompletedCourse(entry_no, course_id, year, semester, grade)    
        ResultSet rs = cm.executeQuery(query);
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

    private void generateTranscripts() throws Exception{
        // 1. Generate transcript for all students for all semesters, one folder per semester per student
        // 2. Store in Transcripts folder

        // Step 1: Generate transcript for all students for all semesters, one folder per semester per student from CompletedCourse(entry_no, course_id, year, semester, grade)
        String query = "SELECT * FROM CompletedCourse ORDER BY entry_no, semester, year;";
        ResultSet rs = cm.executeQuery(query);
        String entry_no = "";
        int semester = 0;
        int year = 0;
        int totalCredits = 0;
        int totalPoints = 0;
        int semesterCredits = 0;
        int semesterPoints = 0;
        double semesterGPA = 0;
        double totalGPA = 0;

        String folder = "";
        String file = "";
        String entry = "";
        while(rs.next()){
            entry = rs.getString("entry_no");
            String course = rs.getString("course_id");
            int yr = rs.getInt("year");
            int sem = rs.getInt("semester");
            String grade = rs.getString("grade");
            Map <String, Integer> gradeMap = Map.of(
                    "A", 10, "A-", 9, "B-", 8, "B", 7, "C", 6, "C-", 5, "D", 4, "D-", 3, "E", 2, "F", 0
            );

            int credits = 0;
            String courseName = "";
            String query2 = "SELECT * FROM Course WHERE id = '" + course + "';";

            ResultSet rs2 = cm.executeQuery(query2);
            rs2.next();
            credits = rs2.getInt("credits");
            courseName = rs2.getString("name");


            ResultSet rs3 = cm.executeQuery("SELECT * FROM student WHERE entry_no = '" + entry + "';");
            rs3.next();
            String name = rs3.getString("name");
            String batch = rs3.getString("batch");

            if(!entry.equals(entry_no) || sem != semester || yr != year){
                if(!file.equals("")) {
                    FileWriter fw = new FileWriter(file, true);
                    // Write in file with proper formatting and uniform spacing
                    if (semesterCredits != 0) {
                        semesterGPA = (double) semesterPoints / (double) semesterCredits;
                    }
                    if (totalCredits != 0) {
                        totalGPA = (double) totalPoints / (double) totalCredits;
                    }

                    fw.write("-------------------------------------------------------------------------------------------------------------------------------\n\n");
                    fw.write("SEMESTER GPA: " + semesterGPA + "\n");
                    fw.write("SEMESTER POINTS: " + semesterPoints + "\n");
                    fw.write("SEMESTER CREDITS: " + semesterCredits + "\n");
                    fw.write("CUMULATIVE GPA: " + totalGPA + "\n");
                    fw.write("CUMULATIVE POINTS: " + totalPoints + "\n");
                    fw.write("CUMULATIVE CREDITS: " + totalCredits + "\n");
                    fw.write("\n-------------------------------------------------------------------------------------------------------------------------------\n\n");
                    fw.close();

                    semesterCredits = 0;
                    semesterPoints = 0;
                    semesterGPA = 0;

                    if (!entry.equals(entry_no)) {
                        totalCredits = 0;
                        totalPoints = 0;
                        totalGPA = 0;
                    }
                }

                // Create new folder
                entry_no = entry;
                semester = sem;
                year = yr;
                folder = "Transcripts/" + entry_no + "/" + year + "_" + semester + "/";

                // Create folder
                File f = new File(folder);
                f.mkdirs();

                // Create file
                file = folder + "gradeSheet.txt";
                f = new File(file);
                f.createNewFile();

                // Write to file
                FileWriter fw1 = new FileWriter(file);
                
                fw1.write("-------------------------------------------- INDIAN INSTITUTE OF TECHNOLOGY, ROPAR --------------------------------------------\n");
                fw1.write("-------------------------------------------------------------------------------------------------------------------------------\n");
                fw1.write("---------------------------------------------------- SEMESTER GRADE REPORT ----------------------------------------------------\n\n\n");

                // Name of student, entry number, department in new lines
                fw1.write(String.format("NAME: %s\n", name));
                fw1.write(String.format("ENTRY NUMBER: %s\n", entry_no));
                fw1.write(String.format("BATCH: %s\n\n", batch));
                
                fw1.write("Transcript for " + entry_no + " for year " + year + " and semester " + semester + " is: \n\n");
                fw1.write("-------------------------------------------------------------------------------------------------------------------------------\n");
                fw1.write(String.format("%-10s\t%-75s\t%-10s\t%-10s\n", "Course ID", "Course Name", "Credits", "Grade"));
                fw1.write("-------------------------------------------------------------------------------------------------------------------------------\n");
                fw1.write(String.format("%-10s\t%-75s\t%-10d\t%-10s\n", course, courseName, credits, grade));
                fw1.close();
            }
            else{
                // Write to file
                FileWriter fw = new FileWriter(file,true);
                // Write in file with proper formatting and uniform spacing
                fw.write(String.format("%-10s\t%-75s\t%-10d\t%-10s\n", course, courseName, credits, grade));
                fw.close();
            }

            semesterCredits += credits;
            semesterPoints += gradeMap.get(grade)*credits;
            totalCredits += credits;
            totalPoints += gradeMap.get(grade)*credits;
        }

        if(!file.equals("")) {
            FileWriter fw = new FileWriter(file, true);
            // Write in file with proper formatting and uniform spacing
            if (semesterCredits != 0) {
                semesterGPA = (double) semesterPoints / (double) semesterCredits;
            }
            if (totalCredits != 0) {
                totalGPA = (double) totalPoints / (double) totalCredits;
            }

            fw.write("-------------------------------------------------------------------------------------------------------------------------------\n\n");
            fw.write("SEMESTER GPA: " + semesterGPA + "\n");
            fw.write("SEMESTER POINTS: " + semesterPoints + "\n");
            fw.write("SEMESTER CREDITS: " + semesterCredits + "\n");
            fw.write("CUMULATIVE GPA: " + totalGPA + "\n");
            fw.write("CUMULATIVE POINTS: " + totalPoints + "\n");
            fw.write("CUMULATIVE CREDITS: " + totalCredits + "\n");
            fw.write("\n-------------------------------------------------------------------------------------------------------------------------------\n\n");
            fw.close();
        }

        System.out.println("Transcripts generated successfully");
    }

    private void viewCourses() throws Exception{
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
        ResultSet rs = cm.executeQuery(query);
        System.out.println("Course details: ");
        System.out.format("%-10s\t%-75s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n", "Course ID", "Course Name", "L", "T", "P", "Credits", "Department");
        while(rs.next()){
            String id = rs.getString("id");
            String name = rs.getString("name");
            int l = rs.getInt("l");
            int t = rs.getInt("t");
            int p = rs.getInt("p");
            int cred = rs.getInt("credits");
            String dept = rs.getString("department");
            System.out.format("%-10s\t%-75s\t%-10d\t%-10d\t%-10d\t%-10d\t%-10s\n", id, name, l, t, p, cred, dept);
        }
        
    }

    private void changeCurrentInfo() throws Exception{
        // 1. Take user input regarding which field to change
        // 2. Check if the field exists in CurrentInfo(field, value)
        // 3. Take user input for new value
        // 4. Update the database

        // Step 1: Take user input regarding which field to change
        System.out.println("Enter field to change: ");
        String field = sc.nextLine();

        // Step 2: Check if the field exists in CurrentInfo(field, value)
        String query = "SELECT * FROM CurrentInfo WHERE field = '" + field + "';";    
        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("Error: Field does not exist");
            return;
        }
        

        // Step 3: Take user input for new value
        System.out.println("Enter new value: ");
        String value = sc.nextLine();

        // Step 4: Update the database
        query = "UPDATE CurrentInfo SET value = '" + value + "' WHERE field = '" + field + "';";
        cm.executeUpdate(query);
        System.out.println("Updated successfully");
    }

    private void graduationCheck() throws Exception{
        // 1. Take user input for entry number
        // 2. Check if the entry number exists in student table
        // 3. Check if the student has completed all the program cores for his/her batch
        // 4. Check if the student has completed minimum elective credits for his/her batch
        // 5. Total credits >= 145, CGPA >= 5.0, HS Elective credits >= 6, Science Elective credits >= 6, Open Elective credits >= 6, Program elective credits >= 6

        int totalCredits = 0;
        int hsElectiveCredits = 0;
        int scienceElectiveCredits = 0;
        int openElectiveCredits = 0;
        int programElectiveCredits = 0;
        int totalGrade = 0;
        double cgpa = 0.0;
        Map <String, Integer> gradeMap = Map.of(
            "A", 10, "A-", 9, "B-", 8, "B", 7, "C", 6, "C-", 5, "D", 4, "D-", 3, "E", 2, "F", 0
        );

        // Step 1: Take user input for entry number
        System.out.println("Enter entry number: ");
        String entry_no = sc.nextLine();

        // Step 2: Check if the entry number exists in student table Student(entry_no, name, email, phone, batch)
        String query = "SELECT * FROM Student WHERE entry_no = '" + entry_no + "';";
        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("Error: Entry number does not exist");
            return;
        }

        // Step 3: Check if the student has completed all the program cores for his/her batch
        String batch = rs.getString("batch");

        // Get program cores for the batch ProgramCore(course_id, batch_id, type)
        query = "SELECT * FROM ProgramCore WHERE batch_id = '" + batch + "';";
        rs = cm.executeQuery(query);

        // Check if the student has completed all the program cores for his/her batch in CompletedCourse(entry_no, course_id, year, semester, grade)
        while(rs.next()){
            String course_id = rs.getString("course_id");
            query = "SELECT * FROM CompletedCourse WHERE entry_no = '" + entry_no + "' AND course_id = '" + course_id + "';";
            ResultSet rs2 = cm.executeQuery(query);
            if(!rs2.next()){
                System.out.println("Student has not completed all the program cores for his/her batch, hence cannot graduate");
                return;
            }


            // Get credits from Course(id, name, l, t, p, s, credits, department)
            query = "SELECT credits FROM Course WHERE id = '" + course_id + "';";
            ResultSet rs3 = cm.executeQuery(query);
            rs3.next();
            int credits = rs3.getInt("credits");
            totalCredits += credits;
            totalGrade += gradeMap.get(rs2.getString("grade")) * credits;
        }

        // Step 4: Check if the student has completed minimum elective credits for his/her batch using CompletedCourse(entry_no, course_id, year, semester, grade)
        query = "SELECT * FROM CompletedCourse WHERE entry_no = '" + entry_no + "';";
        rs = cm.executeQuery(query);

        while (rs.next()){
            // ProgramElective(course_id, batch_id, semester, year, type)
            String course_id = rs.getString("course_id");
            String grade = rs.getString("grade");

            // Check if the course is a program elective
            query = "SELECT * FROM ProgramElective WHERE course_id = '" + course_id + "' AND batch_id = '" + batch +  "';";
            ResultSet rs2 = cm.executeQuery(query);
            if(rs2.next()){
                // Get credits from Course(id, name, l, t, p, s, credits, department)
                query = "SELECT credits FROM Course WHERE id = '" + course_id + "';";
                ResultSet rs3 = cm.executeQuery(query);
                rs3.next();
                int credits = rs3.getInt("credits");
                totalCredits += credits;
                totalGrade += gradeMap.get(grade)*credits;

                String type = rs2.getString("type");
                if(type.equals("HS")){
                    hsElectiveCredits += credits;
                }
                else if(type.equals("Science")){
                    scienceElectiveCredits += credits;
                }
                else if(type.equals("Open")){
                    openElectiveCredits += credits;
                }
                else if(type.equals("Core")){
                    programElectiveCredits += credits;
                }
            }
        }

        // Step 5: Total credits >= 145, CGPA >= 5.0, HS Elective credits >= 6, Science Elective credits >= 6, Open Elective credits >= 6, Program elective credits >= 6
        if(totalCredits!=0) cgpa = totalGrade/totalCredits;

        System.out.println("Student details:");
        System.out.println("Total credits: " + totalCredits);
        System.out.println("Total Grade: " + totalGrade);
        System.out.println("HS Elective credits: " + hsElectiveCredits);
        System.out.println("Science Elective credits: " + scienceElectiveCredits);
        System.out.println("Open Elective credits: " + openElectiveCredits);
        System.out.println("Program Elective credits: " + programElectiveCredits);
        System.out.println("CGPA: " + cgpa);


        boolean flag = (totalCredits >= 145) && (hsElectiveCredits >= 6) && (scienceElectiveCredits >= 6) && (openElectiveCredits >= 6) && (programElectiveCredits >= 6) && (cgpa >= 5.0);
        System.out.println();
        if(flag){
            System.out.println("Student is eligible for graduation");
        }
        else{
            System.out.println("Student is not eligible for graduation");
        }

    }

    private void updateProfile() throws Exception{
        // 1. Display the current details
        // 2. Options to update email or phone in Student(entry_no, name, email, phone, batch)
        // 3. Update the details in the database

        // Step 1: Display the current details
        System.out.println("Your current details are:");
        String query = "SELECT * FROM academic WHERE id = '" + this.username + "'";
        ResultSet rs = cm.executeQuery(query);
        rs.next();
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Email: " + rs.getString("email"));
        System.out.println("Phone: " + rs.getString("phone"));

        // Step 2: Options to update email or phone
        System.out.println("\nDo you want to update your email or phone (enter email or phone):");
        String choice = sc.nextLine();

        if(choice.equals("email")){
            System.out.println("Enter your new email:");
            String newEmail = sc.nextLine();
            query = "UPDATE academic SET email = '" + newEmail + "' WHERE id = '" + this.username + "'";
            cm.executeUpdate(query);
            System.out.println("Email updated successfully");
        }
        else if(choice.equals("phone")){
            System.out.println("Enter your new phone number:");
            String newPhone = sc.nextLine();
            query = "UPDATE academic SET phone = '" + newPhone + "' WHERE id = '" + this.username + "'";
            cm.executeUpdate(query);
            System.out.println("Phone number updated successfully");
        }
        else{
            System.out.println("Invalid choice");
        }
    }
}
