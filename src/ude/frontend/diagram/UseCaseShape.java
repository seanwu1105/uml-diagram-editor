package ude.frontend.diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UseCaseShape extends BaseShape {
    public UseCaseShape(double x, double y) {
        super(x, y, 100, 80);
        shape = new Ellipse(width / 2, height / 2);
        ((Ellipse) shape).centerXProperty().bind(holder.xProperty().add(0.5 * width));
        ((Ellipse) shape).centerYProperty().bind(holder.yProperty().add(0.5 * height));
        shape.setFill(Color.AZURE);
        shape.setStroke(Color.BLACK);
    }
}
