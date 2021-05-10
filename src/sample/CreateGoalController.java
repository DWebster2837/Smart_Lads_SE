package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateGoalController extends Goals implements Initializable, Serializable {

    public Button cancelButton;
    public ToggleGroup goalGroup;
    Goals goals = User.curUser.getGoals();

    LocalDate start, end;
    String goalType;
    int targetValue;

    @FXML
    public RadioButton weightRB;
    public RadioButton bmiRB;
    public RadioButton calIntakeRB;
    public RadioButton calBurntRB;
    public RadioButton walkDistRB;
    public RadioButton runDistRB;
    public RadioButton swimDistRB;
    public RadioButton stepsRB;
    public DatePicker startDP;
    public DatePicker endDP;
    public Button selectButton;
    public SplitPane targetChooser;
    public Label chosenDate;
    public TextField targetTF;
    public Label recommendationLB;
    public Label targetLB;
    public Label errorLB;

    public boolean parsableToInt(String str){
        //string that is a number
        return str.matches("\\d+");
    }

    public void selectType(ActionEvent actionEvent) {


        start = startDP.getValue();
        end = endDP.getValue();

        if(start == null || end == null) {
            errorLB.setVisible(true);
            errorLB.setText("Date not chosen properly");
            return;
        }
        if(start.isAfter(end)){
            errorLB.setVisible(true);
            errorLB.setText("Start date is after end date");
            return;
        }

        if (weightRB.isSelected()) {
            goalType = "Weight";
            recommendationLB.setText("Your recommended target weight is");
            targetLB.setText("Target weight:");
        }
        else if (bmiRB.isSelected()) {
            goalType = "BMI";
            recommendationLB.setText("Your recommended target BMI is");
            targetLB.setText("Target BMI:");
        }
        else if (calIntakeRB.isSelected()) {
            goalType = "Calories Intake";
            recommendationLB.setText("Your recommended target calories intake is");
            targetLB.setText("Target calories intake:");
        }
        else if (calBurntRB.isSelected()) {
            goalType = "Calories Burnt";
            recommendationLB.setText("Your recommended target calories burnt is");
            targetLB.setText("Target calories burnt:");
        }
        else if (walkDistRB.isSelected()) {
            goalType = "Walk Distance";
            recommendationLB.setText("Your recommended target walk distance is");
            targetLB.setText("Target walk distance:");
        }
        else if (runDistRB.isSelected()) {
            goalType = "Run Distance";
            recommendationLB.setText("Your recommended target run distance is");
            targetLB.setText("Target run distance:");
        }
        else if (swimDistRB.isSelected()) {
            goalType = "Swim Distance";
            recommendationLB.setText("Your recommended target swim distance is");
            targetLB.setText("Target swim distance:");
        }
        else if (stepsRB.isSelected()) {
            goalType = "Steps";
            recommendationLB.setText("Your recommended target steps is");
            targetLB.setText("Target steps:");
        }
        else{
            errorLB.setText("Goal type not chosen yet");
            errorLB.setVisible(true);
            return;
        }

        targetChooser.setVisible(true);
        chosenDate.setText(start + " to " + end);

        errorLB.setVisible(false);

    }

    public void createGoal(ActionEvent actionEvent){

        if(!parsableToInt(targetTF.getText())){
            errorLB.setText("Target value is not a number");
            errorLB.setVisible(true);
            return;
        }
        targetValue = Integer.parseInt(targetTF.getText());

        Goal goal = new Goal(targetValue, start, end, goalType, "On Track", 0);

        goals.addGoal(goal);

        User.curUser.saveUser();

        for (Goal g : goals.getGoals()) {
            System.out.println(g);
        }

        targetChooser.setVisible(false);

        errorLB.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        targetChooser.setVisible(false);

        errorLB.setVisible(false);

        try {
            for (Goal g : goals.getGoals()) {
                if(g.end.isBefore(LocalDate.now()) && g.currentValue < g.targetValue && g.state.equals("On Track")){
                    g.state = "Failed";
                }
                goals.addGoal(g);
                System.out.println(g);
            }
        } catch (Exception e) {
            System.out.println("No goals");
        }

    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
    }
}