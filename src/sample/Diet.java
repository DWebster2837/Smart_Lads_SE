package sample;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Diet implements Serializable {

    private ArrayList<Food> foodList;
    private ArrayList<String> mealList;

    public ArrayList<Food> getFoodListDay() {
        return foodListDay;
    }

    public void setFoodListDay(ArrayList<Food> foodListDay) {
        this.foodListDay = foodListDay;
    }

    private ArrayList<Food> foodListDay;
    private int targetCalories;
    private int totalCalories;
    private transient boolean changesMade;
    private LocalDate date;
    private Food food;

    public ArrayList<LocalDate> getCalender() {
        return calender;
    }

    public void setCalender(ArrayList<LocalDate> calender) {
        this.calender = calender;
    }

    private ArrayList<LocalDate> calender;
    private ArrayList<Integer> savedCalories;

    public HashMap<LocalDate, ArrayList<Food>> getMapFoodDate() {
        return mapFoodDate;
    }

    public void setMapFoodDate(HashMap<LocalDate, ArrayList<Food>> mapFoodDate) {
        this.mapFoodDate = mapFoodDate;
    }

    HashMap<LocalDate, ArrayList<Food>> mapFoodDate;


    public Diet(){
        this.targetCalories = 2200;
        this.foodList = new ArrayList<Food>();
        this.mealList = new ArrayList<String>();
        this.calender = new ArrayList<LocalDate>();
    }

    public ArrayList<Food> getFoodList(){
        return foodList;
    }

    public void addFood(Food food){
        this.foodList.add(food);
    }

    public void addFoods(ArrayList<Food> foodList){
        this.foodList.addAll(foodList);
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    public ArrayList<String> getMealList(){
        return mealList;
    }

    public void addMeal(String meal){
        this.mealList.add(meal);
    }

    public void addMeals(ArrayList<String> mealList){
        this.mealList.addAll(mealList);
    }

    public void setMealList(ArrayList<String> mealList) {
        this.mealList = mealList;
    }

    public int getTotalCalories(){
        return totalCalories;
    }

    public void addCalories(int amount){
        this.targetCalories +=amount;
    }

    public int getTargetCalories(){
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories){
        this.targetCalories = targetCalories;
    }

    public boolean isChangesMade() {
        return changesMade;
    }

    public void setChangesMade(boolean changesMade){
        this.changesMade = changesMade;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Integer> getSavedCalories(){
        return savedCalories;
    }

    public void setSavedCalories(ArrayList<Integer> savedCalories){
        this.savedCalories = savedCalories;
    }

    public void saveCalories(int amount){
        this.savedCalories.add(amount);
    }

}
