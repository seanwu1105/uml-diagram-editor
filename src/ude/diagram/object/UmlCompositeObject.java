package ude.diagram.object;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UmlCompositeObject implements UmlBaseObject {
    private final BooleanProperty isSelected = new SimpleBooleanProperty(false);
    private UmlCompositeObject group = null;
    private Set<UmlBaseObject> umlBaseObjects;

    static public UmlBaseObject getRoot(UmlBaseObject umlBaseObject) {
        while (umlBaseObject.getGroup() != null)
            umlBaseObject = umlBaseObject.getGroup();
        return umlBaseObject;
    }

    @Override
    public void select() {
        if (!isSelected.get()) {
            isSelected.set(true);
            for (UmlBaseObject umlBaseShape : umlBaseObjects)
                umlBaseShape.select();
            if (group != null)
                group.select();
        }
    }

    @Override
    public void deselect() {
        if (isSelected.get()) {
            isSelected.set(false);
            for (UmlBaseObject umlBaseShape : umlBaseObjects)
                umlBaseShape.deselect();

            if (group != null)
                group.deselect();
        }
    }

    @Override
    public BooleanProperty selectedProperty() {
        return isSelected;
    }

    @Override
    public UmlCompositeObject getGroup() {
        return group;
    }

    @Override
    public void setGroup(UmlCompositeObject group) {
        this.group = group;
    }

    public void compose(Collection<UmlBaseObject> umlBaseObjects) {
        this.umlBaseObjects = new HashSet<>(umlBaseObjects);    // copy set
        umlBaseObjects.forEach(umlBaseObject -> umlBaseObject.setGroup(this));
    }

    public void decompose() {
        umlBaseObjects.forEach(umlBaseObject -> umlBaseObject.setGroup(null));
        umlBaseObjects.clear();
    }
}
