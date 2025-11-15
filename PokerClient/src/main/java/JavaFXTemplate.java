import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.util.Objects;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        try{
            Parent gameRoot = FXMLLoader.load(getClass().getResource("/ClientFXML/WelcomSceneFXML.fxml"));
            Scene  currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(gameRoot);

        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



	}

}
