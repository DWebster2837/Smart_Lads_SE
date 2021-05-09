package sample;

import java.io.Serializable;

public class Food implements Serializable {

    private String foodName;
    private int calories;

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    private String meal;

    public String getFoodName(){
        return foodName;
    }

    public int getCalories(){
        return calories;
    }

    public void setFoodName(){
        this.foodName = foodName;
    }

    public void setCalories(){
        this.calories = calories;
    }

    Food(String foodName, int calories, String meal){
        this.foodName = foodName;
        this.calories = calories;
        this.meal = meal;
    }
    Food(String foodName, int calories){
        this.foodName = foodName;
        this.calories = calories;
        this.meal = "Other";
    }



    public String toString(){
        return getFoodName() + " " + getCalories();
    }

}
