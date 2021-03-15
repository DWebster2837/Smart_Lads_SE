package sample;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;

public class Exercises {
    private Exercise[] exercisearr;
    private Map<String, Integer> exerciseTypes;

    public Exercises() {
        exercisearr = new Exercise[]{};
        exerciseTypes = new HashMap<>();
    }

    public Exercise[] getExercisearr() {
        return exercisearr;
    }

    public Map<String, Integer> getExerciseTypes() {
        return exerciseTypes;
    }

    public int getCaloriesinRange(Date start, Date end){
        return Arrays.stream(exercisearr)
                .filter((x) -> x.getDate().before(end) & x.getDate().after(start))
                .mapToInt(Exercise::getCalories)
                .sum();
    }
}

