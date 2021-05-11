
public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Show the scene from sample.xml
        Parent root = FXMLLoader.load(getClass().getResource("CreateGoal.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("hello");
        primaryStage.show();

        stage = primaryStage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}