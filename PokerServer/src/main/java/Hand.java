package PokerServer.src.main.java;

import java.util.ArrayList;
import java.util.List;


public class Hand {

    private final ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards){
        this.cards = cards;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public String to_string(){
        return cards.toString();
    }
}
