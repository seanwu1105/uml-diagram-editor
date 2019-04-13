package ude.diagram.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UmlUseCaseObject extends UmlBasicObject {
    private static final double WIDTH = 100, HEIGHT = 80;

    public UmlUseCaseObject(double x, double y) {
        super(new Ellipse(), x, y, WIDTH, HEIGHT);
        shape.setFill(Color.AZURE);
        shape.setStroke(Color.BLACK);
    }

    @Override
    void bindShapeToHolder() {
        ((Ellipse) shape).centerXProperty().bind(xProperty().add(0.5 * getWidth()));
        ((Ellipse) shape).centerYProperty().bind(yProperty().add(0.5 * getHeight()));
        ((Ellipse) shape).radiusXProperty().bind(widthProperty().multiply(0.5));
        ((Ellipse) shape).radiusYProperty().bind(heightProperty().multiply(0.5));
    }
}
