
// Chris Ricchi
// 9/22/2023
// CarExporter class
// This class handles creating the csv file, creating a factory and generating 1000 cars, and writing that data to the csv

import java.io.FileWriter;
import java.io.IOException;

public class CarExporter 
{
    public static void main(String[] args) 
    {
    	// Try to write the file
        try 
        {
            FileWriter writer = new FileWriter("cars.csv");
            writer.write("CarType,Year,Color,Miles\n");

            // Generates the Factory object
            Factory factory = new Factory();
            
            // Loop through 1000 iterations to create 1000 random cars
            for (int i = 0; i < 1000; i++) 
            {
            	// Creates the random car using the factory class
                Car car = factory.createRandomCar();
                
                // This writes the data to the csv. % objects are placeholders for each data type
                writer.write(String.format("%s,%d,%s,%d\n", car.getCarType(), car.getYear(), car.getColor(), car.getMiles()));
            }

            // Close the file after writing is completed
            writer.close();
            
            // Console confirmation
            System.out.println("Cars exported to cars.csv successfully.");
        }
        
        // Catch statement if the csv file is not able to be opened
        catch (IOException e) 
        {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
