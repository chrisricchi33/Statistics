
// Chris Ricchi
// 9/28/2023
// TestHandEvaluator
// Class to TestHandEvaluator to X amount of potential simulations

public class TestHandEvaluator 
{
	public static void main(String[] args) 
	{
		// Changeable parameter to simulate different amounts
        int numSimulations = 10000;
        
        // Changeable parameter to dictate how many cards are drawn
        int cardsToDraw = 5;
        
        // Counts for each probability occurrence
        int pairCount = 0;
        int threeOfAKindCount = 0;
        int fourOfAKindCount = 0;
        int straightCount = 0;
        int flushCount = 0;
        int fullHouseCount = 0;
        int straightFlushCount = 0;
        int royalFlushCount = 0;

        // Create HandEvaluator object
        HandEvaluator evaluator = new HandEvaluator();

        // Run through X simulations and increment based on drawn hand
        for (int i = 0; i < numSimulations; i++) 
        {
        	// Draw a hand of X cards
            evaluator.drawHand(cardsToDraw);

            if (evaluator.hasPair()) 
                pairCount++;

            if (evaluator.hasThreeOfAKind())
                threeOfAKindCount++;

            if (evaluator.hasFourOfAKind())
                fourOfAKindCount++;

            if (evaluator.hasStraight())
                straightCount++;

            if (evaluator.hasFlush())
                flushCount++;

            if (evaluator.hasFullHouse())
                fullHouseCount++;

            if (evaluator.hasStraightFlush())
                straightFlushCount++;

            if (evaluator.hasRoyalFlush())
                royalFlushCount++;

            evaluator.hand.clear();
            evaluator.initializeDeck();
        }
        
        // Calculate all probabilities
        double pairProbability = (double) pairCount / numSimulations;
        double threeOfAKindProbability = (double) threeOfAKindCount / numSimulations;
        double fourOfAKindProbability = (double) fourOfAKindCount / numSimulations;
        double straightProbability = (double) straightCount / numSimulations;
        double flushProbability = (double) flushCount / numSimulations;
        double fullHouseProbability = (double) fullHouseCount / numSimulations;
        double straightFlushProbability = (double) straightFlushCount / numSimulations;
        double royalFlushProbability = (double) royalFlushCount / numSimulations;

        // Print results
        System.out.println("Probability of getting a pair: " + pairProbability);
        System.out.println("Probability of getting three of a kind: " + threeOfAKindProbability);
        System.out.println("Probability of getting four of a kind: " + fourOfAKindProbability);
        System.out.println("Probability of getting a straight: " + straightProbability);
        System.out.println("Probability of getting a flush: " + flushProbability);
        System.out.println("Probability of getting a full house: " + fullHouseProbability);
        System.out.println("Probability of getting a straight flush: " + straightFlushProbability);
        System.out.println("Probability of getting a royal flush: " + royalFlushProbability);
    }
}
