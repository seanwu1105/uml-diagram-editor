package ude.diagram.connection;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class ArrowConnection extends AssociationConnection {
    Shape arrowHead;

    ArrowConnection(double x, double y) {
        super(x, y);

        parentProperty().addListener((observableValue, oldParent, newParent) -> {
            if (newParent != null)
                ((Pane) newParent).getChildren().add(arrowHead);
            else
                ((Pane) oldParent).getChildren().remove(arrowHead);
        });
    }

    @Override
    public void toFront() {
        super.toFront();
        arrowHead.toFront();
    }
}
