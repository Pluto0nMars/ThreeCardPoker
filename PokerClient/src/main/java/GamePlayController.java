import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

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
    Button resetButton;

    @FXML
    ListView<String> messageHistory;

    @FXML
    ComboBox<Integer> wagerList;

    private PokerClient client;

    public void setClient(PokerClient client) {
        this.client = client;
    }


    void initializeWagers(){
        wagerList.getItems().addAll(5, 10, 15, 20, 25);
        // When the user selects a wager, draw cards
        wagerList.setOnAction(event -> {
            Integer wager = wagerList.getValue();
            if (wager != null) {
                drawCards(wager);
            }
        });
    }

    @FXML
    public void initialize() {
        initializeWagers();
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








}
