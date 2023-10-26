
// Chris Ricchi
// 9/22/2023
// Factory Class
// Class that will handle "producing" a randomly generated car from a list of possible options

import java.util.Random;

public class Factory 
{
	// Possible options for each car
    private String[] CAR_TYPES = {"Car", "Truck", "Van", "Sedan", "Sports Cars"};
    private String[] COLORS = {"Red", "Green", "Blue", "Black", "White", "Silver", "Pink"};
    private int MIN_YEAR = 1960;
    private int MAX_YEAR = 2023;

    public Car createRandomCar()
    {
        Random rand = new Random();
        
        String carType;
        
        // This makes the chance of choosing a Sedan 30% compared to 70% of choosing the 4 other
        if (rand.nextDouble() < 0.3)
            carType = "Sedan";
        else
            carType = CAR_TYPES[rand.nextInt(CAR_TYPES.length)];
        
        // Randomly select a year in the possible range
        int year = rand.nextInt(MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;
        
        // Randomly select a color from the possible array
        String color = COLORS[rand.nextInt(COLORS.length)];
        
        // Randomly selects miles in the given range
        int miles = rand.nextInt(250001);

        // Creates the new car object with the given parameters
        return new Car(carType, year, color, miles);
    }
}