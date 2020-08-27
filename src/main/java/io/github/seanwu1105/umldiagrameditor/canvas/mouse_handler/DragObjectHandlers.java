package io.github.seanwu1105.umldiagrameditor.canvas.mouse_handler;

import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class DragObjectHandlers {

    public static class StartDraggingHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(@NotNull final MouseEvent event) {
            final var source = (Node) event.getSource();
            final var graphicComponent = (GraphicComponent<? extends Shape>) source.getParent();
            graphicComponent.setOriginalDraggingPosition(new Position(event.getX(), event.getY()));
        }
    }

    public static class OnDraggingHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(@NotNull final MouseEvent event) {
            final var source = (Node) event.getSource();
            final var graphicComponent = (GraphicComponent<? extends Shape>) source.getParent();
            graphicComponent.moveTo(new Position(event.getX(), event.getY()));
            graphicComponent.setOriginalDraggingPosition(new Position(event.getX(), event.getY()));
        }
    }
}
