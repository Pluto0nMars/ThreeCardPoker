import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ServerGUI.fxml"));
            primaryStage.setTitle("PokerServer GUI");

            Scene s1 = new Scene(root, 500,500);
            s1.getStylesheets().add("/styles/serverGUIstyle.css");
            primaryStage.setScene(s1);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
