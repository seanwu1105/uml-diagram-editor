package io.github.seanwu1105.umldiagrameditor.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface UmlObject {

    @Nullable
    CompositeObject getParent();

    void setParent(@Nullable CompositeObject parent);

    @NotNull
    UmlObject getTopObject();

    void move(int xOffset, int yOffset);
}
