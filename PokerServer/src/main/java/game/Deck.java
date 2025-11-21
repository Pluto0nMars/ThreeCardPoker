package PokerServer.src.main.java.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
<<<<<<< HEAD:PokerServer/src/main/java/Deck.java
//import PokerServer.src.main.java.Deck.Card;
=======
>>>>>>> fbb6b69d3a33d912b9474255f812ac35ea28ab85:PokerServer/src/main/java/game/Deck.java

public class Deck {
    private final ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        char[] suits = {'H', 'D', 'C', 'S'};
        int[] ranks = {2,3,4,5,6,7,8,9,10,11,12,13,14};

        for (int r : ranks){
            for(char s: suits){
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
    public Card deal(){
        if (cards.isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Empty deck");
        }
        else{
            return cards.get(cards.size()-1);
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
<<<<<<< HEAD:PokerServer/src/main/java/Deck.java


    public static class Card {

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

        public Card(Suit suit, Rank rank){
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
=======
>>>>>>> fbb6b69d3a33d912b9474255f812ac35ea28ab85:PokerServer/src/main/java/game/Deck.java
}
