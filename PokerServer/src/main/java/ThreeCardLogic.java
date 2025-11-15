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
    public void evalHand(ArrayList<Card> hand, int wager){

    }

    //Compare player hand with dealer and return winning amount?
   public int comparehands(ArrayList<Card> dealer, ArrayList<Card> player){
        return  0;
   }

   public void shuffle(){
       Collections.shuffle(deck);
   }

   public void reset(){

   }
}

