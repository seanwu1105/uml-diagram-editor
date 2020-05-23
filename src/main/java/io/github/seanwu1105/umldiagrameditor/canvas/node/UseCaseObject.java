package io.github.seanwu1105.umldiagrameditor.canvas.node;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.jetbrains.annotations.NotNull;

public class UseCaseObject extends Ellipse {

    public UseCaseObject(@NotNull final UmlObject umlObject) {
        final var radiusX = umlObject.getWidth() / 2;
        final var radiusY = umlObject.getHeight() / 2;
        setRadiusX(radiusX);
        setRadiusY(radiusY);
        setCenterX(umlObject.getPosition().getX() + radiusX);
        setCenterY(umlObject.getPosition().getY() + radiusY);
        initAppearance();
    }

    private void initAppearance() {
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
