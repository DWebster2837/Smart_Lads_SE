package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {
    public Button userGroupButton;
    public Button dietButton;
    public Button exerciseButton;
    public Button goalButton;
    public Button exitButton;
    public Button logoutButton;

    public void userGroupButtonClick(ActionEvent actionEvent) {
        try {
            //TODO: make the fukin group window
            Main.changeStage(Main.class.getResource("fxml/Login.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void dietButtonClick(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Diet.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void exerciseButtonClick(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/viewExercise.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void goalButtonClick(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/viewProgress.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void exitButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void logoutButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Login.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }
}
