package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicObject implements UmlObject {

    @NotNull
    private final ObjectType objectType;
    @NotNull
    private Position position = new Position(0, 0);
    private int width = 0;
    private int height = 0;
    @Nullable
    private CompositeObject parent;

    public BasicObject(@NotNull final ObjectType objectType) {
        this.objectType = objectType;
    }

    @NotNull
    public ObjectType getObjectType() {
        return objectType;
    }

    @NotNull
    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(@NotNull final Position position) {
        this.position = position;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        if (width < 0)
            throw new IllegalArgumentException("Width should be greater or equal to zero but was " + width + ".");
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        if (height < 0)
            throw new IllegalArgumentException("Height should be greater or equal to zero but was " + height + ".");
        this.height = height;
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
        setPosition(new Position(position.getX() + xOffset, position.getY() + yOffset));
    }

    public enum ObjectType {
        CLASS, USE_CASE
    }
}
