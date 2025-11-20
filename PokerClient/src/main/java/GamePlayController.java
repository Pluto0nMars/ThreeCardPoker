import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


import java.awt.*;

public class GamePlayController {

    @FXML
    public BorderPane GameRoot;

    @FXML
    ImageView dealerCard1 = new ImageView();
    ImageView dealerCard2 = new ImageView();
    ImageView dealerCard3 = new ImageView();

    @FXML
    ImageView playerCard1 = new ImageView();
    ImageView playerCard2 = new ImageView();
    ImageView playerCard3 = new ImageView();

}
