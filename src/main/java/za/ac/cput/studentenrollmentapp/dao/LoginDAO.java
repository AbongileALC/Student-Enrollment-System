package za.ac.cput.studentenrollmentapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import za.ac.cput.studentenrollmentapp.connection.DBConnection;
import za.ac.cput.studentenrollmentapp.domain.Login;

/**
 *
 * @author ATHINI NGQUKE
 */
public class LoginDAO {
    
    // CREATE - Add new login credentials
    public boolean createLogin(Login login) {
        String sql = "INSERT INTO Login (email, password, role) VALUES (?, ?, ?)";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, login.getEmail());
            pstmt.setString(2, login.getPassword());
            pstmt.setString(3, login.getRole());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error creating login: " + ex.getMessage());
            return false;
        }
    }
    
    // READ - Authenticate user
    public boolean authenticateUser(Login login) throws SQLException {
        String sql = "SELECT email, password, role FROM Login WHERE email = ? AND role = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, login.getEmail());
            pstmt.setString(2, login.getRole());
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(login.getPassword());
            }
        }
        return false;
    }
    
    // READ - Check if email exists
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Login WHERE email = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error checking email: " + ex.getMessage());
        }
        return false;
    }
    
    // UPDATE - Update password
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE Login SET password = ? WHERE email = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating password: " + ex.getMessage());
            return false;
        }
    }
    
    // DELETE - Delete login
    public boolean deleteLogin(String email) {
        String sql = "DELETE FROM Login WHERE email = ?";
        
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting login: " + ex.getMessage());
            return false;
        }
    }
}
