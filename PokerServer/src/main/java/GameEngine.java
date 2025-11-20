//package PokerServer.src.main.java;
//
//import java.util.ArrayList;
//
//public class GameEngine {
//    public static PokerServer.src.main.java.Hand.Handrank evaluate(Hand hand){
//        ArrayList<PokerServer.src.main.java.Card> cards = hand.getCards();
//
//        boolean threeOfKind = isThreeOfKind(cards);
//        boolean straight = isStraight(cards);
//        boolean flush = isFlush(cards);
//        boolean pair = isPair(cards);
//
//        if(isThreeOfKind(cards) && isFlush(cards)){ return Hand.Handrank.STRAIGHTFLUSH;}
//        else if(isThreeOfKind(cards)){ return Hand.Handrank.THREEOFAKIND;}
//        else if(isStraight(cards)){ return Hand.Handrank.STRAIGHT;}
//        else if(isFlush(cards)){ return Hand.Handrank.FLUSH;}
//        else if(isPair(cards)){ return Hand.Handrank.PAIR;}
//        return Hand.Handrank.PAIR;
//    }
//
//
//    private static boolean isThreeOfKind(ArrayList<Card> cards){
//        return (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank());
//    }
//
//    private static boolean isStraight(ArrayList<Card> cards){
//        return false;
//    }
//
//    private static boolean isFlush(ArrayList<Card> cards){
//        return (cards.get(0).getSuit() == cards.get(1).getSuit() && cards.get(1).getSuit() == cards.get(2).getSuit());
//    }
//
//    private static boolean isPair(ArrayList<Card> cards){
//        return (cards.get(0).getRank() == cards.get(1).getRank() || cards.get(0).getRank() == cards.get(2).getRank() || cards.get(1).getRank() == cards.get(2).getRank());
//    }
//
//
//
//}
