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