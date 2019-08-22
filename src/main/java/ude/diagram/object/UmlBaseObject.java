package ude.diagram.object;

import javafx.beans.property.BooleanProperty;

public interface UmlBaseObject {
    void select();

    void deselect();

    BooleanProperty selectedProperty();

    // the caller could help to prevent infinite recursion
    void setDraggingOriginal(double x, double y, UmlBaseObject caller);

    UmlCompositeObject getGroup();

    void setGroup(UmlCompositeObject group);

    // the caller could help to prevent infinite recursion
    void move(double offsetX, double offsetY, UmlBaseObject caller);
}
