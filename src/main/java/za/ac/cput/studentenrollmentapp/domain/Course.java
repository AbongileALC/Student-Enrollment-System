package za.ac.cput.studentenrollmentapp.domain;

import java.io.Serializable;

/**
 *
 * @author ATHINI NGQUKE
 */
public class Course implements Serializable {
    private String courseCode;
    private String title;

    // Default constructor
    public Course() {
    }

    // Parameterized constructor
    public Course(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    // Setters
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}