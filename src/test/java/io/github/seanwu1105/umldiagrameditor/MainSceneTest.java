package io.github.seanwu1105.umldiagrameditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.ParentMatchers;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class MainSceneTest {

    @Start
    private void start(@NotNull final Stage stage) throws IOException {
        final var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        final BorderPane hierarchy = loader.load();
        final var mainScene = new Scene(hierarchy);
        stage.setScene(mainScene);
        stage.show();
    }

    @Test
    void shouldHaveToolButtons() {
        verifyThat("#toolBar", ParentMatchers.hasChild());
    }
}