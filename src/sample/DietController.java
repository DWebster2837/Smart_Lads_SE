package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DietController extends Diet implements Initializable, Serializable {
    public Button cancelButton;
    public Button addMeal;
    public Button addFoodDiary;
    public VBox layout;
    public Button addFood;
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
    @FXML private TableColumn<Food, String> breakfastFood = new TableColumn<>("Food");
    @FXML private TableColumn<Food, Integer> breakfastCalorie = new TableColumn<>("Calories");

    LocalDate currentDate;


    /*private Diet readDiet(){
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
    }*/

    /*private void saveDiet() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Test.ser"); //replace with (Integer.toString(user.getUserID())+".ser")
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(diet);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void closeHandle(){
        if (diet.isChangesMade()){
            User.curUser.saveUser();
        }
    }

    public void handleAddMeal(ActionEvent event){
        String mealName = mealInput.getText();

        if (mealName.equals("") || diet.getMealList().contains(mealName)){
            return;
        }

        diet.getMealList().add(mealName);
        diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
        mealSelect.getItems().add(mealName);
        diet.setChangesMade(true);
    }

    public void handleAddFood(ActionEvent event){
        String foodName = foodInput.getText();
        String calories = caloriesInput.getText();

        for (Food food: diet.getFoodList()){
            if (foodName.equals("") || food.getFoodName().equals(foodName)){
                return;
            }
        }
        Food food = new Food(foodName, Integer.parseInt(calories));
        diet.getFoodList().add(food);
        diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
        foodSelect.getItems().add(food);
        diet.setChangesMade(true);
    }

    public void handleAddFoodToDiet(ActionEvent event){
        Food food = new Food(foodSelect.getValue().getFoodName(), foodSelect.getValue().getCalories());
        diet.getFoodListDay().add(food);
        diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
        breakfastTable.setItems(FXCollections.observableList(diet.getFoodListDay()));
        breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);
        diet.setChangesMade(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate date = datePicker.getValue();
                if (diet.getMapFoodDate().containsKey(date)) {
                    diet.setFoodListDay(diet.getMapFoodDate().get(date));
                    breakfastTable.setItems(FXCollections.observableList(diet.getFoodListDay()));
                    breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);
                }
                else{
                    ArrayList<Food> emptyFood = new ArrayList<>();
                    breakfastTable.setItems(FXCollections.observableList(emptyFood));
                    breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);

                }
            }
        });

        diet = User.curUser.getDiet();
        breakfastFood.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        breakfastCalorie.setCellValueFactory(new PropertyValueFactory<>("calories"));

        if (diet != null) {
            LocalDate currentDate = LocalDate.now();
            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());
            if (diet.getDate().compareTo(currentDate) == 0) {  // if it is current day
                if (diet.getFoodListDay() != null) {
                    breakfastTable.setItems(FXCollections.observableList(diet.getMapFoodDate().get(currentDate)));
                    breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);
                }
            } else { //if diet retrieved is not today, create new day
                diet.setFoodListDay(new ArrayList<>());
                diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());
            }
        }
        else { //if user doesn't have diet diary then create new
            diet = new Diet();
            diet.setFoodList(new ArrayList<>());
            diet.setCalender(new ArrayList<>());
            diet.setMealList(new ArrayList<>());
            diet.setMapFoodDate(new HashMap<>());
            diet.setFoodListDay(new ArrayList<>());
            diet.getMapFoodDate().put(LocalDate.now(), diet.getFoodListDay());

            diet.setTargetCalories(2200);
            diet.setDate(LocalDate.now());
            diet.setSavedCalories(new ArrayList<>());
            diet.getMealList().add("Breakfast");
            diet.getMealList().add("Lunch");
            diet.getMealList().add("Dinner");

            Food food1 = new Food("Banana", 234, "Breakfast");
            Food food2 = new Food("Carrot", 90, "Breakfast");
            Food food3 = new Food("Cake", 1000, "Breakfast");
            Food food4 = new Food("Strawberries", 219);

            diet.getFoodList().add(food1);
            diet.getFoodList().add(food2);
            diet.getFoodList().add(food3);
            diet.getFoodList().add(food4);

            mealSelect.getItems().addAll(diet.getMealList());
            foodSelect.getItems().addAll(diet.getFoodList());

            breakfastTable.setItems(FXCollections.observableList(diet.getFoodListDay()));
            breakfastTable.getColumns().setAll(breakfastFood, breakfastCalorie);

        }
        diet.setChangesMade(false);
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }
}
