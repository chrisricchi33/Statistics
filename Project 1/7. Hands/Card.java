
// Chris Ricchi
// 9/28/2023
// Card
// Card class that allows the creation of a card object with a face, suit, and value

public class Card 
{
    private String face;
    private String suit;
    private int value;

    public Card(String face, String suit, int value)
    {
        this.face = face;
        this.suit = suit;
        this.value = value;
    }
    
    public String getFace() {
    	return face;
    }

    public String getSuit() {
    	return suit;
    }
    
    public int getValue() {
        return value;
    }
}