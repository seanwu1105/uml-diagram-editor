package ude.diagram.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class UmlClassObject extends UmlBasicObject {
    private final static double WIDTH = 100, HEIGHT = 150;

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
