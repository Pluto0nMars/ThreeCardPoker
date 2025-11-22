public class Card {

    private char suit;
    private int rank;

    public Card(char suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }

    public char getSuit(){return suit;}
    public int getRank(){return rank;}

    @Override
    public String toString(){
        return rank + " of " + suit;
    }

    public String getCardFile(){
        String rankStr;
        String suitStr;
        if(11 == rank){
           rankStr = "ace";
        }
        else if (12 == rank){
            rankStr = "jack";
        }
        else if (13 == rank) {
            rankStr = "queen";
        }
        else if(14 == rank){
            rankStr = "king";
        }else{
            rankStr = Integer.toString(rank);
        }

        if('C' == suit){
            suitStr = "clubs";
        } else if ('S' == suit) {
            suitStr = "spades";
        } else if ('H' == suit) {
            suitStr = "hearts";
        } else if ('D' == suit) {
            suitStr = "diamonds";
        }
        else{
            suitStr = "";
        }


        return rankStr + "_" + suitStr + ".png";
    }


}
