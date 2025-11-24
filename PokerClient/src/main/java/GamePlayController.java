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
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


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
    Button placeBetButton;

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

    private boolean handInProgress = false;

    int totalBalance = 500;

    private boolean newLookActive = false;
    private final String originalStyle = "-fx-background-color: linear-gradient(to bottom right, #2e8b57,  #008000);";
    private final String newLookStyle = "-fx-background-color: linear-gradient(to bottom right, #00bfff, #2a5298);";
    private final String regularPaneStyle ="-fx-background-color: #008000;" +
                                 "-fx-border-color: white;" +
                                 "-fx-border-style: dashed;" +
                                   "-fx-border-width: 3;";
    private final String newLookPaneStyle ="-fx-background-color: #00bfff;" +
            "-fx-border-color: white;" +
            "-fx-border-style: dashed;" +
            "-fx-border-width: 3;";
    private PokerClient client;

    public void setClient(PokerClient client) {
        this.client = client;
    }


    void initializeWagersAndMenu() {
        for (int i=5; i<=25; i++){
            anteBetList.getItems().add(i);
            pairPlusBetList.getItems().add(i);
        }


        menu.getItems().addAll("FRESH START", "NEW LOOK", "EXIT");

        // When the user selects a wager, draw cards
//                                                                          MIGHT WANT TO ONLY DO THIS ON PLAY BUTTON. BC NEED TO SEND PACKET TO SERVER
        anteBetList.setOnAction(event -> {
            Integer anteBet = anteBetList.getValue();
            Integer pairPlusBet = pairPlusBetList.getValue();

//                updateButtonStates();
                //drawCards(anteBet);
                placeBetButton.setDisable(false);

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

    private void fadeIn(Pane pane, double delayMs) {
        FadeTransition ft = new FadeTransition(Duration.millis(300), pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setDelay(Duration.millis(delayMs));
        ft.play();
    }

    private void flipCardAnimation(Pane pane, Runnable afterHalfFlip) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), pane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(e -> {
            afterHalfFlip.run();
            fadeIn.play();
        });

        fadeOut.play();
    }

    void drawCards(int wager) {
        Deck deck = new Deck();
        playerHand = deck.hand3();
        dealerHand = deck.hand3();


        setCardImage(dealerCard1, dealerHand.get(0), false);
        fadeIn(dealerCard1, 0);

        setCardImage(dealerCard2, dealerHand.get(1), false);
        fadeIn(dealerCard2, 150);

        setCardImage(dealerCard3, dealerHand.get(2), false);
        fadeIn(dealerCard3, 300);

        setCardImage(playerCard1, playerHand.get(0), true);
        fadeIn(playerCard1, 450);

        setCardImage(playerCard2, playerHand.get(1), true);
        fadeIn(playerCard2, 600);

        setCardImage(playerCard3, playerHand.get(2), true);
        fadeIn(playerCard3, 750);


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
        } else if ("FRESH START".equals(c)) {
            freshStart();
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


        flipCardAnimation(dealerCard1, () ->
                setCardImage(dealerCard1, dealerHand.get(0), true)
        );


        PauseTransition p2 = new PauseTransition(Duration.millis(150));
        p2.setOnFinished(e ->
                flipCardAnimation(dealerCard2, () ->
                        setCardImage(dealerCard2, dealerHand.get(1), true)
                )
        );
        p2.play();

        PauseTransition p3 = new PauseTransition(Duration.millis(300));
        p3.setOnFinished(e ->
                flipCardAnimation(dealerCard3, () ->
                        setCardImage(dealerCard3, dealerHand.get(2), true)
                )
        );
        p3.play();
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
        placeBetButton.setDisable(true);
        playButton.setDisable(false);
        foldButton.setDisable(false);

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

        if(anteBetList.getValue() != null){
            anteBetList.setDisable(true);
        }
        if(pairPlusBetList.getValue() != null){
            pairPlusBetList.setDisable(true);
        }
        if(anteBetList.getValue() != null && pairPlusBetList.getValue() != null){
            placeBetButton.setDisable(true);
        }



    }


    @FXML
    public void handlePlay(ActionEvent event) throws IOException {
        System.out.println("PLAY button clicked!");
        if (client == null) return;


        Deck deck = new Deck();
        ArrayList<Card> playerHand = deck.hand3();
        ArrayList<Card> dealerHand = deck.hand3();
        foldButton.setDisable(false);
//
//        setCardImage(dealerCard1, dealerHand.get(0), true);
//        setCardImage(dealerCard2, dealerHand.get(1), true);
//        setCardImage(dealerCard3, dealerHand.get(2), true);

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
            messageHistory.getItems().add("You win!");
        } else if (playerRank == dealerRank) {
            totalWinnings = 0;
            messageHistory.getItems().add("Tie!");
        } else {
            totalWinnings = -anteBet;
            messageHistory.getItems().add("You lose!");
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
            totalBalance += ppWinnings;
        }
        winnings.setText("Total Winnings: $" + totalBalance);
    }

    private void freshStart() {
        // Reset total winnings
        totalBalance = 0;
        winnings.setText("Total Winnings: $0");


        messageHistory.getItems().clear();


        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();



        dealerCard1.getChildren().clear();
        dealerCard2.getChildren().clear();
        dealerCard3.getChildren().clear();
        playerCard1.getChildren().clear();
        playerCard2.getChildren().clear();
        playerCard3.getChildren().clear();
        if(!newLookActive){
            dealerCard1.setStyle(regularPaneStyle);
            dealerCard2.setStyle(regularPaneStyle);
            dealerCard3.setStyle(regularPaneStyle);
            playerCard1.setStyle(regularPaneStyle);
            playerCard2.setStyle(regularPaneStyle);
            playerCard3.setStyle(regularPaneStyle);
        }else{
            dealerCard1.setStyle(newLookPaneStyle);
            dealerCard2.setStyle(newLookPaneStyle);
            dealerCard3.setStyle(newLookPaneStyle);
            playerCard1.setStyle(newLookPaneStyle);
            playerCard2.setStyle(newLookPaneStyle);
            playerCard3.setStyle(newLookPaneStyle);
        }


        anteBetList.setDisable(false);
        pairPlusBetList.setDisable(false);

        anteBetList.getSelectionModel().clearSelection();
        pairPlusBetList.getSelectionModel().clearSelection();

        resetForNewHand();
    }


    private void updateButtonStates() {
        boolean wagerSelected = anteBetList.getValue() != null;

//        if(placeBet.isDisable()){
//
//        }

//        if placeBet.isD
        playButton.setDisable(!wagerSelected);  // Only enable play if a wager is selected
        foldButton.setDisable(!wagerSelected);            // Fold starts disabled until a hand is dealt
    }

    private void resetForNewHand() {
        anteBetList.getSelectionModel().clearSelection();
        playButton.setDisable(true);
        foldButton.setDisable(true);
    }
}
