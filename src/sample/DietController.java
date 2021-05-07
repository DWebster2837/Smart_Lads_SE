package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DietController extends Diet implements Initializable, Serializable {
    private Diet diet;
    private User user;
    @FXML
    public ChoiceBox<Food> foodSelect;
    @FXML
    public ChoiceBox<String> mealSelect;
    @FXML
    private TextField foodInput;
    @FXML
    private TextField caloriesInput;
    @FXML
    private TextField mealInput;
    @FXML
    public DatePicker datePicker;
    @FXML
    public static Label dateSelected;
    @FXML public TableView<Food> breakfastTable;
    @FXML public TableView<Food> lunchTable;
    @FXML public TableView<Food> dinnerTable;
    private final StringProperty twoWayInput = new SimpleStringProperty("");
    @FXML private TableColumn<Food, String> breakfastFood = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> breakfastCalorie = new TableColumn<>("Calories");

    LocalDate currentDate;


    private Diet readDiet(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("any"); //replace with (Integer.toString(user.getUserID())+".ser")
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
            FileOutputStream fileOutputStream = new FileOutputStream(Integer.toString(user.getUserID())+".ser"); //replace with (Integer.toString(user.getUserID())+".ser")
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

    public StringProperty twoWayInputProperty(){
        return twoWayInput;
    }

    public String getTwoWayInput() {
        return twoWayInput.get();
    }

    public void setTwoWayInput(String twoWayInput){
        this.twoWayInput.set(twoWayInput);
    }

    public void handleAddMeal(ActionEvent event){
        String mealName = mealInput.getText();

        if (mealName.equals("") || diet.getMealList().contains(mealName)){
            return;
        }

        diet.getMealList().add(mealName);
        mealSelect.getItems().add(mealName);

    }

    public void handleAddFood(ActionEvent event){
        String foodName = foodInput.getText();
        String calories = caloriesInput.getText();

        for (Food food: diet.getFoodList()){
            if (foodName.equals("") || food.getFoodName().equals(foodName)){
                /*if (calories == null || Integer.parseInt(calories) != (int)){
                    System.out.println("invalid int");
                    return;
                    //if invalid input in calories;
                }*/
                return;
            }
        }
        Food food = new Food(foodName, Integer.parseInt(calories));
        diet.getFoodList().add(food);
        foodSelect.getItems().add(food);
    }

    public void handleAddFoodToDiet(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });



        diet = readDiet();
        //String userid = Integer.toString (user.getUserID());

        if (diet != null) {
            LocalDate currentDate = LocalDate.now();
            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());
            if (diet.getDate().compareTo(currentDate) == 0) {  // if it is current day
                if (diet.getFoodList() != null) {
                    for (String meal : diet.getMealList()) {
                        List<Food> sortedListFood = new ArrayList<Food>();
                        for (Food food : diet.getFoodList()) {
                            if (food.getMeal().equals(meal)) {
                                sortedListFood.add(food);

                            }
                        }
                        switch (meal) {
                            case "Breakfast" -> breakfastTable.setItems(FXCollections.observableList(sortedListFood));
                            case "Lunch" -> lunchTable.setItems(FXCollections.observableList(sortedListFood));
                            case "Dinner" -> dinnerTable.setItems(FXCollections.observableList(sortedListFood));
                        }
                    }
                }
            } else { //if diet retrieved is not today
                Diet savedDiet = diet;
                diet = new Diet();
                diet.setDate(currentDate);
                diet.getCalender().add(currentDate);
                assert false;
                diet.setTargetCalories(savedDiet.getTargetCalories());
                if (savedDiet.getSavedCalories() == null) {
                    diet.setSavedCalories(new ArrayList<>());
                } else {
                    diet.setSavedCalories(savedDiet.getSavedCalories());
                    diet.saveCalories(savedDiet.getTotalCalories());
                }
            }
        }
        else { //if user doesn't have diet diary then create new
            diet = new Diet();
            diet.setFoodList(new ArrayList<>());
            diet.setCalender(new ArrayList<>());
            diet.setMealList(new ArrayList<>());
            diet.setTargetCalories(2200);
            diet.setDate(LocalDate.now());
            diet.setSavedCalories(new ArrayList<>());
            diet.getMealList().add("Breakfast");
            diet.getMealList().add("Lunch");
            diet.getMealList().add("Dinner");

            Food food1 = new Food("Test", 1224, "Breakfast");
            Food food2 = new Food("Test", 1224, "Breakfast");
            Food food3 = new Food("Test", 1224, "Breakfast");
            Food food4 = new Food("Test", 1224);

            diet.getFoodList().add(food1);
            diet.getFoodList().add(food2);
            diet.getFoodList().add(food3);
            diet.getFoodList().add(food4);

            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());


            breakfastFood.setCellValueFactory(new PropertyValueFactory<>("foodName"));
            breakfastCalorie.setCellValueFactory(new PropertyValueFactory<>("calories"));
            breakfastTable.setItems(FXCollections.observableList(diet.getFoodList()));
            //breakfastTable.getColumns().addAll(breakfastFood, breakfastCalorie);
            breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);



            /*for (String meal : diet.getMealList()) {
                List<Food> sortedListFood = new ArrayList<Food>();
                for (Food food : diet.getFoodList()) {
                    if (food.getMeal().equals("Breakfast")) {
                        sortedListFood.add(food);
                    }
                }
                System.out.println(sortedListFood);
                //breakfastTable.setItems(FXCollections.observableList(sortedListFood));
                //switch (meal) {
                    //case "Breakfast" -> breakfastTable.setItems(FXCollections.observableList(sortedListFood));
                    //case "Lunch" -> lunchTable.setItems(FXCollections.observableList(sortedListFood));
                    //case "Dinner" -> dinnerTable.setItems(FXCollections.observableList(sortedListFood));
                //}
            }*/

        }
        diet.setChangesMade(false);

        //mealInput.textProperty().bindBidirectional(twoWayInputProperty());
        //foodInput.textProperty().bindBidirectional(twoWayInputProperty());






    }



}
