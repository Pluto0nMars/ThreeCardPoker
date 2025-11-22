package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ArrayList<Card> hand(int n){
        ArrayList<Card> hand = new ArrayList<>();
        for(int i=0; i<n; i++){
            Card rv = deal();
            hand.add(rv);
            cards.remove(rv);
        }
        return hand;
    }

    public ArrayList<Card> hand3(){
        ArrayList<Card> hand = new ArrayList<>();
        for(int i=0; i<3; i++){
            Card rv = deal();
            hand.add(rv);
            cards.remove(rv);
        }
        return hand;
    }

    public void printDeck(){
        for (Card card : cards){
            System.out.println(card.toString());
        }
    }
}