package sample;

import java.util.HashSet;
import java.util.Objects;

public class Food {
    protected String foodName;
    protected int calories;
    protected Meal mealType;
    public HashSet<Food> foodList = new HashSet<Food>();

    Food(String foodName, int calories, Meal mealType){
        this.foodName = foodName;
        this.calories = calories;
        this.mealType = mealType;
        Food food = new Food(foodName, calories, mealType);
        foodList.add(food);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return calories == food.calories && Objects.equals(foodName, food.foodName) && Objects.equals(mealType, food.mealType) && Objects.equals(foodList, food.foodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName, calories, mealType, foodList);
    }
}
