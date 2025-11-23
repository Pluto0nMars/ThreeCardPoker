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
import shared.game.*;
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
    Button foldButton;

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

    private ArrayList<Card> playerHand;

    private ArrayList<Card> dealerHand;


    int totalWinnings = 0;


    private boolean newLookActive = false;
    private final String originalStyle = "-fx-background-color: linear-gradient(to bottom right, #2e8b57,  #008000);";
    private final String newLookStyle = "-fx-background-color: linear-gradient(to bottom right, #00bfff, #2a5298);";

    private PokerClient client;

    public void setClient(PokerClient client) {
        this.client = client;
    }


    void initializeWagersAndMenu() {
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
                updateButtonStates();
                drawCards(anteBet);
            }
        });

        menu.setOnAction(event -> {
            String choice = menu.getValue();
            if (choice != null) {
                menuChoice(choice);
            }
        });
    }

    @FXML
    public void initialize() {
        initializeWagersAndMenu();
        updateButtonStates();
    }


    void drawCards(int wager) {
        Deck deck = new Deck();
        playerHand = deck.hand3();
        dealerHand = deck.hand3();


        setCardImage(dealerCard1, dealerHand.get(0), false);
        setCardImage(dealerCard2, dealerHand.get(1), false);
        setCardImage(dealerCard3, dealerHand.get(2), false);


        setCardImage(playerCard1, playerHand.get(0), true);
        setCardImage(playerCard2, playerHand.get(1), true);
        setCardImage(playerCard3, playerHand.get(2), true);

    }

    public void menuChoice(String c) {
        if ("EXIT".equals(c)) {
            if (client == null) {
                return;
            }

            PokerInfo info = new PokerInfo();
            info.setAction(ClientAction.QUIT);
            info.setMessage("Player quit.");

            try {
                Platform.exit();
                System.exit(0);
                client.getOutputStream().writeObject(info);
                client.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if ("NEW LOOK".equals(c)) {
            if (newLookActive) {
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


    public void startListening() {
        new Thread(() -> {
            try {
                ObjectInputStream in = client.getInputStream();
                while (true) {
                    PokerInfo info = (PokerInfo) in.readObject();
                    Platform.runLater(() -> {
                        System.out.println("Received message: " + info.getMessage()); // <- DEBUG
                        updateGUI(info);
                        updateHands(info);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateHands(PokerInfo info) {
        ArrayList<Card> playerHand = info.getPlayerHand();
        ArrayList<Card> dealerHand = info.getDealerHand();

        setCardImage(dealerCard1, dealerHand.get(0), false);
        setCardImage(dealerCard2, dealerHand.get(1), false);
        setCardImage(dealerCard3, dealerHand.get(2), false);

        setCardImage(playerCard1, playerHand.get(0), true);
        setCardImage(playerCard2, playerHand.get(1), true);
        setCardImage(playerCard3, playerHand.get(2), true);
    }

    public void flipDealer(PokerInfo info) {
        ArrayList<Card> dealerHand = info.getDealerHand();

        setCardImage(dealerCard1, dealerHand.get(0), true);
        setCardImage(dealerCard2, dealerHand.get(1), true);
        setCardImage(dealerCard3, dealerHand.get(2), true);

    }

    public void setCardImage(Pane cardPane, Card card, boolean faceUp) {
        cardPane.getChildren().clear();

        String imagePath;
        if (faceUp) {
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
    public void handlePlaceWager(ActionEvent event) throws IOException {
        System.out.println("HANDLE WAGER button clicked!");
        if (client == null || anteBetList.getValue() == null) return;

        PokerInfo info = new PokerInfo();
        info.setAction(ClientAction.PLACE_BET);
        if (anteBetList.getValue() == null) {
            info.setAnteBet(5);
        } else {
            info.setAnteBet(anteBetList.getValue());
        }
        if (pairPlusBetList.getValue() == null) {
            info.setPairPlusBet(5);
        } else {
            info.setPairPlusBet(pairPlusBetList.getValue());
        }
        info.setMessage("Player bets");

        System.out.println(info.getAction() + info.getMessage() + info.getPlayerHand() + info.getDealerHand() + info.getPairPlusBet() + info.getAnteBet());
        try {
            client.getOutputStream().writeObject(info);
            client.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawCards(anteBetList.getValue());
    }


    @FXML
    public void handlePlay(ActionEvent event) throws IOException {
        System.out.println("PLAY button clicked!");
        if (client == null) return;


        Deck deck = new Deck();
        ArrayList<Card> playerHand = deck.hand3();
        ArrayList<Card> dealerHand = deck.hand3();
        foldButton.setDisable(false);


        setCardImage(playerCard1, playerHand.get(0), true);
        setCardImage(playerCard2, playerHand.get(1), true);
        setCardImage(playerCard3, playerHand.get(2), true);

        flipDealer(new PokerInfo() {{
            setDealerHand(dealerHand);
        }}); // flip after drawing


        PokerInfo info = new PokerInfo();
        info.setAction(ClientAction.PLAY);
        info.setAnteBet(anteBetList.getValue() != null ? anteBetList.getValue() : 5);
        info.setPairPlusBet(pairPlusBetList.getValue() != null ? pairPlusBetList.getValue() : 5);
        info.setMessage("Player plays");

        info.setPlayerHand(playerHand);
        info.setDealerHand(dealerHand);

        int playerRank = ThreeCardLogic.rankHand(playerHand);
        int dealerRank = ThreeCardLogic.rankHand(dealerHand);

        int anteBet = anteBetList.getValue() != null ? anteBetList.getValue() : 5;
        int totalWinnings;
        if (playerRank > dealerRank) {
            totalWinnings = anteBet * 2;
            info.setMessage("You win!");
        } else if (playerRank == dealerRank) {
            totalWinnings = 0;
            info.setMessage("Tie!");
        } else {
            totalWinnings = -anteBet;
            info.setMessage("You lose!");
        }

        winnings.setText("$" + totalWinnings);

    }

    @FXML
    private void handleFold() {
        playButton.setDisable(true);
        foldButton.setDisable(true);

        messageHistory.getItems().add("You folded. Ante and Pair Plus wagers are resolved.");

        int ppWinnings = ThreeCardLogic.evalPairPlusWinnings(
                playerHand,
                pairPlusBetList.getValue() != null ? pairPlusBetList.getValue() : 5
        );


        if (ppWinnings > 0) {
            messageHistory.getItems().add("Pair Plus won: $" + ppWinnings);
            totalWinnings += ppWinnings;
        }

        winnings.setText("Total Winnings: $" + totalWinnings);


    }

    private void updateButtonStates() {
        boolean wagerSelected = anteBetList.getValue() != null;
        playButton.setDisable(!wagerSelected);  // Only enable play if a wager is selected
        foldButton.setDisable(true);            // Fold starts disabled until a hand is dealt
    }

    private void resetForNewHand() {
        anteBetList.getSelectionModel().clearSelection();
        playButton.setDisable(true);
        foldButton.setDisable(true);
    }
}
