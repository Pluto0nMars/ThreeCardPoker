import PokerServer.src.main.java.Deck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ServerMain extends Application {
    class ServerThread extends Thread{
        PokerServer myServer = new PokerServer();
        public void run(){
            myServer.run();
        }

    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.


            ServerThread newThread = new ServerThread();
            newThread.start();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ServerGUI.fxml"));
            primaryStage.setTitle("PokerServer GUI");

            Scene s1 = new Scene(root, 500,500);
            s1.getStylesheets().add("/styles/serverGUIstyle.css");
            primaryStage.setScene(s1);
            primaryStage.show();


        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
    public static void main(String[] args) {
        launch(args);
    }

    public static class GameEngine {
        public static PokerServer.src.main.java.Hand.Handrank evaluate(Hand hand){
            ArrayList<Deck.Card> cards = hand.getCards();

            boolean threeOfKind = isThreeOfKind(cards);
            boolean straight = isStraight(cards);
            boolean flush = isFlush(cards);
            boolean pair = isPair(cards);

            if(isThreeOfKind(cards) && isFlush(cards)){ return Hand.Handrank.STRAIGHTFLUSH;}
            else if(isThreeOfKind(cards)){ return Hand.Handrank.THREEOFAKIND;}
            else if(isStraight(cards)){ return Hand.Handrank.STRAIGHT;}
            else if(isFlush(cards)){ return Hand.Handrank.FLUSH;}
            else if(isPair(cards)){ return Hand.Handrank.PAIR;}
            return Hand.Handrank.PAIR;
        }


        private static boolean isThreeOfKind(ArrayList<Deck.Card> cards){
            return (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank());
        }

        private static boolean isStraight(ArrayList<Deck.Card> cards){
            return false;
        }

        private static boolean isFlush(ArrayList<Deck.Card> cards){
            return (cards.get(0).getSuit() == cards.get(1).getSuit() && cards.get(1).getSuit() == cards.get(2).getSuit());
        }

        private static boolean isPair(ArrayList<Deck.Card> cards){
            return (cards.get(0).getRank() == cards.get(1).getRank() || cards.get(0).getRank() == cards.get(2).getRank() || cards.get(1).getRank() == cards.get(2).getRank());
        }



    }
}
