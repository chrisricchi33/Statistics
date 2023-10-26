import java.util.Random;

//P34 2.20 Answer A:
//P(A) = P(E1) + P(E2) + P(E3)
//P(E1) = P(E2) = P(E3) = 1/3

//P34 2.20 Answer B:
//The best strategy to give the contestant the best odds of 
//winning is to switch to the other remaining unopened curtain.
//The initial choice made by the contestant gives them a 1/3 choice 
//of winning, but after the switch this option ends at 2/3 chance of winning.

public class DoorGame 
{
	//Reference to three doors where 1 = prize and 0 = dud
    private int door1;
    private int door2;
    private int door3;
    
    //Counters to add up total amount of wins for each method
    private int wins_when_switching = 0;
    private int wins_when_not_switching = 0;

    public DoorGame() 
    {
    	//Array to store the 3 door values
        int[] doors = {0, 0, 0};
        
        //Initialize random location in array for prize location
        Random r = new Random();
        int prizeLocation = r.nextInt(3);
        
        //Set the random location to 1 to indicate prize location
        doors[prizeLocation] = 1;
        
        //Store values back in array
        this.door1 = doors[0];
        this.door2 = doors[1];
        this.door3 = doors[2];
    }

    public void PlayGame(int choice, boolean switchDoor) 
    {
        // Int for reference to the revealed door
        int revealedDoor;

        // Determine the revealed door based on the initial choice
        if (choice == 1)
            revealedDoor = (door2 == 0) ? 2 : 3;
        else if (choice == 2)
            revealedDoor = (door1 == 0) ? 1 : 3;
        else
            revealedDoor = (door1 == 0) ? 1 : 2;

        // If switching, change the choice based on revealedDoor
        if (switchDoor) 
        {
            choice = (choice == 1 && revealedDoor == 2) ? 3 : 
                     (choice == 1 && revealedDoor == 3) ? 2 : 
                     (choice == 2 && revealedDoor == 1) ? 3 : 
                     (choice == 2 && revealedDoor == 3) ? 1 : 
                     (choice == 3 && revealedDoor == 1) ? 2 : 1;
        }

        // Check if the player won and update counters
        if ((choice == 1 && door1 == 1) || (choice == 2 && door2 == 1) || (choice == 3 && door3 == 1)) 
        {
            if (switchDoor)
                wins_when_switching++;
            else
                wins_when_not_switching++;
        }
    }

    //Method to play game with door switching
    public double PlayGameWithSwitching(int numGames) 
    {
        Random random = new Random();
        
        for (int j = 0; j < numGames; j++) {
            int choice = random.nextInt(3) + 1;
            PlayGame(choice, true);
        }
        
        return (wins_when_switching / (double) numGames * 100);
    }

    //Method to play game without door switching
    public double PlayGameWithoutSwitching(int numGames) 
    {
        Random random = new Random();
        
        for (int j = 0; j < numGames; j++) {
            int choice = random.nextInt(3) + 1;
            PlayGame(choice, false);
        }
        return (wins_when_not_switching / (double) numGames * 100);
    }

    public static void main(String[] args) 
    {
        DoorGame game = new DoorGame();
        System.out.println("Percentage of games won with switching doors (10,000 trials): " + game.PlayGameWithSwitching(10000) + "%");

        game = new DoorGame();
        System.out.println("Percentage of games won without switching doors (10,000 trials): " + game.PlayGameWithoutSwitching(10000) + "%");
    }
}
