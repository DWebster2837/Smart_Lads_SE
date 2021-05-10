package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;

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

    public HashSet<Goal> getGoalsByDate(LocalDate start, LocalDate end){
        return new HashSet<>();
    }

    //public HashSet<Goal> getCompleteGoals(){
    //    return new HashSet<>();
    //}

    //public HashSet<Goal> getFailedGoals(){
    //    return new HashSet<>();
    //}

    public HashSet<Goal> getGoals(){ return goalsSet; }

    public void addGoal(Goal goal){
        goalsSet.add(goal);
    }

    public void removeGoal(Goal goal){
        goalsSet.remove(goal);
    }

    /*
    public static void saveGoals(Goals goals) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(goals);
        os.close();
        System.out.println("Goals saved");
    }

    public static HashSet<Goal> loadGoals() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Goals g = (Goals) is.readObject();
        return g.goalsSet;
    }
    */




}
