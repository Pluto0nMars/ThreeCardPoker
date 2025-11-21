import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;

public class GamePlayController {

    @FXML
    public BorderPane GameRoot;

    @FXML
    ImageView dealerCard1 = new ImageView();

    @FXML
    ImageView dealerCard2 = new ImageView();

    @FXML
    ImageView dealerCard3 = new ImageView();

    @FXML
    ImageView playerCard1 = new ImageView();

    @FXML
    ImageView playerCard2 = new ImageView();

    @FXML
    ImageView playerCard3 = new ImageView();

    @FXML
    Button drawButton = new Button();

    @FXML
    Button resetButton = new Button();

    @FXML
    ListView<String> messageHistory = new ListView<>();

    @FXML
    ComboBox<Integer> wagerList = new ComboBox<>();
    void initializeWagers(){
        wagerList.getItems().addAll(5, 10, 15, 20, 25);
    }




}
