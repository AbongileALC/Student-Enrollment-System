package za.ac.cput.studentenrollmentapp.domain;

import java.io.Serializable;

/**
 *
 * @author ATHINI NGQUKE
 */
public class Student implements Serializable {
    private String studentEmail;
    private String password;
    private String role;
    private String studentNumber;
    private String name;
    
    // Default constructor
    public Student() {
    }

    // Full parameterized constructor
    public Student(String studentEmail, String password, String role, String studentNumber, String name) {
        this.studentEmail = studentEmail;
        this.password = password;
        this.role = role;
        this.studentNumber = studentNumber;
        this.name = name;
    }

    // Constructor for login purposes
    public Student(String email, String password, String role) {
        this.studentEmail = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getStudentEmail() {
        return studentEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

}
