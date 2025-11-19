package PokerServer.src.main.java;

import java.util.ArrayList;
import java.util.List;


public class Hand {
    public enum Handrank{
        STRAIGHTFLUSH,
        THREEOFAKIND,
        STRAIGHT,
        FLUSH,
        PAIR;
    }


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
