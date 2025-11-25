package game;
/*
A round has 1 connected client
1 decks of cards
1 chosen hand for player
1 chosen hand for house

env variables:
  * ante wager
  * pair plus wager
  * outcome W or L
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Round {
    private Deck deck;
    private Hand clientHand;
    private Hand serverHand;

    private int anteWager;
    private int plusWager;
    private String outcome;

    public Round(int anteWager, int plusWager){
        deck = new Deck();

        // need to remove cards from deck tho
        this.clientHand = new Hand(deck.hand3());
        this.serverHand = new Hand(deck.hand3());
        this.anteWager = anteWager;
        this.plusWager = plusWager;
        outcome = "lost";
    }

    public void setOutcome() {
        if (ThreeCardLogic.rankHand(clientHand.getCards()) > 0){
            outcome = "won";
        }
        else outcome = "lost";
    }

    public int getHighestCard(ArrayList<Card> hand){
        int c1 = hand.get(0).getRank();
        int c2 = hand.get(1).getRank();
        int c3 = hand.get(2).getRank();

        int[] vals = {c1,c2,c3};
        Arrays.sort(vals);

        return vals[2];
    }

    public Hand getClientHand() {
        return clientHand;
    }

    public ArrayList<Card> getClientHand_arrList() {
        return clientHand.getCards();
    }

    public Hand getServerHand() {
        return serverHand;
    }

    public ArrayList<Card> getServerHand_arrList() {
        return serverHand.getCards();
    }

    public String getOutcome() {
        return outcome;
    }

    public int getAnteWager() {
        return anteWager;
    }

    public int getPlusWager() {
        return plusWager;
    }
}
