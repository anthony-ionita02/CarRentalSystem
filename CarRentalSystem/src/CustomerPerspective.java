import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;




/*This class sets the GUI labels, textfeilds, and buttons as need for the customer perspective. It calls the DatabaseManager methods to 
to rent cars (add them to the RentedCars table and remove them from the Inventory table), or to return them (add them to the Inventory table
and remove them from the RentedCars table). 
*/



public class CustomerPerspective implements ActionListener{
	
	
	private JFrame frame;
	private JPanel panelOne;
	private JPanel panelTwo;
	private JTextField textFieldvehicleID;
	private JLabel primaryLabel;
	private JLabel resultLabel;
	private JLabel vehicleIDLabel;
	private JButton buttonPanelOne;
	private JButton buttonPanelTwo;
	private JComboBox comboBox;
	private JTextArea textArea;
	private static int userCommand = 0;
	private Color backgroundColor = new Color(197,255,220);
	
	
	private HashSet<Integer> rentedCars = new HashSet<>();
	private HashSet<Integer> carInventory = new HashSet<>();
	
	
	
	
	CustomerPerspective(){
		
		
		DatabaseManager setBuilder = new DatabaseManager();
		rentedCars = setBuilder.buildHashSet(0);
		carInventory = setBuilder.buildHashSet(1);
		
		frame = new JFrame("Car Rental System");
		frame.getContentPane().setBackground(backgroundColor);
		panelOne = new JPanel();
		panelOne.setBackground(backgroundColor);
		panelTwo = new JPanel();
		panelTwo.setBackground(backgroundColor);
		primaryLabel = new JLabel();
		resultLabel = new JLabel();
		vehicleIDLabel = new JLabel("Vehicle ID: ");
		vehicleIDLabel.setSize(70,30);
		textFieldvehicleID = new JTextField();
		textArea = new JTextArea();
		textArea.setBackground(backgroundColor);
		
		String[] optionList = {"Check Inventory", "Rent Car", "Return Car"};
		comboBox = new JComboBox(optionList);
		
		
		primaryLabel.setHorizontalAlignment(JLabel.LEFT);
		primaryLabel.setSize(400, 100);
		primaryLabel.setText("Select the action you wish to perform from the drop-down menu: ");
		resultLabel.setHorizontalAlignment(JLabel.RIGHT);
		resultLabel.setSize(425, 100);
		resultLabel.setVisible(false);
		
		
		
		buttonPanelOne = new JButton("OK");
		buttonPanelOne.setBounds(400,200,100,50);
		buttonPanelOne.addActionListener(this);
		buttonPanelTwo = new JButton();
		buttonPanelTwo.setBounds(400,200,100,50);
		buttonPanelTwo.addActionListener(this);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new FlowLayout()); 
		panelOne.add(primaryLabel);
		panelOne.add(comboBox);
		panelOne.add(buttonPanelOne);
		panelOne.add(resultLabel);
		frame.setVisible(true);
		
		
		textFieldvehicleID.setPreferredSize(new Dimension(70,30));
		panelTwo.add(vehicleIDLabel);
		panelTwo.add(textFieldvehicleID);
		panelTwo.add(buttonPanelTwo);
		panelTwo.setVisible(false);
		frame.add(panelOne);
		frame.add(panelTwo);
		frame.add(textArea);
	
		
		
	}




	
	
	public void actionPerformed(ActionEvent e) {
		
		String userChoice = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		DatabaseManager dbManager = new DatabaseManager();
        Vehicle vehicle = new Vehicle();
		
		resultLabel.setVisible(false);
		textArea.setVisible(false);
		panelTwo.setVisible(false);
		
		
		if(e.getSource() == buttonPanelOne) {
			
			
			switch(userChoice) {
			
			case "Check Inventory":
				textArea.setText(dbManager.displayInventory());
				textArea.setVisible(true);
				break;
				
			case "Rent Car":
				textArea.setText(dbManager.displayInventory());
				textArea.setVisible(true);
				panelTwo.setVisible(true);
				buttonPanelTwo.setText("RENT");
				userCommand = 1;
				break;
				
			case "Return Car":
				textArea.setVisible(true);
				panelTwo.setVisible(true);
				buttonPanelTwo.setText("RETURN");
				userCommand = 2;
				break;
				
			default:
				System.out.println("Action could not be performed");
				break;
			}
			
		}
		
		if(e.getSource() == buttonPanelTwo) {
			
			if(userCommand == 1) {
				resultLabel.setForeground(Color.RED);
				 
				try {
		        	vehicle = new Vehicle(Integer.parseInt(textFieldvehicleID.getText()));
		        	
		        	    if(carInventory.contains(vehicle.getVehicleID())) {
		        		    carInventory.remove(vehicle.getVehicleID());
		        		    rentedCars.add(vehicle.getVehicleID());
		    		        dbManager.addVehicle(vehicle, 0, false);
		    		        dbManager.removeVehicle(vehicle, 1);
			    	        resultLabel.setText("Car Rental Complete                          ");
			    	        resultLabel.setForeground(Color.BLACK);
		        	    }
		        	    else {
		        		     resultLabel.setText("A vehicle with that ID does not exist in the database.");
		        	    }
		        	}
		        	catch(Exception e2) {
		        		
		        		resultLabel.setText("An error occured. Please make sure you entered the Vehicle ID correctly.");
		        		
		        	}
				resultLabel.setVisible(true);
			}
			
			if(userCommand == 2) {
				
				resultLabel.setForeground(Color.RED);
				 
				try {
		        	vehicle = new Vehicle(Integer.parseInt(textFieldvehicleID.getText()));
		        	
		        	    if(rentedCars.contains(vehicle.getVehicleID())) {
		        	    	carInventory.add(vehicle.getVehicleID());
		        		    rentedCars.remove(vehicle.getVehicleID());
		    		        dbManager.addVehicle(vehicle, 1, true);
		    		        dbManager.removeVehicle(vehicle, 0);
			    	        resultLabel.setText("Car Returned                         ");
			    	        resultLabel.setForeground(Color.BLACK);
		        	    }
		        	    else {
		        		     resultLabel.setText("A vehicle with that ID has not been rented out.");
		        	    }
		        	}
		        	catch(Exception e2) {
		        		
		        		resultLabel.setText("An error occured. Please make sure you entered the Vehicle ID correctly.");
		        		
		        	}
				resultLabel.setVisible(true);
				
			}
			
		}
		
	}

}
