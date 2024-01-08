import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

import javax.security.auth.login.LoginContext;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	JPanel upPanel = new JPanel();
	
	JPanel downPanel = new JPanel();

	JLabel welcomeL = new JLabel("Welcome sample text");
	
	JButton registerBt = new JButton("Register");
	JButton loginBt = new JButton("Log In");
	
	JFrame mainFrame;

	public MainFrame() {
		
		this.setTitle("Welcome Page");
		this.setSize(300, 350);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		int padding = 10;

		upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		downPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

		upPanel.add(welcomeL);

		downPanel.setLayout(new GridLayout(1, 2));

		downPanel.add(registerBt);
		downPanel.add(loginBt);

		this.add(upPanel, BorderLayout.PAGE_START);
		this.add(downPanel, BorderLayout.PAGE_END);
		
		mainFrame = this;

		registerBt.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				
				RegisterFrame registerFrame = new RegisterFrame();				
				registerFrame.setVisible(true);
				
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
			
		});
		
		loginBt.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				LoginFrame loginFrame = new LoginFrame(mainFrame);				
				loginFrame.setVisible(true);
				
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

		this.setVisible(true);
	}
}
