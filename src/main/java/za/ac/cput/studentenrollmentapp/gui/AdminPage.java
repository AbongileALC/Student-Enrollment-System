package za.ac.cput.studentenrollmentapp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import za.ac.cput.studentenrollmentapp.dao.CourseDAO;
import za.ac.cput.studentenrollmentapp.dao.StudentDAO;
import za.ac.cput.studentenrollmentapp.domain.Course;
import za.ac.cput.studentenrollmentapp.domain.Student;

/**
 *
 * @author ATHINI NGQUKE
 */
public class AdminPage extends JFrame {
    private JFrame mainFrame;
    private JPanel panelTop;
    private JLabel lblTitle;
    
    // Course Tab Components
    private JPanel panelCourse;
    private JLabel lblCourseCode;
    private JTextField txtCourseCode;
    private JLabel lblCourseTitle;
    private JTextField txtCourseTitle;
    private JButton btnAddCourse;
    
    // Student Tab Components
    private JPanel panelStudent;
    private JLabel lblStudentNumber;
    private JTextField txtStudentNumber;
    private JLabel lblStudentName;
    private JTextField txtStudentName;
    private JLabel lblStudentEmail;
    private JTextField txtStudentEmail;
    private JLabel lblStudentPassword;
    private JPasswordField txtStudentPassword;
    private JButton btnAddStudent;
    
    private JPanel panelBottom;
    private JButton btnLogout;
    
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    
    public AdminPage() {
        super("ADMIN PAGE");
        
        mainFrame = new JFrame("ADMIN PAGE");
        panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(mainFrame.getWidth(), 40));
        lblTitle = new JLabel("ADMIN DASHBOARD");
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // ===== COURSE TAB =====
        panelCourse = new JPanel();
        panelCourse.setLayout(new GridLayout(5, 1, 10, 10));
        
        lblCourseCode = new JLabel("Course Code:");
        txtCourseCode = new JTextField(20);
        lblCourseTitle = new JLabel("Course Title:");
        txtCourseTitle = new JTextField(20);
        btnAddCourse = new JButton("ADD COURSE");
        
        panelCourse.add(lblCourseCode);
        panelCourse.add(txtCourseCode);
        panelCourse.add(lblCourseTitle);
        panelCourse.add(txtCourseTitle);
        panelCourse.add(btnAddCourse);
        
        btnAddCourse.addActionListener(e -> {
            String code = txtCourseCode.getText().trim();
            String title = txtCourseTitle.getText().trim();
            
            if (code.isEmpty() || title.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Course course = new Course(code, title);
                CourseDAO dao = new CourseDAO();
                boolean success = dao.save(course);
                
                if (success) {
                    txtCourseCode.setText("");
                    txtCourseTitle.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error saving course: " + ex.getMessage());
            }
        });
        
        // ===== STUDENT TAB =====
        panelStudent = new JPanel();
        panelStudent.setLayout(new GridLayout(9, 1, 10, 10));
        
        lblStudentNumber = new JLabel("Student Number:");
        txtStudentNumber = new JTextField(20);
        lblStudentName = new JLabel("Student Name:");
        txtStudentName = new JTextField(20);
        lblStudentEmail = new JLabel("Student Email:");
        txtStudentEmail = new JTextField(20);
        lblStudentPassword = new JLabel("Password:");
        txtStudentPassword = new JPasswordField(20);
        btnAddStudent = new JButton("ADD STUDENT");
        
        panelStudent.add(lblStudentNumber);
        panelStudent.add(txtStudentNumber);
        panelStudent.add(lblStudentName);
        panelStudent.add(txtStudentName);
        panelStudent.add(lblStudentEmail);
        panelStudent.add(txtStudentEmail);
        panelStudent.add(lblStudentPassword);
        panelStudent.add(txtStudentPassword);
        panelStudent.add(btnAddStudent);
        
        btnAddStudent.addActionListener(e -> {
            String number = txtStudentNumber.getText().trim();
            String name = txtStudentName.getText().trim();
            String email = txtStudentEmail.getText().trim();
            String password = new String(txtStudentPassword.getPassword()).trim();
            
            if (number.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Student student = new Student(email, password, "Student", number, name);
                StudentDAO dao = new StudentDAO();
                boolean success = dao.save(student);
                
                if (success) {
                    txtStudentNumber.setText("");
                    txtStudentName.setText("");
                    txtStudentEmail.setText("");
                    txtStudentPassword.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error saving student: " + ex.getMessage());
            }
        });
        
        // Add tabs
        tabbedPane.addTab("Add Course", panelCourse);
        tabbedPane.addTab("Add Student", panelStudent);
        
        // Bottom panel with logout
        panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 1));
        btnLogout = new JButton("LOGOUT");
        
        btnLogout.addActionListener(e -> {
            mainFrame.dispose();
            new Welcome();
        });
        
        panelBottom.add(btnLogout);
        
        mainFrame.setLayout(new BorderLayout());
        panelTop.setLayout(new FlowLayout());
        
        panelTop.add(lblTitle);
        
        mainFrame.add(panelTop, BorderLayout.NORTH);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
        mainFrame.add(panelBottom, BorderLayout.SOUTH);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(450, 400);
        mainFrame.setVisible(true);
    }
}