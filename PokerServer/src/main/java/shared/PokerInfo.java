package shared;

import shared.game.Card;

import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    //Player and dealer hand (could be just array I guess)
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;

    private int totBalance;
    private int roundNum;

    private int anteBet;
    private int pairPlusBet;
    private String message;
    private ClientAction action;

    public PokerInfo (){
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        totBalance = 0;
        roundNum = 0;
        anteBet = 0;
        pairPlusBet = 0;
        message = "";
        action = null;
    }

    public ArrayList<Card> getPlayerHand(){return playerHand;}
    public void setPlayerHand(ArrayList<Card> playerHand){this.playerHand = playerHand;}

    public ArrayList<Card> getDealerHand(){return dealerHand;}
    public void setDealerHand(ArrayList<Card> dealerHand){this.dealerHand = dealerHand;}

    public int getTotBalance(){return totBalance;}
    public void setTotBalance(int balance){totBalance = balance;}

    public int getRoundNum(){return roundNum;}
    public void setRoundNum(int round){roundNum = round;}

    public int getAnteBet(){return anteBet;}
    public void setAnteBet(int ante){anteBet = ante;}

    public int getPairPlusBet(){return pairPlusBet;}
    public void setPairPlusBet(int pairPlus){pairPlusBet = pairPlus;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public ClientAction getAction(){return action;}
    public void setAction(ClientAction action){this.action = action;}
}
