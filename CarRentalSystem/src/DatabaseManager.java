import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class DatabaseManager {
	
	//This class deals with connection to the database and CRUD functions. 
	
	
	//Establishing connection to the database:
	public Connection Connect() {
		
		Connection con = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:sqlite:CarInventory.db");
			return con;
			
		}catch(SQLException e) {
			
			System.out.println("Connection could not be established");
			return con;
		}
		
	}
	
	
	
	//Adding the vehicles previously entered into the database to their respective HashSets:
	
    public HashSet<Integer> buildHashSet(int setChoice){
		
		
		HashSet<Integer> returnSet = new HashSet<>();
		
		
		String setString = "";
		
		if(setChoice == 1) {
			setString = "Inventory";
		}
		else {
			setString = "RentedCars";
		}
		
		try {
		Connection con = this.Connect();
		
		String sqlCode = "SELECT *"
				+ "FROM " + setString;
		Statement statement = con.createStatement();
		

			ResultSet rs = statement.executeQuery(sqlCode);
			
			while(rs.next()) {
				returnSet.add(rs.getInt("vehicleID"));
			}
		} 
		catch (SQLException e) {
			System.out.println("Database connection failed.");
		}
		
		return returnSet;
	}
    
    
    
    /*For some additions into the database required by other methods, a full set of information for the vehicle may be required
     * when only the vehicleID is given. This method searches either the RentedCars or the Inventory table from the database
     * as need to find the full set of information for the vehicle object associated with that ID. 
     */
     
    public Vehicle resultSetValues(int givenID, int setChoice) {
    	
    	Connection con = null;
		Vehicle thisVehicle = new Vehicle();
    	
		String setString = "Inventory;";
		
		if(setChoice != 1) {
			setString = "RentedCars;";
		}
		
    	try {
    		
    		con = this.Connect();
    		String sqlCode = "SELECT *"
    				+ "FROM " + setString;
    				
    		Statement statement = con.createStatement();
    		ResultSet rs = statement.executeQuery(sqlCode);
    		
    		while (rs.next()) {
    			if(rs.getInt("vehicleID") == givenID) {
    				thisVehicle.setColor(rs.getString("color"));
    	    		thisVehicle.setType(rs.getString("type"));
    	    		thisVehicle.setYear(rs.getInt("year"));
    	    		thisVehicle.setMileage(rs.getString("mileage"));
    	    		thisVehicle.setVehicleID(rs.getInt("vehicleID"));
    			}
    		}
    		
    		
    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return thisVehicle;
    }
    
    
    //Adding vehicle to either the RentedCars or Inventory Table, depending on user input: 
    
    public void addVehicle(Vehicle vehicle, int setChoice, boolean returningRental) {
    	
    	try {
    		Connection con = this.Connect();
    		Vehicle vehicleData = new Vehicle();
    		
    		String setString = "";
    		
    		if(setChoice == 1) {
    			setString = "Inventory";
    			vehicleData = vehicle;
    			
    			if(returningRental == true) {
    				DatabaseManager manager = new DatabaseManager();
            	    vehicleData = manager.resultSetValues(vehicle.getVehicleID(), 0);
    			}
    			
    		}
    		else {
    			setString = "RentedCars";
    			DatabaseManager manager = new DatabaseManager();
        	    vehicleData = manager.resultSetValues(vehicle.getVehicleID(), 1);
    		}
    		
    	
    	    
    		
    		String sqlCode = "INSERT INTO " + setString + "(color,type,year,mileage,vehicleID)"
    				+ "VALUES(?,?,?,?,?);";
    		
    		PreparedStatement statement = con.prepareStatement(sqlCode);
    		{
    			statement.setString(1, vehicleData.getColor());
    			statement.setString(2, vehicleData.getType());
    			statement.setInt(3, vehicleData.getYear());
    			statement.setString(4, vehicleData.getMileage());
    			statement.setInt(5, vehicleData.getVehicleID());
    			statement.executeUpdate();
    			
    		}
    		
 
    	    
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    
    
    
    //Removing vehicles from either the RentedCars table, or if setChoice == 1, the Inventory table:
    
    public void removeVehicle(Vehicle vehicle, int setChoice) {
    	
    	try {
    		
    		Connection con = this.Connect();
    		
    		String setString = "";
    		
    		if(setChoice == 1) {
    			setString = "Inventory";
    		}
    		else {
    			setString = "RentedCars";
    		}
    		
    		String sqlCode = "DELETE FROM " + setString
    				+ " WHERE vehicleID = ?";
    		
    		PreparedStatement statement = con.prepareStatement(sqlCode);
    		{
    			statement.setInt(1, vehicle.getVehicleID());
    			statement.executeUpdate();
    		}
    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    
    
    //Updating the information in the Inventory table: 
    
    public void updateInfo(Vehicle vehicle) {
    	
    	try {
    		Connection con = this.Connect();
    		
    		String sqlCode = "UPDATE Inventory "
    				+ "SET color = ?, type = ?, year = ?, mileage = ?, vehicleID = ? "
    				+ "WHERE vehicleID = ?;";
    		
    		PreparedStatement statement = con.prepareStatement(sqlCode);
    		{
    			statement.setString(1, vehicle.getColor());
    			statement.setString(2,  vehicle.getType());
    			statement.setInt(3, vehicle.getYear());
    			statement.setString(4, vehicle.getMileage());
    			statement.setInt(5, vehicle.getVehicleID());
    			statement.setInt(6, vehicle.getVehicleID());
    			statement.executeUpdate();
    			
    		}
    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    
    
    //Displaying the whole inventory:
    
    public String displayInventory() {
		
		
		String returnString = "Color:" + "\t" + "Type:" + "\t" + "Year:" + "\t" + "Mileage:" + "\t" + "Vehicle ID:" + "\n";
		
		
		try {
			Connection con = this.Connect();
			
			String sqlCode = "SELECT *"
					+ "FROM Inventory;";
			
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sqlCode);
			
			
			while(rs.next()) {
				
				returnString = returnString + rs.getString("color") + "\t" + rs.getString("type") + "\t"
						+ rs.getInt("year") + "\t" + rs.getString("mileage") + "\t" + rs.getString("vehicleID") + "\n";
				
				
			}
			
			return returnString;
			
			
		}catch(SQLException e) {
			
			System.out.println(e.getMessage());
			
		}
		
		
		return returnString;
	}
	
	
	
	
	

}
