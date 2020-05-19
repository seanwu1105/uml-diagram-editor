package io.github.seanwu1105.umldiagrameditor.object;

import io.github.seanwu1105.umldiagrameditor.model.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicObject implements UmlObject {

    @Nullable
    private CompositeObject parent;

    @NotNull
    private Position position = new Position(0, 0);

    @Nullable
    @Override
    public CompositeObject getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nullable final CompositeObject parent) {
        this.parent = parent;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull final Position position) {
        this.position = position;
    }

    @Override
    public void move(final int xOffset, final int yOffset) {
        setPosition(new Position(position.getX() + xOffset, position.getY() + yOffset));
    }
}
