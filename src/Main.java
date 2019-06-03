import javafx.application.Application;
import javafx.stage.Stage;
import ude.MainScene;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UML Diagram Editor");
        primaryStage.setScene(new MainScene());
        primaryStage.show();
    }
}