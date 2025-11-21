import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML private TextField gameLogs;
    @FXML private HBox bottom;          //
    @FXML private TextField serverLogs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub`
//        HBox.setHgrow(numClients, Priority.ALWAYS);
//        HBox.setHgrow(gameLogs, Priority.ALWAYS);
//        HBox.setHgrow(serverLogs, Priority.ALWAYS);
//
//        numClients.setMaxWidth(Double.MAX_VALUE);
//        gameLogs.setMaxWidth(Double.MAX_VALUE);
//        serverLogs.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(middle, Priority.ALWAYS);
    }

    @FXML
    public void startServerMethod()  {
        startServer.setDisable(true);
        startServer.setText("Server Started");
    }

    @FXML
    public void endServerMethod()  {
        // maybe needs to be recursive to end all clients.... or we can just shut down the server?
        endServer.setDisable(true);
        startServer.setText("Server Ended");

        // add logic here
    }

}
