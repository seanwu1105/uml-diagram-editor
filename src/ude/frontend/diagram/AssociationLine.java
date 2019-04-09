package ude.frontend.diagram;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class AssociationLine extends Line implements Paintable {

    public AssociationLine(double x, double y) {
        super(x, y, x, y);
    }

    @Override
    public void paint(Pane container) {
        container.getChildren().add(this);
    }
}
