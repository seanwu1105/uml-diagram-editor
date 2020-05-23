package io.github.seanwu1105.umldiagrameditor.canvas.node;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class ClassObject extends Rectangle {

    public ClassObject(@NotNull final UmlObject umlObject) {
        setX(umlObject.getPosition().getX());
        setY(umlObject.getPosition().getY());
        setWidth(umlObject.getWidth());
        setHeight(umlObject.getHeight());
        initAppearance();
    }

    private void initAppearance() {
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
