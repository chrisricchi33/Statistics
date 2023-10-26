
// Chris Ricchi
// 9/19/2023
// Birthday Problem Class
// Test the probability of two people sharing a birthday in a class of x students

import java.util.Random;

public class Person 
{
    private int birthMonth;
    private int birthDay;

    // Basic constructor
    public Person()
    {
    	// Randomly assigns the person object a month and day
        Random rand = new Random();
        this.birthMonth = rand.nextInt(12) + 1;
        this.birthDay = rand.nextInt(31) + 1;
    }

    // Returns birth month
    public int getBirthMonth() 
    {
        return birthMonth;
    }

    // Returns birth day
    public int getBirthDay() 
    {
        return birthDay;
    }

    // Compares two Person references and returns true if they share a birthday or false if they do not
    public boolean hasSameBirthdayAs(Person other) 
    {
        return this.birthMonth == other.birthMonth && this.birthDay == other.birthDay;
    }
}
