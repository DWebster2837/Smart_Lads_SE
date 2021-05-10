package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button userGroupButton;
    public Button dietButton;
    public Button exerciseButton;
    public Button goalButton;
    public Button exitButton;
    public Button logoutButton;
    public Label heightLabel;
    public Label weightLabel;
    public Label bmiLabel;

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
            throw new RuntimeException(e);
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
        /*try {
            Main.changeStage(Main.class.getResource("fxml/viewProgress.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }*/
        //TODO: the distinct lack of goal view
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heightLabel.setText("Height: " + User.curUser.getHeight_cm() + "cm");
        weightLabel.setText("Weight: " + User.curUser.getWeight_kg() + "kg");
        bmiLabel.setText("BMI: " + User.curUser.getWeight_kg() / Math.pow(User.curUser.getHeight_cm()/100, 2));
    }
}
