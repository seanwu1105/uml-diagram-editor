package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.CompositeObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject.UmlObjectEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class Diagram {

    @NotNull
    private final Collection<BasicObject> basicObjects = new HashSet<>();
    @NotNull
    private final Collection<CompositeObject> compositeObjects = new HashSet<>();
    @NotNull
    private final Collection<UmlObjectEventListener> onAddedListeners = new HashSet<>();

    public void addObject(@NotNull final BasicObject basicObject) {
        basicObjects.add(basicObject);
        onAddedListeners.forEach(listener -> listener.updated(basicObject));
    }

    public void addOnAddedListener(@NotNull final UmlObjectEventListener listener) {
        onAddedListeners.add(listener);
    }

    // TODO: group with hierarchy
    //  parent.group(umlObjects.map(o -> getTopObject(o).toSet())
    public void group(@NotNull final Iterable<UmlObject> umlObjects) {
        final var parent = new CompositeObject();
        parent.group(umlObjects);
        compositeObjects.add(parent);
    }
}
