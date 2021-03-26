package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ExerciseController implements Initializable {

    String type;
    Duration duration;
    LocalDate date;
    int strokes;
    int distance;
    int calories;
    int steps;

    //Variables that are connected to FXML file components
    @FXML
    public TextField strokesField;
    public Label strokesLabel;
    public ComboBox<String> activityCB = new ComboBox<>();
    public ComboBox<Integer> secondCB = new ComboBox<>();
    public ComboBox<Integer> minuteCB = new ComboBox<>();
    public ComboBox<Integer> hourCB = new ComboBox<>();
    public DatePicker datePicker;
    public TextField distField;
    public Label distLabel;
    public TextField calorieField;
    public Label stepLabel;
    public TextField stepField;
    public Label errorMsg;

    //What are stored in the combo boxes
    ObservableList<String> activityList = FXCollections.observableArrayList("Running","Walking", "Cycling","Gym","Swimming","Yoga","Other");
    ObservableList<Integer> secondList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
            26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
    ObservableList<Integer> minuteList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
            26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
    ObservableList<Integer> hourList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);

    //Running the scene initially
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityCB.setItems(activityList);
        secondCB.setItems(secondList);
        minuteCB.setItems(minuteList);
        hourCB.setItems(hourList);
        //Swimming strokes not available initially
        strokesLabel.setVisible(false);
        strokesField.setVisible(false);
        stepField.setVisible(false);
        stepLabel.setVisible(false);

        errorMsg.setVisible(false);

    }

    public void comboChanged(ActionEvent actionEvent) {
        //Swimming strokes only shown for swimming
        if(activityCB.getValue().equals("Swimming")){
            strokesLabel.setVisible(true);
            strokesField.setVisible(true);
            stepField.setVisible(false);
            stepLabel.setVisible(false);
            strokesLabel.setTranslateY(-45);
            strokesField.setTranslateY(-45);
            distLabel.setVisible(true);
            distField.setVisible(true);
        }
        else if(activityCB.getValue().equals("Running") || activityCB.getValue().equals("Walking")){
            strokesLabel.setVisible(false);
            strokesField.setVisible(false);
            stepField.setVisible(true);
            stepLabel.setVisible(true);
            distField.setVisible(true);
            distLabel.setVisible(true);
        }
        else if(activityCB.getValue().equals("Cycling")){
            strokesLabel.setVisible(false);
            strokesField.setVisible(false);
            stepField.setVisible(false);
            stepLabel.setVisible(false);
            distField.setVisible(true);
            distLabel.setVisible(true);
        }
        else{
            strokesLabel.setVisible(false);
            strokesField.setVisible(false);
            stepField.setVisible(false);
            stepLabel.setVisible(false);
            distLabel.setVisible(false);
            distField.setVisible(false);
        }

    }

    public boolean isFuture(LocalDate date){
        if(LocalDate.now().isBefore(date)){
            return true;
        }
        return false;
    }

    public boolean parsableToInt(String str){
        if(str.matches("\\d+")){         //string that is a number
            return true;
        }
        return false;
    }

    public void add(ActionEvent actionEvent){

        type = activityCB.getValue();

        try{
            duration = Duration.ofSeconds(secondCB.getValue()).plusMinutes(minuteCB.getValue()).plusHours(hourCB.getValue());
        }catch (NullPointerException e){
            duration = Duration.ofSeconds(0);
        }
        System.out.println(duration);
        date = datePicker.getValue();

        if(type==null){//No exercise type chosen
            errorMsg.setVisible(true);
            errorMsg.setText("ERROR: Type of activity has not been set");
        }
        else if(date == null){
            errorMsg.setVisible(true);
            errorMsg.setText("ERROR: Date of exercise not yet chosen");
        }
        else if(isFuture(date)){
            errorMsg.setText("ERROR: Date not yet reached");
        }
        else if(!parsableToInt(calorieField.getText())){
            errorMsg.setText("ERROR: Inputted calories are not numbers");
            errorMsg.setVisible(true);
        }
        else if(!parsableToInt(distField.getText())){
            errorMsg.setText("ERROR: Inputted distances are not numbers");
            errorMsg.setVisible(true);
        }
        else if(!parsableToInt(strokesField.getText()) && type.equals("Swimming")){
            errorMsg.setText("ERROR: Inputted strokes are not numbers");
            errorMsg.setVisible(true);
        }
        else if(!parsableToInt(stepField.getText()) && (type.equals("Running") || type.equals("Walking"))){
            errorMsg.setText("ERROR: Inputted steps are not numbers");
            errorMsg.setVisible(true);
        }else{
            //No errors
            calories = Integer.parseInt(calorieField.getText());

            distance = Integer.parseInt(distField.getText());

            if(type.equals("Swimming")){

                strokes = Integer.parseInt(strokesField.getText());

            }
            else if(type.equals("Running") || type.equals("Walking")){

                steps = Integer.parseInt(stepField.getText());

            }

            errorMsg.setVisible(false);
        }
    }
}