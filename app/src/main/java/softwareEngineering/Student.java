package softwareEngineering;
import java.sql.*;

// TODO: How to integrate program core and program elective courses? - No use on student side
// TODO: Apply filters in CompletedCourse for grades viewing

public class Student extends Person{
    Scanner sc;
    Connection con;
    
    public Student(String userName) throws Exception{
        this.username = userName;
        this.role = "student";

        sc = new Scanner();
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.getConnection();
    }

    public void manager(){
        int choice = 0;
        while(choice != 7){
            displayOptions();
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
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
                    System.out.println("Thank you for using the system. Have a nice day!\n");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
        
        sc.close();
    }

    private void displayOptions(){
        String options = "\n\n1. Register for a course \n";
        options += "2. Deregister for a course \n";
        options += "3. Get course info \n";
        options += "4. View enrolled courses \n";
        options += "5. View completed courses \n";
        options += "6. Compute current CGPA \n";
        options += "7. Exit \n";

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

    public String getBatchInfo(){
        // Step 1: Get the batch from Student(entry_no, name, email, phone, batch) table
        String query = "SELECT batch FROM Student WHERE entry_no = '" + this.username + "';";
        String batch = "";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                batch = rs.getString("batch");
                return batch;
            }
            else{
                return "Error: Batch not found";
            }
        }
        catch(Exception e){
            return "Error: " + e;
        }
    }

    public String getSemesterInfo(){
        // 1. Get the batch from Student(entry_no, name, email, phone, batch) table
        // 2. Get the semester from Batch(id, semester, year, department) table
        // 3. Return the semester

        String batch = getBatchInfo();
        if(batch.equals("Error: Batch not found")){
            return "Error: Batch not found";
        }
        
        // Step 2: Get the semester from Batch(id, semester, year, department) table
        String query = "SELECT semester FROM Batch WHERE id = '" + batch + "';";
        String semester = "";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                // Convert the semester to an String
                
                semester = String.valueOf(rs.getInt("semester"));
            }
            else{
                return "Error: Semester not found";
            }
        }
        catch(Exception e){
            return "Error: " + e;
        }

        // Step 3: Return the semester
        return semester;
    }

    private String courseExist(String course_id){
        String query = "SELECT * FROM course WHERE id = '" + course_id + "'";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("Course does not exist");
                return "NOT EXIST";
            }
            else{
                return "EXIST";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "ERROR";
        }
    }

    private int getCreditLimit(){
        // 1. Get the current semester
        // 2. However, the number of credits he/she is allowed is governed by the scheme governed by the institute (1.25 times the average of the credits earned in the previous two semesters.
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
            try{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    prevSemCredits += rs.getInt("credits");
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e);
                return 0;
            }

            // Get the credits earned in the semester before the previous semester
            prevSem = Integer.parseInt(currentSem) - 2;
            prevSemStr = String.valueOf(prevSem);
            query = "SELECT credits FROM Course WHERE id IN (SELECT course_id FROM CompletedCourse WHERE entry_no = '" + this.username + "' AND semester = '" + prevSemStr + "');";
            int prevPrevSemCredits = 0;
            try{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    prevPrevSemCredits += rs.getInt("credits");
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e);
                return 0;
            }

            // Calculate the credit limit
            creditLimit = (int) Math.ceil((prevSemCredits + prevPrevSemCredits)/2 * 1.25);
        }
        
        return creditLimit;
    }

    public String registerForCourse(){
        /*
         1. Check if course registration is open
         2. Get the course id from the user and check if the course exists and is offered in the current semester
         3. Check if the student is already registered for the course 
         4. Check if the student has done the course before
         5. Check if registering for the course will exceed the maximum number of credits (24 credits)
         6. Check if the student has completed the prerequisites for the course
         7. Check if the student has more cgpa than the minimum cgpa required for the course from CourseOffering(course_id, year, semester, instructor, cgpa)
         8. Register the student for the course
         */

        try{
            // Step 1: Check if course registration is open
            String event = getEventInfo();
            if(event.equals("Error: Event not found")){
                System.out.println("Error: Event not found");
                return "Error: Event not found";
            }
            else if(!event.equals("3")){
                System.out.println("Course registeration/deregisteration is not open");
                return "Course registeration/deregisteration is not open";
            }

            // Step 2.1: Check if the course exists in Course(id, name, l, t, p, credits, department) table
            String course_id;
            System.out.println("Enter the course id: ");
            course_id = sc.nextLine();
            
            if(courseExist(course_id).equals("NOT EXIST")){
                return "Course does not exist";
            }

            String currentSem = getSemesterInfo();
            String currentYear = "";

            // get year from CurrentInfo table

            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
            }
            

            // Step 2.2: Check if the course is offered in the current semester
            String query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("Course is not offered in the current semester");
                return "Course is not offered in the current semester";
            }
            

            // Step 3: Check if the student is already registered for the course. Check in EnrolledCourse(entry_no, course_id, year, semester)
            query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                System.out.println("You are already registered for this course");
                return "You are already registered for this course";
            }
            

            // Step 4: Check if the student has done the course before. Check in CompletedCourse(entry_no, course_id, year, semester, grade)
            query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                System.out.println("You have already done this course");
                return "You have already done this course";
            }


            // Step 5: Check if registering for the course will exceed the maximum number of credits (24 credits)
            int creditLimit = getCreditLimit();
            query = "SELECT credits FROM course WHERE id = '" + course_id + "'";
            int credits = 0;
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                credits = rs.getInt("credits");
            }


            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT SUM(credits) FROM course WHERE id IN (SELECT course_id FROM enrolledcourse WHERE entry_no = '" + this.username + "')");
            if(rs.next()){
                int total_credits = rs.getInt("sum");
                if(total_credits + credits > creditLimit){
                    System.out.println("Credit limit: " + creditLimit);
                    System.out.println("You cannot register for this course as it will exceed the maximum number of credits");
                    return "You cannot register for this course as it will exceed the maximum number of credits";
                }
            }

            // Step 6: Check if the student has completed the prerequisites for the course. Check in Prerequisite(course_id, group_id)
            // Then for each group_id Prerequisite_group(group_id, pre_req_id), check if the student has completed at least one course in the group. Check in CompletedCourse(entry_no, course_id, year, semester, grade)
            query = "SELECT * FROM prerequisite WHERE course_id = '" + course_id + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                String group_id = rs.getString("group_id");
                query = "SELECT * FROM prerequisite_group WHERE group_id = '" + group_id + "'";
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query);
                boolean flag = false;
                while(rs2.next()){
                    String pre_req_id = rs2.getString("pre_req_id");
                    query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + pre_req_id + "'";
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(query);
                    if(rs3.next()){
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    System.out.println("You have not completed the prerequisites for this course");
                    return "You have not completed the prerequisites for this course";
                }
            }


            // Step 7: More cgpa than required for the course
            query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                double cgpa = rs.getDouble("cgpa");
                double student_cgpa = computeCGPA(false);
                if( student_cgpa < cgpa){
                    System.out.println("You do not have the required cgpa for this course");
                    return "You do not have the required cgpa for this course";
                }
            }
            
            
            // Step 8: Insert into EnrolledCourse(entry_no, course_id, year, semester)
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO enrolledcourse(entry_no, course_id, year, semester) VALUES('" + this.username + "', '" + course_id + "', '" + currentYear + "', '" + currentSem + "')");
            System.out.println("You have been registered for the course");
            return "You have been registered for the course";
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    public String deregisterForCourse(){
        /*  
            1. Check if the course registration is open
            2. Get the course id from the user
            3. Check if the course exists in Course(id, name, l, t, p, credits, department) table
            4. Check if the student is registered for the course in EnrolledCourse(entry_no, course_id, year, semester)
            5. Deregister the student from the course
         */

        try{
            // Step 1: Check if the course registration is open
            String event = getEventInfo();
            if(event.equals("Error: Event not found")){
                System.out.println("Error: Event not found");
                return "Error: Event not found";
            }
            else if(!event.equals("3")){
                System.out.println("Course registeration/deregisteration is not open");
                return  "Course registeration/deregisteration is not open";
            }

            // Step 2: Get the course id from the user
            String course_id;
            System.out.println("Enter the course id: ");
            course_id = sc.nextLine();

            // Step 3: Check if the course exists in Course(id, name, l, t, p, credits, department) table
            if(courseExist(course_id).equals("NOT EXIST")){
                return "Course does not exist";
            }

            // Step 4: Check if the student is registered for the course in EnrolledCourse(entry_no, course_id, year, semester)
            String query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("You are not registered for this course");
                return "You are not registered for this course";
            }


            // Step 5: Deregister the student from the course
            query = "DELETE FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND course_id = '" + course_id + "'";
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("You have been deregistered from the course");
            return "You have been deregistered from the course";
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    private void getCourseInfo(){
        // 1. Get the course id from the user
        // 2. Check if the course exists in Course(id, name, l, t, p, credits, department) table
        // 3. Get the course info from the table
        // 4. Check the course in CourseOffering(course_id, year, semester, instructor) and if it is offered in the current semester and current year, print the instructor name

        try{
            // Step 1: Get the course id from the user
            String course_id;
            System.out.println("Enter the course id: ");
            course_id = sc.nextLine();

            // Step 2: Check if the course exists in Course(id, name, l, t, p, credits, department) table
            String query = "SELECT * FROM course WHERE id = '" + course_id + "'";
            
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.next()){
                    System.out.println("Course does not exist");
                    return;
                }
        
            // Step 3: Get the course info from the table
            query = "SELECT * FROM course WHERE id = '" + course_id + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
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
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM prerequisite WHERE course_id = '" + course_id + "'");
            while(rs.next()){
                String group_id = rs.getString("group_id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM prerequisite_group WHERE group_id = '" + group_id + "'");
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
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
            }
        

            query = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                System.out.println("Instructor: " + rs.getString("instructor"));
            }
            else{
                System.out.println("Course is not offered in the current semester");
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

    private void viewEnrolledCourses(){
        // 1. Get the current semester and year from CurrentInfo table
        // 2. Get the courses from EnrolledCourse(entry_no, course_id, year, semester) table
        // 3. Print the courses

        // Step 1: Get the current semester and year from CurrentInfo table
        String currentSem = getSemesterInfo();
        String currentYear = "";

        // Get year from CurrentInfo table
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM currentinfo");
            // Values in currentinfo are of the form (field, value)
            while(rs.next()){
                if(rs.getString("field").equals("current_year")){
                    currentYear = rs.getString("value");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }

        // Step 2: Get the courses from EnrolledCourse(entry_no, course_id, year, semester) table
        // Step 3: Print the courses
        String query = "SELECT * FROM enrolledcourse WHERE entry_no = '" + this.username + "' AND year = '" + currentYear + "' AND semester = '" + currentSem + "'";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("You are not registered for any courses in the current semester");
                return;
            }
            else{
                System.out.println("You are registered for the following courses in the current semester:");
                System.out.println("Course id\tCourse name\t\t\t\t\tInstructor\tL\tT\tP\tCredits\tDepartment");
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
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    if(rs2.next()){
                        course_name = rs2.getString("name");
                        l = rs2.getInt("l");
                        t = rs2.getInt("t");
                        p = rs2.getInt("p");
                        credits = rs2.getInt("credits");
                        department = rs2.getString("department");
                    }

                    String query3 = "SELECT * FROM courseoffering WHERE course_id = '" + course_id + "' AND year = '" + currentYear + "' AND semester = '" + (String.valueOf(Integer.parseInt(currentSem) % 2)) + "'";
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(query3);
                    if(rs3.next()){
                        instructor = rs3.getString("instructor");
                    }
                    // Print all the details wiht proper formatting and uniform spacing
                    System.out.format("%-15s\t%-45s\t%-15s\t%-5d\t%-5d\t%-5d\t%-5d\t%-15s\n", course_id, course_name, instructor, l, t, p, credits, department);
                    
                }while(rs.next());
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return;
        }
    }

    public String viewCompletedCourses(){
        // 1. Get the courses from CompletedCourse(entry_no, course_id, year, semester, grade) table
        // 2. Print the courses

        String query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "'";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("You have not completed any courses");
                return "You have not completed any courses";
            }
            else{
                System.out.println("You have completed the following courses:");
                System.out.println("Course id\tCourse name\t\t\t\t\tYear\t\tSemester\tGrade");
                do{
                    String course_id = rs.getString("course_id");
                    String course_name = "";
                    String year = rs.getString("year");
                    String semester = rs.getString("semester");
                    String grade = rs.getString("grade");

                    String query2 = "SELECT * FROM course WHERE id = '" + course_id + "'";
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    if(rs2.next()){
                        course_name = rs2.getString("name");
                    }

                    // Print all the details wiht proper formatting and uniform spacing
                    System.out.format("%-15s\t%-45s\t%-15s\t%-15s\t%-15s\n", course_id, course_name, year, semester, grade);
                }while(rs.next());
                return "Completed courses printed";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    public double computeCGPA(Boolean print){
        // 1. Get the courses from CompletedCourse(entry_no, course_id, year, semester, grade) table
        // 2. Compute the CGPA

        String query = "SELECT * FROM completedcourse WHERE entry_no = '" + this.username + "'";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                System.out.println("You have not completed any courses");
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
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    if(rs2.next()){
                        credits = rs2.getInt("credits");
                    }

                    String grade = rs.getString("grade");
                    if(grade.equals("A")){
                        gradePoints = 10;
                    }
                    else if(grade.equals("A-")){
                        gradePoints = 9;
                    }
                    else if(grade.equals("B")){
                        gradePoints = 8;
                    }
                    else if(grade.equals("B-")){
                        gradePoints = 7;
                    }
                    else if(grade.equals("C")){
                        gradePoints = 6;
                    }
                    else if(grade.equals("C-")){
                        gradePoints = 5;
                    }
                    else if(grade.equals("D")){
                        gradePoints = 4;
                    }
                    else if(grade.equals("E")){
                        gradePoints = 3;
                    }
                    else if(grade.equals("F")){
                        gradePoints = 0;
                    }
                    else{
                        if(print) System.out.println("Error: Invalid grade");
                        return 0;
                    }

                    totalCredits += credits;
                    totalGradePoints += (credits * gradePoints);
                }while(rs.next());

                double cgpa = (double)totalGradePoints / (double)totalCredits;
                if(print) System.out.println("Your CGPA is: " + cgpa);
                return cgpa;
            }
        }
        catch(Exception e){
            if(print) System.out.println("Error: " + e);
            return 0;
        }

    }
}
