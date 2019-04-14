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
    // for the drag event, the original coordinates of when pressed the mouse

    static public UmlBaseObject getRoot(UmlBaseObject umlBaseObject) {
        while (umlBaseObject.getGroup() != null)
            umlBaseObject = umlBaseObject.getGroup();
        return umlBaseObject;
    }

    @Override
    public void select() {
        if (!isSelected.get()) {
            isSelected.set(true);
            umlBaseObjects.forEach(UmlBaseObject::select);
            if (group != null)
                group.select();
        }
    }

    @Override
    public void deselect() {
        if (isSelected.get()) {
            isSelected.set(false);
            umlBaseObjects.forEach(UmlBaseObject::deselect);
            if (group != null)
                group.deselect();
        }
    }

    @Override
    public BooleanProperty selectedProperty() {
        return isSelected;
    }

    @Override
    public void setDraggingOriginal(double draggingOriginalX, double draggingOriginalY, UmlBaseObject caller) {
        if (group != null && group != caller)
            group.setDraggingOriginal(draggingOriginalX, draggingOriginalY, this);
        else
            umlBaseObjects.forEach(object -> object.setDraggingOriginal(draggingOriginalX, draggingOriginalY, this));
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

    public void move(double offsetX, double offsetY, UmlBaseObject caller) {
        if (group != null && group != caller)
            group.move(offsetX, offsetY, this);
        else
            umlBaseObjects.forEach(umlBaseObject -> umlBaseObject.move(offsetX, offsetY, this));
    }
}
