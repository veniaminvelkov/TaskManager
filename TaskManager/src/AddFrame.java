import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddFrame extends JFrame{
	private JTextField titleField, descriptionField, deadlineField, priorityField;
    private JButton addButton;
    private JLabel messageLabel;
    
    private ViewFrame viewFrame; 
    private int user_id; 
    
    Connection conn = null;

    public AddFrame(ViewFrame viewFrame, int user_id) {
    	this.viewFrame = viewFrame;
    	this.user_id = user_id;
    	
        setTitle("Add a task");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(4, 2));
        
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(2,1));

        
        upPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        upPanel.add(titleField);

        upPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        upPanel.add(descriptionField);

        upPanel.add(new JLabel("Deadline:"));
        deadlineField = new JTextField();
        upPanel.add(deadlineField);

        upPanel.add(new JLabel("Priority:"));
        priorityField = new JTextField();
        upPanel.add(priorityField);
        
        int padding = 10;

        upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        addButton = new JButton("Add");
		
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String deadline = deadlineField.getText();
                String priority = priorityField.getText();

                if (title.isEmpty() || description.isEmpty() || deadline.isEmpty() || priority.isEmpty()) {
                    messageLabel.setText("None of the fields can be empty.");
                } else if (!title.matches("[a-zA-Z ]+")) {
                    messageLabel.setText("Titles can only contain letters.");
                } else if (!priority.matches("[0-9]+")) {
                    messageLabel.setText("Priorities can only contain digits.");
                }
                else {
                    try {
                    	conn=DBConnection.getConnection();
            			String sql = "INSERT INTO \"Task\" (title, description, deadline, priority, user_id) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, title);
                        statement.setString(2, description);
                        statement.setString(3, deadline);
                        statement.setString(4, priority);
                        statement.setInt(5, user_id);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                        	viewFrame.refreshTable(); 
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

        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        downPanel.add(messageLabel);
        downPanel.add(addButton);
        
        this.add(upPanel, BorderLayout.PAGE_START);
		this.add(downPanel, BorderLayout.PAGE_END);
    }
}