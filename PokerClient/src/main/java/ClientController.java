import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ClientController {
    @FXML
    public BorderPane root;

    @FXML
    private  TextField ipTextField;

    @FXML
    private  TextField portTextField;

    @FXML
    private Button joinButton;

    private PokerClient client = new PokerClient();

    private boolean connectToServer(){

        try{
            String ip = ipTextField.getText().trim();
            int port = Integer.parseInt(portTextField.getText());
            Socket socketClient = new Socket(ip, port);

            System.out.println("Successful Connection!");
            return true;

        }catch (Exception e){

            System.err.println("Connection failed: " + e.getMessage());
            return false;
        }

//        client.connectToServer(ip, port);

    }

    public void joinMethod(ActionEvent e) throws IOException{
        try{
            if(connectToServer()){
                Parent root = FXMLLoader.load(getClass().getResource("/ClientFXML/GamePlay.fxml"));
                Scene welcomeScene = new Scene(root, 700,700);

                welcomeScene.getStylesheets().add("/clientStyles/welcomeStyle.css");
                Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                currentStage.setScene(welcomeScene);
            }
        } catch (Exception ex) {
            System.err.println("Error Connecting: " + ex.getMessage());
        }
    }


}
