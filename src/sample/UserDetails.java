package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class UserDetails implements Initializable {
    public TextField feetBox;
    public TextField inchesBox;
    public TextField weightBox;
    public TextField firstnameBox;
    public TextField surnameBox;
    public Label errorLabel;

    public void cancelButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void applyButtonClicked(ActionEvent actionEvent) {
        int feet = 0;
        double inches = 0;
        double weight = 0;
        try{
            feet = Integer.parseInt(feetBox.getText());
            inches = Double.parseDouble(inchesBox.getText());
            weight = Double.parseDouble(weightBox.getText());
        }
        catch(NumberFormatException e){
            errorLabel.setText("Please enter valid numbers");
            return;
        }
        if(!firstnameBox.getText().isBlank() && !surnameBox.getText().isBlank()){
            User.curUser.setHeight_cm(feet * 30.48 + inches * 2.54);
            User.curUser.setWeight_kg(weight);
            User.curUser.setFirstname(firstnameBox.getText());
            User.curUser.setLastname(surnameBox.getText());
            User.curUser.saveUser();
            errorLabel.setText("");
        }
        else{
            errorLabel.setText("Please fill all boxes");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double feet = Math.floor(User.curUser.getHeight_cm()/30.48);
        double inches = (User.curUser.getHeight_cm() % 30.48)/2.54;
        DecimalFormat format = new DecimalFormat("#");
        feetBox.setText(format.format(feet));
        inchesBox.setText(format.format(inches));
        weightBox.setText(format.format(User.curUser.getWeight_kg()));
        firstnameBox.setText(User.curUser.getFirstname());
        surnameBox.setText(User.curUser.getLastname());
    }
}
