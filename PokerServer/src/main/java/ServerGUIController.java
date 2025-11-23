import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerGUIController implements Initializable {
    @FXML private VBox root;
    @FXML private BorderPane root2;
    @FXML private HBox top;             //
    @FXML private Button startServer;
    @FXML private TextField numClients;
    @FXML private Button endServer;
    @FXML private HBox middle;          //
    @FXML private ListView<String> gameLogs;
    @FXML private HBox bottom;          //
    @FXML private ListView<String> serverLogs;

//    private ServerMain serverMain = new ServerMain();
    private PokerServer server;

    void setServer(PokerServer server){
        this.server = server;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub`
        VBox.setVgrow(middle, Priority.ALWAYS);

//        server = new PokerServer(
//                "localhost",
//                5555,
//                message -> Platform.runLater(() ->serverLogs.getItems().add(message)),
//                count -> Platform.runLater(() -> numClients.setText(count + " Players"))
//        );
//        new Thread(() -> server.run()).start();
    }


    @FXML
    public void startServerMethod()  {
        startServer.setDisable(true);
        startServer.setText("Server Started");
//        serverMain.startServer();
//        new Thread(() -> server.run()).start();
    }

    @FXML
    public void endServerMethod()  {
        // maybe needs to be recursive to end all clients.... or we can just shut down the server?
        endServer.setDisable(true);
        startServer.setText("Server Ended");
        System.out.println("[SUCCESS] Server has closed.");
        Platform.exit();
        System.exit(0);
    }
}
