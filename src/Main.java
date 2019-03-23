import javafx.application.Application;
import javafx.stage.Stage;
import ude.frontend.MainScene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("UML Diagram Editor");

        primaryStage.setScene(new MainScene());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
