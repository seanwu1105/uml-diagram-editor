package ude.frontend.diagram;

import ude.backend.CompositeObject;
import ude.backend.UmlObject;
import ude.frontend.diagram.shape.UmlBaseShape;

public class ShapeGroup extends CompositeObject implements Selectable {
    private boolean isSelected = false;

    public ShapeGroup() {
        super();
    }

    @Override
    public void add(UmlObject child) {
        super.add(child);
        if (child instanceof UmlBaseShape && ((UmlBaseShape) child).isSelected())
            select();
    }

    @Override
    public void select() {
        if (!isSelected) {
            isSelected = true;
            for (UmlObject child : children) {
                if (child instanceof Selectable)
                    ((Selectable) child).select();
            }
            if (parent instanceof Selectable)
                ((Selectable) parent).select();
        }
    }

    @Override
    public void deselect() {
        if (isSelected) {
            isSelected = false;
            for (UmlObject child : children) {
                if (child instanceof Selectable)
                    ((Selectable) child).deselect();
            }
            if (parent instanceof Selectable)
                ((Selectable) parent).deselect();
        }
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
}
