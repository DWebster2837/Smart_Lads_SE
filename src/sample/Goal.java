package sample;

import java.io.Serializable;
import java.time.LocalDate;

public class Goal implements Serializable {

    int targetValue;
    LocalDate start, end;
    String goalType;
    String state;
    int currentValue;

    public Goal(int targetValue, LocalDate start, LocalDate end, String goalType, String state, int currentValue){
        this.targetValue = targetValue;
        this.start = start;
        this.end = end;
        this.goalType = goalType;
        this.state = state;
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "targetValue=" + targetValue +
                ", start=" + start +
                ", end=" + end +
                ", goalType='" + goalType + '\'' +
                ", state='" + state + '\'' +
                ", currentValue=" + currentValue +
                '}';
    }
}
