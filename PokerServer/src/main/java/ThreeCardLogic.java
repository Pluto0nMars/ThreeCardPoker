import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class ThreeCardLogic{

    class Card {
        private char suit;
        private int value;
        private Stack<Card> deck;

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

    public void evalHand(ArrayList<Card> hand, int wager){

    }

   public int comparehands(ArrayList<Card> dealer, ArrayList<Card> player){
        return  0;
   }

   public void shuffle(){

   }

   public void reset(){

   }
}

