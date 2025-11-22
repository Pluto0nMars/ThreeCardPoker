import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    ComboBox<Integer> wagerList;

    @FXML
    ComboBox<String> menu;

    @FXML
    Label winnings;

    private PokerClient client;

    public void setClient(PokerClient client) {
        this.client = client;
    }


    void initializeWagersAndMenu(){
        wagerList.getItems().addAll(5, 10, 15, 20, 25);

        menu.getItems().addAll("FRESH START", "NEW LOOK", "EXIT");
        // When the user selects a wager, draw cards
        wagerList.setOnAction(event -> {
            Integer wager = wagerList.getValue();
            if (wager != null) {
                drawCards(wager);
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
        System.out.println("Drawing cards using wager: " + wager);
    }

    @FXML
    public void handleFold(ActionEvent event)throws IOException {
        if(client == null){
            return;
        }

        PokerInfo playerInfo = new PokerInfo();
        playerInfo.setMessage("Player folds");
        playerInfo.setCurrentWager(wagerList.getValue());

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
            GameRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, #1e3c72, #2a5298);");
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
                    Platform.runLater(()->updateGUI(info));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void updateHands(PokerInfo info){
        ArrayList<Card> playerHand = info.getPlayerHand();
        ArrayList<Card> dealerHand = info.getDealerHand();


        setCardImage(playerCard1, playerHand.get(0), true);
        setCardImage(playerCard2, playerHand.get(1), true);
        setCardImage(playerCard3, playerHand.get(2), true);
    }

    public void setCardImage(Pane cardPane, Card card, boolean faceUp){
        cardPane.getChildren().clear();
        String imagePath;
        if(faceUp){
            imagePath = "ClientStyles/png/" + card.getCardFile();
        }
        else{
            imagePath =   "ClientStyles/png/card back red.png";
        }

        ImageView iv = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(imagePath)));
        iv.setFitWidth(120);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
        cardPane.getChildren().add(iv);
    }








}
