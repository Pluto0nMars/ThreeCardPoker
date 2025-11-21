package PokerServer.src.main.java.game;

import java.util.ArrayList;


public class Hand {

    private final ArrayList<PokerServer.src.main.java.Deck.Card> cards;

    public Hand(ArrayList<PokerServer.src.main.java.Deck.Card> cards){
        this.cards = cards;
    }

    public ArrayList<PokerServer.src.main.java.Deck.Card> getCards(){
        return cards;
    }

    public String to_string(){
        return cards.toString();
    }
}
