package za.ac.cput.studentenrollmentapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.studentenrollmentapp.connection.DBConnection;
import za.ac.cput.studentenrollmentapp.domain.Course;

/**
 *
 * @author abong
 */
public class CourseDAO {
    
    // CREATE - Add new course
    public boolean save(Course course) {
        String sql = "INSERT INTO Course (course_code, title) VALUES (?, ?)";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getTitle());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Course saved successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving course: " + ex.getMessage());
        }
        return false;
    }
    
    // READ - Get course by course code
    public Course getByCourseCode(String courseCode) {
        String sql = "SELECT course_code, title FROM Course WHERE course_code = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Course(
                    rs.getString("course_code"),
                    rs.getString("title")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving course: " + ex.getMessage());
        }
        return null;
    }
    
    // READ - Get course by title
    public Course getByTitle(String title) {
        String sql = "SELECT course_code, title FROM Course WHERE title = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Course(
                    rs.getString("course_code"),
                    rs.getString("title")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving course: " + ex.getMessage());
        }
        return null;
    }
    
    // READ - Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT course_code, title FROM Course ORDER BY course_code";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getString("course_code"),
                    rs.getString("title")
                );
                courses.add(course);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving courses: " + ex.getMessage());
        }
        return courses;
    }
    
    // READ - Get all course titles (for combobox - matches your getAll method)
    public ArrayList<String> getAll() {
        ArrayList<String> titles = new ArrayList<>();
        String sql = "SELECT title FROM Course ORDER BY title";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving course titles: " + ex.getMessage());
        }
        return titles;
    }
    
    // READ - Get course codes
    public ArrayList<String> getAllCourseCodes() {
        ArrayList<String> codes = new ArrayList<>();
        String sql = "SELECT course_code FROM Course ORDER BY course_code";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                codes.add(rs.getString("course_code"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving course codes: " + ex.getMessage());
        }
        return codes;
    }
    
    // UPDATE - Update course title
    public boolean update(String courseCode, String newTitle) {
        String sql = "UPDATE Course SET title = ? WHERE course_code = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, newTitle);
            pstmt.setString(2, courseCode);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Course updated successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating course: " + ex.getMessage());
        }
        return false;
    }
    
    // DELETE - Delete course
    public boolean delete(String courseCode) {
        String sql = "DELETE FROM Course WHERE course_code = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, courseCode);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Course deleted successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting course: " + ex.getMessage());
        }
        return false;
    }
}
