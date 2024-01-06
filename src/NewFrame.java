import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewFrame extends JFrame {

	JPanel customerPanel= new JPanel();
	JPanel menuPanel = new JPanel();
	JPanel orderPanel = new JPanel();
	JPanel searchPanel = new JPanel();

	JTabbedPane tab = new JTabbedPane();

	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	int id = -1;
	
	JPanel upPanelCustomer=new JPanel();
	JPanel midPanelCustomer=new JPanel();
	JPanel downPanelCustomer=new JPanel();
	
	JPanel upPanelMenu=new JPanel();
	JPanel midPanelMenu=new JPanel();
	JPanel downPanelMenu=new JPanel();
	
	JPanel upPanelOrder=new JPanel();
	JPanel downPanelOrder=new JPanel();
	
	JPanel upPanelSearch=new JPanel();
	JPanel downPanelSearch=new JPanel();
	
	//customerPanel labels
	JLabel fnameL=new JLabel("Име:");
	JLabel lnameL=new JLabel("Фамилия:");
	JLabel tableL=new JLabel("Маса:");
	
	//menuPanel labels
	JLabel dishNameL=new JLabel("Име на ястие:");
	JLabel ingredientsL=new JLabel("Съставки:");
	JLabel priceL=new JLabel("Цена:");
	
	//orderPanel labels
	JLabel customerL=new JLabel("Клиент:");
	JLabel dishL=new JLabel("Ястие:");
	JLabel quantityL=new JLabel("Количество:");
	
	//searchPanel labels
	JLabel firstTableL=new JLabel("Първа таблица:");
	JLabel secondTableL=new JLabel("Втора таблица:");
	JLabel firstCriteriaL=new JLabel("Първи критерий вид:");
	JLabel secondCriteriaL=new JLabel("Втори критерий вид:");
	JLabel firstCriteriaTFL=new JLabel("Първи критерий:");
	JLabel secondCriteriaTFL=new JLabel("Втори критерий:");
	
	//customerPanel text fields
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField tableTF=new JTextField();
	
	//menuPanel text fields
	JTextField dishNameTF=new JTextField();
	JTextField ingredientsTF=new JTextField();
	JTextField priceTF=new JTextField();
	
	//orderPanel text fields
	JTextField customerTF=new JTextField();
	JTextField dishTF=new JTextField();
	JTextField quantityTF=new JTextField();
	
	//searchPanel text fields
	JTextField firstCriteriaTF=new JTextField();
	JTextField secondCriteriaTF=new JTextField();
	JTextField firstCriteriaTypeTF=new JTextField();
	JTextField secondCriteriaTypeTF=new JTextField("quantity");
	
	//searchPanel combo boxes
	String[] firstTableComboItems = {"customer","menu"};
	String[] firstCriteriaComboItems = {"numtable", "price"};
	String[] comboOrders = {"quantity"};
		
	JComboBox<String> firstTableCombo=new JComboBox<String>(firstTableComboItems);
	JComboBox<String> secondTableCombo=new JComboBox<String>(new String[]{"orders"});

	//customerPanel buttons
	JButton addBtCustomer=new JButton("Добавяне");
	JButton deleteBtCustomer=new JButton("Изтриване");
	JButton editBtCustomer=new JButton("Редактиране");
	JButton searchBtCustomer=new JButton("Търсене по номер на маса");
	JButton refreshBtCustomer=new JButton("Обнови");
	
	//menuPanel buttons
	JButton addBtMenu=new JButton("Добавяне");
	JButton deleteBtMenu=new JButton("Изтриване");
	JButton editBtMenu=new JButton("Редактиране");
	JButton searchBtMenu=new JButton("Търсене по цена");
	JButton refreshBtMenu=new JButton("Обнови");
	
	//orderPanel buttons
	JButton addBtOrder=new JButton("Добавяне");
	JButton deleteBtOrder=new JButton("Изтриване");
	JButton editBtOrder=new JButton("Редактиране");
	JButton searchBtOrder=new JButton("Търсене по количество");
	JButton refreshBtOrder=new JButton("Обнови");
	
	//searchPanel buttons
	JButton searchBtSearch=new JButton("Търси");
	JButton refreshBtSearch=new JButton("Обнови");
	
	//customerPanel table
	JTable tableCustomer = new JTable();
	JScrollPane myScrollCustomer = new JScrollPane(tableCustomer);
	
	//menuPanel table
	JTable tableMenu = new JTable();
	JScrollPane myScrollMenu = new JScrollPane(tableMenu);
	
	//orderPanel table
	JTable tableOrder = new JTable();
	JTable tableOrderCustomer = new JTable();
	JTable tableOrderMenu = new JTable();
	JScrollPane myScrollOrder = new JScrollPane(tableOrder);
	JScrollPane myScrollOrderCustomer = new JScrollPane(tableOrderCustomer);
	JScrollPane myScrollOrderMenu = new JScrollPane(tableOrderMenu);
	
	//searchPanel table
	JTable tableSearch = new JTable();
	JScrollPane myScrollSearch = new JScrollPane(tableSearch);

	public NewFrame() {
		this.setSize(400,600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		tab.add(customerPanel,"Клиенти");
		tab.add(menuPanel,"Менюта");
		tab.add(orderPanel,"Поръчки");
		tab.add(searchPanel,"Търсене по ...");

		customerPanel.setLayout(new GridLayout(3, 1));
		menuPanel.setLayout(new GridLayout(3, 1));
		orderPanel.setLayout(new GridLayout(2, 1));
		searchPanel.setLayout(new GridLayout(2,1));
		
		this.add(tab);
		
		// customerPanel upPanel --------------------------------------
		upPanelCustomer.setLayout(new GridLayout(3, 2));
		upPanelCustomer.add(fnameL);
		upPanelCustomer.add(fnameTF);
		upPanelCustomer.add(lnameL);
		upPanelCustomer.add(lnameTF);
		upPanelCustomer.add(tableL);
		upPanelCustomer.add(tableTF);
		
		customerPanel.add(upPanelCustomer);
		
		// menuPanel upPanel --------------------------------------
		upPanelMenu.setLayout(new GridLayout(3, 2));
		upPanelMenu.add(dishNameL);
		upPanelMenu.add(dishNameTF);
		upPanelMenu.add(ingredientsL);
		upPanelMenu.add(ingredientsTF);
		upPanelMenu.add(priceL);
		upPanelMenu.add(priceTF);
		
		menuPanel.add(upPanelMenu);
		
		//orderPanel upPanel --------------------------------------
		upPanelOrder.setLayout(new GridLayout(6, 2));
		upPanelOrder.add(customerL);
		upPanelOrder.add(customerTF);
		upPanelOrder.add(dishL);
		upPanelOrder.add(dishTF);
		upPanelOrder.add(quantityL);
		upPanelOrder.add(quantityTF);
		
		upPanelOrder.add(addBtOrder);
		upPanelOrder.add(deleteBtOrder);
		upPanelOrder.add(editBtOrder);
		upPanelOrder.add(searchBtOrder);
		upPanelOrder.add(refreshBtOrder);
				
		orderPanel.add(upPanelOrder);
		
		//searchPanel upPanel --------------------------------------
		upPanelSearch.setLayout(new GridLayout(7, 2));
		
		upPanelSearch.add(firstTableL);
		upPanelSearch.add(firstTableCombo);
		upPanelSearch.add(secondTableL);
		upPanelSearch.add(secondTableCombo);
		upPanelSearch.add(firstCriteriaL);
		upPanelSearch.add(firstCriteriaTypeTF);
		upPanelSearch.add(firstCriteriaTFL);
		upPanelSearch.add(firstCriteriaTF);
		upPanelSearch.add(secondCriteriaL);
		upPanelSearch.add(secondCriteriaTypeTF);
		upPanelSearch.add(secondCriteriaTFL);
		upPanelSearch.add(secondCriteriaTF);
		
		upPanelSearch.add(searchBtSearch);
		upPanelSearch.add(refreshBtSearch);
		
		searchPanel.add(upPanelSearch);

		//customerPanel midPanel-----------------------------------
		midPanelCustomer.add(addBtCustomer);
		midPanelCustomer.add(deleteBtCustomer);
		midPanelCustomer.add(editBtCustomer);

		midPanelCustomer.add(searchBtCustomer);
		midPanelCustomer.add(refreshBtCustomer);

		customerPanel.add(midPanelCustomer);
		
		//menuPanel midPanel---------------------------------------
		midPanelMenu.add(addBtMenu);
		midPanelMenu.add(deleteBtMenu);
		midPanelMenu.add(editBtMenu);

		midPanelMenu.add(searchBtMenu);
		midPanelMenu.add(refreshBtMenu);

		menuPanel.add(midPanelMenu);
		
		//customerPanel downPanel ----------------------------------
		myScrollCustomer.setPreferredSize(new Dimension(350, 150));
		downPanelCustomer.add(myScrollCustomer);
		customerPanel.add(downPanelCustomer);
		
		//menuPanel downPanel --------------------------------------
		myScrollMenu.setPreferredSize(new Dimension(350, 150));
		downPanelMenu.add(myScrollMenu);
		menuPanel.add(downPanelMenu);
		
		//orderPanel downPanel -------------------------------------
		myScrollOrder.setPreferredSize(new Dimension(350, 80));
		myScrollOrderCustomer.setPreferredSize(new Dimension(350, 80));
		myScrollOrderMenu.setPreferredSize(new Dimension(350, 80));
		
		downPanelOrder.add(myScrollOrder);
		downPanelOrder.add(myScrollOrderCustomer);
		downPanelOrder.add(myScrollOrderMenu);
		
		orderPanel.add(downPanelOrder);
		
		//searchPanel downPanel ----------------------------------
		myScrollSearch.setPreferredSize(new Dimension(350, 250));
		downPanelSearch.add(myScrollSearch);
		searchPanel.add(downPanelSearch);

		//Customer listeners
		addBtCustomer.addActionListener(new AddAction());
		deleteBtCustomer.addActionListener(new DeleteAction());
		searchBtCustomer.addActionListener(new SearchAction());
		refreshBtCustomer.addActionListener(new RefreshAction());
		editBtCustomer.addActionListener(new EditAction());
		
		tableCustomer.addMouseListener(new MouseAction());
		
		//Menu listeners
		addBtMenu.addActionListener(new AddAction());
		deleteBtMenu.addActionListener(new DeleteAction());
		searchBtMenu.addActionListener(new SearchAction());
		refreshBtMenu.addActionListener(new RefreshAction());
		editBtMenu.addActionListener(new EditAction());
		
		tableMenu.addMouseListener(new MouseAction());
	
		//Order listeners
		addBtOrder.addActionListener(new AddAction());
		deleteBtOrder.addActionListener(new DeleteAction());
		searchBtOrder.addActionListener(new SearchAction());
		refreshBtOrder.addActionListener(new RefreshAction());
		editBtOrder.addActionListener(new EditAction());
		
		tableOrder.addMouseListener(new MouseAction());
		tableOrderCustomer.addMouseListener(new MouseAction());
		tableOrderMenu.addMouseListener(new MouseAction());
		
		//Search listeners
		searchBtSearch.addActionListener(new SearchAction());
		refreshBtSearch.addActionListener(new RefreshAction());
		
		firstTableCombo.addItemListener(new ItemChangeListener());
		
		tab.addChangeListener(new ChangeAction());
		
		refreshTable();
		
		this.setVisible(true);
	}
	
	public void refreshTable() {
		conn=DBConnection.getConnection();
		try {
			if (tab.getSelectedIndex() == 0) {
				state=conn.prepareStatement("select * from customer");
				result=state.executeQuery();
				tableCustomer.setModel(new MyModel(result));
				tableCustomer.getColumnModel().getColumn(0).setMinWidth(0);
				tableCustomer.getColumnModel().getColumn(0).setMaxWidth(0);
			}
			else if (tab.getSelectedIndex() == 1) {
				state=conn.prepareStatement("select * from menu");
				result=state.executeQuery();
				tableMenu.setModel(new MyModel(result));
				tableMenu.getColumnModel().getColumn(0).setMinWidth(0);
				tableMenu.getColumnModel().getColumn(0).setMaxWidth(0);
			}
			else if (tab.getSelectedIndex() == 2) {
				state=conn.prepareStatement("select * from orders");
				result=state.executeQuery();
				tableOrder.setModel(new MyModel(result));
				tableOrder.getColumnModel().getColumn(0).setMinWidth(0);
				tableOrder.getColumnModel().getColumn(0).setMaxWidth(0);
				
				state=conn.prepareStatement("select * from customer");
				result=state.executeQuery();
				tableOrderCustomer.setModel(new MyModel(result));
				tableOrderCustomer.getColumnModel().getColumn(0).setMinWidth(0);
				tableOrderCustomer.getColumnModel().getColumn(0).setMaxWidth(0);
				
				state=conn.prepareStatement("select * from menu");
				result=state.executeQuery();
				tableOrderMenu.setModel(new MyModel(result));
				tableOrderMenu.getColumnModel().getColumn(0).setMinWidth(0);
				tableOrderMenu.getColumnModel().getColumn(0).setMaxWidth(0);
			}
			else if (tab.getSelectedIndex() == 3) {
				state=conn.prepareStatement("select * from customer c join orders o on c.id = o.custid join menu m on m.id = o.dishid");
				result=state.executeQuery();
				tableSearch.setModel(new MyModel(result));
				tableSearch.getColumnModel().getColumn(0).setMinWidth(0);
				tableSearch.getColumnModel().getColumn(0).setMaxWidth(0);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearForm() {
		if (tab.getSelectedIndex() == 0) {
			fnameTF.setText("");
			lnameTF.setText("");
			tableTF.setText("");
		}
		else if (tab.getSelectedIndex() == 1) {
			dishNameTF.setText("");
			ingredientsTF.setText("");
			priceTF.setText("");
		}
		else if (tab.getSelectedIndex() == 2) {
			customerTF.setText("");
			dishTF.setText("");
			quantityTF.setText("");
		}
	}
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			conn=DBConnection.getConnection();
			if (tab.getSelectedIndex() == 0) {
				String sql="insert into customer(fname, lname, numtable) values(?,?,?)";
				
				try {
					state=conn.prepareStatement(sql);
					state.setString(1, fnameTF.getText());
					state.setString(2, lnameTF.getText());
					state.setInt(3, Integer.parseInt(tableTF.getText()));
					
					state.execute();
					
					refreshTable();
					clearForm();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else if (tab.getSelectedIndex() == 1) {
				String sql="insert into menu(dishname, ingredients, price) values(?,?,?)";
				
				try {
					state=conn.prepareStatement(sql);
					state.setString(1, dishNameTF.getText());
					state.setString(2, ingredientsTF.getText());
					state.setDouble(3, Double.parseDouble(priceTF.getText()));
					
					state.execute();
					
					refreshTable();
					clearForm();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else if (tab.getSelectedIndex() == 2) {
				String sql="insert into orders(custid, dishid, quantity) values(?,?,?)";
				try {
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(customerTF.getText()));
					state.setInt(2, Integer.parseInt(dishTF.getText()));
					state.setInt(3, Integer.parseInt(quantityTF.getText()));
					
					state.execute();
					
					refreshTable();
					clearForm();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	class MouseAction implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (tab.getSelectedIndex() == 0) {
				int row = tableCustomer.getSelectedRow();
				id = Integer.parseInt(tableCustomer.getValueAt(row, 0).toString());
				fnameTF.setText(tableCustomer.getValueAt(row, 1).toString());
				lnameTF.setText(tableCustomer.getValueAt(row, 2).toString());
				tableTF.setText(tableCustomer.getValueAt(row, 3).toString());
			}
			else if (tab.getSelectedIndex() == 1) {
				int row = tableMenu.getSelectedRow();
				id = Integer.parseInt(tableMenu.getValueAt(row, 0).toString());
				dishNameTF.setText(tableMenu.getValueAt(row, 1).toString());
				ingredientsTF.setText(tableMenu.getValueAt(row, 2).toString());
				priceTF.setText(tableMenu.getValueAt(row, 3).toString());
			}
			else if (tab.getSelectedIndex() == 2) {
				if (tableOrder.getSelectedRow() != -1) {
					int row = tableOrder.getSelectedRow();
					id = Integer.parseInt(tableOrder.getValueAt(row, 0).toString());
					customerTF.setText(tableOrder.getValueAt(row, 1).toString());
					dishTF.setText(tableOrder.getValueAt(row, 2).toString());
					quantityTF.setText(tableOrder.getValueAt(row, 3).toString());
				}
				if (tableOrderCustomer.getSelectedRow() != -1) {
					int row = tableOrderCustomer.getSelectedRow();
					customerTF.setText(tableOrderCustomer.getValueAt(row,0).toString());
				}
				if(tableOrderMenu.getSelectedRow() != -1){
					int row = tableOrderMenu.getSelectedRow();
					dishTF.setText(tableOrderMenu.getValueAt(row, 0).toString());
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn=DBConnection.getConnection();
			
			try {
				if (tab.getSelectedIndex() == 0) {
					String sql = "delete from customer where id = ?";
					state = conn.prepareStatement(sql);
				}
				else if (tab.getSelectedIndex() == 1) {
					String sql = "delete from menu where id = ?";
					state = conn.prepareStatement(sql);
				}
				else if (tab.getSelectedIndex() == 2) {
					String sql = "delete from orders where id = ?";
					state = conn.prepareStatement(sql);
				}
				
				state.setInt(1, id);
				state.execute();
				refreshTable();
				clearForm();
				id = -1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class SearchAction implements  ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			conn=DBConnection.getConnection();
			try {
				if (tab.getSelectedIndex() == 0) {
					String sql="select * from customer where numtable =?";
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(tableTF.getText()));
					result=state.executeQuery();
					tableCustomer.setModel(new MyModel(result));
				}
				else if (tab.getSelectedIndex() == 1) {
					String sql="select * from menu where price =?";
					state=conn.prepareStatement(sql);
					state.setDouble(1, Double.parseDouble(priceTF.getText()));
					result=state.executeQuery();
					tableMenu.setModel(new MyModel(result));
				}
				else if (tab.getSelectedIndex() == 2) {
					String sql="select * from orders where custid =?";
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(customerTF.getText()));
					result=state.executeQuery();
					tableOrder.setModel(new MyModel(result));
					
					sql="select * from customer where id =?";
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(customerTF.getText()));
					result=state.executeQuery();
					tableOrderCustomer.setModel(new MyModel(result));
				}
				else if (tab.getSelectedIndex() == 3) {
					String sql;
					
					if (firstTableCombo.getSelectedItem().toString().equals("customer")) {
						sql ="select * from customer join orders on orders.custid = customer.id where numtable = ? and quantity = ?";
						state=conn.prepareStatement(sql);
						state.setInt(1, Integer.parseInt(firstCriteriaTF.getText()));
						state.setInt(2, Integer.parseInt(secondCriteriaTF.getText()));
					}
					else {
						sql ="select * from menu join orders on orders.dishid = menu.id where price = ? and quantity = ?";
						state=conn.prepareStatement(sql);
						state.setDouble(1, Double.parseDouble(firstCriteriaTF.getText()));
						state.setInt(2, Integer.parseInt(secondCriteriaTF.getText()));
					}
					result=state.executeQuery();
					tableSearch.setModel(new MyModel(result));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable();
		}
	}
	
	class EditAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn=DBConnection.getConnection();
			
			try {
				if (tab.getSelectedIndex() == 0) {
					String sql="update customer set fname = ?, lname = ?, numtable = ? where id = ?";
					state=conn.prepareStatement(sql);
					state.setString(1, fnameTF.getText());
					state.setString(2, lnameTF.getText());
					state.setInt(3, Integer.parseInt(tableTF.getText()));
					state.setInt(4, id);
				}
				else if (tab.getSelectedIndex() == 1) {
					String sql="update menu set dishname = ?, ingredients = ?, price = ? where id = ?";
					state=conn.prepareStatement(sql);
					state.setString(1, dishNameTF.getText());
					state.setString(2, ingredientsTF.getText());
					state.setDouble(3, Double.parseDouble(priceTF.getText()));
					state.setInt(4, id);
				}
				else if (tab.getSelectedIndex() == 2) {
					String sql="update orders set custid = ?, dishid = ?, quantity = ? where id = ?";
					state=conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(customerTF.getText()));
					state.setInt(2, Integer.parseInt(dishTF.getText()));
					state.setInt(3, Integer.parseInt(quantityTF.getText()));
					state.setInt(4, id);
				}
				
				state.execute();
				refreshTable();
				clearForm();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			refreshTable();
		}
	}
	class ChangeAction implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			refreshTable();
		}
	}
	class ItemChangeListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Object selected = event.getItem();
				if (selected.equals("customer")) {
					firstCriteriaTypeTF.setText("numtable");
				}
				else {
					firstCriteriaTypeTF.setText("price");
				}
			}
		}   
	}
}
