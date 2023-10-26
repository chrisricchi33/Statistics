
// Chris Ricchi
// 9/28/2023
// HandEvaluator
// Class that allows the creation of a poker deck, drawing hands, and detecting hand conditions

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HandEvaluator 
{
	// Lists for the deck and hand
	public ArrayList<Card> deck;
	public ArrayList<Card> hand;

	// Constructor to initialize a hand
    public HandEvaluator()
    {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        
        initializeDeck();
    }

    // Create a deck of cards
    public void initializeDeck()
    {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] faces = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (String suit : suits) 
            for (int value = 1; value <= 13; value++)
                deck.add(new Card(faces[value - 1], suit, value));
    }

    // Draw a card
    public void drawCard()
    {
        if (!deck.isEmpty())
        {
            Random rand = new Random();
            int index = rand.nextInt(deck.size());
            hand.add(deck.remove(index));
        }
    }
    
    // Draw a hand of X cards
    public void drawHand(int amount)
    {
    	for (int i = 0; i < amount; i++) {
            drawCard();
        }
    }
    
    // Check if hand contains a pair
    public boolean hasPair()
    {
        for (int i = 0; i < hand.size(); i++) 
        {
            for (int j = i + 1; j < hand.size(); j++)
                if (hand.get(i).getValue() == hand.get(j).getValue())
                    return true;
        }
        
        return false;
    }
    
    // Check if hand contains three of a kind
    public boolean hasThreeOfAKind() 
    {
        for (int i = 0; i < hand.size(); i++) 
            for (int j = i + 1; j < hand.size(); j++) 
                for (int k = j + 1; k < hand.size(); k++) 
                    if (hand.get(i).getValue() == hand.get(j).getValue() && hand.get(j).getValue() == hand.get(k).getValue())
                        return true;
        
        return false;
    }

    // Check if hand contains four of a kind
    public boolean hasFourOfAKind() 
    {
        for (int i = 0; i < hand.size(); i++) 
            for (int j = i + 1; j < hand.size(); j++)
                for (int k = j + 1; k < hand.size(); k++)
                    for (int l = k + 1; l < hand.size(); l++)
                        if (hand.get(i).getValue() == hand.get(j).getValue() && hand.get(j).getValue() == hand.get(k).getValue() && hand.get(k).getValue() == hand.get(l).getValue())
                            return true;
        
        return false;
    }
    
    // Check if hand contains a straight
    public boolean hasStraight() 
    {
        Collections.sort(hand, (a, b) -> Integer.compare(a.getValue(), b.getValue()));

        for (int i = 0; i < hand.size() - 1; i++)
            if (hand.get(i + 1).getValue() - hand.get(i).getValue() != 1)
                return false;
        
        return true;
    }

    // Check if hand contains a flush
    public boolean hasFlush() 
    {
        String suit = hand.get(0).getSuit();
        
        for (Card card : hand)
            if (!card.getSuit().equals(suit))
                return false;
        
        return true;
    }

    // Check if hand contains a full house
    public boolean hasFullHouse() 
    {
        Collections.sort(hand, (a, b) -> Integer.compare(a.getValue(), b.getValue()));

        if ((hand.get(0).getValue() == hand.get(1).getValue() && hand.get(1).getValue() == hand.get(2).getValue() && hand.get(3).getValue() == hand.get(4).getValue()) || (hand.get(0).getValue() == hand.get(1).getValue() && hand.get(2).getValue() == hand.get(3).getValue() && hand.get(3).getValue() == hand.get(4).getValue()))
            return true;
        
        return false;
    }
    
    // Check if hand contains a straight flush
    public boolean hasStraightFlush() 
    {
        // Sort the hand by card value
        Collections.sort(hand, (a, b) -> Integer.compare(a.getValue(), b.getValue()));

        // Check if the hand has a flush (all cards of the same suit)
        if (!hasFlush()) {
            return false;
        }

        // Check for a straight within the sorted hand
        for (int i = 0; i < hand.size() - 4; i++) {
            boolean isStraight = true;
            for (int j = i; j < i + 4; j++) {
                if (hand.get(j + 1).getValue() - hand.get(j).getValue() != 1) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) {
                return true;
            }
        }

        return false;
    }

    // Check if hand contains a royal flush
    public boolean hasRoyalFlush() 
    {
        Collections.sort(hand, (a, b) -> Integer.compare(a.getValue(), b.getValue()));
        return hasFlush() && hand.get(0).getValue() == 1 && hand.get(4).getValue() == 13;
    }
}