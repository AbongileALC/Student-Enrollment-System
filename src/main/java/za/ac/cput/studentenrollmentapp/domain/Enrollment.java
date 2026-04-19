package za.ac.cput.studentenrollmentapp.domain;

import java.io.Serializable;

/**
 *
 * @author ATHINI NGQUKE
 */
public class Enrollment implements Serializable {
    private String studentNo;
    private String name;
    private String course;

    // Default constructor
    public Enrollment() {
    }

    // Parameterized constructor
    public Enrollment(String studentNo, String name, String course) {
        this.studentNo = studentNo;
        this.name = name;
        this.course = course;
    }

    // Getters
    public String getStudentNo() {
        return studentNo;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    // Setters
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrollment{" + 
               "studentNo=" + studentNo + 
               ", name=" + name + 
               ", course=" + course + 
               '}';
    }
}