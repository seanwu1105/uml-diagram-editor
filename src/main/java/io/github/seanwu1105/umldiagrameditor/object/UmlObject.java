package io.github.seanwu1105.umldiagrameditor.object;

import org.jetbrains.annotations.Nullable;

interface UmlObject {

    @Nullable
    CompositeObject getParent();

    void setParent(@Nullable CompositeObject parent);

    void move(int xOffset, int yOffset);
}
