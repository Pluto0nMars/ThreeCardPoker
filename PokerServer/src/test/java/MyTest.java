
import static org.junit.jupiter.api.Assertions.*;
import game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MyTest {

    private Deck deck;
    @BeforeEach
    public void setup() {
        deck = new Deck();
    }

    @Test
    void ThreeCardLogic_StraightFlush(){
        ArrayList<Card> hand = DummyData.createSFdeck();
        assertEquals(5, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_StraightFlush_ACE(){
        ArrayList<Card> hand = DummyData.createSFdeckACE();
        assertEquals(5, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_ThreeOfKind(){
        ArrayList<Card> hand = DummyData.create3Kinddeck();
        assertEquals(4, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_Straight(){
        ArrayList<Card> hand = DummyData.createStraightdeck();
        assertEquals(3, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_Straight_ACE(){
        ArrayList<Card> hand = DummyData.createStraightdeck_ACE();
        assertEquals(3, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_Flush(){
        ArrayList<Card> hand = DummyData.createFlushdeck();
        assertEquals(2, ThreeCardLogic.rankHand(hand));
    }
    @Test
    void ThreeCardLogic_Pair(){
        ArrayList<Card> hand = DummyData.createPairdeck();
        assertEquals(1, ThreeCardLogic.rankHand(hand));
    }

    @Test
    void Card_GetFilename_AceSpade(){
        Card c1 = new Card('S', 14);
        assertEquals("ace_of_spades.png", c1.getCardFile());
    }
    @Test
    void Card_GetFilename_2Clubs(){
        Card c1 = new Card('C', 2);
        assertEquals("2_of_clubs.png", c1.getCardFile());
    }
    @Test
    void Card_GetFilename_KingHearts(){
        Card c1 = new Card('H', 13);
        assertEquals("king_of_hearts.png", c1.getCardFile());
    }
    @Test
    void Card_GetFilename_7Diamonds(){
        Card c1 = new Card('D', 7);
        assertEquals("7_of_diamonds.png", c1.getCardFile());
    }


    @Test
    void ThreeCardLogic_Pair_Alt() {
        ArrayList<Card> hand = MyTest.DummyData.createPairdeck();
        assertEquals(1, ThreeCardLogic.rankHand(hand));
    }

    @Test
    void ThreeCardLogic_ThreeOfKind_Alt() {
        ArrayList<Card> hand = MyTest.DummyData.create3Kinddeck();
        assertEquals(4, ThreeCardLogic.rankHand(hand));
    }

    @Test
    void Card_GetFilename_QueenDiamonds() {
        Card c = new Card('D', 12);
        assertEquals("queen_of_diamonds.png", c.getCardFile());
    }

    @Test
    void Card_GetFilename_JackClubs() {
        Card c = new Card('C', 11);
        assertEquals("jack_of_clubs.png", c.getCardFile());
    }

    @Test
    void Card_GetFilename_10Hearts() {
        Card c = new Card('H', 10);
        assertEquals("10_of_hearts.png", c.getCardFile());
    }

    @Test
    void Card_GetFilename_3Spades() {
        Card c = new Card('S', 3);
        assertEquals("3_of_spades.png", c.getCardFile());
    }

    @Test
    void ThreeCardLogic_Straight_Alt() {
        ArrayList<Card> hand = MyTest.DummyData.createStraightdeck();
        assertEquals(3, ThreeCardLogic.rankHand(hand));
    }

    @Test
    void ThreeCardLogic_Straight_ACE_Alt() {
        ArrayList<Card> hand = MyTest.DummyData.createStraightdeck_ACE();
        assertEquals(3, ThreeCardLogic.rankHand(hand));
    }

    @Test
    void Card_GetFilename_5Hearts() {
        Card c = new Card('H', 5);
        assertEquals("5_of_hearts.png", c.getCardFile());
    }

//    @Test
//    void Deck_pullCards(){
//
//        Hand clientHand = new Hand(deck.hand3());
//        Hand serverHand = new Hand(deck.hand3());
//
//        clientHand.printHand();
//        serverHand.printHand();
//        System.out.println("\n\n");
//
//        deck.printDeck();
//
//    }





    public static class DummyData {
        public static ArrayList<Card> createSFdeck(){
            Card c1 = new Card('S', 10);
            Card c2 = new Card('S', 9);
            Card c3 = new Card('S', 8);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> createSFdeckACE(){
            Card c1 = new Card('H', 14);
            Card c2 = new Card('H', 2);
            Card c3 = new Card('H', 3);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> create3Kinddeck(){
            Card c1 = new Card('C', 14);
            Card c2 = new Card('S', 14);
            Card c3 = new Card('H', 14);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> createStraightdeck(){
            Card c1 = new Card('C', 6);
            Card c2 = new Card('S', 7);
            Card c3 = new Card('H', 8);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> createStraightdeck_ACE(){
            Card c1 = new Card('C', 14);
            Card c2 = new Card('S', 2);
            Card c3 = new Card('H', 3);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> createFlushdeck(){
            Card c1 = new Card('D', 9);
            Card c2 = new Card('D', 7);
            Card c3 = new Card('D', 13);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

        public static ArrayList<Card> createPairdeck(){
            Card c1 = new Card('D', 13);
            Card c2 = new Card('H', 9);
            Card c3 = new Card('S', 13);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(c1);
            hand.add(c2);
            hand.add(c3);

            return hand;
        }

    }
}
