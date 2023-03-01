package softwareEngineering;
import java.io.*;

public class Scanner{
    private java.util.Scanner sc;

    public Scanner(){
        try{
            sc = new java.util.Scanner(System.in);
        }
        catch(Exception e){
            System.out.println("Error in creating scanner object");
        }
    }

    public Scanner(InputStream in){
        try{
            sc = new java.util.Scanner(in);
        }
        catch(Exception e){
            System.out.println("Error in creating scanner object");
        }
    }

    public String nextLine(){
        return sc.nextLine();
    }

    public int nextInt(){
        while(!sc.hasNextInt()){
            System.out.println("Enter a valid integer");
            sc.next();
        }
        int value = sc.nextInt();
        
        // done to compensate for weird behavior
        sc.nextLine();
        return value;
    }
    
    public void close(){
        sc.close();
    }
}
