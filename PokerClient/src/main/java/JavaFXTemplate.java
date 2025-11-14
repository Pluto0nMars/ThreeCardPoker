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

import java.util.Objects;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/ClientFXML/clientFXML.fxml"));
            primaryStage.setTitle("Three-Card-Poker Join");
            Scene welcomeScene = new Scene(root, 700,700);

            welcomeScene.getStylesheets().add("/clientStyles/clientStyle_1.css");
            primaryStage.setScene(welcomeScene);
            primaryStage.show();


        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



	}

}
