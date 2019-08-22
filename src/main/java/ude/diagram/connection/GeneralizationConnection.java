package ude.diagram.connection;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class GeneralizationConnection extends ArrowConnection {
    public GeneralizationConnection(double x, double y) {
        super(x, y);
        double arrowHeadLength = 10;
        arrowHead = new Polygon(
                0, 0, // the centre of triangle
                +arrowHeadLength, 0,
                0, +arrowHeadLength
        );
        arrowHead.layoutXProperty().bind(startXProperty());
        arrowHead.layoutYProperty().bind(startYProperty());

        arrowHead.setFill(Color.GHOSTWHITE);
        arrowHead.setStroke(Color.BLACK);

        Rotate rotate = new Rotate();
        rotate.angleProperty().bind(Bindings.createDoubleBinding(() -> {
                    // Use startXY and endXY properties to calculate the rotate degrees of arrowHead.
                    double deg = -45 + Math.toDegrees(Math.atan((endYProperty().get() - startYProperty().get()) / (endXProperty().get() - startXProperty().get())));
                    // In case that arctan switch between +90 degrees to -90 degrees.
                    if (getEndX() - getStartX() < 0)
                        return 180 + deg;
                    return deg;
                },
                startXProperty(), startYProperty(), endXProperty(), endYProperty()
        ));
        arrowHead.getTransforms().add(rotate);
    }
}
