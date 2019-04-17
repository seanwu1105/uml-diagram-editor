package ude.diagram.connection;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class CompositionConnection extends ArrowConnection {
    public CompositionConnection(double x, double y) {
        super(x, y);
        double arrowHeadLength = 10;
        arrowHead = new Rectangle(arrowHeadLength, arrowHeadLength);
        ((Rectangle) arrowHead).xProperty().bind(startXProperty());
        ((Rectangle) arrowHead).yProperty().bind(startYProperty());

        arrowHead.setFill(Color.GHOSTWHITE);
        arrowHead.setStroke(Color.BLACK);

        Rotate rotate = new Rotate();
        rotate.pivotXProperty().bind(startXProperty());
        rotate.pivotYProperty().bind(startYProperty());
        rotate.angleProperty().bind(Bindings.createDoubleBinding(() -> {
                    // Use startXY and endXY properties to calculate the rotate degrees of arrowHead.
                    double deg = -45 + Math.toDegrees(Math.atan((endYProperty().get() - startYProperty().get()) / (endXProperty().get() - startXProperty().get())));
                    if (getEndX() - getStartX() < 0) return 180 + deg;
                    return deg;
                },
                startXProperty(), startYProperty(), endXProperty(), endYProperty()
        ));
        arrowHead.getTransforms().add(rotate);
    }
}
