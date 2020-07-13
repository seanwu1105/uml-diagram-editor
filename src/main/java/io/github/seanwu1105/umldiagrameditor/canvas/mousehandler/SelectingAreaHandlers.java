package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

import io.github.seanwu1105.umldiagrameditor.canvas.Canvas;
import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectingAreaHandlers {

    public static class CreateSelectingAreaHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(final MouseEvent event) {
            if (event.getTarget() instanceof Canvas) {
                final var canvas = (Canvas) event.getSource();
                canvas.createSelectingArea(new Position(event.getX(), event.getY()));
            }
        }
    }

    public static class ResizeSelectingAreaHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(final MouseEvent event) {
            final var canvas = (Canvas) event.getSource();
            canvas.resizeSelectingArea(new Position(event.getX(), event.getY()));
        }
    }

    public static class RemoveSelectingAreaHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(final MouseEvent event) {
            final var canvas = (Canvas) event.getSource();
            canvas.selectInArea(new Position(event.getX(), event.getY()));
            canvas.removeSelectingArea();
        }
    }
}
