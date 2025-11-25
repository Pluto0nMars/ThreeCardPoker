import garbage.ThreeCardLogic;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import shared.*;
import shared.game.Card;
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

    @FXML
    ComboBox<String> menu;

    @FXML
    Label winnings;

    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private boolean playerFolded = false;
    private int totalBalance = 0;
    private boolean listening = true;

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
        pairPlusBetList.getItems().add(0);
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
//        Deck deck = new Deck();
//        playerHand = deck.hand3();
//        dealerHand = deck.hand3();

        // Prepare UI for a new round — server will provide real cards later.
        // Disable Play/Fold until server deals (or until user can play).
//        playButton.setDisable(true);
//        foldButton.setDisable(true);

//        setCardImage(dealerCard1, dealerHand.get(0), false);
//        fadeIn(dealerCard1, 0);
//
//        setCardImage(dealerCard2, dealerHand.get(1), false);
//        fadeIn(dealerCard2, 150);
//
//        setCardImage(dealerCard3, dealerHand.get(2), false);
//        fadeIn(dealerCard3, 300);
//
//        setCardImage(playerCard1, playerHand.get(0), true);
//        fadeIn(playerCard1, 450);
//
//        setCardImage(playerCard2, playerHand.get(1), true);
//        fadeIn(playerCard2, 600);
//
//        setCardImage(playerCard3, playerHand.get(2), true);
//        fadeIn(playerCard3, 750);

        setCardImage(dealerCard1, null, false);
        fadeIn(dealerCard1, 0);

        setCardImage(dealerCard2, null, false);
        fadeIn(dealerCard2, 150);

        setCardImage(dealerCard3, null, false);
        fadeIn(dealerCard3, 300);

        setCardImage(playerCard1, null, false);
        fadeIn(playerCard1, 450);

        setCardImage(playerCard2, null, false);
        fadeIn(playerCard2, 600);

        setCardImage(playerCard3, null, false);
        fadeIn(playerCard3, 750);

        playerFolded = false;
        messageHistory.getItems().add("Waiting for server to deal cards...");

    }

    public void menuChoice(String c) {
        if ("EXIT".equals(c)) {
            try{
                PokerInfo info = new PokerInfo();
                info.setAction(ClientAction.QUIT);
                info.setMessage("Player quit.");
                client.getOutputStream().writeObject(info);
                client.getOutputStream().flush();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            try{
                listening = false;
                client.getSocket().close();
            } catch (IOException e) {}

            Platform.exit();
            System.exit(0);
            return;


        } else if ("NEW LOOK".equals(c)) {
            if (newLookActive) {
                GameRoot.setStyle(originalStyle); // restore original
                newLookActive = false;
            } else {
                GameRoot.setStyle(newLookStyle); // apply new look
                newLookActive = true;
            }
        } else if ("FRESH START".equals(c)) {
            resetForNewRound();
        }
    }

    private void updateGUI(PokerInfo info) {
        messageHistory.getItems().add(info.getMessage());
    }


    public void startListening() {
        new Thread(() -> {
            try {
                ObjectInputStream in = client.getInputStream();
                while (listening) {
                    PokerInfo info;
                    try{
                        info = (PokerInfo) in.readObject();
                    }catch(IOException e){
                        break;
                    }

                    if (info == null) break;

                    Platform.runLater(() -> {
                        updateGUI(info);
                        updateHands(info);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateHands(PokerInfo info) {
//        ArrayList<Card> playerHand = info.getPlayerHand();
//        ArrayList<Card> dealerHand = info.getDealerHand();
        // later used in handle play and handle fold

        System.out.println("CLIENT: updateHands called");
        System.out.println("CLIENT: info.getPlayerHand() = " + info.getPlayerHand());

        this.playerHand = info.getPlayerHand();
        this.dealerHand = info.getDealerHand();

        // enable play/fold buttons once server cards are received
        playButton.setDisable(false);
        foldButton.setDisable(false);

        if (dealerHand != null && dealerHand.size() >= 3) {
            setCardImage(dealerCard1, dealerHand.get(0), false);
            setCardImage(dealerCard2, dealerHand.get(1), false);
            setCardImage(dealerCard3, dealerHand.get(2), false);
        } else {
            // show backs if malformed
            setCardImage(dealerCard1, null, false);
            setCardImage(dealerCard2, null, false);
            setCardImage(dealerCard3, null, false);
        }

        if (playerHand != null && playerHand.size() >= 3) {
            setCardImage(playerCard1, playerHand.get(0), true);
            setCardImage(playerCard2, playerHand.get(1), true);
            setCardImage(playerCard3, playerHand.get(2), true);
        } else {
            setCardImage(playerCard1, null, false);
            setCardImage(playerCard2, null, false);
            setCardImage(playerCard3, null, false);
        }
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
        if (card == null) {
            // no card. show card back
            imagePath = "/ClientStyles/png/back_card.png";
        }
        else if (faceUp) {
            // show actual card image
            imagePath = "/ClientStyles/png/" + card.getCardFile();
        }
        else {
            // hide card. show back
            imagePath = "/ClientStyles/png/back_card.png";
        }
        cardPane.setStyle("-fx-background-color: white;");

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
        playButton.setDisable(true);
        foldButton.setDisable(true);

        if (client == null || anteBetList.getValue() == null) return;

        PokerInfo info = new PokerInfo();
        info.setAction(ClientAction.PLACE_BET);
        if (anteBetList.getValue() == null) {
            info.setAnteBet(5);
            info.setPlayBet(5);
        } else {
            info.setAnteBet(anteBetList.getValue());
            info.setPlayBet(anteBetList.getValue());
        }
        if (pairPlusBetList.getValue() == null) {
            info.setPairPlusBet(0);
        } else {
            info.setPairPlusBet(pairPlusBetList.getValue());
        }
        info.setMessage("Player places bet Ante:" + info.getAnteBet() + " , Pair Plus: " + info.getPairPlusBet()+ ".");

        try {
            client.getOutputStream().writeObject(info);
            client.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        messageHistory.getItems().add("Wager of Ante: "+ info.getAnteBet() + ", Pair Plus: " + info.getPairPlusBet() + " sent!");
        messageHistory.getItems().add("Waiting for server to deal cards...");
    }

    @FXML
    public void handlePlay(ActionEvent event) throws IOException {
        if (client == null) return;
        foldButton.setDisable(false);

        ArrayList<Card> playerHand = this.playerHand;
        ArrayList<Card> dealerHand= this.dealerHand;

        if (playerHand == null || dealerHand == null){
            System.out.println("ERROR. Pressed play before card got dealt!");
            return;
        }

        // flip dealer cards
        flipDealer(new PokerInfo() {{
            setDealerHand(dealerHand);
        }});

        int anteBet = anteBetList.getValue() != null ? anteBetList.getValue() : 5;
        int pairPlusBet = pairPlusBetList.getValue() != null ? pairPlusBetList.getValue() : 0;
        int playBet = anteBet;

        // Evaluate hand ranks
        int playerRank = ThreeCardLogic.rankHand(playerHand);
        int dealerRank = ThreeCardLogic.rankHand(dealerHand);

        // Dealer qualification (Queen high rank >= 12)
        int dealerHigh = dealerHand.stream().mapToInt(Card::getRank).max().orElse(0);
        boolean dealerQualifies = dealerHigh >= 12;

        int totalWinnings = 0;

        int ppWin = ThreeCardLogic.evalPairPlusBet(playerHand, pairPlusBet);
        if (ppWin > 0) {
            messageHistory.getItems().add("Pair Plus won: +$" + ppWin);
            totalWinnings += ppWin;
        }
        else{
            messageHistory.getItems().add("Pair Plus not won: -$" + pairPlusBet);
            totalWinnings -= pairPlusBet;
        }

        // no win or lose
        if(!dealerQualifies){
            messageHistory.getItems().add("Dealer does not have Queen high.");
            messageHistory.getItems().add("Ante and play wager returned to you!");
        }
//        else if (playerRank == dealerRank){
//            messageHistory.getItems().add("Dealer rank = your rank. Bets returned!");
//        }
        else{
            messageHistory.getItems().add("Dealer has a better hand. You lose -$" + (anteBet + playBet));
            totalWinnings -= (anteBet + playBet);
        }

        String txt = winnings.getText().replace("WINNINGS: $", "").trim();
        int oldWinnings = Integer.parseInt(txt);
        winnings.setText("WINNINGS: $" + (oldWinnings + (totalBalance+totalWinnings)));
        playButton.setDisable(true);
        foldButton.setDisable(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(4.5));
        pause.setOnFinished(e -> resetForNewRound());
        pause.play();

    }


    // if fold lose ante bet and pair plus if yes
    @FXML
    private void handleFold() {
        playerFolded = true;
        playButton.setDisable(true);
        foldButton.setDisable(true);

        int anteBet = anteBetList.getValue();
        int pairBet = pairPlusBetList.getValue() != null ? pairPlusBetList.getValue() : 0;

        messageHistory.getItems().add("You folded. Ante bet -$" + anteBet + "! Pair Plus lost: -$"+pairBet+"!");

        String txt = winnings.getText().replace("WINNINGS: $", "").trim();
        int oldWinnings = Integer.parseInt(txt);
        totalBalance -= (anteBet+pairBet);
        winnings.setText("WINNINGS: $" + (oldWinnings + totalBalance));

        PauseTransition pause = new PauseTransition(Duration.seconds(4.5));
        pause.setOnFinished(e -> resetForNewRound());
        pause.play();
    }

//    private void freshStart() {
//        // Reset total winnings
//        totalBalance = 500;
//        winnings.setText("Total Winnings: $" + totalBalance);
//
//
//        messageHistory.getItems().clear();
//
//
//        playerHand = new ArrayList<>();
//        dealerHand = new ArrayList<>();
//
//        dealerCard1.getChildren().clear();
//        dealerCard2.getChildren().clear();
//        dealerCard3.getChildren().clear();
//        playerCard1.getChildren().clear();
//        playerCard2.getChildren().clear();
//        playerCard3.getChildren().clear();
//        if(!newLookActive){
//            dealerCard1.setStyle(regularPaneStyle);
//            dealerCard2.setStyle(regularPaneStyle);
//            dealerCard3.setStyle(regularPaneStyle);
//            playerCard1.setStyle(regularPaneStyle);
//            playerCard2.setStyle(regularPaneStyle);
//            playerCard3.setStyle(regularPaneStyle);
//        }else{
//            dealerCard1.setStyle(newLookPaneStyle);
//            dealerCard2.setStyle(newLookPaneStyle);
//            dealerCard3.setStyle(newLookPaneStyle);
//            playerCard1.setStyle(newLookPaneStyle);
//            playerCard2.setStyle(newLookPaneStyle);
//            playerCard3.setStyle(newLookPaneStyle);
//        }
//
//
//        anteBetList.setDisable(false);
//        pairPlusBetList.setDisable(false);
//
//        anteBetList.getSelectionModel().clearSelection();
//        pairPlusBetList.getSelectionModel().clearSelection();
//
//        resetForNewHand();
//    }


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

    private void resetForNewRound(){
        String txt = winnings.getText().replace("WINNINGS: $", "").trim();
        int oldWinnings = Integer.parseInt(txt);
        winnings.setText("WINNINGS: $" + oldWinnings);
        // Clear the cards from display
        dealerCard1.getChildren().clear();
        dealerCard2.getChildren().clear();
        dealerCard3.getChildren().clear();
        playerCard1.getChildren().clear();
        playerCard2.getChildren().clear();
        playerCard3.getChildren().clear();

        // Reset card pane styles
        if(!newLookActive){
            dealerCard1.setStyle(regularPaneStyle);
            dealerCard2.setStyle(regularPaneStyle);
            dealerCard3.setStyle(regularPaneStyle);
            playerCard1.setStyle(regularPaneStyle);
            playerCard2.setStyle(regularPaneStyle);
            playerCard3.setStyle(regularPaneStyle);
        } else {
            dealerCard1.setStyle(newLookPaneStyle);
            dealerCard2.setStyle(newLookPaneStyle);
            dealerCard3.setStyle(newLookPaneStyle);
            playerCard1.setStyle(newLookPaneStyle);
            playerCard2.setStyle(newLookPaneStyle);
            playerCard3.setStyle(newLookPaneStyle);
        }

        // Clear hand references
        playerHand = null;
        dealerHand = null;
        playerFolded = false;

        // Re-enable betting controls
        anteBetList.setDisable(false);
        pairPlusBetList.setDisable(false);
        anteBetList.getSelectionModel().clearSelection();
        pairPlusBetList.getSelectionModel().clearSelection();

        // Enable place bet button, disable play/fold
        placeBetButton.setDisable(false);
        playButton.setDisable(true);
        foldButton.setDisable(true);
    }
    /*
    * Need amount and if user won
    * */
    private void showWinPopup(boolean playerWon, int amount) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WinPopup.fxml"));
            Parent root = loader.load();

            winController controller = loader.getController();
            controller.setResult(playerWon, amount);

            controller.setPlayAgainCallback(() -> {
                // Reset game when user clicks play again
                resetForNewRound();
            });

            Stage popup = new Stage();
            popup.setTitle("Game Result");
            popup.setScene(new Scene(root));
            popup.setResizable(false);
            popup.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
