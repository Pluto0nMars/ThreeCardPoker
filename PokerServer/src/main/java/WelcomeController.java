import javafx.application.Platform;
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
import java.io.IOException;
import java.util.function.Consumer;


public class WelcomeController {
    @FXML public BorderPane root;
    @FXML private  TextField ipTextField;
    @FXML private  TextField portTextField;
    @FXML private Button joinButton;

    private boolean serverRunning = false;

    private boolean startServer(){
        try{
            String ip = ipTextField.getText().trim();
            int port = Integer.parseInt(portTextField.getText());

            // the main thread of the server. There is only 1 server every running
            new Thread( () -> {
                try {
                    Consumer<String> logger = message -> {
                        System.out.println("[LOG] " + message);
                    };
                    Consumer<Integer> clientCounter = count -> { };
                    PokerServer server = new PokerServer(ip, port, logger, clientCounter);
                    serverRunning = true;
                    server.run();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    serverRunning = false;
                }
            }).start();

            System.out.println("[SUCCESS] Server started on " + ip + ":" + port);
            return true;
        }
        catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            return false;
        }
    }

    /*
        When user clicks Join, we take them to another scene, the serverGUI/ dashboard. We load fxml file
        @ Param : our button click event e
     */
    public void joinMethod(ActionEvent e) throws IOException{
        try {
            String ip = ipTextField.getText().trim();
            int port = Integer.parseInt(portTextField.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ServerGUI.fxml"));
            Parent root = loader.load();

            ServerGUIController controller = loader.getController();

            PokerServer server = new PokerServer(
                    ip,
                    port,
                    message -> Platform.runLater(() -> controller.serverLogs.getItems().add(message)),
                    count -> Platform.runLater(() -> controller.numClients.setText(count + " Players"))
            );

            controller.setServer(server);

            new Thread(() -> server.run()).start();

            Scene dashboard = new Scene(root, 700,700);
            dashboard.getStylesheets().add("/styles/serverGUIstyle.css");
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            currentStage.setScene(dashboard);
        }
        catch(Exception err){
            System.err.println("Failed to start server: " + err.getMessage());
        }


//            if(startServer()){
//                Parent root = FXMLLoader.load(getClass().getResource("/FXML/ServerGUI.fxml"));
//                Scene welcomeScene = new Scene(root, 700,700);
//
//                welcomeScene.getStylesheets().add("/styles/serverGUIstyle.css");
//                Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//                currentStage.setScene(welcomeScene);
//            }
//        } catch (Exception ex) {
//            System.err.println("Error Connecting: " + ex.getMessage());
    }


//    public void joinMethod(ActionEvent e) throws IOException{
//        try{
//            if(startServer()){
//                Parent root = FXMLLoader.load(getClass().getResource("/FXML/ServerGUI.fxml"));
//                Scene welcomeScene = new Scene(root, 700,700);
//
//                welcomeScene.getStylesheets().add("/styles/serverGUIstyle.css");
//                Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//                currentStage.setScene(welcomeScene);
//            }
//        } catch (Exception ex) {
//            System.err.println("Error Connecting: " + ex.getMessage());
//        }
//    }
}
