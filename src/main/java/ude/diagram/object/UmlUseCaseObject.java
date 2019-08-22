package ude.diagram.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UmlUseCaseObject extends UmlBasicObject<Ellipse> {
    private static final double WIDTH = 100, HEIGHT = 60;

    public UmlUseCaseObject(double x, double y) {
        super(new Ellipse(), x, y, WIDTH, HEIGHT);
        getShape().setFill(Color.AZURE);
        getShape().setStroke(Color.BLACK);
    }

    @Override
    void bindShapeToHolder() {
        getShape().centerXProperty().bind(xProperty().add(0.5 * getWidth()));
        getShape().centerYProperty().bind(yProperty().add(0.5 * getHeight()));
        getShape().radiusXProperty().bind(widthProperty().multiply(0.5));
        getShape().radiusYProperty().bind(heightProperty().multiply(0.5));
    }
}
