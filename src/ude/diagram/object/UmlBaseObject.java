package ude.diagram.object;

import javafx.beans.property.BooleanProperty;

public interface UmlBaseObject {
    void select();

    void deselect();

    BooleanProperty selectedProperty();

    // the caller prevents infinite recursion as the parent will also call child.setDraggingOriginal()
    void setDraggingOriginal(double x, double y, UmlBaseObject caller);

    UmlCompositeObject getGroup();

    void setGroup(UmlCompositeObject group);

    // the caller prevents infinite recursion as the parent will also call child.setDraggingOriginal()
    void move(double offsetX, double offsetY, UmlBaseObject caller);
}
