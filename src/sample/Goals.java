package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class Goals implements Serializable {

    //static String fileName = "goal.ser";

    private HashSet<Goal> goalsSet = new HashSet<>();

    public HashSet<Goal> getGoalsOfType(String goalType){
        HashSet<Goal> goalTypes = new HashSet<>();
        for(Goal goal : goalsSet){
            if(goal.goalType.equals(goalType)){
                goalTypes.add(goal);
            }
        }
        return goalTypes;
    }

    public HashSet<Goal> filterGoals(Predicate<Goal> func){
        HashSet<Goal> filtGoals = new HashSet<>();
        for(Goal goal : goalsSet){
            if(func.test(goal)){
                filtGoals.add(goal);
            }
        }
        return filtGoals;
    }
    public HashSet<Goal> getGeneralGoals(){
        return filterGoals(g -> g.goalType.equals("Weight") || g.goalType.equals("BMI") || g.goalType.equals("Calories Intake") && g.state.equals("On Track"));
    }
    public HashSet<Goal> getExerciseGoals(){
        return filterGoals(g -> (g.goalType.equals("Walk Distance") || g.goalType.equals("Run Distance") || g.goalType.equals("Swim Distance") || g.goalType.equals("Steps")|| g.goalType.equals("Calories Burnt")) && g.state.equals("On Track"));
    }
    public HashSet<Goal> getHistoryGoals(){
        return filterGoals(g-> g.state.equals("Failed") || g.state.equals("Beaten"));
    }

    public HashSet<Goal> getGoalsSet() {
        return goalsSet;
    }

    /*public HashSet<Goal> getGoalsByDate(LocalDate start, LocalDate end){
        return new HashSet<>();
    }

    public HashSet<Goal> getCompleteGoals(){
        return new HashSet<>();
    }

    //public HashSet<Goal> getFailedGoals(){
        return new HashSet<>();
    }*/


    public void addGoal(Goal goal){
        goalsSet.add(goal);
    }

    /*public static void saveGoals(Goals goals) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(goals);
        os.close();
        System.out.println("Goals saved");
    }*/

    /*public static HashSet<Goal> loadGoals() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Goals g = (Goals) is.readObject();
        return g.goalsSet;
    }*/

    /*public static HashSet<Goal> loadGeneralGoal() throws IOException, ClassNotFoundException {
        HashSet<Goal> generals = new HashSet<>();
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Goals gs = (Goals) is.readObject();
        for(Goal g: gs.goalsSet){
            if((g.goalType.equals("Weight") || g.goalType.equals("BMI") || g.goalType.equals("Calories Intake"))
                && g.state.equals("On Track")){
                generals.add(g);
            }
        }
        return generals;
    }*/

    /*public static HashSet<Goal> loadExerciseGoal() throws IOException, ClassNotFoundException {
        HashSet<Goal> exercises = new HashSet<>();
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Goals gs = (Goals) is.readObject();
        for(Goal g: gs.goalsSet){
            if((g.goalType.equals("Walk Distance") || g.goalType.equals("Run Distance") || g.goalType.equals("Swim Distance")
                    || g.goalType.equals("Steps")|| g.goalType.equals("Calories Burnt"))
                    && g.state.equals("On Track")){
                exercises.add(g);
            }
        }
        return exercises;
    }*/

    /*public static HashSet<Goal> loadHistoryGoal() throws IOException, ClassNotFoundException {
        HashSet<Goal> exercises = new HashSet<>();
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Goals gs = (Goals) is.readObject();
        for(Goal g: gs.goalsSet){
            if(g.state.equals("Failed") || g.state.equals("Beaten")){
                exercises.add(g);
            }
        }
        return exercises;
    }*/

    public void copyGoals(HashSet<Goal> goals){
        goalsSet = goals;
    }

    public void remove(Goal goal){
        for(Goal f : goalsSet){
            if(f.goalType.equals(goal.goalType) && f.targetValue == goal.targetValue && f.currentValue == goal.currentValue
                    && f.start.equals(goal.start) && f.end.equals(goal.end))
            {
                goalsSet.remove(f);
                System.out.println("removed");
                break;
            }
        }
    }




}
