package game;

import java.util.ArrayList;


public class Hand {

    private final ArrayList<Deck> cards;

    public Hand(ArrayList<Deck> cards){
        this.cards = cards;
    }

    public ArrayList<Deck> getCards(){
        return cards;
    }

    public String to_string(){
        return cards.toString();
    }
}
