package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

public abstract class UmlObject {

    @NotNull
    private final Collection<UmlObjectEventListener> onMovedListeners = new HashSet<>();

    public abstract @NotNull Position getPosition();

    public abstract double getHeight();

    public abstract double getWidth();

    @Nullable
    public abstract CompositeObject getParent();

    public abstract void setParent(@Nullable CompositeObject parent);

    @NotNull
    public abstract UmlObject getTopObject();

    protected abstract void move(final double xOffset, final double yOffset);

    public void drag(final double xOffset, final double yOffset) {
        move(xOffset, yOffset);
        onMovedListeners.forEach(listener -> listener.updated(this));
    }

    public void addOnMovedListener(@NotNull final UmlObjectEventListener listener) {
        onMovedListeners.add(listener);
    }

    public interface UmlObjectEventListener {
        void updated(@NotNull final UmlObject umlObject);
    }
}
