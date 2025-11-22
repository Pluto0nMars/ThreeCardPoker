package game;

public class Card {

    private char suit;
    private int rank;

    public Card(char suit,  int rank){
        this.suit = suit;
        this.rank = rank;
    }

    public char getSuit(){return suit;}
    public int getRank(){return rank;}

    @Override
    public String toString(){
        return rank + " of " + suit;
    }

    public String getCardFile(){
        return rank + "_" + suit + ".png";
    }
}
