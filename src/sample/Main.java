package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private DietController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Diet.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Food Diary");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        controller = loader.getController();
        primaryStage.setOnCloseRequest(e-> {
            controller.closeHandle();
            Platform.exit();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }


}
