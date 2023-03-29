package softwareEngineering;
import java.sql.*;
import java.util.Map;

public class Student extends Person{
    Scanner sc;
    Connection con;
    ConnectionManager cm;
    
    public Student(String userName) throws Exception{
        this.username = userName;
        this.role = "student";

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
                        registerForCourse();
                        break;
                    case 2:
                        deregisterForCourse();
                        break;
                    case 3:
                        getCourseInfo();
                        break;
                    case 4:
                        viewEnrolledCourses();
                        break;
                    case 5:
                        viewCompletedCourses();
                        break;
                    case 6:
                        computeCGPA(true);
                        break;
                    case 7:
                        updateProfile();
                        break;
                    case 8:
                        System.out.println("Thank you for using the system. Have a nice day!\n");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            
            sc.close();
            cm.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    // This method displays the menu for the student
    private void displayOptions(){
        System.out.println();
        System.out.println("============================================");
        System.out.println("+++++++++++++++++++ MENU +++++++++++++++++++");
        System.out.println();

        System.out.println("1. Register for a course ");
        System.out.println("2. Deregister for a course ");
        System.out.println("3. Get course info ");
        System.out.println("4. View enrolled courses ");
        System.out.println("5. View completed courses ");
        System.out.println("6. Compute current CGPA ");
        System.out.println("7. Update Profile ");
        System.out.println("8. Exit ");
        System.out.println();
        System.out.println("============================================");
        System.out.println();
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

        // Step 2: Return the event value
        return event;
    }

    private String getBatchInfo() throws Exception{
        // 1. Get the batch from Student(entry_no, name, email, phone, batch) table
        // 2. Return the batch

        // Step 1: Get the batch from Student(entry_no, name, email, phone, batch) table
        String query = "SELECT batch FROM Student WHERE entry_no = '" + this.username + "';";
        String batch = "";
        ResultSet rs = cm.executeQuery(query);
        rs.next();
        batch = rs.getString("batch");

        // Step 2: Return the batch
        return batch;
    }

    private String getSemesterInfo() throws Exception{
        // 1. Get the batch from Student(entry_no, name, email, phone, batch) table
        // 2. Get the semester from Batch(id, semester, year, department) table
        // 3. Return the semester

        // Step 1: Get the batch using getBatchInfo()
        String batch = getBatchInfo();
        
        // Step 2: Get the semester from Batch(id, semester, year, department) table
        String query = "SELECT semester FROM Batch WHERE id = '" + batch + "';";
        String semester = "";
        ResultSet rs = cm.executeQuery(query);
        rs.next();
        // Convert the semester to String
        semester = String.valueOf(rs.getInt("semester"));

        // Step 3: Return the semester
        return semester;
    }

    private String courseExist(String course_id) throws Exception{
        // This method checks if the course exists in the Course table

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

    private int getCreditLimit() throws Exception{
        // 1. Get the current semester
        // 2. The number of credits he/she is allowed is governed by the scheme governed by the institute (1.25 times the average of the credits earned in the previous two semesters.
        // For the first and second semester, the limit is 21 credits (Assumed). For rest of the semesters, calculate the average of the credits earned in the previous two semesters
        // from CompletedCourse(entry_no, course_id, year, semester, grade), find the courses and their credits from Course(id, name, l, t, p, credits, department) 
        // and calculate the average of the credits earned in the previous two semesters

        // Step 1: Get the current semester
        String currentSem = getSemesterInfo();

        // Step 2: Calculate the credit limit
        int creditLimit = 0;
        if(currentSem.equals("1") || currentSem.equals("2")){
            creditLimit = 21;
        }
        else{
            // Get the previous semester
            int prevSem = Integer.parseInt(currentSem) - 1;
            String prevSemStr = String.valueOf(prevSem);

            // Get the credits earned in the previous semester
            String query = "SELECT credits FROM Course WHERE id IN (SELECT course_id FROM CompletedCourse WHERE entry_no = '" + this.username + "' AND semester = '" + prevSemStr + "');";
            int prevSemCredits = 0;

            ResultSet rs = cm.executeQuery(query);
            while(rs.next()){
                prevSemCredits += rs.getInt("credits");
            }

            // Get the credits earned in the semester before the previous semester
            prevSem = Integer.parseInt(currentSem) - 2;
            prevSemStr = String.valueOf(prevSem);
            query = "SELECT credits FROM Course WHERE id IN (SELECT course_id FROM CompletedCourse WHERE entry_no = '" + this.username + "' AND semester = '" + prevSemStr + "');";
            int prevPrevSemCredits = 0;

            rs = cm.executeQuery(query);
            while(rs.next()){
                prevPrevSemCredits += rs.getInt("credits");
            }
            
            // Calculate the credit limit
            creditLimit = (int) Math.ceil((prevSemCredits + prevPrevSemCredits)/2 * 1.25);
        }
        
        return creditLimit;
    }

    private String courseEligibility(String course_id,Boolean print) throws Exception{
        /*
         1. Check if the course exists and is offered in the current semester
         2. Check if the student is already registered for the course
         3. Check if the student has done the course before
         4. Check if registering for the course will exceed the maximum number of credits (24 credits)
         5. Check if the student has completed the prerequisites for the course
         6. Check if the student has more cgpa than the minimum cgpa required for the course from CourseOffering(course_id, year, semester, instructor, cgpa)
         7. Register the student for the course
         */

        // Step 1: Check if the course exists and is offered in the current semester
        if(courseExist(course_id).equals("NOT EXIST")){
            return "NOT EXIST";
        }

        String currentSem = getSemesterInfo();
        String currentYear = "";

        // get year from CurrentInfo table


        ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
        // Values in currentinfo are of the form (field, value)
        while(rs.next()){
            if(rs.getString("field").equals("current_year")){
                currentYear = rs.getString("value");
            }
        }


        // Step 2.2: Check if the course is offered in the current semester
        String query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
        rs = cm.executeQuery(query);
        if(!rs.next()){
            if(print) System.out.println("Course is not offered in the current semester");
            return "NOT OFFERED";
        }


        // Step 3: Check if the student is already registered for the course. Check in EnrolledCourse(entry_no, course_id, year, semester)
        query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";

        rs = cm.executeQuery(query);
        if(rs.next()){
            if(print) System.out.println("You are already registered for this course");
            return "ALREADY REGISTERED";
        }


        // Step 4: Check if the student has done the course before. Check in CompletedCourse(entry_no, course_id, year, semester, grade)
        query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";

        rs = cm.executeQuery(query);
        if(rs.next()){
            if(print) System.out.println("You have already done this course");
            return "ALREADY DONE";
        }


        // Step 5: Check if registering for the course will exceed the maximum number of credits (24 credits)
        int creditLimit = getCreditLimit();
        query = "SELECT credits FROM course WHERE id = '" + course_id + "'";
        int credits = 0;

        rs = cm.executeQuery(query);
        rs.next();
        credits = rs.getInt("credits");



        rs = cm.executeQuery("SELECT SUM(credits) FROM course WHERE id IN (SELECT course_id FROM enrolledcourse WHERE entry_no = '" + this.username + "')");
        if(rs.next()){
            int total_credits = rs.getInt("sum");
            if(total_credits + credits > creditLimit){
                if(print){
                    System.out.println("Credit limit: " + creditLimit);
                    System.out.println("You cannot register for this course as it will exceed the maximum number of credits");
                }
                return "CREDIT LIMIT EXCEEDED";
            }
        }

        // Step 6: Check if the student has completed the prerequisites for the course. Check in Prerequisite(course_id, group_id)
        // Then for each group_id Prerequisite_group(group_id, pre_req_id), check if the student has completed at least one course in the group. Check in CompletedCourse(entry_no, course_id, year, semester, grade)
        query = "SELECT * FROM prerequisite WHERE course_id = '" + course_id + "'";

        rs = cm.executeQuery(query);
        while(rs.next()){
            String group_id = rs.getString("group_id");
            query = "SELECT * FROM prerequisite_group WHERE group_id = '" + group_id + "'";
            ResultSet rs2 = cm.executeQuery(query);
            boolean flag = false;
            while(rs2.next()){
                String pre_req_id = rs2.getString("pre_req_id");
                query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + pre_req_id + "'";
                ResultSet rs3 = cm.executeQuery(query);
                if(rs3.next()){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                if(print) System.out.println("You have not completed the prerequisites for this course");
                return "PREREQUISITES NOT MET";
            }
        }


        // Step 7: More cgpa than required for the course
        query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "'";

        rs = cm.executeQuery(query);
        if(rs.next()){
            double cgpa = rs.getDouble("cgpa");
            double student_cgpa = computeCGPA(false);
            if( student_cgpa < cgpa){
                if(print) System.out.println("You do not have the required cgpa for this course");
                return "CGPA NOT MET";
            }
        }

        return "OK";
    }

    private void displayPossibleCourses() throws Exception{
        // 1. Get all the courses from Course(id, name, l, t, p, s, credits, department)
        // 2. Check their eligibility
        // 3. Display the courses that are eligible

        // Step 1: Get all the courses from Course(id, name, l, t, p, s, credits, department)
        ResultSet rs = cm.executeQuery("SELECT * FROM course");

        // Step 2: Check their eligibility
        System.out.println();
        System.out.println("Possible courses to register for:");
        int index = 1;
        while(rs.next()){
            String course_id = rs.getString("id");
            if(courseEligibility(course_id,false).equals("OK")){
                // Step 3: Display the courses that are eligible
                System.out.println(index + ". " + course_id + " " + rs.getString("name"));
                index++;
            }
        }
        System.out.println();
    }

    private void registerForCourse() throws Exception{
        /*
         1. Check if course registration is open
         2. Display all the courses possible to register for
         3. Ask the student to enter the course id
         4. Check for course eligibility and register the student for the course
         */

        // Step 1: Check if course registration is open
        String event = getEventInfo();
        if(!event.equals("3")){
            System.out.println("Course registeration/deregisteration is not open");
            return;
        }

        // Step 2: Display all the courses possible to register for
        displayPossibleCourses();

        // Step 3: Ask the student to enter the course id
        String course_id;
        System.out.println("Enter the course id: ");
        course_id = sc.nextLine();

        // Step 4: Check for course eligibility and register the student for the course
        String currentSem = getSemesterInfo();
        String currentYear = "";

        // get year from CurrentInfo table
        ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
        // Values in currentinfo are of the form (field, value)
        while(rs.next()){
            if(rs.getString("field").equals("current_year")){
                currentYear = rs.getString("value");
            }
        }

        if(courseEligibility(course_id,true).equals("OK")){
            cm.executeUpdate("INSERT INTO enrolledcourse(entry_no, course_id, year, semester) VALUES('" + this.username + "', '" + course_id + "', '" + currentYear + "', '" + currentSem + "')");
            System.out.println("You have been registered for the course");
            return;
        }
        else{
            return;
        }
    }

    private void deregisterForCourse() throws Exception{
        /*  
            1. Check if the course registration is open
            2. Get the course id from the user
            3. Check if the course exists in Course(id, name, l, t, p, credits, department) table
            4. Check if the student is registered for the course in EnrolledCourse(entry_no, course_id, year, semester)
            5. Deregister the student from the course
         */

        // Step 1: Check if the course registration is open
        String event = getEventInfo();
        if(!event.equals("3")){
            System.out.println("Course registeration/deregisteration is not open");
            return;
        }

        // Step 2: Get the course id from the user
        String course_id;
        System.out.println("Enter the course id: ");
        course_id = sc.nextLine();

        // Step 3: Check if the course exists in Course(id, name, l, t, p, credits, department) table
        if(courseExist(course_id).equals("NOT EXIST")){
            return;
        }

        // Step 4: Check if the student is registered for the course in EnrolledCourse(entry_no, course_id, year, semester)
        String query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";

        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("You are not registered for this course");
            return;
        }


        // Step 5: Deregister the student from the course
        query = "DELETE FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";
        
        cm.executeUpdate(query);
        System.out.println("You have been deregistered from the course");
        return;
        

    }

    private void getCourseInfo() throws Exception{
        // 1. Get the course id from the user
        // 2. Check if the course exists in Course(id, name, l, t, p, credits, department) table
        // 3. Get the course info from the table
        // 4. Check the course in CourseOffering(course_id, year, semester, instructor) and if it is offered in the current semester and current year, print the instructor name


        // Step 1: Get the course id from the user
        String course_id;
        System.out.println("Enter the course id: ");
        course_id = sc.nextLine();

        // Step 2: Check if the course exists in Course(id, name, l, t, p, credits, department) table
        String query = "SELECT * FROM course WHERE id = '" + course_id + "'";
        
            ResultSet rs = cm.executeQuery(query);
            if(!rs.next()){
                System.out.println("Course does not exist");
                return;
            }
    
        // Step 3: Get the course info from the table
        query = "SELECT * FROM course WHERE id = '" + course_id + "'";
        
        rs = cm.executeQuery(query);
        if(rs.next()){
            System.out.println();
            System.out.println("Course details: ");
            System.out.println("Course id: " + rs.getString("id"));
            System.out.println("Course name: " + rs.getString("name"));
            System.out.println("L: " + rs.getInt("l"));
            System.out.println("T: " + rs.getInt("t"));
            System.out.println("P: " + rs.getInt("p"));
            System.out.println("Credits: " + rs.getInt("credits"));
            System.out.println("Department: " + rs.getString("department"));
        }
        

        // Get all the prerequisites from Prerequisite_group(group_id, pre_req_id) and Prerequisite(course_id, group_id) 
        String prerequisites = "";
        
        rs = cm.executeQuery("SELECT * FROM prerequisite WHERE course_id = '" + course_id + "'");
        while(rs.next()){
            String group_id = rs.getString("group_id");
            ResultSet rs2 = cm.executeQuery("SELECT * FROM prerequisite_group WHERE group_id = '" + group_id + "'");
            String prerequisite_group = "( ";
            while(rs2.next()){
                prerequisite_group += rs2.getString("pre_req_id") + " OR ";
            }
            prerequisites += prerequisite_group.substring(0, prerequisite_group.length() - 4) + " ) AND ";
        }
        

        if(prerequisites.length() > 0){
            System.out.println("Prerequisites: " + prerequisites.substring(0, prerequisites.length() - 5));
        }


        // Step 4: Check the course in CourseOffering(course_id, year, semester, instructor) and if it is offered in the current semester and current year, print the instructor name
        String currentSem = getSemesterInfo();
        String currentYear = "";

        // Get year and semester from CurrentInfo table
        
        rs = cm.executeQuery("SELECT * FROM currentinfo");
        // Values in currentinfo are of the form (field, value)
        while(rs.next()){
            if(rs.getString("field").equals("current_year")){
                currentYear = rs.getString("value");
            }
        }
    

        query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
        
        rs = cm.executeQuery(query);
        if(rs.next()){
            System.out.println("Instructor: " + rs.getString("instructor"));
        }
        else{
            System.out.println("Course is not offered in the current semester");
        }
    }

    private void viewEnrolledCourses() throws Exception{
        // 1. Get the current semester and year from CurrentInfo table
        // 2. Get the courses from EnrolledCourse(entry_no, course_id, year, semester) table
        // 3. Print the courses

        // Step 1: Get the current semester and year from CurrentInfo table
        String currentSem = getSemesterInfo();
        String currentYear = "";

        // Get year from CurrentInfo table
        ResultSet rs = cm.executeQuery("SELECT * FROM currentinfo");
        // Values in currentinfo are of the form (field, value)
        while(rs.next()){
            if(rs.getString("field").equals("current_year")){
                currentYear = rs.getString("value");
            }
        }


        // Step 2: Get the courses from EnrolledCourse(entry_no, course_id, year, semester) table
        // Step 3: Print the courses
        String query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND year = '" + currentYear + "' AND semester = '" + currentSem + "'";
        rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("You are not registered for any courses in the current semester");
            return;
        }
        else{
            System.out.println("You are registered for the following courses in the current semester:");
            System.out.format("%-15s\t%-60s\t%-15s\t%-5s\t%-5s\t%-5s\t%-5s\t%-15s\n", "Course ID", "Course Name", "Instructor", "L", "T", "P", "Credits", "Department");
            do{
                String course_id = rs.getString("course_id");
                String course_name = "";
                String instructor = "";
                int l = 0;
                int t = 0;
                int p = 0;
                int credits = 0;
                String department = "";

                String query2 = "SELECT * FROM course WHERE id = '" + course_id + "'";
                ResultSet rs2 = cm.executeQuery(query2);
                if(rs2.next()){
                    course_name = rs2.getString("name");
                    l = rs2.getInt("l");
                    t = rs2.getInt("t");
                    p = rs2.getInt("p");
                    credits = rs2.getInt("credits");
                    department = rs2.getString("department");
                }

                String query3 = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
                ResultSet rs3 = cm.executeQuery(query3);
                if(rs3.next()){
                    instructor = rs3.getString("instructor");
                }
                // Print all the details wiht proper formatting and uniform spacing
                System.out.format("%-15s\t%-60s\t%-15s\t%-5d\t%-5d\t%-5d\t%-5d\t%-15s\n", course_id, course_name, instructor, l, t, p, credits, department);
                
            }while(rs.next());
        }
    }

    private void viewCompletedCourses() throws Exception{
        // 1. Get the courses from CompletedCourse(entry_no, course_id, year, semester, grade) table
        // 2. Print the courses

        String query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "'";

        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("You have not completed any courses");
            return;
        }
        else{
            System.out.println("You have completed the following courses:");
            System.out.format("%-15s\t%-60s\t%-15s\t%-15s\t%-15s\n", "Course ID", "Course Name", "Year", "Semester", "Grade");
            do{
                String course_id = rs.getString("course_id");
                String course_name = "";
                String year = rs.getString("year");
                String semester = rs.getString("semester");
                String grade = rs.getString("grade");

                String query2 = "SELECT * FROM course WHERE id = '" + course_id + "'";
                ResultSet rs2 = cm.executeQuery(query2);
                if(rs2.next()){
                    course_name = rs2.getString("name");
                }

                // Print all the details wiht proper formatting and uniform spacing
                System.out.format("%-15s\t%-60s\t%-15s\t%-15s\t%-15s\n", course_id, course_name, year, semester, grade);
            }while(rs.next());
            return;
        }
    }

    private double computeCGPA(Boolean print) throws Exception{
        // 1. Get the courses from CompletedCourse(entry_no, course_id, year, semester, grade) table
        // 2. Compute the CGPA

        String query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "'";
        ResultSet rs = cm.executeQuery(query);
        if(!rs.next()){
            System.out.println("You have not completed any courses. Hence the CGPA is 0.0");
            return 0;
        }
        else{
            int totalCredits = 0;
            int totalGradePoints = 0;
            do{
                String course_id = rs.getString("course_id");
                int credits = 0;
                int gradePoints = 0;

                String query2 = "SELECT * FROM course WHERE id = '" + course_id + "'";
                ResultSet rs2 = cm.executeQuery(query2);
                rs2.next();
                credits = rs2.getInt("credits");


                String grade = rs.getString("grade");
                Map <String, Integer> gradeMap = Map.of(
                        "A", 10, "A-", 9, "B-", 8, "B", 7, "C", 6, "C-", 5, "D", 4, "D-", 3, "E", 2, "F", 0
                );

                totalCredits += credits;
                totalGradePoints += (credits * gradeMap.get(grade));
            }while(rs.next());

            double cgpa = (double)totalGradePoints / (double)totalCredits;
            if(print){
                System.out.println("Your total credits are: " + totalCredits);
                System.out.println("Your total grade points are: " + totalGradePoints);
                System.out.println("Your CGPA is: " + cgpa);
            }
            return cgpa;
        }
    }

    private void updateProfile() throws Exception{
        // 1. Display the current details
        // 2. Options to update email or phone in Student(entry_no, name, email, phone, batch)
        // 3. Update the details in the database

        // Step 1: Display the current details
        System.out.println();
        System.out.println("Your current details are:");
        String query = "SELECT * FROM student WHERE entry_no = '" + this.username + "'";
        ResultSet rs = cm.executeQuery(query);
        rs.next();
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Email: " + rs.getString("email"));
        System.out.println("Phone: " + rs.getString("phone"));
        System.out.println("Batch: " + rs.getString("batch"));

        // Step 2: Options to update email or phone
        System.out.println("\nDo you want to update your email or phone (enter email or phone):");
        String choice = sc.nextLine();

        if(choice.equals("email")){
            System.out.println("Enter your new email:");
            String newEmail = sc.nextLine();
            query = "UPDATE student SET email = '" + newEmail + "' WHERE entry_no = '" + this.username + "'";
            cm.executeUpdate(query);
            System.out.println("Email updated successfully");
        }
        else if(choice.equals("phone")){
            System.out.println("Enter your new phone number:");
            String newPhone = sc.nextLine();
            query = "UPDATE student SET phone = '" + newPhone + "' WHERE entry_no = '" + this.username + "'";
            cm.executeUpdate(query);
            System.out.println("Phone number updated successfully");
        }
        else{
            System.out.println("Invalid choice");
        }
    }
}
