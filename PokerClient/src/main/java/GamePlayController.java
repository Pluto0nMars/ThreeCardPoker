import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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







}
