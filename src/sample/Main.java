package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    private static Stage _primaryStage;
    //private DietController controller;

    public static Stage getPrimaryStage() {
        return _primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //String var = getClass().getResource("Login.fxml").getPath();
        URL var = Main.class.getResource("fxml/Login.fxml");
        FXMLLoader loader = new FXMLLoader(var);
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 670, 452));
        primaryStage.show();
        _primaryStage = primaryStage;

        /*controller = loader.getController();
        primaryStage.setOnCloseRequest(e-> {
            controller.closeHandle();
            Platform.exit();
        });*/
    }

    //resource, width, height, i think
    public static void changeStage(URL stageResource, Double v, Double v1) throws Exception{
        FXMLLoader loader = new FXMLLoader(stageResource);
        Parent root = loader.load();
        //_primaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            _primaryStage.setScene(new Scene(root, v, v1));
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        //_primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}