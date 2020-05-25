package io.github.seanwu1105.umldiagrameditor.canvas.graph;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public abstract class GraphicComponent<T extends Shape> extends Group {

    @NotNull
    private final Rectangle box = new Rectangle();

    GraphicComponent(@NotNull final UmlObject umlObject) {
        initBox(umlObject);

        final var shape = initShape(umlObject);
        bindBox(shape);
        getChildren().addAll(shape, box);
    }

    private void initBox(@NotNull final UmlObject umlObject) {
        getBox().setX(umlObject.getPosition().getX());
        getBox().setY(umlObject.getPosition().getY());
        getBox().setWidth(umlObject.getWidth());
        getBox().setHeight(umlObject.getHeight());
        getBox().setFill(Color.TRANSPARENT);
        getBox().setStroke(Color.BLUE);
    }

    @NotNull
    abstract T initShape(@NotNull final UmlObject umlObject);

    abstract void bindBox(@NotNull final T shape);

    @NotNull Rectangle getBox() {
        return box;
    }
}
