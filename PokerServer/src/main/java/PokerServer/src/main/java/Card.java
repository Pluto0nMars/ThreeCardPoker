package PokerServer.src.main.java;

public class Card {

    //https://www.geeksforgeeks.org/java/enum-in-java/
    public enum Suit{
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS;
    }
    public enum Rank{
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE;
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit,  Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){return suit;}
    public Rank getRank(){return rank;}

//    @Override
//    String toString(){
    String to_string(){
        return rank + " of " + suit;
    }
}
