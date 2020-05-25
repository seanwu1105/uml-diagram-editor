package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

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
        graphicComponent.select();
    }
}
