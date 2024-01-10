import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditFrame extends JFrame {
    private JTextField titleField, descriptionField, deadlineField, priorityField;
    private JButton editButton;
    private JLabel messageLabel;
    
    private ViewFrame viewFrame; 
    private int user_id; 
    private Long task_id; 
    
    Connection conn = null;

    public EditFrame(ViewFrame viewFrame, int user_id, Long task_id, String title, String description, Date deadline, Integer priority) {
        this.viewFrame = viewFrame;
        this.user_id = user_id;
        this.task_id = task_id;
        
        setTitle("Edit a task");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(4, 2));
        
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(2,1));

        upPanel.add(new JLabel("Title:"));
        titleField = new JTextField(title);
        upPanel.add(titleField);

        upPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField(description);
        upPanel.add(descriptionField);

        upPanel.add(new JLabel("Deadline:"));
        deadlineField = new JTextField(deadline.toString());
        upPanel.add(deadlineField);

        upPanel.add(new JLabel("Priority:"));
        priorityField = new JTextField(priority.toString());
        upPanel.add(priorityField);
        
        int padding = 10;
        upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
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
                        String sql = "UPDATE \"Task\" SET title = ?, description = ?, deadline = ?, priority = ? WHERE task_id = ?";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, title);
                        statement.setString(2, description);
                        statement.setString(3, deadline);
                        statement.setString(4, priority);
                        statement.setLong(5, task_id);

                        int rowsUpdated = statement.executeUpdate();
                        if (rowsUpdated > 0) {
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
        downPanel.add(editButton);
        
        this.add(upPanel, BorderLayout.PAGE_START);
        this.add(downPanel, BorderLayout.PAGE_END);
    }
}