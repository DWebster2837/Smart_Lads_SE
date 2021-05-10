package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //String var = getClass().getResource("Login.fxml").getPath();
        URL var = Main.class.getResource("fxml/Login.fxml");
        FXMLLoader loader = new FXMLLoader(var);
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 670, 452));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
