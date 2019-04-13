package ude.diagram.object;

import javafx.beans.property.BooleanProperty;

public interface UmlBaseObject {
    void select();

    void deselect();

    BooleanProperty selectedProperty();

    UmlCompositeObject getGroup();

    void setGroup(UmlCompositeObject group);
}
