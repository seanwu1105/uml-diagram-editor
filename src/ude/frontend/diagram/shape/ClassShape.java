package ude.frontend.diagram.shape;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ClassShape extends UmlBaseShape {
    public ClassShape(double x, double y) {
        super(x, y, 100, 150);
        shape = new Rectangle(width, height);
        ((Rectangle) shape).xProperty().bind(holder.xProperty());
        ((Rectangle) shape).yProperty().bind(holder.yProperty());
        shape.setFill(Color.ANTIQUEWHITE);
        shape.setStroke(Color.BLACK);
    }
}
