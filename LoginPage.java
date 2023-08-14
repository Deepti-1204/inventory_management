package loginpage;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class LoginPage extends JFrame {
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public LoginPage() {
        initComponents();
        setTitle("Login To Inventory DB");
        setSize(390, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userField = new JTextField();
        passwordField = new JPasswordField();
        submitButton = new JButton("Login!");
        
        Font font = new Font("Rockwell", Font.BOLD, 16);


        userLabel.setBounds(40, 30, 150, 25);
        userLabel.setFont(font);
        userLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(40, 60, 150, 25);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(font);
        userField.setBounds(200, 30, 160, 25);
        userField.setFont(font);
        passwordField.setBounds(200, 60, 160, 25);
        passwordField.setFont(font);
        submitButton.setBounds(130, 100, 150, 25);
        submitButton.setFont(font);
        
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\vilan\\OneDrive\\Desktop\\wall1.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 600);
        
        // Add the background label to the content pane of the frame
        

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("system")) {
                    Tools tools = new Tools();
                    tools.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLayout(null);
        add(userLabel);
        add(passwordLabel);
        add(userField);
        add(passwordField);
        add(submitButton);
        add(backgroundLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            }
        });
    }
}

