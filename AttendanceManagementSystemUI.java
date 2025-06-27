import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AttendanceManagementSystemUI {

    // Store users and their credentials
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, Boolean> attendance = new HashMap<>();

    public static void main(String[] args) {
        // Default users (username: password)
        users.put("admin", "admin123");
        users.put("faculty", "faculty123");
        users.put("student", "student123");

        // Create home page
        createHomePage();
    }

    private static void createHomePage() {
        JFrame homeFrame = new JFrame("Attendance Management System");
        homeFrame.setSize(600, 500);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("Attendance Management System", JLabel.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        // Center Panel for Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton facultyButton = new JButton("Faculty Login");
        JButton adminButton = new JButton("Admin Login");
        JButton studentButton = new JButton("Student Login");
        JButton registerButton = new JButton("Register New User");

        // Set button styles
        styleButton(facultyButton, new Color(173, 216, 230));
        styleButton(adminButton, new Color(144, 238, 144));
        styleButton(studentButton, new Color(255, 182, 193));
        styleButton(registerButton, new Color(240, 230, 140));

        centerPanel.add(facultyButton);
        centerPanel.add(adminButton);
        centerPanel.add(studentButton);
        centerPanel.add(registerButton);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(192, 192, 192));
        JLabel footerLabel = new JLabel("© 2024 Attendance Management System", JLabel.CENTER);
        footerPanel.add(footerLabel);

        homeFrame.add(headerPanel, BorderLayout.NORTH);
        homeFrame.add(centerPanel, BorderLayout.CENTER);
        homeFrame.add(footerPanel, BorderLayout.SOUTH);

        // Button Actions
        facultyButton.addActionListener(e -> showLogin(homeFrame, "Faculty"));
        adminButton.addActionListener(e -> showLogin(homeFrame, "Admin"));
        studentButton.addActionListener(e -> showLogin(homeFrame, "Student"));
        registerButton.addActionListener(e -> showRegister(homeFrame));

        homeFrame.setVisible(true);
    }

    // Universal method for applying styles to buttons
    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    private static void showLogin(JFrame homeFrame, String userType) {
        homeFrame.setVisible(false);

        JFrame loginFrame = new JFrame(userType + " Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel(userType + " Login", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back to Home");

        styleButton(loginButton, new Color(173, 216, 230));
        styleButton(backButton, new Color(255, 228, 181));

        centerPanel.add(userLabel);
        centerPanel.add(userField);
        centerPanel.add(passLabel);
        centerPanel.add(passField);
        centerPanel.add(loginButton);
        centerPanel.add(backButton);

        loginFrame.add(headerPanel, BorderLayout.NORTH);
        loginFrame.add(centerPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                if (userType.equals("Faculty")) {
                    showFacultyDashboard(loginFrame);
                } else if (userType.equals("Admin")) {
                    showAdminDashboard(loginFrame);
                } else {
                    showStudentDashboard(loginFrame, username);
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            loginFrame.dispose();
            homeFrame.setVisible(true);
        });

        loginFrame.setVisible(true);
    }

    private static void showRegister(JFrame homeFrame) {
        homeFrame.setVisible(false);

        JFrame registerFrame = new JFrame("Register New User");
        registerFrame.setSize(400, 300);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel userLabel = new JLabel("New Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Home");

        styleButton(registerButton, new Color(240, 230, 140));
        styleButton(backButton, new Color(255, 228, 181));

        registerFrame.add(userLabel);
        registerFrame.add(userField);
        registerFrame.add(passLabel);
        registerFrame.add(passField);
        registerFrame.add(confirmPassLabel);
        registerFrame.add(confirmPassField);
        registerFrame.add(registerButton);
        registerFrame.add(backButton);

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(registerFrame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.put(username, password);
                JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                registerFrame.dispose();
                homeFrame.setVisible(true);
            }
        });

        backButton.addActionListener(e -> {
            registerFrame.dispose();
            homeFrame.setVisible(true);
        });

        registerFrame.setVisible(true);
    }

    private static void showFacultyDashboard(JFrame loginFrame) {
        loginFrame.dispose();

        JFrame facultyFrame = new JFrame("Faculty Dashboard");
        facultyFrame.setSize(500, 500);
        facultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        facultyFrame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        JPanel inputPanel = new JPanel();
        JLabel addLabel = new JLabel("Add Student:");
        JTextField addField = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton backButton = new JButton("Back to Home");

        styleButton(addButton, new Color(144, 238, 144));
        styleButton(backButton, new Color(255, 228, 181));

        inputPanel.add(addLabel);
        inputPanel.add(addField);
        inputPanel.add(addButton);
        inputPanel.add(backButton);

        addButton.addActionListener(e -> {
            String name = addField.getText().trim();
            if (!name.isEmpty() && !attendance.containsKey(name)) {
                attendance.put(name, false);
                updateAttendanceList(centerPanel);
                facultyFrame.revalidate();
            }
        });

        backButton.addActionListener(e -> {
            facultyFrame.dispose();
            main(null); // Go back to the homepage
        });

        updateAttendanceList(centerPanel);

        facultyFrame.add(inputPanel, BorderLayout.NORTH);
        facultyFrame.add(scrollPane, BorderLayout.CENTER);

        facultyFrame.setVisible(true);
    }

    private static void updateAttendanceList(JPanel panel) {
        panel.removeAll();
        for (String student : attendance.keySet()) {
            JCheckBox checkBox = new JCheckBox(student);
            checkBox.setSelected(attendance.get(student));
            checkBox.addActionListener(e -> attendance.put(student, checkBox.isSelected()));
            panel.add(checkBox);
        }
    }

    private static void showAdminDashboard(JFrame loginFrame) {
        loginFrame.dispose();

        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(500, 500);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLayout(new BorderLayout());

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(reportPanel);

        for (Map.Entry<String, Boolean> entry : attendance.entrySet()) {
            JLabel studentStatus = new JLabel(entry.getKey() + ": " + (entry.getValue() ? "Present" : "Absent"));
            reportPanel.add(studentStatus);
        }

        JButton backButton = new JButton("Back to Home");
        styleButton(backButton, new Color(255, 228, 181));

        backButton.addActionListener(e -> {
            adminFrame.dispose();
            main(null); // Go back to the homepage
        });

        adminFrame.add(scrollPane, BorderLayout.CENTER);
        adminFrame.add(backButton, BorderLayout.SOUTH);

        adminFrame.setVisible(true);
    }

    private static void showStudentDashboard(JFrame loginFrame, String username) {
        loginFrame.dispose();

        JFrame studentFrame = new JFrame("Student Dashboard");
        studentFrame.setSize(400, 300);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JLabel statusLabel = new JLabel("Your Attendance Status:");
        JLabel attendanceStatus = new JLabel();

        if (attendance.containsKey(username)) {
            attendanceStatus.setText(username + ": " + (attendance.get(username) ? "Present" : "Absent"));
        } else {
            attendanceStatus.setText("No record found!");
        }

        JButton backButton = new JButton("Back to Home");
        styleButton(backButton, new Color(255, 228, 181));

        backButton.addActionListener(e -> {
            studentFrame.dispose();
            main(null); // Go back to the homepage
        });

        centerPanel.add(statusLabel);
        centerPanel.add(attendanceStatus);

        studentFrame.add(centerPanel, BorderLayout.CENTER);
        studentFrame.add(backButton, BorderLayout.SOUTH);

        studentFrame.setVisible(true);
    }
}
