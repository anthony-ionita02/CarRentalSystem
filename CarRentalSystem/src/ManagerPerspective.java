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





public class ManagerPerspective implements ActionListener{
	
	
	private JFrame frame;
	private JPanel panelOne;
	private JPanel panelTwo;
	private JTextField textFieldColor;
	private JTextField textFieldType;
	private JTextField textFieldYear;
	private JTextField textFieldMileage;
	private JTextField textFieldvehicleID;
	private JLabel primaryLabel;
	private JLabel resultLabel;
	private JLabel colorLabel;
	private JLabel typeLabel;
	private JLabel yearLabel;
	private JLabel vehicleIDLabel;
	private JLabel mileageLabel;
	private JButton buttonPanelOne;
	private JButton buttonPanelTwo;
	private JComboBox comboBox;
	private JTextArea textArea;
	private static int userCommand = 0;
	private Color backgroundColor = new Color(197,255,220);
	
	
	private HashSet<Integer> carInventory = new HashSet<>();
	
	
	
	
	//ManagerPerspective constructor creates the GUI used when the user selects "Manager Perspective from the drop down menu in Main.
	
	ManagerPerspective(){
		
		DatabaseManager setBuilder = new DatabaseManager();
		carInventory = setBuilder.buildHashSet(1);
		
		frame = new JFrame("Car Rental System");
		frame.getContentPane().setBackground(backgroundColor);
		panelOne = new JPanel();
		panelOne.setBackground(backgroundColor);
		panelTwo = new JPanel();
		panelTwo.setBackground(backgroundColor);
		primaryLabel = new JLabel();
		resultLabel = new JLabel();
		colorLabel = new JLabel("Color: ");
		typeLabel = new JLabel("Type: ");
		yearLabel = new JLabel("Year: ");
		mileageLabel = new JLabel("Mileage: ");
		vehicleIDLabel = new JLabel("Vehicle ID: ");
		textFieldColor = new JTextField();
		textFieldType = new JTextField();
		textFieldYear = new JTextField();
		textFieldMileage = new JTextField();
		textFieldvehicleID = new JTextField();
		textArea = new JTextArea();
		textArea.setBackground(backgroundColor);
		
		String[] optionList = {"Check Inventory", "Add Car", "Remove Car", "Update Car Info"};
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
		
		
		JLabel[] labelArray = {colorLabel, typeLabel, yearLabel, mileageLabel, vehicleIDLabel};
		JTextField[] textFieldArr = {textFieldColor, textFieldType, textFieldYear, textFieldMileage, textFieldvehicleID};
		
		for(int i = 0; i < labelArray.length; i++) {
			textFieldArr[i].setPreferredSize(new Dimension(70,30));
			labelArray[i].setSize(70,30);
			panelTwo.add(labelArray[i]);
			panelTwo.add(textFieldArr[i]);
		}
		
		
		panelTwo.add(buttonPanelTwo);
		panelTwo.setVisible(false);
		frame.add(panelOne);
		frame.add(panelTwo);
		frame.add(textArea);
	
		
		
		
		
	}
	
	
	
	/*The method below sets the GUI labels, textfeilds, and buttons as need. It calls the DatabaseManager methods to 
	to add, remove, or update information regarding the vehicles in the database in accordance to the users input.
	*/
	
	public void actionPerformed(ActionEvent e) {
		
		
		String userChoice = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		DatabaseManager dbManager = new DatabaseManager();
        Vehicle vehicle = new Vehicle();
		JLabel[] labelArray = {colorLabel, typeLabel, yearLabel, mileageLabel, vehicleIDLabel};
		JTextField[] textFieldArr = {textFieldColor, textFieldType, textFieldYear, textFieldMileage, textFieldvehicleID};
		
		for(int i = 0; i < labelArray.length; i++) {
			labelArray[i].setVisible(true);
			textFieldArr[i].setVisible(true);
		}
		
		resultLabel.setVisible(false);
		textArea.setVisible(false);
		panelTwo.setVisible(false);
		
		
		
		if(e.getSource() == buttonPanelOne) {
			
			switch(userChoice) {
			
			
			case "Check Inventory":
				textArea.setText(dbManager.displayInventory());
				textArea.setVisible(true);
				break;
			
			case "Add Car":
				panelTwo.setVisible(true);
				buttonPanelTwo.setText("ADD");
				userCommand = 1;
				break;
				
			case "Remove Car":
				textArea.setText(dbManager.displayInventory());
				for(int i = 0; i < labelArray.length - 1; i++) {
					labelArray[i].setVisible(false);
					textFieldArr[i].setVisible(false);
				}
				textArea.setVisible(true);
				panelTwo.setVisible(true);
				buttonPanelTwo.setText("REMOVE");
				userCommand = 2;
				break;
				
			case "Update Car Info":
				textArea.setText(dbManager.displayInventory());
				textArea.setVisible(true);
				panelTwo.setVisible(true);
				buttonPanelTwo.setText("UPDATE");
				userCommand = 3;
				break;
				
			default:
				System.out.println("Action could not be performed.");
			    break;
			
			
			}
		}
		
		if(e.getSource() == buttonPanelTwo) {
			
			if(userCommand == 1) {
				resultLabel.setForeground(Color.RED);
				
                try {
		    		
		    		String color = textFieldColor.getText();
		    		String type = textFieldType.getText();
		    		int year = Integer.parseInt(textFieldYear.getText());
		    	    String mileage = textFieldMileage.getText();
		    		int vehicleID = Integer.parseInt(textFieldvehicleID.getText());
		    		
		    		vehicle = new Vehicle(color, type, year, mileage, vehicleID);
		    		
		    		if(!carInventory.contains(vehicle.getVehicleID())) {
		    			dbManager.addVehicle(vehicle, 1, false);
				    	resultLabel.setText("Car Added                      ");
				    	resultLabel.setForeground(Color.BLACK);
				    	carInventory.add(vehicle.getVehicleID());
				    	
		    		}
		    		else {
		    			resultLabel.setText("A vehicle with that ID already exists in the database.");
		    		}
		    		
		    		}catch(Exception ex) { 
		    			
		    			resultLabel.setText("An error occured. Please make sure you entered the information correctly"
		    					+ " and filled in all feilds.");
		    			
		    		}
                resultLabel.setVisible(true);
			}
			
			if(userCommand == 2) {
				resultLabel.setForeground(Color.RED);
				 
				try {
		        	vehicle = new Vehicle(Integer.parseInt(textFieldvehicleID.getText()));
		        	
		        	    if(carInventory.contains(vehicle.getVehicleID())) {
		        		    carInventory.remove(vehicle.getVehicleID());
		    		        dbManager.removeVehicle(vehicle, 1);
			    	        resultLabel.setText("Car Removed                          ");
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
			
			if(userCommand == 3) {
				resultLabel.setForeground(Color.RED);
				
				try {
					
					String color = textFieldColor.getText();
		    		String type = textFieldType.getText();
		    		int year = Integer.parseInt(textFieldYear.getText());
		    	    String mileage = textFieldMileage.getText();
		    		int vehicleID = Integer.parseInt(textFieldvehicleID.getText());
		    		
		    		vehicle = new Vehicle(color, type, year, mileage, vehicleID);
		    		
		    		if(carInventory.contains(vehicle.getVehicleID())) {
		    			dbManager.updateInfo(vehicle);
				    	resultLabel.setText("Info Updated                      ");
				    	resultLabel.setForeground(Color.BLACK);
				    	
		    		}
		    		else {
		    			resultLabel.setText("A vehicle with that ID does not exist in the database.");
		    		}
					
				}
				catch(Exception e3) {
					resultLabel.setText("An error occured. Please make sure you entered the Vehicle ID correctly.");
				}
				resultLabel.setVisible(true);
			}
		}
		
		
		
	}
	
	
	

}
