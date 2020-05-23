package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UmlObject {

    @NotNull Position getPosition();

    int getHeight();

    int getWidth();

    @Nullable
    CompositeObject getParent();

    void setParent(@Nullable CompositeObject parent);

    @NotNull
    UmlObject getTopObject();

    void move(int xOffset, int yOffset);
}
