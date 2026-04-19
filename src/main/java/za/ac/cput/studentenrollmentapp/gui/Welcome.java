package za.ac.cput.studentenrollmentapp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import za.ac.cput.studentenrollmentapp.dao.LoginDAO;
import za.ac.cput.studentenrollmentapp.domain.Login;

/**
 *
 * @author ATHINI NGQUKE
 */
public class Welcome extends JFrame {
    private JFrame mainFrame;
    
    private JPanel panelTop;
    private JLabel lblTitle;
    
    private JPanel panelNorth;
    private ImageIcon icon;
    private JLabel lblIcon;
    
    private JPanel panelCenter;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    
    private JPanel panelSouth;
    private JButton btnLogin;
    
    private LoginDAO dao;
    
    public Welcome() {
        super("Welcome");
        
        mainFrame = new JFrame();
        panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(mainFrame.getWidth(), 30));
        lblTitle = new JLabel("STUDENT ENROLLMENT SYSTEM - LOGIN");

        panelNorth = new JPanel();
        icon = new ImageIcon();
        lblIcon = new JLabel("");
        
        panelCenter = new JPanel();
        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);
        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);
        
        panelSouth = new JPanel();
        btnLogin = new JButton("LOGIN");
        
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Welcome.this, "Please enter email and password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                LoginDAO dao = new LoginDAO();
                
                // Try Admin login first
                Login adminLogin = new Login(email, password, "Admin");
                boolean isAdmin = dao.authenticateUser(adminLogin);
                
                if (isAdmin) {
                    JOptionPane.showMessageDialog(null, "Admin Login Successful!");
                    new AdminPage().setVisible(true);
                    ((JFrame) SwingUtilities.getWindowAncestor(btnLogin)).dispose();
                    return;
                }
                
                // Try Student login
                Login studentLogin = new Login(email, password, "Student");
                boolean isStudent = dao.authenticateUser(studentLogin);
                
                if (isStudent) {
                    JOptionPane.showMessageDialog(null, "Student Login Successful!");
                    new StudentPage().setVisible(true);
                    ((JFrame) SwingUtilities.getWindowAncestor(btnLogin)).dispose();
                    return;
                }
                
                // If neither worked
                JOptionPane.showMessageDialog(null, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        mainFrame.setLayout(new BorderLayout());
        panelTop.setLayout(new FlowLayout());
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(4, 1, 5, 10));
        panelSouth.setLayout(new GridLayout(1, 1));
        
        panelTop.add(lblTitle);
        panelNorth.add(lblIcon);
        
        panelCenter.add(lblEmail);
        panelCenter.add(txtEmail);
        panelCenter.add(lblPassword);
        panelCenter.add(txtPassword);
        
        panelSouth.add(btnLogin);
        
        mainFrame.add(panelTop, BorderLayout.PAGE_START);
        mainFrame.add(panelNorth, BorderLayout.NORTH);
        mainFrame.add(panelCenter, BorderLayout.CENTER);
        mainFrame.add(panelSouth, BorderLayout.SOUTH);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400, 250);
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Welcome();
    }
}