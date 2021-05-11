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
    public Label errorLabel;

    public void buttonRegisterClick(){
        String pass = setPasswordField.getText();
        int feet=0, inches=0, weight=0;
        try{
            feet=Integer.parseInt(feetTextBox.getText());
            inches = Integer.parseInt(inchesTextBox.getText());
        }
        catch(NumberFormatException e){
            errorLabel.setText("Please enter a valid number");
            return;
        }
        if(!pass.equals(confirmPasswordField.getText())){
            errorLabel.setText("Passwords do not match");
            return;
        }
        //check fields not blank and passwords match
        if(!confirmPasswordField.getText().isBlank() &&
                !firstnameTextField.getText().isBlank() &&
                !lastnameTextField.getText().isBlank() &&
                !emailTextBox.getText().isBlank() &&
                !usernameTextField.getText().isBlank() &&
                !firstnameTextField.getText().isBlank())
        {
            Account.registerUser(usernameTextField.getText(), emailTextBox.getText(), pass);
        }
        else{
            errorLabel.setText("Please enter a value for all fields");
            return;
        }
        //set user's variables
        User.curUser.setFirstname(firstnameTextField.getText());
        double height = (Integer.parseInt(feetTextBox.getText())*30.48) + (Integer.parseInt(inchesTextBox.getText())*2.54);
        User.curUser.setHeight_cm(height);
        User.curUser.setWeight_kg(Double.parseDouble(weightTextField.getText()));
        User.curUser.getAccount().setEmail(emailTextBox.getText());
        User.curUser.setFirstname(firstnameTextField.getText());
        User.curUser.setLastname(lastnameTextField.getText());
        User.curUser.saveUser();

        //go to dashboard
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);

        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //go back to login
    public void buttonCancelClick(){
        try {
            Main.changeStage(Main.class.getResource("fxml/Login.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
