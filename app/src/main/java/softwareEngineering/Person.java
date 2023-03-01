package softwareEngineering;

public class Person {
    public String username;
    public String role;

    public Person(){
        this.username = "";
    }

    public Person(String username) {
        this.username = username;
    }

    // An method which will be overridden by the child classes
    public void manager(){
        System.out.println("This is the manager method of the Person class");
    }
}
