import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JTextField usernameF = new JTextField();
	JTextField passwordF = new JPasswordField();	

	JLabel usernameL = new JLabel("Enter Username:");
	JLabel passwordL = new JLabel("Enter Password:");
	private JLabel messageL;
	
	JButton loginBt = new JButton("Log In");
	
	
	
	public LoginFrame(JFrame mainFrame) {
		this.setTitle("Login Page");
		this.setSize(300, 350);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		int padding = 10;

		upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		downPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		
		upPanel.setLayout(new BorderLayout());
		
		upPanel.setLayout(new GridLayout(2, 2));
        
        upPanel.add(usernameL);
        upPanel.add(usernameF);
        upPanel.add(passwordL);
        upPanel.add(passwordF);
        
        downPanel.setLayout(new GridLayout(2, 1));
        
        messageL = new JLabel();
		messageL.setForeground(Color.RED);
		downPanel.add(messageL);
        
        downPanel.add(loginBt);
        
        this.add(upPanel, BorderLayout.PAGE_START);
		this.add(downPanel, BorderLayout.PAGE_END);
		
		loginBt.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				conn=DBConnection.getConnection();
				
				String username = usernameF.getText();
                String password = new String(((JPasswordField) passwordF).getPassword());
				
				String sql = "SELECT * from \"User\" where USERNAME = ?";
				try {
					state = conn.prepareStatement(sql);
					state.setString(1, username);
					result = state.executeQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					if (!result.first()) {
						messageL.setText("No user exists with those credentials.");
					}
					else {
						
						String smt = result.getString("USERNAME");
						String smtpass = result.getString("PASSWORD");
						
						if (password.equals(smtpass)) {
							int user_id = result.getInt("USER_ID");
							
							//TODO: Login finishes, open main task window. The message is for testing purposes.
							messageL.setText("Login success!");
							new ViewFrame(user_id, username).setVisible(true);
							
							state.close();
							conn.close();
							dispose();
							if (mainFrame != null) {								
								mainFrame.dispose();
							}
						}
						else {
							messageL.setText("Wrong password.");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        setVisible(true);
	}
}
