import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CalendarFrame extends JFrame{
	
	public CalendarFrame(ViewFrame openedFrom) 
	{
		setTitle("Registration");
	    setSize(300, 350);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	    JPanel upPanel = new JPanel();
	    upPanel.setLayout(new GridLayout(3, 2));
	    
	    JPanel downPanel = new JPanel();
	    downPanel.setLayout(new GridLayout(2, 1));
	    	    
	    SpinnerNumberModel yearModel = new SpinnerNumberModel(2024, 2024, 2100, 1);
	    JSpinner yearSpinner = new JSpinner(yearModel);
	    JLabel yearLabel = new JLabel("Year:");
	    upPanel.add(yearLabel);
	    upPanel.add(yearSpinner);
	    
	    SpinnerNumberModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
	    JSpinner monthSpinner = new JSpinner(monthModel);
	    JLabel monthLabel = new JLabel("Month:");
	    upPanel.add(monthLabel);
	    upPanel.add(monthSpinner);
	    
	    SpinnerNumberModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
	    JSpinner daySpinner = new JSpinner(dayModel);
	    JLabel dayLabel = new JLabel("Day:");
	    upPanel.add(dayLabel);
	    upPanel.add(daySpinner);
	    
	    int padding = 10;

        upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        downPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        
        JButton changeDateButton = new JButton("Confirm");
        changeDateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String year =  yearSpinner.getValue().toString();
				String month = monthSpinner.getValue().toString();
				String day =  daySpinner.getValue().toString();
				
				if ( (Integer) monthSpinner.getValue() < 10) {
					month = "0" + month;
				}
				else if ( (Integer) daySpinner.getValue() < 10) {
					day = "0" + day;
				}
				
				openedFrom.visibleDate = year + '-' + month + '-' + day;
				openedFrom.refreshTable();
				dispose();
			}
		});
        
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        downPanel.add(messageLabel);
        downPanel.add(changeDateButton);
        
        this.add(upPanel, BorderLayout.PAGE_START);
		this.add(downPanel, BorderLayout.PAGE_END);
	}
}
