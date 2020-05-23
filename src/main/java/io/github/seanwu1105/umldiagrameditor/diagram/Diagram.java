package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class Diagram {

    @NotNull
    private final Collection<BasicObject> basicObjects = new HashSet<>();
    @NotNull
    private final Collection<DiagramEventListener> onAddedListeners = new HashSet<>();

    public void addObject(@NotNull final BasicObject basicObject) {
        basicObjects.add(basicObject);
        onAddedListeners.forEach(listener -> listener.updated(basicObject));
    }

    public void addOnAddedListener(@NotNull final DiagramEventListener listener) {
        onAddedListeners.add(listener);
    }
}
