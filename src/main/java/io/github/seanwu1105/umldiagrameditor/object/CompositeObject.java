package io.github.seanwu1105.umldiagrameditor.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class CompositeObject implements UmlObject {

    @NotNull
    private Iterable<UmlObject> children = new HashSet<>();
    @Nullable
    private CompositeObject parent;

    public void group(@NotNull final Iterable<UmlObject> children) {
        children.forEach(child -> child.setParent(this));
        this.children = children;
    }

    public Iterable<UmlObject> ungroup() {
        final var children = this.children;
        this.children = new HashSet<>();
        children.forEach(child -> child.setParent(null));
        return children;
    }

    @NotNull
    public Iterable<UmlObject> getChildren() {
        return children;
    }

    @Nullable
    @Override
    public CompositeObject getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nullable final CompositeObject parent) {
        this.parent = parent;
    }

    @Override
    public @NotNull UmlObject getTopObject() {
        if (parent == null) return this;
        return parent.getTopObject();
    }

    @Override
    public void move(final int xOffset, final int yOffset) {
        children.forEach(child -> child.move(xOffset, yOffset));
    }
}
