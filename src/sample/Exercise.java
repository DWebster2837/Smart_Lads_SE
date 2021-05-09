package sample;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    String type;
    LocalDate date;
    Duration duration;
    int caloriesBurnt;
    int numOfStrokes;
    int distance;
    int steps;


    public Exercise(String type, LocalDate date, Duration duration, int caloriesBurnt, int numOfstrokes, int distance, int steps){
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.caloriesBurnt = caloriesBurnt;
        this.numOfStrokes = numOfstrokes;
        this.distance = distance;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return date + " " + type;
    }


}
