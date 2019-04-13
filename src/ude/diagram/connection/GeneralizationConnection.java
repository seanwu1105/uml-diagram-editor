package ude.diagram.connection;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GeneralizationConnection extends ArrowConnection {
    public GeneralizationConnection(double x, double y) {
        super(x, y);
        double arrowHeadLength = 10 / Math.sqrt(2);
        arrowHead = new Polygon(
                0, 0, // the centre of triangle
                -arrowHeadLength, -arrowHeadLength,
                -arrowHeadLength, +arrowHeadLength,
                +arrowHeadLength, +arrowHeadLength
        );
        arrowHead.layoutXProperty().bind(startXProperty());
        arrowHead.layoutYProperty().bind(startYProperty());

        // Use startXY and endXY properties to calculate the rotate degrees of arrowHead.
        arrowHead.rotateProperty().bind(Bindings.createDoubleBinding(
                () -> {
                    double deg = Math.toDegrees(Math.atan((endYProperty().get() - startYProperty().get()) / (endXProperty().get() - startXProperty().get())));
                    // In case that arctan switch between +90 degrees to -90 degrees.
                    if (getEndX() - getStartX() < 0)
                        return 45 + 180 + deg;
                    return 45 + deg;
                    },
                startXProperty(), startYProperty(), endXProperty(), endYProperty()
        ));
        arrowHead.setFill(Color.GHOSTWHITE);
        arrowHead.setStroke(Color.BLACK);
    }
}
