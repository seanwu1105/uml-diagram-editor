package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class CreateComponentHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(final MouseEvent event) {
        final var newUmlObject = createUmlObject();
        final var canvas = (Canvas) event.getTarget();
    }

    abstract UmlObject createUmlObject();
}
