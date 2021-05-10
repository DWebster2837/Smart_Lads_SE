package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

import java.io.File;
import java.net.URL;


public class RegistrationController{
    public ImageView registerImageView;
    public TextField usernameTextField;
    public TextField firstnameTextField;
    public TextField lastnameTextField;
    public TextField emailTextBox;
    public PasswordField setPasswordField;
    public TextField weightTextField;
    public RadioButton ppTextField;
    public Button registerButton;
    public Button cancelButton;
    public PasswordField confirmPasswordField;
    public TextField feetTextBox;
    public TextField inchesTextBox;

    public void buttonRegisterClick(){
        String pass = setPasswordField.getText();
        if(pass.equals(confirmPasswordField.getText())) {
            Account.registerUser(usernameTextField.getText(), emailTextBox.getText(), pass);
        }
        else{return;}
        User.curUser.setFirstname(firstnameTextField.getText());
        double height = (Integer.parseInt(feetTextBox.getText())*30.48) + (Integer.parseInt(inchesTextBox.getText())*2.54);
        User.curUser.setHeight_cm(height);
        User.curUser.setWeight_kg(Double.parseDouble(weightTextField.getText()));
        User.curUser.getAccount().setEmail(emailTextBox.getText());
        User.curUser.setFirstname(firstnameTextField.getText());
        User.curUser.setLastname(lastnameTextField.getText());

        //TODO:go to dashboard after register
    }

    //go back to login
    public void buttonCancelClick(){
        try {
            Main.changeStage(Main.class.getResource("fxml/Login.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }
}
