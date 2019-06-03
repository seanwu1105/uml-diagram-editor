package ude.diagram;

import javafx.scene.input.MouseEvent;

public interface UmlBaseShape {
    void onCreated(MouseEvent event, Diagram diagram);
}
