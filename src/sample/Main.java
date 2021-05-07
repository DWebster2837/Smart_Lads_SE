package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Diet.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        /*Diet diet = new Diet();
        Food food = new Food("Test", 1224, "Breakfast");
        Food food2 = new Food("Test", 1224, "Breakfast");
        Food food3 = new Food("Test", 1224, "Breakfast");
        Food food4 = new Food("Test", 1224);
        diet.addFood(food);
        diet.addFood(food2);
        diet.addFood(food3);
        diet.addFood(food4);
        ArrayList<Food> foodList = new ArrayList<Food>();
        foodList = diet.getFoodList();

        for (Food f: foodList){
            System.out.println(f.toString());
        }*/
    }


}
