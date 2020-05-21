package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class Canvas extends Pane {

    @NotNull
    private Mode mode = ModeFactory.createSelectMode();

    public Canvas() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressed(event));
    }

    public void setMode(@NotNull final Mode mode) {
        this.mode = mode;
    }
}
