package uni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;

public class ViewFrame extends JFrame {
    private JTable taskTable;
    private int user_id;
    private String username; 

    Connection conn = null;
    PreparedStatement statement;
    ResultSet result;
    
    String visibleDate = LocalDate.now().toString();

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
                    String title = (String) taskTable.getModel().getValueAt(selectedRow, 1);
                    String description = (String) taskTable.getModel().getValueAt(selectedRow, 2);
                    Date deadline = (Date) taskTable.getModel().getValueAt(selectedRow, 3);
                    Integer priority = (Integer) taskTable.getModel().getValueAt(selectedRow, 4);

                    new EditFrame(ViewFrame.this, user_id, task_id, title, description, deadline, priority).setVisible(true);
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
        
        
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SortFrame(ViewFrame.this, user_id).setVisible(true);
            }
        });
        buttonPanel.add(sortButton);


        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame().setVisible(true);
            }
        });
        buttonPanel.add(logoutButton);
        
        JButton changeDateButton = new JButton("Change Day");
        changeDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CalendarFrame(ViewFrame.this).setVisible(true);
                refreshTable();
            }
        });
        buttonPanel.add(changeDateButton);

        this.add(buttonPanel, BorderLayout.PAGE_END);
    }
    
    public void refreshTable() {
		conn=DBConnection.getConnection();
		try {
			statement = conn.prepareStatement("select * from \"Task\" WHERE user_id = ? AND deadline = ?");
			statement.setInt(1, this.user_id);
			statement.setString(2, visibleDate);
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
    
    public void sortTable(boolean isAscending) {
        String sortBy;
        if(isAscending == true)
        	sortBy = "ASC";
        else
        	sortBy = "DESC";
        conn=DBConnection.getConnection();
        try {
            statement = conn.prepareStatement("SELECT * FROM \"Task\" WHERE user_id = ? ORDER BY priority " + sortBy);
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




