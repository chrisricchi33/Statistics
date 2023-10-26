
// Chris Ricchi
// 9/19/2023
// Birthday Problem Test Class
// Test the implementation of the Person.java class

import java.text.NumberFormat;

public class TestPerson 
{
    public static void main(String[] args) 
    {
    	// This is the total number of students in a classroom
        int numStudents = 30;
        
        // This is the total number of test trials to run
        int numTrials = 10000;

        // The total count of shared birthdays
        int sharedBirthdayCount = 0;

        
        // Loop through the test trials
        for (int i = 0; i < numTrials; i++) 
        {
            Person[] classroom = new Person[numStudents];

            // Create a new person object for each number of students in the array
            for (int j = 0; j < numStudents; j++) 
                classroom[j] = new Person();
            
            // Call the hasSharedBirthday method to check if a birthday is shared
            if (hasSharedBirthday(classroom))
                sharedBirthdayCount++;
        }

        // Calculate the probability
        double probability = (double) sharedBirthdayCount / numTrials;
        
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        
        // Output
        System.out.println("Calculated probability of two people sharing a birthday (" + numStudents + " students): " + percentFormat.format(probability));
    }

    // Loops through each person in the classroom and makes a comparison for birthday using the hasSameBirthdayAs method
    public static boolean hasSharedBirthday(Person[] classroom) 
    {
        for (int i = 0; i < classroom.length; i++) 
        {
            for (int j = i + 1; j < classroom.length; j++) 
            {
                if (classroom[i].hasSameBirthdayAs(classroom[j]))
                    return true;
            }
        }
        return false;
    }
}