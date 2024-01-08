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

        model.addRow(new Object[]{"", "", "", ""});

        JScrollPane scrollPane = new JScrollPane(taskTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();        
        JPanel buttonPanel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    String title = (String) model.getValueAt(selectedRow, 0);
                    String description = (String) model.getValueAt(selectedRow, 1);
                    String deadline = (String) model.getValueAt(selectedRow, 2);
                    int priority = Integer.parseInt((String) model.getValueAt(selectedRow, 3));

                    try {
                        conn = DBConnection.getConnection();
                        String sql = "INSERT INTO \"Task\" (title, description, deadline, priority, user_id) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, title);
                        statement.setString(2, description);
                        statement.setString(3, deadline);
                        statement.setInt(4, priority);
                        statement.setInt(5, user_id);

                        statement.execute();
                        
                        model.addRow(new Object[]{"", "", "", ""});
                        
                        statement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int task_id = (int) model.getValueAt(selectedRow, 0);
                    String title = (String) model.getValueAt(selectedRow, 1);
                    String description = (String) model.getValueAt(selectedRow, 2);
                    Date deadline = (Date) model.getValueAt(selectedRow, 3);
                    int priority = (int) model.getValueAt(selectedRow, 4);

                    try {
                        conn = DBConnection.getConnection();
                        String sql = "UPDATE Tasks SET title = ?, description = ?, deadline = ?, priority = ? WHERE user_id = ? AND task_id = ?";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, title);
                        statement.setString(2, description);
                        statement.setDate(3, deadline);
                        statement.setInt(4, priority);
                        statement.setInt(5, user_id);
                        statement.setInt(6, task_id);

                        statement.executeUpdate();

                        statement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int task_id = (int) model.getValueAt(selectedRow, 0);

                    try {
                        conn = DBConnection.getConnection();
                        String sql = "DELETE FROM Tasks WHERE user_id = ? AND task_id = ?";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setInt(1, user_id);
                        statement.setInt(2, task_id);

                        statement.executeUpdate();
                        model.removeRow(selectedRow);

                        statement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
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
			statement = conn.prepareStatement("select * from \"Task\"");
			result = statement.executeQuery();
			taskTable.setModel(new MyModel(result));
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
