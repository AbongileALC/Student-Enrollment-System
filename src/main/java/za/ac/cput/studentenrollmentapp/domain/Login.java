package za.ac.cput.studentenrollmentapp.domain;

import java.io.Serializable;

/**
 *
 * @author ATHINI NGQUKE
 */
public class Login implements Serializable {
    private String email;
    private String password;
    private String role;
    
    // Default constructor
    public Login() {
    }

    // Parameterized constructor
    public Login(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}