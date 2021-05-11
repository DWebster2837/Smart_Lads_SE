package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

public class ViewExerciseController implements Initializable {


    @FXML
    public ListView<Exercise> listExercise;
    public DatePicker date;
    public SplitPane exercisePopUp;
    public Button close;
    public Label popupTitle, duration, calories, distance, other;
    public Button removeBtn;
    public Button showAllBtn;

    //ObservableList<Exercise> exercisesList = FXCollections.observableArrayList(Exercises.loadExercises());
    Exercises exer = User.curUser.getExercises();

    public ViewExerciseController() throws IOException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        listExercise.setItems(FXCollections.observableArrayList(exer.getMultipleExercises()));
        //for( Exercise e : exer.getMultipleExercises()){
        //    System.out.println(e);
        //}
        distance.setVisible(false);
        other.setVisible(false);
        exercisePopUp.setVisible(false);
    }

    public boolean isFuture(LocalDate date){
        return LocalDate.now().isBefore(date);
    }


    public void selectExercise(MouseEvent mouseEvent) {
        Exercise ex = listExercise.getSelectionModel().getSelectedItem();

        if(ex == null)
        {
            return;
        }
        exercisePopUp.setVisible(true);

        popupTitle.setText(ex.type + " " + ex.date);

        if(ex.duration.toHoursPart() == 0 && ex.duration.toMinutesPart() == 0){
            duration.setText(ex.duration.toSecondsPart() + " s ");
        }
        else if(ex.duration.toHoursPart() == 0){
            duration.setText(ex.duration.toMinutesPart() + " mins " + ex.duration.toSecondsPart() + " secs ");
        }
        else{
            duration.setText(ex.duration.toHoursPart() + " hrs " + ex.duration.toMinutesPart() + " mins " + ex.duration.toSecondsPart() + " secs ");
        }
        calories.setText(ex.caloriesBurnt + " calories");

        switch (ex.type) {
            case "Running", "Walking" -> {
                distance.setVisible(true);
                distance.setText(ex.distance + " m");
                other.setVisible(true);
                other.setText(ex.steps + " steps");
            }
            case "Cycling" -> {
                distance.setVisible(true);
                distance.setText(ex.distance + " m");
            }
            case "Swimming" -> {
                distance.setVisible(true);
                distance.setText(ex.distance + " m");
                other.setVisible(true);
                other.setText(ex.numOfStrokes + " strokes");
            }
        }
    }

    public void selectDate(ActionEvent actionEvent){
        LocalDate ld = date.getValue();
        ObservableList<Exercise> exercisesOnDate = FXCollections.observableArrayList();

        for(Exercise exercise : exer.getMultipleExercises()){
            if(exercise.date.equals(ld)){
                exercisesOnDate.add(exercise);
            }
        }
        listExercise.setItems(exercisesOnDate);
    }

    public void closePopup(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        exercisePopUp.setVisible(false);
        distance.setVisible(false);
        other.setVisible(false);
    }

    public void removeExercise(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Exercises exercises = new Exercises();

        Exercise ex = listExercise.getSelectionModel().getSelectedItem();
        HashSet<Exercise> temp = exer.getMultipleExercises();
        exercises.copyExercises(temp);
        exercises.remove(ex);
        //Exercises.saveExercises(exercises);
        User.curUser.saveUser();

        //exercisesList = FXCollections.observableArrayList(Exercises.loadExercises());
        ObservableList<Exercise> updatedExercises = FXCollections.observableArrayList();

        LocalDate ld = date.getValue();
        //No date is chosen
        if(ld == null){
            listExercise.setItems(FXCollections.observableArrayList(exer.getMultipleExercises()));
        }
        else { //Date is chosen
            for(Exercise exercise : exer.getMultipleExercises()){
                if(exercise.date.equals(ld)){
                    updatedExercises.add(exercise);
                }
            }

            listExercise.setItems(updatedExercises);
        }

        exercisePopUp.setVisible(false);

    }

    public void showAll(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        listExercise.setItems(FXCollections.observableArrayList(exer.getMultipleExercises()));
    }

    public void addExerciseButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/AddExercise.fxml"), 335d, 448d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
