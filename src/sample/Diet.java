package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Diet implements Serializable {
    //Todo: clean.
    private ArrayList<Food> foodList;
    private ArrayList<String> mealList;
    public double getBarUpdater() {
        return barUpdater.get();
    }

    public DoubleProperty barUpdaterProperty() {
        if (barUpdater == null){
            barUpdater = new SimpleDoubleProperty(0);
        }
        return barUpdater;
    }


    public void setBarUpdater(double barUpdater) {
        this.barUpdaterProperty().set(barUpdater);
    }

    private transient DoubleProperty barUpdater;

    public ArrayList<Food> getFoodListDay() {
        if(foodListDay == null){foodListDay = new ArrayList<Food>(){};}
        return foodListDay;
    }

    public void setFoodListDay(ArrayList<Food> _foodListDay) {
        this.foodListDay = new ArrayList<>(){};
        this.foodListDay.addAll(_foodListDay);
    }


    private ArrayList<Food> foodListDay;
    private int targetCalories;

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void addTotalCalories(int totalCalories){
        this.totalCalories += totalCalories;
    }

    private int totalCalories;
    private transient boolean changesMade;
    private LocalDate date;
    private Food food;

    public int getLastSavedValue() {
        return lastSavedValue;
    }

    public void setLastSavedValue(int lastSavedValue) {
        this.lastSavedValue = lastSavedValue;
    }

    private int lastSavedValue;

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    private String meal;

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


    public HashMap<LocalDate, Integer> getMapTargetDate() {
        return mapTargetDate;
    }

    public void setMapTargetDate(HashMap<LocalDate, Integer> mapTargetDate) {
        this.mapTargetDate = mapTargetDate;
    }

    HashMap<LocalDate, Integer> mapTargetDate;


    public Diet(){
        this.targetCalories = 2200;
        this.foodList = new ArrayList<Food>();
        this.mealList = new ArrayList<String>();
        this.calender = new ArrayList<LocalDate>();
        this.date = LocalDate.now();
        this.mapFoodDate = new HashMap<LocalDate, ArrayList<Food>>(){};
        this.totalCalories = 0;
    }

    public Diet(ArrayList<Food> foodList, ArrayList<String> mealList, ArrayList<Food> foodListDay, int targetCalories, int totalCalories, boolean changesMade, LocalDate date, Food food, ArrayList<LocalDate> calender, ArrayList<Integer> savedCalories, HashMap<LocalDate, ArrayList<Food>> mapFoodDate) {
        this.foodList = foodList;
        this.mealList = mealList;
        this.foodListDay = foodListDay;
        this.targetCalories = targetCalories;
        this.totalCalories = totalCalories;
        this.changesMade = changesMade;
        this.date = date;
        this.food = food;
        this.calender = calender;
        this.savedCalories = savedCalories;
        this.mapFoodDate = mapFoodDate;
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

    public void descreaseCalories(int calories){
        setTotalCalories(getTotalCalories() - calories);
    }

}
