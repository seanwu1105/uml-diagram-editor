package io.github.seanwu1105.umldiagrameditor.canvas.node;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class ClassObject extends Rectangle {

    public ClassObject(@NotNull final Position position, final int width, final int height) {
        setX(position.getX());
        setY(position.getY());
        setWidth(width);
        setHeight(height);
        initAppearance();
    }

    private void initAppearance() {
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
