package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DietController extends Diet implements Initializable, Serializable {
    private Diet diet;
    private User user;
    @FXML public ChoiceBox<Food> foodSelect;
    @FXML public ChoiceBox<String> mealSelect;
    @FXML public ChoiceBox<Food> foodSelect1;
    @FXML public ChoiceBox<String> mealSelect1;
    @FXML private TextField foodInput;
    @FXML private TextField caloriesInput;
    @FXML private TextField mealInput;
    @FXML public DatePicker datePicker;
    @FXML TextField targetInput;
    @FXML public TableView<Food> breakfastTable;
    @FXML public TableView<Food> lunchTable;
    @FXML public TableView<Food> dinnerTable;
    @FXML private TableView<Food> other;
    @FXML private TableColumn<Food, String> foodNameBreakfast = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> foodCaloriesBreakfast = new TableColumn<>("Calories");
    @FXML private TableColumn<Food, String> foodNameLunch = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> foodCaloriesLunch = new TableColumn<>("Calories");
    @FXML private TableColumn<Food, String> foodNameDinner = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> foodCaloriesDinner = new TableColumn<>("Calories");
    @FXML private TableColumn<Food, String> foodNameOther = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> foodCaloriesOther = new TableColumn<>("Calories");
    @FXML private TextField totalCalories;
    @FXML private ProgressBar progressBar;
    @FXML private Label goalTarget;
    @FXML private Label foodConsumed;
    @FXML private Label remainingCalories;
    @FXML private Button removeSelectedFood;
    @FXML ObservableList<PieChart.Data> pieData;
    @FXML private PieChart mealBreakdown;
    @FXML private Label labelBreakfast;
    @FXML private Label labelLunch;
    @FXML private Label labelDinner;
    @FXML private Label labelOther;
    //Todo: TotalCaloriesTracked, Total Days Tracked.
    int breakfastTotal, lunchTotal, dinnerTotal, otherTotal;


    private String mealSelected;


    private Diet readDiet(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("Test.ser"); //replace with (Integer.toString(user.getUserID())+".ser")
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Diet) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveDiet() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Test.ser"); //replace with (Integer.toString(user.getUserID())+".ser")
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(diet);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeHandle(){
        if (diet.isChangesMade()){
            saveDiet();
        }
    }

    public void handleAddMeal(ActionEvent event){
        String mealName = mealInput.getText();

        if (mealName.equals("") || diet.getMealList().contains(mealName)){
            return;
        }
        if (diet.getMealList().size() >= 5){
            return;
        }

        diet.getMealList().add(mealName);
        mealSelect.getItems().add(mealName);
        mealSelect1.getItems().add(mealName);
        diet.setChangesMade(true);
    }

    public void handleAddFood(ActionEvent event){
        String foodName = foodInput.getText();
        String calories = caloriesInput.getText();

        for (Food food: diet.getFoodList()){
            if (foodName.equals("") || (food.getFoodName().equals(foodName)
                    && food.getCalories() == Integer.parseInt(calories))){ //checks for duplicate
                return;
            }
        }
        Food food = new Food(foodName, Integer.parseInt(calories));
        diet.getFoodList().add(food);
        foodSelect.getItems().add(food);
        foodSelect1.getItems().add(food);
        diet.setChangesMade(true);
    }

    public void handleAddFoodToDiet(ActionEvent event){
        if (mealSelect.getValue() == null || foodSelect.getValue() == null){
            return;
        };
        Food food = new Food(foodSelect.getValue().getFoodName(), foodSelect.getValue().getCalories(), mealSelect.getValue());
        diet.getFoodListDay().add(food);
        diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
        sortListToTable();
        diet.addTotalCalories(foodSelect.getValue().getCalories());
        foodConsumed.textProperty().set(String.valueOf(diet.getTotalCalories()));
        diet.setChangesMade(true);
        updateProgressBar();
    }


    public void updateProgressBar(){
        goalTarget.setText(String.valueOf(diet.getTargetCalories()));
        foodConsumed.setText(String.valueOf(diet.getTotalCalories()));
        remainingCalories.setText(String.valueOf(diet.getTargetCalories() - diet.getTotalCalories()));
        remainingCalories.setTextFill(Color.web("#008000", 1));
        int remaining = diet.getTargetCalories() - diet.getTotalCalories();
        if (remaining < 0){
            remainingCalories.setTextFill(Color.web("#FF0000", 1));
        }
        diet.setBarUpdater(diet.getTotalCalories()/(double)diet.getTargetCalories());

    }

    public void handleRemoveFoodFromFoodListDay(ActionEvent event){
        Food selectedItem;

        switch (mealSelected){
            case "Breakfast":
                selectedItem = breakfastTable.getSelectionModel().getSelectedItem();
                breakfastTable.getItems().remove(selectedItem);
                diet.getFoodListDay().remove(selectedItem);
                diet.setTotalCalories(diet.getTotalCalories() - selectedItem.getCalories());
                updateProgressBar();
                break;
            case "Lunch":
                selectedItem = lunchTable.getSelectionModel().getSelectedItem();
                lunchTable.getItems().remove(selectedItem);
                diet.getFoodListDay().remove(selectedItem);
                diet.setTotalCalories(diet.getTotalCalories() - selectedItem.getCalories());
                updateProgressBar();
                break;
            case "Dinner":
                selectedItem = dinnerTable.getSelectionModel().getSelectedItem();
                dinnerTable.getItems().remove(selectedItem);
                diet.getFoodListDay().remove(selectedItem);
                diet.setTotalCalories(diet.getTotalCalories() - selectedItem.getCalories());
                updateProgressBar();
                break;
            default:
                selectedItem = other.getSelectionModel().getSelectedItem();
                other.getItems().remove(selectedItem);
                diet.getFoodListDay().remove(selectedItem);
                diet.setTotalCalories(diet.getTotalCalories() - selectedItem.getCalories());
                updateProgressBar();
        }
        sortListToTable();
    }

    public void handleRemoveFood(ActionEvent event){
        if (foodSelect1.getValue() == null){
            return;
        }
        removeFoodFromFoodList(foodSelect1.getValue());
        foodSelect1.getItems().remove(foodSelect1.getValue());
        foodSelect.getItems().remove(foodSelect1.getValue());
        foodSelect1.setValue(null);
    }

    public void handleRemoveMeal(ActionEvent event){
        if (mealSelect1.getValue() == null || mealSelect1.getValue().equals("Breakfast") || mealSelect1.getValue().equals("Lunch")
                || mealSelect1.getValue().equals("Dinner")){
            return;
        }
        removeMeal(mealSelect1.getValue());
        mealSelect1.getItems().remove(mealSelect1.getValue());
        mealSelect.getItems().remove(mealSelect1.getValue());
        mealSelect1.setValue(null);
    }

    public void removeMeal(String meal){
        diet.getMealList().remove(meal);
    }
    public void removeFoodFromFoodList(Food f){
        diet.getFoodList().remove(f);
    }
    public void handleAddTarget(ActionEvent event){
        int targetCalorie = Integer.parseInt(targetInput.getText());
        if (isNumber(targetInput.getText())){
            diet.setTargetCalories(targetCalorie);
        }
        diet.getMapTargetDate().put(LocalDate.now(), diet.getTargetCalories());
        updateProgressBar();
        diet.setLastSavedValue(targetCalorie);
        diet.setChangesMade(true);
    }
    public boolean isNumber(String s){
        try {
            Double.parseDouble(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate date = datePicker.getValue();
                int displaytotalcalories = 0;
                if (diet.getMapTargetDate().containsKey(date)){
                    diet.setTargetCalories(diet.getMapTargetDate().get(date));
                }
                if (diet.getMapFoodDate().containsKey(date)) {
                    diet.setFoodListDay(diet.getMapFoodDate().get(date));
                    for (Food f: diet.getFoodListDay()){
                        displaytotalcalories += f.getCalories();
                    }
                    diet.setTotalCalories(displaytotalcalories);
                    foodConsumed.setText(String.valueOf(diet.getTotalCalories()));
                    updateProgressBar();
                    sortListToTable();
                }
                else{
                    ArrayList<Food> emptyFood = new ArrayList<>();
                    diet.setTotalCalories(0);
                    diet.setFoodListDay(null);
                    diet.setTargetCalories(diet.getLastSavedValue());
                    breakfastTable.setItems(FXCollections.observableList(emptyFood));
                    breakfastTable.getColumns().setAll(foodNameBreakfast, foodCaloriesBreakfast);
                    lunchTable.setItems(FXCollections.observableList(emptyFood));
                    lunchTable.getColumns().setAll(foodNameLunch, foodCaloriesLunch);
                    dinnerTable.setItems(FXCollections.observableList(emptyFood));
                    dinnerTable.getColumns().setAll(foodNameDinner, foodCaloriesDinner);
                    other.setItems(FXCollections.observableList(emptyFood));
                    other.getColumns().setAll(foodNameOther, foodCaloriesOther);
                    foodConsumed.textProperty().set(String.valueOf(diet.getTotalCalories()));
                    updateProgressBar();
                }
            }
        });
        diet = readDiet();

        foodNameBreakfast.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        foodCaloriesBreakfast.setCellValueFactory(new PropertyValueFactory<>("calories"));
        foodNameLunch.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        foodCaloriesLunch.setCellValueFactory(new PropertyValueFactory<>("calories"));
        foodNameDinner.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        foodCaloriesDinner.setCellValueFactory(new PropertyValueFactory<>("calories"));
        foodNameOther.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        foodCaloriesOther.setCellValueFactory(new PropertyValueFactory<>("calories"));

        breakfastTable.setOnMouseClicked(e ->{
            mealSelected = "Breakfast";
        });
        lunchTable.setOnMouseClicked(e ->{
            mealSelected = "Lunch";
        });
        dinnerTable.setOnMouseClicked(e ->{
            mealSelected = "Dinner";
        });
        other.setOnMouseClicked(e ->{
            mealSelected = "";
        });

        if (diet != null) {
            LocalDate currentDate = LocalDate.now();
            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());
            mealSelect1.getItems().addAll(diet.getMealList());
            foodSelect1.getItems().addAll(diet.getFoodList());
            if (diet.getDate().compareTo(currentDate) == 0) {  // if it is current day
                if (diet.getFoodListDay() != null) {
                    int displayTotalCalories = 0;
                    for (Food f: diet.getFoodListDay()){
                        displayTotalCalories += f.getCalories();
                    }
                    sortListToTable();
                    diet.setTotalCalories(displayTotalCalories);
                    foodConsumed.setText(String.valueOf(diet.getTotalCalories()));
                    sortListToTable();
                    updateProgressBar();
                }
                else{
                    diet.setFoodListDay(new ArrayList<>());
                }
            } else { //if diet retrieved is not today, create new day
                diet.setFoodListDay(new ArrayList<>());
                diet.setTotalCalories(0);
                diet.setDate(currentDate);
                diet.getMapTargetDate().put(LocalDate.now(), diet.getTargetCalories());
                diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
                updateProgressBar();
            }
        }
        else { //if user doesn't have diet diary then create new
            diet = new Diet();
            diet.setFoodList(new ArrayList<>());
            diet.setCalender(new ArrayList<>());
            diet.setMealList(new ArrayList<>());
            diet.setMapFoodDate(new HashMap<>());
            diet.setMapTargetDate(new HashMap<>());
            diet.setFoodListDay(new ArrayList<>());
            diet.getMapFoodDate().put(LocalDate.now(), new ArrayList<>(diet.getFoodListDay()));
            diet.getMapTargetDate().put(LocalDate.now(), diet.getTargetCalories());
            diet.setDate(LocalDate.now());
            diet.setTotalCalories(0);
            diet.setSavedCalories(new ArrayList<>());
            diet.getMealList().add("Breakfast");
            diet.getMealList().add("Lunch");
            diet.getMealList().add("Dinner");
            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());
            mealSelect1.getItems().addAll(diet.getMealList());
            foodSelect1.getItems().addAll(diet.getFoodList());

            /*Food food1 = new Food("Banana", 234, "Breakfast");
            Food food2 = new Food("Carrot", 90, "Lunch");
            Food food3 = new Food("Cake", 1000, "Dinner");
            Food food4 = new Food("Strawberries", 219);

            diet.getFoodList().add(food1);
            diet.getFoodList().add(food2);
            diet.getFoodList().add(food3);
            diet.getFoodList().add(food4);

            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());
            mealSelect1.getItems().addAll(diet.getMealList());
            foodSelect1.getItems().addAll(diet.getFoodList());

            diet.getFoodListDay().add(food1);
            diet.getFoodListDay().add(food2);
            diet.getFoodListDay().add(food3);
            diet.getFoodListDay().add(food4);

            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 10), new ArrayList<>(diet.getFoodListDay()));
            diet.getFoodListDay().add(food1);
            diet.getFoodListDay().add(food2);
            diet.getFoodListDay().add(food3);
            diet.getFoodListDay().add(food4);
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 9), new ArrayList<>(diet.getFoodListDay()));
            diet.getFoodListDay().add(food1);
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 8), new ArrayList<>(diet.getFoodListDay()));
            diet.getFoodListDay().add(food1);
            diet.getFoodListDay().add(food4);
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 7), new ArrayList<>(diet.getFoodListDay()));
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 6), new ArrayList<>(diet.getFoodListDay()));
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 5), new ArrayList<>(diet.getFoodListDay()));
            diet.getMapFoodDate().put(LocalDate.of(2021, 05, 4), new ArrayList<>(diet.getFoodListDay()));
            diet.getMapTargetDate().put(LocalDate.of(2021,05,10), 3000);
            diet.setFoodListDay(new ArrayList<>());*/


            ArrayList<Food> sortedListBreakfast = new ArrayList<>();
            ArrayList<Food> sortedListLunch = new ArrayList<>();
            ArrayList<Food> sortedListDinner = new ArrayList<>();
            ArrayList<Food> sortedOther = new ArrayList<>();
            breakfastTable.setItems(FXCollections.observableList(sortedListBreakfast));
            breakfastTable.getColumns().setAll(foodNameBreakfast, foodCaloriesBreakfast);
            lunchTable.setItems(FXCollections.observableList(sortedListLunch));
            lunchTable.getColumns().setAll(foodNameLunch, foodCaloriesLunch);
            dinnerTable.setItems(FXCollections.observableList(sortedListDinner));
            dinnerTable.getColumns().setAll(foodNameDinner, foodCaloriesDinner);
            other.setItems(FXCollections.observableList(sortedOther));
            other.getColumns().setAll(foodNameOther, foodCaloriesOther);
            updateProgressBar();
        }
        diet.setChangesMade(false);
        sortListToTable();
        updateProgressBar();
        progressBar.progressProperty().bind(diet.barUpdaterProperty());
    }

    public void sortListToTable() {
        ArrayList<Food> sortedListBreakfast = new ArrayList<>();
        ArrayList<Food> sortedListLunch = new ArrayList<>();
        ArrayList<Food> sortedListDinner = new ArrayList<>();
        ArrayList<Food> sortedOther = new ArrayList<>();
        String mealName = "";
        breakfastTotal = 0;
        lunchTotal = 0;
        dinnerTotal = 0;
        otherTotal = 0;
        for (Food f: diet.getFoodListDay()){
            mealName = f.getMeal();
            switch (mealName){
                case "Breakfast":
                    sortedListBreakfast.add(f);
                    break;
                case "Lunch":
                    sortedListLunch.add(f);
                    break;
                case "Dinner":
                    sortedListDinner.add(f);
                    break;
                default:
                    sortedOther.add(f);
            }
        }
        for (Food s: sortedListBreakfast){
            breakfastTotal += s.getCalories();
        }
        for (Food s: sortedListLunch){
            lunchTotal += s.getCalories();
        }
        for (Food s: sortedListDinner){
            dinnerTotal += s.getCalories();
        }
        for (Food s: sortedOther){
            otherTotal += s.getCalories();
        }

        pieData = FXCollections.observableArrayList(
                new PieChart.Data("Breakfast", breakfastTotal),
                new PieChart.Data("Lunch,", lunchTotal),
                new PieChart.Data("Dinner", dinnerTotal),
                new PieChart.Data("Other", otherTotal)
        );

        mealBreakdown.setData(pieData);
        mealBreakdown.setTitle("Meal breakdown");

        labelBreakfast.setText("Breakfast, Total Calories: " + breakfastTotal);
        labelLunch.setText("Lunch, Total Calories: " + lunchTotal);
        labelDinner.setText("Dinner, Total Calories: " + dinnerTotal);
        labelOther.setText("Other, Total Calories: " + otherTotal);

        breakfastTable.setItems(FXCollections.observableList(sortedListBreakfast));
        breakfastTable.getColumns().setAll(foodNameBreakfast, foodCaloriesBreakfast);
        lunchTable.setItems(FXCollections.observableList(sortedListLunch));
        lunchTable.getColumns().setAll(foodNameLunch, foodCaloriesLunch);
        dinnerTable.setItems(FXCollections.observableList(sortedListDinner));
        dinnerTable.getColumns().setAll(foodNameDinner, foodCaloriesDinner);
        other.setItems(FXCollections.observableList(sortedOther));
        other.getColumns().setAll(foodNameOther, foodCaloriesOther);
    }




}
