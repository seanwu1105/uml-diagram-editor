package io.github.seanwu1105.umldiagrameditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        final var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        final MainController controller = loader.getController();
        final BorderPane hierarchy = loader.load();
        final var mainScene = new Scene(hierarchy);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
