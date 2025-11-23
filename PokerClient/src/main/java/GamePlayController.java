import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import shared.*;
import shared.game.Card;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class GamePlayController {

    @FXML
    public BorderPane GameRoot;

    @FXML
    Pane dealerCard1;

    @FXML
    Pane dealerCard2;

    @FXML
    Pane dealerCard3;

    @FXML
    Pane playerCard1;

    @FXML
    Pane playerCard2;

    @FXML
    Pane playerCard3;

    @FXML
    Button drawButton;

    @FXML
    Button playButton;

    @FXML
    ListView<String> messageHistory;

    @FXML
    ComboBox<Integer> anteBetList;

    @FXML
    ComboBox<Integer> pairPlusBetList;

//    @FXML
//    ComboBox<Integer> wagerList;

    @FXML
    ComboBox<String> menu;

    @FXML
    Label winnings;


    private boolean newLookActive = false;
    private final String originalStyle = "-fx-background-color: linear-gradient(to bottom right, #2e8b57,  #008000);";
    private final String newLookStyle = "-fx-background-color: linear-gradient(to bottom right, #00bfff, #2a5298);";

    private PokerClient client;

    public void setClient(PokerClient client) {
        this.client = client;
    }


    void initializeWagersAndMenu(){
        // might need to change this to [5-25] cpntinuous
        anteBetList.getItems().addAll(5, 10, 15, 20, 25);
        pairPlusBetList.getItems().addAll(5, 10, 15, 20, 25);

        menu.getItems().addAll("FRESH START", "NEW LOOK", "EXIT");

        // When the user selects a wager, draw cards
//                                                                          MIGHT WANT TO ONLY DO THIS ON PLAY BUTTON. BC NEED TO SEND PACKET TO SERVER
        anteBetList.setOnAction(event -> {
            Integer anteBet = anteBetList.getValue();
            Integer pairPlusBet = pairPlusBetList.getValue();
            if (anteBet != null) {
                drawCards(anteBet);
            }
        });

        menu.setOnAction(event->{
            String choice = menu.getValue();
            if(choice !=  null){
                menuChoice(choice);
            }
        });
    }

    @FXML
    public void initialize() {
        initializeWagersAndMenu();
    }


    void drawCards(int wager){
        setCardImage(dealerCard1, new Card('H', 5), false);
        setCardImage(dealerCard2, new Card('S', 11), false);
        setCardImage(dealerCard3, new Card('D', 2), false);

        setCardImage(playerCard1, new Card('H', 5), true);
        setCardImage(playerCard2, new Card('S', 11), true);
        setCardImage(playerCard3, new Card('D', 2), true);
    }

    @FXML
    public void handleFold(ActionEvent event)throws IOException {
        if(client == null){
            return;
        }

        PokerInfo playerInfo = new PokerInfo();
        playerInfo.setMessage("Player folds");
//        playerInfo.setCurrentWager(wagerList.getValue());
        playerInfo.setAnteBet(anteBetList.getValue());

        try{
            client.getOutputStream().writeObject(playerInfo);
            client.getOutputStream().flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void menuChoice(String c){
        if("EXIT".equals(c)){
            Platform.exit();
            System.exit(0);
        } else if ("NEW LOOK".equals(c)) {
            if(newLookActive){
                GameRoot.setStyle(originalStyle); // restore original
                newLookActive = false;
            } else {
                GameRoot.setStyle(newLookStyle); // apply new look
                newLookActive = true;
            }
        }
    }

    private void updateGUI(PokerInfo info) {
        messageHistory.getItems().add(info.getMessage());
    }


    public void startListening(){
        new Thread(()->{
            try{
                ObjectInputStream in = client.getInputStream();
                while(true){
                    PokerInfo info = (PokerInfo) in.readObject();
                    Platform.runLater(() -> {
                        updateGUI(info);
                        updateHands(info);
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void updateHands(PokerInfo info){
        ArrayList<Card> playerHand = info.getPlayerHand();
        ArrayList<Card> dealerHand = info.getDealerHand();

        setCardImage(dealerCard1, dealerHand.get(0), false);
        setCardImage(dealerCard2, dealerHand.get(1), false);
        setCardImage(dealerCard3, dealerHand.get(2), false);

        setCardImage(playerCard1, playerHand.get(0), true);
        setCardImage(playerCard2, playerHand.get(1), true);
        setCardImage(playerCard3, playerHand.get(2), true);
    }

    public void flipDealer(PokerInfo info){
        ArrayList<Card> dealerHand = info.getDealerHand();

        setCardImage(dealerCard1, dealerHand.get(0), true);
        setCardImage(dealerCard2, dealerHand.get(1), true);
        setCardImage(dealerCard3, dealerHand.get(2), true);

    }

    public void setCardImage(Pane cardPane, Card card, boolean faceUp){
        cardPane.getChildren().clear();

        String imagePath;
        if(faceUp){
            imagePath = "/ClientStyles/png/" + card.getCardFile();
            cardPane.setStyle("-fx-background-color: white;");
        } else {
            imagePath = "/ClientStyles/png/back_card.png";
            cardPane.setStyle("-fx-background-color: white;");
        }

        var stream = getClass().getResourceAsStream(imagePath);
        if (stream == null) {
            System.out.println("Could NOT load: " + imagePath);
            return;
        }

        ImageView iv = new ImageView(new javafx.scene.image.Image(stream));
        iv.setFitWidth(150);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        cardPane.getChildren().add(iv);

    }



    // improve this once gui gets updated
    @FXML
    public void handlePlaceWager(ActionEvent event) throws IOException{
        if (client == null || anteBetList.getValue() == null) return;

        PokerInfo info = new PokerInfo();
        info.setAction(ClientAction.PLACE_BET);
        info.setAnteBet(anteBetList.getValue());
        //info.setPairPlusBet(...);

        try{
            client.getOutputStream().writeObject(info);
            client.getOutputStream().flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        drawCards(anteBetList.getValue());
    }

    // improve this once gui gets updated
    public void handleFoldAlex(){
        if (client == null || anteBetList.getValue() == null) return;

        PokerInfo info = new PokerInfo();
        info.setAction(ClientAction.FOLD);
        info.setAnteBet(anteBetList.getValue());
        //info.setPairPlusBet(...);

        try{
            client.getOutputStream().writeObject(info);
            client.getOutputStream().flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
