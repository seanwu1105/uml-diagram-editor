package ude.diagram.connection;

import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import ude.diagram.Diagram;
import ude.diagram.UmlBaseShape;
import ude.diagram.object.UmlBasicObject;

import java.util.List;

public class AssociationConnection extends Line implements UmlBaseShape {

    public AssociationConnection(double x, double y) {
        super(x, y, x, y);
    }

    @Override
    public void onCreated(MouseEvent event, Diagram diagram) {
        event.consume();    // only in SELECT mode can UML basicObjects be selected (get MOUSE_PRESSED event)
        UmlBasicObject<? extends Shape> lineSource = diagram.getUmlBasicObject(event.getTarget());
        if (lineSource != null) {
            List<ObservableValue<Number>> sourcePortPosition = lineSource.getClosestPortPositionProperty(event.getX(), event.getY());
            this.startXProperty().bind(sourcePortPosition.get(0));
            this.startYProperty().bind(sourcePortPosition.get(1));
            diagram.connections.add(this);
            diagram.getChildren().add(this);
        }
    }
}
