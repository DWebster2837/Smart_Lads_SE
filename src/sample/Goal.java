package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;

    int targetValue;
    LocalDate start, end;
    String goalType;
    String state;
    int currentValue;
    DoubleProperty percentFinish;
    HashMap<LocalDate, Integer> dataAdded;


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

    public DoubleProperty getGoalProperty(){
        if(percentFinish == null){
            percentFinish = new SimpleDoubleProperty(0);
        }
        double progPercent = ((double)currentValue / (double) targetValue);
        percentFinish.setValue(progPercent);
        return percentFinish;
    }


}
