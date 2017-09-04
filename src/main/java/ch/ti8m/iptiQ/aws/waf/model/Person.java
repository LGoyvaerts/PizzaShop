package ch.ti8m.iptiQ.aws.waf.model;

/**
 * Created by gol on 24.05.2017.
 */
public class Person {
    String firstName;
    String lastName;
    public Person (String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
