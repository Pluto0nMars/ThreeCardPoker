package PokerServer.src.main.java.game;


import java.util.ArrayList;
import java.util.Arrays;

public class ThreeCardLogic{

    private ArrayList<Card> hand;

    public static int rankHand(ArrayList<Card> hand){
        if(isStraightFlush(hand)){ return 5;}
        else if(isThreeOfKind(hand)){ return 4;}
        else if(isStraight(hand)){ return 3;}
        else if(isFlush(hand)){ return 2;}
        else if(isPair(hand)){ return 1;}
        return 0;
    }

    private static boolean isStraightFlush(ArrayList<Card> cards){
        return isStraight(cards) && isFlush(cards);
    }

    private static boolean isThreeOfKind(ArrayList<Card> cards){
        return (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank());
    }

    // order cards and then compare expected result
    private static boolean isStraight(ArrayList<Card> cards){
        int c1 = cards.get(0).getRank();
        int c2 = cards.get(1).getRank();
        int c3 = cards.get(2).getRank();

        int[] vals = {c1,c2,c3};
        Arrays.sort(vals);

        c1 = vals[0];
        c2 = vals[1];
        c3 = vals[2];

        if (c3 == 14){
            return c3 == 14 && c2 == 3 && c1 == 2;
        }
        return (c1 + 1 == c2) && (c2 + 1 == c3);
    }

    // flush means same suit for each card
    private static boolean isFlush(ArrayList<Card> cards){
        return (cards.get(0).getSuit() == cards.get(1).getSuit() && cards.get(1).getSuit() == cards.get(2).getSuit());
    }

    // pair means 2 matching
    private static boolean isPair(ArrayList<Card> cards){
        return (cards.get(0).getRank() == cards.get(1).getRank() || cards.get(0).getRank() == cards.get(2).getRank() || cards.get(1).getRank() == cards.get(2).getRank());
    }



}

