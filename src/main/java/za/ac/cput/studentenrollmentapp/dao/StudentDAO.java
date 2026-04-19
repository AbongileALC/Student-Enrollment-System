package za.ac.cput.studentenrollmentapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.studentenrollmentapp.connection.DBConnection;
import za.ac.cput.studentenrollmentapp.domain.Student;

/**
 *
 * @author ATHINI NGQUKE
 */
public class StudentDAO {
    
    // CREATE - Add new student (creates Login record too)
    public boolean save(Student student) {
        Connection con = null;
        PreparedStatement pstmtLogin = null;
        PreparedStatement pstmtStudent = null;
        
        try {
            con = DBConnection.derbyConnection();
            con.setAutoCommit(false);
            
            // Create login record
            String sqlLogin = "INSERT INTO Login (email, password, role) VALUES (?, ?, ?)";
            pstmtLogin = con.prepareStatement(sqlLogin);
            pstmtLogin.setString(1, student.getStudentEmail());
            pstmtLogin.setString(2, student.getPassword());
            pstmtLogin.setString(3, "Student");
            pstmtLogin.executeUpdate();
            
            // Create student record
            String sqlStudent = "INSERT INTO Student (student_number, student_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            pstmtStudent = con.prepareStatement(sqlStudent);
            pstmtStudent.setString(1, student.getStudentNumber());
            pstmtStudent.setString(2, student.getName());
            pstmtStudent.setString(3, student.getStudentEmail());
            pstmtStudent.setString(4, student.getPassword());
            pstmtStudent.setString(5, "Student");
            pstmtStudent.executeUpdate();
            
            con.commit();
            JOptionPane.showMessageDialog(null, "Student saved successfully!");
            return true;
            
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Error saving student: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (pstmtLogin != null) pstmtLogin.close();
                if (pstmtStudent != null) pstmtStudent.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // READ - Get student by student number
    public Student getByStudentNumber(String studentNumber) {
        String sql = "SELECT student_number, student_name, email, password, role FROM Student WHERE student_number = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("student_number"),
                    rs.getString("student_name")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving student: " + ex.getMessage());
        }
        return null;
    }
    
    // READ - Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_number, student_name, email, password, role FROM Student ORDER BY student_name";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("student_number"),
                    rs.getString("student_name")
                );
                students.add(student);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving students: " + ex.getMessage());
        }
        return students;
    }
    
    // READ - Get all student names (for combobox)
    public ArrayList<String> getName() {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT student_name FROM Student ORDER BY student_name";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                names.add(rs.getString("student_name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving names: " + ex.getMessage());
        }
        return names;
    }
    
    // READ - Get all student numbers (for combobox)
    public ArrayList<String> getNumber() {
        ArrayList<String> numbers = new ArrayList<>();
        String sql = "SELECT student_number FROM Student ORDER BY student_number";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                numbers.add(rs.getString("student_number"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving numbers: " + ex.getMessage());
        }
        return numbers;
    }
    
    // UPDATE - Update student information
    public boolean update(String studentNumber, Student student) {
        String sql = "UPDATE Student SET student_name = ?, email = ?, password = ? WHERE student_number = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getStudentEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, studentNumber);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Student updated successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating student: " + ex.getMessage());
        }
        return false;
    }
    
    // DELETE - Delete student
    public boolean delete(String studentNumber) {
        String sql = "DELETE FROM Student WHERE student_number = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, studentNumber);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Student deleted successfully!");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting student: " + ex.getMessage());
        }
        return false;
    }
}