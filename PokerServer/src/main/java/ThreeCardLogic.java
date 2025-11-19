import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class ThreeCardLogic{
    private Stack<Card> deck;
    class Card {
        private char suit;
        private int value;


        Card(char suit, int value){

            this.suit = suit;
            this.value = value;
        }
        int getValue(){
            return value;
        }
        char getSuit(){
            return suit;
        }
    }

    //Will implement when I understand how poker works
    public void evalHand(ArrayList<Card> hand){


    }

    //Compare player hand with dealer and return winning amount?
   public int comparehands(ArrayList<Card> dealer, ArrayList<Card> player){
        return  0;
   }

   public void shuffle(){
       Collections.shuffle(deck);
   }

   public ArrayList<Card> deal(){
        ArrayList<Card> hand = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            hand.add(deck.pop());
        }
        return hand;
   }

   public void reset(){
       deck = new Stack<>();
       char[] suits = {'H', 'D', 'C', 'S'};
       for (char suit : suits) {
           for (int value = 2; value <= 14; value++) {
               deck.push(new Card(suit, value));
           }
       }
       shuffle();
   }
}

