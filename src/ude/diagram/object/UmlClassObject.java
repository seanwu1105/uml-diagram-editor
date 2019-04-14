package ude.diagram.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UmlClassObject extends UmlBasicObject {
    private final static double WIDTH = 100, HEIGHT = 150;

    // TODO: add a decorating line
    public UmlClassObject(double x, double y) {
        super(new Rectangle(), x, y, WIDTH, HEIGHT);
        shape.setFill(Color.ANTIQUEWHITE);
        shape.setStroke(Color.BLACK);
    }

    @Override
    void bindShapeToHolder() {
        ((Rectangle) shape).xProperty().bind(xProperty());
        ((Rectangle) shape).yProperty().bind(yProperty());
        ((Rectangle) shape).widthProperty().bind(widthProperty());
        ((Rectangle) shape).heightProperty().bind(heightProperty());
    }
}
