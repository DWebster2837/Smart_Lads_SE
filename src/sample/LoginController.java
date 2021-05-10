package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView loginImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    PasswordField userPasswordField;
    @FXML
    TextField usernameTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       File loginFile = new File("Images/Login.png");
       Image loginImage = new Image(loginFile.toURI().toString());
       loginImageView.setImage(loginImage);

        File lockFile = new File("Images/Lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginButtonOnAction(ActionEvent event) {

        if (usernameTextField.getText().isBlank() == false && userPasswordField.getText().isBlank()==false){
            loginMessageLabel.setText("You tried to Login");
        }  else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(){

    }


}
