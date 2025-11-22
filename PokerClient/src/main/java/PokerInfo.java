import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
        //Player and dealer hand (could be just array I guess)
        private ArrayList<Card> playerHand;
        private ArrayList<Card> dealerHand;

        private int currWager;

        private String message;

        public PokerInfo (){
            playerHand = new ArrayList<>();
            dealerHand = new ArrayList<>();
        }

        public ArrayList<Card> getPlayerHand(){
            return playerHand;
        }

        public ArrayList<Card> getDealerHand(){
            return dealerHand;
        }

        public void setPlayerHand(ArrayList<Card> playerHand){
            this.playerHand = playerHand;
        }

        public void setDealerHand(ArrayList<Card> dealerHand){
            this.dealerHand = dealerHand;
        }

        public int getCurrWager(){
            return currWager;
        }

        public void setCurrentWager(int currentWager) {
            this.currWager = currentWager;
        }

        public String getMessage(){
            return message;
        }

        public void setMessage(String message){
            this.message = message;
        }
}
