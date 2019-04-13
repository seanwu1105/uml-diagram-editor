package ude.diagram.connection;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CompositionConnection extends ArrowConnection {
    public CompositionConnection(double x, double y) {
        super(x, y);
        double arrowHeadLength = 10;
        arrowHead = new Rectangle(arrowHeadLength, arrowHeadLength);
        ((Rectangle) arrowHead).xProperty().bind(startXProperty().subtract(0.5 * arrowHeadLength));
        ((Rectangle) arrowHead).yProperty().bind(startYProperty().subtract(0.5 * arrowHeadLength));

        // Use startXY and endXY properties to calculate the rotate degrees of arrowHead.
        arrowHead.rotateProperty().bind(Bindings.createDoubleBinding(
                () -> 45 + Math.toDegrees(Math.atan((endYProperty().get() - startYProperty().get()) / (endXProperty().get() - startXProperty().get()))),
                startXProperty(), startYProperty(), endXProperty(), endYProperty()
        ));
        arrowHead.setFill(Color.GHOSTWHITE);
        arrowHead.setStroke(Color.BLACK);
    }
}
