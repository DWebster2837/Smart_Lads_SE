package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupController implements Initializable {

    public Button sendInviteButton;
    @FXML TextField email;
    @FXML TextField groupID;
    @FXML ComboBox groupName;


    public void goBack(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void joinById(ActionEvent actionEvent) {
        String ID = groupID.getText();
    }

    public void sendGroupInvite(ActionEvent actionEvent) {
        String userEmail = email.getText();
        String group = (String) groupName.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
