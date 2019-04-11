package ude.frontend.diagram.line;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import ude.frontend.diagram.Paintable;

public class AssociationLine extends Line implements Paintable {

    public AssociationLine(double x, double y) {
        super(x, y, x, y);
    }

    @Override
    public void paint(Pane container) {
        container.getChildren().add(this);
    }
}
