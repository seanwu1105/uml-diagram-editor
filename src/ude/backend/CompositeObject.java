package ude.backend;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CompositeObject extends UmlObject {
    private Set<UmlObject> children = new HashSet<>();

    CompositeObject() {
        super();
    }

    public CompositeObject(UmlObject child) {
        add(child);
    }

    public CompositeObject(Collection<UmlObject> children) {
        add(children);
    }

    public Set<UmlObject> getChildren() {
        return children;
    }

    public void add(UmlObject child) {
        children.add(child);
        child.parent = this;
    }

    public void add(Collection<UmlObject> children) {
        this.children.addAll(children);
        children.forEach(child -> child.parent = this);
    }

    public void ungroup() {
        for (UmlObject child : children) {
            child.parent = null;
        }
        children.clear();
    }
}
