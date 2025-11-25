import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class winController {

    @FXML
    private Label resultLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Button playAgainButton;

    @FXML
    private Button exitButton;

    private Runnable playAgainCallback;

    public void setResult(boolean won, int amountWon) {
        if (won) {
            resultLabel.setText("YOU WIN!");
            amountLabel.setText("You won $" + amountWon);
            resultLabel.setStyle("-fx-text-fill: green;");
        } else {
            resultLabel.setText("YOU LOST");
            amountLabel.setText("You lost $" + Math.abs(amountWon));
            resultLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void setPlayAgainCallback(Runnable callback) {
        this.playAgainCallback = callback;
    }

    @FXML
    private void initialize() {
        playAgainButton.setOnAction(e -> {
            if (playAgainCallback != null)
                playAgainCallback.run();

            closeWindow();
        });

        exitButton.setOnAction(e -> System.exit(0));
    }
    /*
    * Closing current stage
    * */
    private void closeWindow() {
        Stage stage = (Stage) resultLabel.getScene().getWindow();
        stage.close();
    }
}
