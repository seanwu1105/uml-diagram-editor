package io.github.seanwu1105.umldiagrameditor.canvas.graph;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public abstract class GraphicComponent<T extends Shape> {

    @NotNull
    private final T shape;
    @NotNull
    private final Rectangle container = new Rectangle();

    GraphicComponent(@NotNull final UmlObject umlObject) {
        initContainer(umlObject);
        shape = initShape(umlObject);
        bindContainer(shape);
    }

    private void initContainer(@NotNull final UmlObject umlObject) {
        getContainer().setX(umlObject.getPosition().getX());
        getContainer().setY(umlObject.getPosition().getY());
        getContainer().setWidth(umlObject.getWidth());
        getContainer().setHeight(umlObject.getHeight());
        getContainer().setFill(Color.TRANSPARENT);
        getContainer().setStroke(Color.BLUE);
    }

    @NotNull
    abstract T initShape(@NotNull final UmlObject umlObject);

    abstract void bindContainer(@NotNull final T shape);

    @NotNull
    public Shape getShape() {
        return shape;
    }

    @NotNull
    public Rectangle getContainer() {
        return container;
    }
}
