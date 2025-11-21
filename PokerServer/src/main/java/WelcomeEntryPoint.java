import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class WelcomeEntryPoint extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcomeGUI.fxml"));
            primaryStage.setTitle("Three-Card-Poker Join");
            Scene welcomeScene = new Scene(root, 700,700);
            welcomeScene.getStylesheets().add("/styles/serverStyle_1.css");
            primaryStage.setScene(welcomeScene);
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
	}
}
