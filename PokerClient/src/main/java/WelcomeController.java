import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.awt.*;

public class WelcomeController {


    @FXML
    public BorderPane WelcomeRoot;

    @FXML
    private TextField wagerField;

    @FXML
    private Button playButton;



    public void playGameMethod(ActionEvent e) throws IOException{


        String wagerFieldText = wagerField.getText();
        int wager = Integer.parseInt(wagerFieldText);
        if(wager <= 0){
            System.out.println("Wager must be greater than 0");
            return;

        }





    }
}
