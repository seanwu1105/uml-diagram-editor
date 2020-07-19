package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

abstract class CreateObjectHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(final @NotNull MouseEvent event) {
        final var newObject = createObject();
        final var canvas = (Canvas) event.getSource();
        newObject.setPosition(new Position((int) event.getX(), (int) event.getY()));
        canvas.addObject(newObject);
    }

    @NotNull
    abstract BasicObject createObject();
}
