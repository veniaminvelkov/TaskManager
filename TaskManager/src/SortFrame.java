import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortFrame extends JFrame {
    private JButton ascButton, descButton;
    private JLabel messageLabel;
    
    private ViewFrame viewFrame; 
    private int user_id; 
    
    public SortFrame(ViewFrame viewFrame, int user_id) {
        this.viewFrame = viewFrame;
        this.user_id = user_id;
        
        setTitle("Sort tasks by priority");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));

        upPanel.add(new JLabel("How would you like to sort the priority?"));
        upPanel.add(Box.createVerticalStrut(30));
        
        ascButton = new JButton("By ascending");
        upPanel.add(ascButton);

        descButton = new JButton("By descending");
        upPanel.add(descButton);
        
        int padding = 10;
        upPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        ascButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFrame.sortTable(true); 
                dispose();
            }
        });

        descButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFrame.sortTable(false); 
                dispose();
            }
        });

        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        
        this.add(upPanel, BorderLayout.PAGE_START);
        this.add(messageLabel, BorderLayout.PAGE_END);
    }
}