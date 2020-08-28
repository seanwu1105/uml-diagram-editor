package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.CompositeObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject.UmlObjectEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

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

    public void group(@NotNull final Collection<UmlObject> umlObjects) {
        final var topObjects = umlObjects.stream()
                .map(UmlObject::getTopObject)
                .collect(Collectors.toUnmodifiableSet());
        if (topObjects.size() > 1) {
            final var parent = new CompositeObject();
            parent.group(topObjects);
            compositeObjects.add(parent);
        }
    }

    public void ungroup(@NotNull final Collection<UmlObject> umlObjects) {
        umlObjects.stream()
                .map(UmlObject::getTopObject)
                .filter(umlObject -> umlObject instanceof CompositeObject)
                .collect(Collectors.toUnmodifiableSet())
                .forEach(umlObject -> {
                    final var composite = (CompositeObject) umlObject;
                    composite.ungroup();
                    compositeObjects.remove(composite);
                });
    }
}
