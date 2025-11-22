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

    public Hand getClientHand() {
        return clientHand;
    }

    public Hand getServerHand() {
        return serverHand;
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
