package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.canvas.node.ClassObject;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.ParentMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class CanvasTest {

    private Canvas canvas;

    @Start
    private void start(@NotNull final Stage stage) {
        canvas = Canvas.getNewInstance();
        final var scene = new Scene(canvas);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldCreateClassObject(@NotNull final FxRobotInterface robot) {
        final var addClassObjectMode = ModeFactory.createAddClassObjectMode();
        canvas.setMode(addClassObjectMode);
        robot.clickOn(canvas);
        assertAll(
                () -> verifyThat(canvas, ParentMatchers.hasChildren(1)),
                () -> assertThat(canvas.getChildren().get(0), instanceOf(ClassObject.class))
        );
    }
}