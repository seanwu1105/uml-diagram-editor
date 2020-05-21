package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.UmlComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UmlObject extends UmlComponent {

    @Nullable
    CompositeObject getParent();

    void setParent(@Nullable CompositeObject parent);

    @NotNull
    UmlObject getTopObject();

    void move(int xOffset, int yOffset);
}
