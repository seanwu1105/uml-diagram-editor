package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class CompositeObject extends UmlObject {

    @NotNull
    private Iterable<UmlObject> children = new HashSet<>();
    @Nullable
    private CompositeObject parent;

    @Override
    public @NotNull Position getPosition() {
        var xMin = Double.MAX_VALUE;
        var yMin = Double.MAX_VALUE;
        for (final var child : getChildren()) {
            xMin = Math.min(xMin, child.getPosition().getX());
            yMin = Math.min(yMin, child.getPosition().getY());
        }
        return new Position(xMin, yMin);
    }

    @Override
    public double getWidth() {
        var xMax = Double.MIN_VALUE;
        for (final var child : getChildren()) {
            xMax = Math.max(xMax, child.getPosition().getX() + child.getWidth());
        }
        return xMax - getPosition().getX();
    }

    @Override
    public double getHeight() {
        var yMax = Double.MIN_VALUE;
        for (final var child : getChildren()) {
            yMax = Math.max(yMax, child.getPosition().getY() + child.getHeight());
        }
        return yMax - getPosition().getY();
    }

    public void group(@NotNull final Iterable<UmlObject> children) {
        children.forEach(child -> child.setParent(this));
        this.children = children;
    }

    @NotNull
    public Iterable<UmlObject> ungroup() {
        final var children = getChildren();
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
    protected void move(final double xOffset, final double yOffset) {
        getChildren().forEach(child -> child.move(xOffset, yOffset));
    }
}
