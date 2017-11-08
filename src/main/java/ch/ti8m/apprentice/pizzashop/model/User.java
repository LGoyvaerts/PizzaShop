package ch.ti8m.apprentice.pizzashop.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {

    private String email;

    private String password;

    public User(){
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    @XmlElement(name = "password")
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}

