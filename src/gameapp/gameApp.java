package gameapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class gameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("zillionare.fxml"));

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("zillionare game");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
