package io.github.seanwu1105.umldiagrameditor.object;

import io.github.seanwu1105.umldiagrameditor.model.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface UmlObject {

    @Nullable
    CompositeObject getParent();

    void setParent(@Nullable CompositeObject parent);

    void setPosition(@NotNull final Position position);

    void move(int xOffset, int yOffset);
}
