package ude.diagram.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class UmlClassObject extends UmlBasicObject<Rectangle> {
    private final static double WIDTH = 100, HEIGHT = 80;

    public UmlClassObject(double x, double y) {
        super(new Rectangle(), x, y, WIDTH, HEIGHT);
        getShape().setFill(Color.ANTIQUEWHITE);
        getShape().setStroke(Color.BLACK);
    }

    @Override
    void bindShapeToHolder() {
        getShape().xProperty().bind(xProperty());
        getShape().yProperty().bind(yProperty());
        getShape().widthProperty().bind(widthProperty());
        getShape().heightProperty().bind(heightProperty());
        addDecorations();
    }

    private void addDecorations() {
        Line[] lines = new Line[2];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line();
            lines[i].startXProperty().bind(xProperty());
            lines[i].startYProperty().bind(yProperty().add(heightProperty().multiply((double) (i + 1) / 3)));
            lines[i].endXProperty().bind(xProperty().add(widthProperty()));
            lines[i].endYProperty().bind(yProperty().add(heightProperty().multiply((double) (i + 1) / 3)));
            decorations.add(lines[i]);
        }
    }
}
