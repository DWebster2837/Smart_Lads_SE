package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;



public class AddExerciseController extends Exercises implements Initializable{
    //@Serial
    //private static final long serialVersionUID = 1L;

    Exercises exercises = User.curUser.getExercises();

    String type;
    Duration duration;
    LocalDate date;
    int strokes;
    int distance;
    int calories;
    int steps;

    Exercise exercise;

    Goals gs;

    Boolean b = false;

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
    public Label message;
    public Label success;
    public SplitPane goalFinish;
    public Label congrats;
    public Button accept;

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
        message.setVisible(false);
        success.setVisible(false);
        goalFinish.setVisible(false);

        //load exercises from database
        //for(Exercise ex: User.curUser.getExercises().getMultipleExercises()) {
        //    exercises.addExercise(ex);
        //    System.out.println(ex);
        //}
        //exercises = User.curUser.getExercises();
        for (Goal g : User.curUser.getGoals().getGoalsSet()) {
            if (g.end.isBefore(LocalDate.now()) && g.currentValue < g.targetValue && g.state.equals("On Track")) {
                g.state = "Failed";
            }
            System.out.println(g);
        }


    }

    public void comboChanged(ActionEvent actionEvent) {

        //Swimming strokes only shown for swimming
        switch (activityCB.getValue()) {
            case "Swimming" -> {
                strokesLabel.setVisible(true);
                strokesField.setVisible(true);
                stepField.setVisible(false);
                stepLabel.setVisible(false);
                strokesLabel.setTranslateY(-45);
                strokesField.setTranslateY(-45);
                distLabel.setVisible(true);
                distField.setVisible(true);
            }
            case "Running", "Walking" -> {
                strokesLabel.setVisible(false);
                strokesField.setVisible(false);
                stepField.setVisible(true);
                stepLabel.setVisible(true);
                distField.setVisible(true);
                distLabel.setVisible(true);
            }
            case "Gym" -> {
                strokesLabel.setVisible(false);
                strokesField.setVisible(false);
                stepField.setVisible(false);
                stepLabel.setVisible(false);
                distField.setVisible(false);
                distLabel.setVisible(false);
            }
            case "Cycling" -> {
                strokesLabel.setVisible(false);
                strokesField.setVisible(false);
                stepField.setVisible(false);
                stepLabel.setVisible(false);
                distField.setVisible(true);
                distLabel.setVisible(true);
            }
            default -> {
                strokesLabel.setVisible(false);
                strokesField.setVisible(false);
                stepField.setVisible(false);
                stepLabel.setVisible(false);
                distLabel.setVisible(true);
                distField.setVisible(true);
            }
        }

    }

    public boolean isFuture(LocalDate date){
        return LocalDate.now().isBefore(date);
    }

    public boolean parsableToInt(String str){
        //string that is a number
        return str.matches("\\d+");
    }

    public void add(ActionEvent actionEvent) throws IOException, ClassNotFoundException, InterruptedException {
        success.setVisible(false);
        type = activityCB.getValue();

        try{
            duration = Duration.ofSeconds(secondCB.getValue()).plusMinutes(minuteCB.getValue()).plusHours(hourCB.getValue());
        }catch (NullPointerException e){
            duration = Duration.ofSeconds(0);
        }
        System.out.println(duration);
        date = datePicker.getValue();

        if(type==null){//No exercise type chosen
            message.setVisible(true);
            message.setText("ERROR: Type of activity has not been set");
        }
        else if(date == null){
            message.setVisible(true);
            message.setText("ERROR: Date of exercise not yet chosen");
        }
        else if(isFuture(date)){
            message.setVisible(true);
            message.setText("ERROR: Date not yet reached");
        }
        else if(!parsableToInt(calorieField.getText())){
            message.setText("ERROR: Inputted calories is not numbers");
            message.setVisible(true);
        }
        else if(!parsableToInt(distField.getText()) && !type.equals("Yoga") && !type.equals("Gym")){
            message.setText("ERROR: Inputted distances is not numbers");
            message.setVisible(true);
        }
        else if(!parsableToInt(strokesField.getText()) && type.equals("Swimming")){
            message.setText("ERROR: Inputted strokes is not numbers");
            message.setVisible(true);
        }
        else if(!parsableToInt(stepField.getText()) && (type.equals("Running") || type.equals("Walking"))){
            message.setText("ERROR: Inputted steps is not numbers");
            message.setVisible(true);
        }else{
            //No errors
            calories = Integer.parseInt(calorieField.getText());
            System.out.println("calories: " + calories);
            if(!type.equals("Yoga")  && !type.equals("Gym")){
                distance = Integer.parseInt(distField.getText());
            }

            if(type.equals("Swimming")){

                strokes = Integer.parseInt(strokesField.getText());

            }
            else if(type.equals("Running") || type.equals("Walking")){

                steps = Integer.parseInt(stepField.getText());

            }

            //Add exercise to the array
            exercise = new Exercise(type, date, duration, calories, strokes, distance, steps);

            exercises.addExercise(exercise);
            User.curUser.saveUser();

            //for(Exercise ex: loadExercises()){
            //    System.out.println(ex);
            //}

            String verb = "";
            String unit = "";
            gs = new Goals();

            for(Goal g: User.curUser.getGoals().getGoalsSet()){
                if(g.state.equals("On Track") && (g.start.equals(exercise.date) || g.start.isBefore(exercise.date))
                        && (g.end.isAfter(exercise.date) || g.end.equals(exercise.date))){

                    int initial = g.currentValue;

                    if(exercise.type.equals("Walking") && g.goalType.equals("Walk Distance")){
                        g.currentValue += exercise.distance;
                        verb = "walked";
                        unit = "metre";
                    }
                    else if(exercise.type.equals("Running") && g.goalType.equals("Run Distance")) {
                        g.currentValue += exercise.distance;
                        verb = "ran";
                        unit = "metre";
                    }
                    else if(exercise.type.equals("Swimming") && g.goalType.equals("Swim Distance")) {
                        g.currentValue += exercise.distance;
                        verb = "swam";
                        unit = "metre";
                    }

                    if((exercise.type.equals("Walking")||exercise.type.equals("Running")) && g.goalType.equals("Steps")){
                        g.currentValue += exercise.steps;
                        verb = "did";
                        unit = "steps";
                    }

                    if(g.goalType.equals("Calories Burnt")){
                        g.currentValue += exercise.caloriesBurnt;
                        verb = "burnt";
                        unit = "calories";
                    }

                    if(g.currentValue >= g.targetValue){
                        g.state = "Beaten";

                        congrats.setText("Congratulations! You " + verb + " " + g.currentValue + " " + unit + " from " + g.start + " to "
                                + g.end);

                        goalFinish.setVisible(true);

                    }

                    if(g.dataAdded == null){
                        g.dataAdded = new HashMap<>();
                    }

                    int finalValue = g.currentValue;

                    g.dataAdded.put(exercise.date, finalValue - initial);
                }

                gs.addGoal(g);
            }
            //saveGoals(gs);
            User.curUser.saveUser();

            //for(Goal goals : loadGoals()){
            //    System.out.println(goals);
            //}

            message.setVisible(false);

            success.setVisible(true);

        }
    }

    /*public void replaceSceneContent(String fxml) throws Exception {
        Parent page;
        page = FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
        Stage stage = Main.stage;
        stage.setScene(new Scene(page));
        stage.show();

    }*/

    public void onAccept(ActionEvent actionEvent){

        //replaceSceneContent("CreateGoal.fxml");
        try {
            Main.changeStage(Main.class.getResource("fxml/CreateGoal.fxml"), 334d, 451d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onDecline(ActionEvent actionEvent){
        goalFinish.setVisible(false);
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/viewExercise.fxml"), 335d, 448d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
