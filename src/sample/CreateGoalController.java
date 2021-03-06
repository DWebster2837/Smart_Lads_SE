package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class CreateGoalController extends Goals implements Initializable, Serializable {

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
    public Label targetLB;
    public Label errorLB;
    public ListView<Goal> listGeneralLV;
    public ListView<Goal> listExerciseLV;
    public SplitPane historySP;
    public ListView<Goal> listHistoryLV;
    public SplitPane showGoalSP;
    public Label goalNameLB;
    public Label goalDateLB;
    public ProgressBar goalPB;
    public Label progressLB;
    public LineChart<String, Integer> lineChart;
    public CategoryAxis goalCA;


    ObservableList<Goal> listGeneralGoals = FXCollections.observableArrayList(goals.getGeneralGoals());
    ObservableList<Goal> listExerciseGoals = FXCollections.observableArrayList(goals.getExerciseGoals());
    ObservableList<Goal> listHistoryGoals = FXCollections.observableArrayList(goals.getHistoryGoals());


    String goalChosen;


    public CreateGoalController() throws IOException, ClassNotFoundException {
    }

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
            targetLB.setText("Target weight:");
        }
        else if (bmiRB.isSelected()) {
            goalType = "BMI";
            targetLB.setText("Target BMI:");
        }
        else if (calIntakeRB.isSelected()) {
            goalType = "Calories Intake";
            targetLB.setText("Target calories intake:");
        }
        else if (calBurntRB.isSelected()) {
            goalType = "Calories Burnt";
            targetLB.setText("Target calories burnt:");
        }
        else if (walkDistRB.isSelected()) {
            goalType = "Walk Distance";
            targetLB.setText("Target walk distance:");
        }
        else if (runDistRB.isSelected()) {
            goalType = "Run Distance";
            targetLB.setText("Target run distance:");
        }
        else if (swimDistRB.isSelected()) {
            goalType = "Swim Distance";
            targetLB.setText("Target swim distance:");
        }
        else if (stepsRB.isSelected()) {
            goalType = "Steps";
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

    public void createGoal(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        if(!parsableToInt(targetTF.getText())){
            errorLB.setText("Target value is not a number");
            errorLB.setVisible(true);
            return;
        }
        targetValue = Integer.parseInt(targetTF.getText());

        Goal goal = new Goal(targetValue, start, end, goalType, "On Track", 0);

        goals.addGoal(goal);

        User.curUser.saveUser();

        //for (Goal g : loadGoals()) {
        //    System.out.println(g);
        //}

        targetChooser.setVisible(false);

        errorLB.setVisible(false);

        listGeneralGoals = FXCollections.observableArrayList(goals.getGeneralGoals());
        listExerciseGoals = FXCollections.observableArrayList(goals.getExerciseGoals());

        listExerciseLV.setItems(listExerciseGoals);
        listGeneralLV.setItems(listGeneralGoals);



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            listExerciseLV.setItems(listExerciseGoals);
            listGeneralLV.setItems(listGeneralGoals);
            listHistoryLV.setItems(listHistoryGoals);

        }catch (Exception e){
            System.out.println("Empty list");
        }

        targetChooser.setVisible(false);
        historySP.setVisible(false);
        showGoalSP.setVisible(false);

        errorLB.setVisible(false);


        for (Goal g : User.curUser.getGoals().getGoalsSet()) {
            if (g.end.isBefore(LocalDate.now()) && g.currentValue < g.targetValue && g.state.equals("On Track")) {
                g.state = "Failed";
            }
            goals.addGoal(g);
            System.out.println(g);
        }



    }

    public void seeHistory(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        listHistoryGoals = FXCollections.observableArrayList(goals.getHistoryGoals());
        listHistoryLV.setItems(listHistoryGoals);

        historySP.setVisible(true);
    }

    public void close(ActionEvent actionEvent){
        historySP.setVisible(false);
    }

    public void selectGeneralGoal(MouseEvent mouseEvent){
        goalChosen = "General";
        String measurement = "";
        Goal gl = listGeneralLV.getSelectionModel().getSelectedItem();

        if(gl == null)
        {
            return;
        }

        goalPB.progressProperty().bind(gl.getGoalProperty());

        goalNameLB.setText(gl.goalType);

        goalDateLB.setText(gl.start + " -> " + gl.end);

        switch (gl.goalType) {
            case "Weight" -> measurement = "kg";
            case "BMI" -> measurement = "";
            case "Calories Intake" -> measurement = "calories";
        }

        progressLB.setText(gl.currentValue + " of " + gl.targetValue + " " + measurement);

        lineChart.setVisible(false);

        showGoalSP.setVisible(true);

    }

    private static HashMap<LocalDate, Integer> sortValues(HashMap<LocalDate, Integer> map)
    {
        List<Map.Entry<LocalDate, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Map.Entry.comparingByKey());
//copying the sorted list in HashMap to preserve the iteration order
        HashMap<LocalDate, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Iterator<Map.Entry<LocalDate, Integer>> it = list.iterator(); it.hasNext();)
        {
            Map.Entry<LocalDate, Integer> entry = it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public void selectExerciseGoal(MouseEvent mouseEvent) {
        lineChart.setVisible(true);
        goalChosen = "Exercise";
        String measurement = "";
        ObservableList<String> observableList = FXCollections.observableArrayList();
        Goal gl = listExerciseLV.getSelectionModel().getSelectedItem();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        gl.dataAdded = sortValues(gl.dataAdded);
        try{
            for (Map.Entry<LocalDate, Integer> h : gl.dataAdded.entrySet()) {
                observableList.add(h.getKey().toString());
                series.getData().add(new XYChart.Data<>(h.getKey().toString(), h.getValue()));

            }
        }catch (NullPointerException nullPointerException){
            System.out.println("No exercises added to goal");
            return;
        }

        goalCA.setCategories(observableList);
        lineChart.setData(FXCollections.observableArrayList());
        lineChart.getData().add(series);

        //if (gl == null) {
        //    return;
        //}

        goalPB.progressProperty().bind(gl.getGoalProperty());

        goalNameLB.setText(gl.goalType);

        goalDateLB.setText(gl.start + " -> " + gl.end);

        switch (gl.goalType) {
            case "Weight", "Run Distance", "Swim Distance" -> measurement = "km";
            case "Steps" -> measurement = "steps";
            case "Calories Burnt" -> measurement = "calories";
        }

        progressLB.setText(gl.currentValue + " of " + gl.targetValue + " " + measurement);

        showGoalSP.setVisible(true);

    }

    public void closeGoal (ActionEvent actionEvent){
        showGoalSP.setVisible(false);
        goalChosen = "";
    }

    public void onRemove(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Goals goals = new Goals();
        Goal gl = null;
        if(goalChosen.equals("General")){
            gl = listGeneralLV.getSelectionModel().getSelectedItem();
        }
        else if(goalChosen.equals("Exercise")){
            gl = listExerciseLV.getSelectionModel().getSelectedItem();
        }
        HashSet<Goal> temp = User.curUser.getGoals().getGoalsSet();
        goals.copyGoals(temp);
        goals.remove(gl);
        User.curUser.saveUser();

        listGeneralGoals = FXCollections.observableArrayList(goals.getGeneralGoals());
        listExerciseGoals = FXCollections.observableArrayList(goals.getExerciseGoals());

        listGeneralLV.setItems(listGeneralGoals);
        listExerciseLV.setItems(listExerciseGoals);

        showGoalSP.setVisible(false);
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