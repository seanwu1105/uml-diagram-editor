package ude.backend;

import java.util.HashSet;
import java.util.Set;

public class CompositeObject extends UmlObject {
    protected Set<UmlObject> children = new HashSet<>();

    public CompositeObject() {
    }

    /**
     * Get the group root which the umlObject belongs to.
     *
     * @param umlObject the target object to search for group root
     * @return the root object in group or umlObject itself if there is no parent
     */
    public static UmlObject getRoot(UmlObject umlObject) {
        while (umlObject.parent != null)
            umlObject = umlObject.parent;
        return umlObject;
    }

    public void add(UmlObject child) {
        children.add(child);
        child.parent = this;
    }

    void ungroup() {
        for (UmlObject child : children) {
            child.parent = null;
        }
        children.clear();
    }
}
