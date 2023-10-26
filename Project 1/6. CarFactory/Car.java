
// Chris Ricchi
// 9/22/2023
// Car Object
// Class to create a Car object that contains carType, year, color, and miles

public class Car 
{
	// Initialize 4 variables of the car
	private String carType;
    private int year;
    private String color;
    private int miles;

    // Constructor to allow creation
    public Car(String carType, int year, String color, int miles) 
    {
        this.carType = carType;
        this.year = year;
        this.color = color;
        this.miles = miles;
    }

    // Basic getters and setters below
    
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }
}