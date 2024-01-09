import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewFrame extends JFrame {
    private JTable taskTable;
    private int user_id;
    private String username; 

    Connection conn = null;
    PreparedStatement statement;
    ResultSet result;

    public ViewFrame(int user_id, String username) {
        this.user_id = user_id; 
        this.username = username; 

        setTitle("Tasks");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        add(welcomeLabel, BorderLayout.PAGE_START);

        String[] columnNames = {"Title", "Description", "Deadline", "Priority"};

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        taskTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(taskTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();      
        
        JPanel buttonPanel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new AddFrame(ViewFrame.this, user_id).setVisible(true);
            }
        });
        buttonPanel.add(addButton);


        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Long task_id = (Long) taskTable.getModel().getValueAt(selectedRow, 0);
                    new EditFrame(ViewFrame.this, user_id, task_id).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });
        buttonPanel.add(editButton);



        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Long task_id = (Long) taskTable.getModel().getValueAt(selectedRow, 0);
                    try {
                        PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM \"Task\" WHERE task_id = ?");
                        deleteStatement.setLong(1, task_id);
                        deleteStatement.executeUpdate();
                        refreshTable();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });
        buttonPanel.add(deleteButton);


        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame().setVisible(true);
            }
        });
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.PAGE_END);
    }
    
    public void refreshTable() {
		conn=DBConnection.getConnection();
		try {
			statement = conn.prepareStatement("select * from \"Task\" WHERE user_id = ?");
			statement.setInt(1, this.user_id);
			result = statement.executeQuery();
			taskTable.setModel(new MyModel(result));
			
			taskTable.getColumnModel().getColumn(0).setMinWidth(0);
	        taskTable.getColumnModel().getColumn(0).setMaxWidth(0);
	        taskTable.getColumnModel().getColumn(taskTable.getColumnCount()-1).setMinWidth(0);
	        taskTable.getColumnModel().getColumn(taskTable.getColumnCount()-1).setMaxWidth(0);
		}
		catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}