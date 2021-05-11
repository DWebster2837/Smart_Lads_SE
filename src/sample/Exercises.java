package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;

import static sample.Goals.loadGoals;
import static sample.Goals.saveGoals;

public class Exercises implements Serializable{

    private static final long serialVersionUID = 1L;

    private HashSet<Exercise> multipleExercises = new HashSet<>();
    private String[] exerciseType;

    static String fileName = "exercise.ser";

    public static int getCaloriesInRange(LocalDate first, LocalDate last){

        return 0;
    }

    public static Exercise[] getExerciseByName(String name){
        return null;
    }

    public static HashSet<Exercise> getExerciseByDate(LocalDate first, LocalDate last){

        return null;
    }

    public static HashSet<Exercise> loadExercises() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Exercises ex= (Exercises) is.readObject();
        return ex.multipleExercises;

    }

    public void addExercise(Exercise exercise){

        multipleExercises.add(exercise);

    }

    public void copyExercises(HashSet<Exercise> ex){
        multipleExercises = ex;
    }

    public static void saveExercises(Exercises ex) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(ex);
        os.close();
        System.out.println("Exercises saved");
    }

    public void remove(Exercise exercise) throws IOException, ClassNotFoundException {

        Goals gs = new Goals();

        for(Exercise e : multipleExercises){
            if (e.type.equals(exercise.type) && e.date.equals(exercise.date) && e.duration.getSeconds() == exercise.duration.getSeconds()
                    && e.caloriesBurnt == exercise.caloriesBurnt && e.numOfStrokes == exercise.numOfStrokes
                    && e.distance == exercise.distance && e.steps == exercise.steps){
                multipleExercises.remove(e);
                for(Goal g : loadGoals()){
                    if(exercise.type.equals("Walking") && g.goalType.equals("Walk Distance")){
                        g.currentValue -= exercise.distance;
                        g.dataAdded.remove(e.date, e.distance);
                        System.out.println("walk");
                    }
                    else if(exercise.type.equals("Running") && g.goalType.equals("Run Distance")) {
                        g.currentValue -= exercise.distance;
                        g.dataAdded.remove(e.date, e.distance);
                    }
                    else if(exercise.type.equals("Swimming") && g.goalType.equals("Swim Distance")) {
                        g.currentValue -= exercise.distance;
                        g.dataAdded.remove(e.date, e.distance);
                    }

                    if((exercise.type.equals("Walking")||exercise.type.equals("Running")) && g.goalType.equals("Steps")){
                        g.currentValue -= exercise.steps;
                        g.dataAdded.remove(e.date, e.steps);
                    }

                    if(g.goalType.equals("Calories Burnt")){
                        g.currentValue -= exercise.caloriesBurnt;
                        g.dataAdded.remove(e.date, e.caloriesBurnt);
                    }
                    if(g.currentValue < g.targetValue && g.state.equals("Beaten")){
                        g.state = "On Track";
                    }
                    gs.addGoal(g);
                }
                break;
            }
        }

        saveGoals(gs);
    }



    public static String[] getTypes(){
        return null;
    }

}
