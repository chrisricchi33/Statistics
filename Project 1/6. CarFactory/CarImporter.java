
// Chris Ricchi
// 9/22/2023
// CarImporter class
// This class handles importing the csv file, and printing the contents

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CarImporter 
{
    public static void main(String[] args) 
    {
    	// File path to choose from
        String filePath = "cars.csv";

        // Buffered reader allows file to be read from
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            boolean firstLine = true;

            // Skipping the header of the csv file
            while ((line = reader.readLine()) != null) 
            {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Split the line at each comma
                String[] data = line.split(",");
                
                // Data length will always be 4
                if (data.length == 4) 
                {
                	// Manually select each individual line between commas for each section in the data array
                    String carType = data[0];
                    int year = Integer.parseInt(data[1]);
                    String color = data[2];
                    int miles = Integer.parseInt(data[3]);

                    // Create a car object and print its details
                    Car car = new Car(carType, year, color, miles);
                    System.out.println("Imported Car: " + car.getCarType() + ", " + car.getYear() + ", " + car.getColor() + ", " + car.getMiles());
                }
            }

            // Console confirmation
            System.out.println("Cars imported successfully.");
            
        } 
        
        // Catch statement if the file cannot be read
        catch (IOException e) 
        {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}