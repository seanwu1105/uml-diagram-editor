package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.graph.ClassObject;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.UseCaseObject;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.ParentMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testfx.api.FxAssert.verifyThat;

// TODO: create an issue to TestFx about:
//       1. Robot fails to work in headless mode.
//       2. Robot will move the mouse to (0, 0) in sequential tests.
@Disabled
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
        canvas.setMode(ModeFactory.getAddClassObjectMode());

        robot.clickOn(canvas);

        assertAll(
                () -> verifyThat(canvas, ParentMatchers.hasChildren(1)),
                () -> assertThat(canvas.getChildrenUnmodifiable().get(0), instanceOf(ClassObject.class))
        );
    }

    @Test
    void shouldCreateDifferentClassObjects(@NotNull final FxRobotInterface robot) {
        canvas.setMode(ModeFactory.getAddClassObjectMode());

        robot.clickOn(canvas);
        robot.clickOn(canvas);

        assertAll(
                () -> verifyThat(canvas, ParentMatchers.hasChildren(2)),
                () -> assertNotEquals(canvas.getChildrenUnmodifiable().get(0), canvas.getChildrenUnmodifiable().get(1))
        );
    }

    @Test
    void shouldCreateUseCaseObject(@NotNull final FxRobotInterface robot) {
        canvas.setMode(ModeFactory.getAddUseCaseObjectMode());

        robot.clickOn(canvas);

        assertAll(
                () -> verifyThat(canvas, ParentMatchers.hasChildren(1)),
                () -> assertThat(canvas.getChildrenUnmodifiable().get(0), instanceOf(UseCaseObject.class))
        );
    }

    @Test
    void shouldCreateDifferentUseCaseObjects(@NotNull final FxRobotInterface robot) {
        canvas.setMode(ModeFactory.getAddUseCaseObjectMode());

        robot.clickOn(canvas);
        robot.clickOn(canvas);

        assertAll(
                () -> verifyThat(canvas, ParentMatchers.hasChildren(2)),
                () -> assertNotEquals(canvas.getChildrenUnmodifiable().get(0), canvas.getChildrenUnmodifiable().get(1))
        );
    }
}