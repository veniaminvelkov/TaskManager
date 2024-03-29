import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, emailField, countryField, cityField, phoneField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel messageLabel;
    
    JPanel downPanel = new JPanel();
    
    Connection conn = null;

    public RegisterFrame() {
        setTitle("Registration");
        setSize(300, 350);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 4));

        int padding = 10;
        
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		downPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Country:"));
        countryField = new JTextField();
        panel.add(countryField);

        panel.add(new JLabel("City:"));
        cityField = new JTextField();
        panel.add(cityField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        
        downPanel.setLayout(new GridLayout(2, 1));
        
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
		downPanel.add(messageLabel);
		
		this.add(downPanel, BorderLayout.PAGE_END);

        registerButton = new JButton("Register");
        downPanel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String((passwordField).getPassword());
                String email = emailField.getText();
                String country = countryField.getText();
                String city = cityField.getText();
                String phone = phoneField.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Username and password cannot be empty.");
                } else if (!country.matches("[a-zA-Z ]+") || !city.matches("[a-zA-Z ]+")) {
                    messageLabel.setText("Countries and cities can only contain letters.");
                } else if (!phone.matches("[0-9]+")) {
                    messageLabel.setText("Phone numbers can only contain digits.");
                } else {
                    try {
                    	conn=DBConnection.getConnection();
            			String sql = "INSERT INTO \"User\" (username, password, email, country, city, phone) VALUES (?, ?, ?, ?, ?, ?)";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, username);
                        statement.setString(2, password);
                        statement.setString(3, email);
                        statement.setString(4, country);
                        statement.setString(5, city);
                        statement.setString(6, phone);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            messageLabel.setText("Registration successful!");
                            dispose();
                        }

                        statement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        add(panel);
    }
}
