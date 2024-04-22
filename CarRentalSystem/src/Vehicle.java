
public class Vehicle {

	
	private String color;
	private String type;
	private int year;
	private String mileage;
	private int vehicleID;
	
	
	
	public Vehicle(){
		
	}
	
	
	public Vehicle(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	
	
	public Vehicle(String color, String type, int year, String mileage, int vehicleID){
		this.color = color;
		this.type = type;
		this.year = year;
		this.mileage = mileage;
		this.vehicleID = vehicleID;
		
	}
	
	
	public void setColor(String color) {
		this.color = color;
	}
	
	
	public String getColor() {
		return this.color;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getType() {
		return this.type;
	}
	
	
	public void setYear(int year) {
		this.year = year;
	}
	
	
	public int getYear() {
		return this.year;
	}
	
	
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	
	
	public String getMileage() {
		return this.mileage;
	}
	
	
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	
	
	public int getVehicleID() {
		return this.vehicleID;
	}
	
}
