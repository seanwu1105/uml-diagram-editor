package io.github.seanwu1105.umldiagrameditor.canvas.graph;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.jetbrains.annotations.NotNull;

public class UseCaseObject extends GraphicComponent<Ellipse> {

    public UseCaseObject(@NotNull final UmlObject umlObject) {
        super(umlObject);
    }

    @Override
    @NotNull Ellipse initShape(final @NotNull UmlObject umlObject) {
        final var radiusX = umlObject.getWidth() / 2;
        final var radiusY = umlObject.getHeight() / 2;
        final var ellipse = new Ellipse(
                umlObject.getPosition().getX() + radiusX,
                umlObject.getPosition().getY() + radiusY,
                radiusX,
                radiusY
        );
        ellipse.setFill(Color.WHITE);
        ellipse.setStroke(Color.BLACK);
        return ellipse;
    }

    @Override
    void bindBox(final @NotNull Ellipse shape) {
        shape.centerXProperty().bind(getBox().xProperty().add(getBox().getWidth() / 2));
        shape.centerYProperty().bind(getBox().yProperty().add(getBox().getHeight() / 2));
    }
}
