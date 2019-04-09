package ude.frontend.diagram;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class ArrowLine extends AssociationLine {
    Shape arrowHead;

    ArrowLine(double x, double y) {
        super(x, y);
    }

    @Override
    public void paint(Pane container) {
        super.paint(container);
        container.getChildren().add(arrowHead);
    }

    @Override
    public void toFront() {
        super.toFront();
        arrowHead.toFront();
    }
}
