import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main implements ActionListener{
	
	
	private JFrame frame;
	private JPanel panel;
	private JButton button;
	private JLabel label;
	private JComboBox comboBox;
	private Color backgroundColor = new Color(197,255,220);
	
	
	
	
	
//This class provides the main menu where the user can select between the customer or the manager perspective. 
	
	public static void main(String[] args) {
		
		new Main();
		
		
	}
	
	
	
    Main() {
		
		
		frame = new JFrame("Car Rental System");
		panel = new JPanel();
		label = new JLabel("Please select the perspective you wish to open: ");
		button = new JButton();
		comboBox = new JComboBox();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new FlowLayout()); 
		frame.getContentPane().setBackground(backgroundColor);
		
		String[] userOptions = {"Manager Perspective", "Customer Perspective"};
		comboBox = new JComboBox(userOptions);
		
		button = new JButton("OK");
		button.setBounds(400,200,100,50);
		button.addActionListener(this);
		
		panel.setBackground(backgroundColor);
		panel.add(label);
		panel.add(comboBox);
		panel.add(button);
		
		
		frame.add(panel);
		frame.setVisible(true);
		
	    
		
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
		
		String userChoice = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		
		if(userChoice == "Manager Perspective") {
			new ManagerPerspective();
		}
		else {
			new CustomerPerspective();
		}
		
	}
}
