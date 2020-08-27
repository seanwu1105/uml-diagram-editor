package io.github.seanwu1105.umldiagrameditor.canvas.mouse_handler;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class DeselectAllObjectsHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(@NotNull final MouseEvent event) {
        final var canvas = (Canvas) event.getSource();
        canvas.deselectAll();
    }
}
