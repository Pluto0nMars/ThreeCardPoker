//package PokerServer.src.main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Card;
public class Deck {
    private final ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();

        for (Card.Rank r : Card.Rank.values()){
            for(Card.Suit s: Card.Suit.values()){
                cards.add(new Card(s, r));
            }
        }
        shuffle();
    }

    // built in method for randomized list
    public void shuffle(){
        Collections.shuffle(cards);
    }

    // pop 1 card in remaining already randomized list
    // WHAT DO WE DO IF WE RUN OUT OF CARDS. JUST MAKE A NEW DECK AND START FROM THERE
    //    52/3 == 17.33
    // function can only be called max of 17 times with same deck
    public Card deal(){
        if (!cards.isEmpty()){
            return cards.getLast();
        }
    }

    // deals 3 cards
    public List<Card> hand(int n){
        List<Card> hand = new ArrayList<>();
        for(int i=0; i<n; i++){
            hand.add(deal());
        }
        return hand;
    }

    public List<Card> hand3(){
        List<Card> hand = new ArrayList<>();
        for(int i=0; i<3; i++){
            hand.add(deal());
        }
        return hand;
    }


}
