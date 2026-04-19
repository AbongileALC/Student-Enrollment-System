package za.ac.cput.studentenrollmentapp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import za.ac.cput.studentenrollmentapp.dao.CourseDAO;
import za.ac.cput.studentenrollmentapp.dao.EnrollmentDAO;
import za.ac.cput.studentenrollmentapp.dao.StudentDAO;
import za.ac.cput.studentenrollmentapp.domain.Enrollment;
import za.ac.cput.studentenrollmentapp.domain.Student;

/**
 *
 * @author ATHINI NGQUKE
 */
public class StudentPage extends JFrame {
    private JFrame mainFrame;
    private JPanel panelTop;
    private JLabel lblTitle;
    
    private JPanel panelCenter;
    private JLabel lblStudentNo;
    private JTextField txtStudentNo;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblCourses;
    private JComboBox<String> box;
    private JButton btnLoadStudent;
    
    private JPanel panelSouth;
    private JButton btnEnroll;
    private JButton btnLogout;
    
    private EnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    
    public StudentPage() {
        super("STUDENT PAGE");
        
        mainFrame = new JFrame();
        panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(mainFrame.getWidth(), 30));
        lblTitle = new JLabel("STUDENT PAGE - COURSE ENROLLMENT");
        
        panelCenter = new JPanel();
        
        lblStudentNo = new JLabel("Student number:");
        txtStudentNo = new JTextField(20);
        
        lblName = new JLabel("Name:");
        txtName = new JTextField(20);
        txtName.setEditable(false); // Name auto-fills from database
        
        btnLoadStudent = new JButton("LOAD MY INFO");
        
        lblCourses = new JLabel("Select Course:");
        box = new JComboBox<>();
        
        panelSouth = new JPanel();
        btnEnroll = new JButton("ENROLL");
        btnLogout = new JButton("LOGOUT");
        
        // Load courses into combobox
        courseDAO = new CourseDAO();
        studentDAO = new StudentDAO();
        ArrayList<String> courseList = courseDAO.getAll();
        
        if (courseList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No courses available. Please contact admin.");
        } else {
            for (String course : courseList) {
                box.addItem(course);
            }
        }
        
        // Load student info button
        btnLoadStudent.addActionListener(e -> {
            String studentNumber = txtStudentNo.getText().trim();
            
            if (studentNumber.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter your student number!");
                return;
            }
            
            try {
                Student student = studentDAO.getByStudentNumber(studentNumber);
                
                if (student != null) {
                    txtName.setText(student.getName());
                    JOptionPane.showMessageDialog(mainFrame, "Student info loaded successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Student number not found! Please contact admin to create your account.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    txtName.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error loading student: " + ex.getMessage());
            }
        });
        
        btnEnroll.addActionListener(e -> {
            String number = txtStudentNo.getText().trim();
            String name = txtName.getText().trim();
            String course = (String) box.getSelectedItem();
            
            if (number.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(StudentPage.this, 
                    "Please load your student information first!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (course == null || course.isEmpty()) {
                JOptionPane.showMessageDialog(StudentPage.this, 
                    "Please select a course!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Verify student exists first
                Student student = studentDAO.getByStudentNumber(number);
                
                if (student == null) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Invalid student number! Please contact admin.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Enrollment enrollment = new Enrollment(number, name, course);
                EnrollmentDAO dao = new EnrollmentDAO();
                boolean success = dao.insertRecord(enrollment);
                
                if (success) {
                    // Don't clear the fields, student might want to enroll in another course
                    JOptionPane.showMessageDialog(mainFrame, "Successfully enrolled in: " + course);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error enrolling student: " + ex.getMessage());
            }
        });
        
        btnLogout.addActionListener(e -> {
            mainFrame.dispose();
            new Welcome();
        });
        
        mainFrame.setLayout(new BorderLayout());
        panelTop.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(7, 1, 5, 10));
        panelSouth.setLayout(new GridLayout(1, 2, 10, 5));
        
        panelTop.add(lblTitle);
        
        panelCenter.add(lblStudentNo);
        panelCenter.add(txtStudentNo);
        panelCenter.add(btnLoadStudent);
        panelCenter.add(lblName);
        panelCenter.add(txtName);
        panelCenter.add(lblCourses);
        panelCenter.add(box);
       
        panelSouth.add(btnEnroll);
        panelSouth.add(btnLogout);
       
        mainFrame.add(panelTop, BorderLayout.NORTH);
        mainFrame.add(panelCenter, BorderLayout.CENTER);
        mainFrame.add(panelSouth, BorderLayout.SOUTH);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400, 350);
        mainFrame.setVisible(true);
    }
}