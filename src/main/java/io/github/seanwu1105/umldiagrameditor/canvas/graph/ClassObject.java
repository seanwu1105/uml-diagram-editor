package io.github.seanwu1105.umldiagrameditor.canvas.graph;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public class ClassObject extends GraphicComponent<Rectangle> {

    public ClassObject(@NotNull final UmlObject umlObject) {
        super(umlObject);
    }

    @NotNull
    @Override
    Rectangle initShape(@NotNull final UmlObject umlObject) {
        final var rectangle = new Rectangle(
                umlObject.getPosition().getX(),
                umlObject.getPosition().getY(),
                umlObject.getWidth(),
                umlObject.getHeight()
        );
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);

        return rectangle;
    }

    @Override
    void bindBox(@NotNull final Rectangle shape) {
        shape.xProperty().bind(getBox().xProperty());
        shape.yProperty().bind(getBox().yProperty());
    }
}
