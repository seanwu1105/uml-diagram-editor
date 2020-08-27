package io.github.seanwu1105.umldiagrameditor.canvas.mouse_handler;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class SelectObjectHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(@NotNull final MouseEvent event) {
        final var source = (Node) event.getSource();
        final var graphicComponent = (GraphicComponent<? extends Shape>) source.getParent();
        final var canvas = (Canvas) graphicComponent.getParent();
        canvas.select(graphicComponent);
    }
}
