/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package softwareEngineering;
import java.sql.*;

public class App {

    public static Person handleAuth(String userName, String userPassword, String databaseName) throws Exception{
        ConnectionManager cm = ConnectionManager.getCM(databaseName);
        
        ResultSet rs = cm.executeQuery("SELECT * FROM users WHERE username = '" + userName + "' AND password = '" + userPassword + "'");

        if(rs.next()){
            String role = rs.getString("role");
            if(role.equals("student")){
                System.out.println("\nWelcome, Student " + userName + "!");
                return new Student(userName);
            }
            else if(role.equals("instructor")){
                System.out.println("\nWelcome, Instructor " + userName + "!");
                return new Instructor(userName);
            }
            else if(role.equals("academic")){
                System.out.println("\nWelcome, Academic Section " + userName + "!");
                return new AcademicSection(userName);
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner();
        Person person = null;

        System.out.println("Welcome to the Academic Portal!");

        while(person == null){
            System.out.println("Please enter your credentials to login: ");
            System.out.println();
            System.out.println("Enter your user-name: ");
            String userName = sc.nextLine();
            System.out.println("Enter your password: ");
            String userPassword = sc.nextLine();
            
            person = null;
            try{
                if(args.length>0 && args[0].equals("test"))
                    person = handleAuth(userName, userPassword,"academicsystemtest");
                else
                    person = handleAuth(userName, userPassword,"academicsystem");
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }

            if(person == null){
                System.out.println("\nInvalid username or password");
                System.out.println("Press -1 to exit or any other integer to continue");
                int choice = sc.nextInt();
                if(choice == -1){
                    System.out.println("\nExiting...");
                    break;
                }
            }
        }

        if(person != null){
            person.manager();
        }
        
        sc.close();
    }
}
