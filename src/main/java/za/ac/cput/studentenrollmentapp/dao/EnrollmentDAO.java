package za.ac.cput.studentenrollmentapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import za.ac.cput.studentenrollmentapp.connection.DBConnection;
import za.ac.cput.studentenrollmentapp.domain.Enrollment;

/**
 *
 * @author abong
 */
public class EnrollmentDAO {
    
    // CREATE - Enroll student in course (matches your insertRecord method)
    public boolean insertRecord(Enrollment enrollment) {
        // Check if already enrolled
        if (isEnrolled(enrollment.getStudentNo(), enrollment.getCourse())) {
            JOptionPane.showMessageDialog(null, "Student already enrolled in this course!");
            return false;
        }
        
        String sql = "INSERT INTO Enrollment (student_number, name, course) VALUES (?, ?, ?)";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, enrollment.getStudentNo());
            pstmt.setString(2, enrollment.getName());
            pstmt.setString(3, enrollment.getCourse());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Enrollment successful!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error enrolling student: " + ex.getMessage());
        }
        return false;
    }
    
    // READ - Check if student is enrolled in a course
    public boolean isEnrolled(String studentNumber, String courseTitle) {
        String sql = "SELECT COUNT(*) FROM Enrollment WHERE student_number = ? AND course = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            pstmt.setString(2, courseTitle);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error checking enrollment: " + ex.getMessage());
        }
        return false;
    }
    
    // READ - Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT student_number, name, course FROM Enrollment ORDER BY name, course";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                    rs.getString("student_number"),
                    rs.getString("name"),
                    rs.getString("course")
                );
                enrollments.add(enrollment);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving enrollments: " + ex.getMessage());
        }
        return enrollments;
    }
    
    // READ - Get courses for a specific student
    public ArrayList<String> getCoursesForStudent(String studentNumber) {
        ArrayList<String> courses = new ArrayList<>();
        String sql = "SELECT course FROM Enrollment WHERE student_number = ? ORDER BY course";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                courses.add(rs.getString("course"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving courses: " + ex.getMessage());
        }
        return courses;
    }
    
    // READ - Get students enrolled in a specific course
    public ArrayList<String> getStudentsInCourse(String courseTitle) {
        ArrayList<String> students = new ArrayList<>();
        String sql = "SELECT student_number, name FROM Enrollment WHERE course = ? ORDER BY name";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, courseTitle);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String studentInfo = rs.getString("student_number") + " - " + rs.getString("name");
                students.add(studentInfo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving students: " + ex.getMessage());
        }
        return students;
    }
    
    // READ - Get enrollment count for a course
    public int getEnrollmentCount(String courseTitle) {
        String sql = "SELECT COUNT(*) FROM Enrollment WHERE course = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, courseTitle);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error counting enrollments: " + ex.getMessage());
        }
        return 0;
    }
    
    // DELETE - Remove enrollment (drop course)
    public boolean removeEnrollment(String studentNumber, String courseTitle) {
        String sql = "DELETE FROM Enrollment WHERE student_number = ? AND course = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            pstmt.setString(2, courseTitle);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Enrollment removed successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error removing enrollment: " + ex.getMessage());
        }
        return false;
    }
    
    // DELETE - Remove all enrollments for a student
    public boolean removeAllForStudent(String studentNumber) {
        String sql = "DELETE FROM Enrollment WHERE student_number = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "All enrollments removed!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error removing enrollments: " + ex.getMessage());
        }
        return false;
    }
}