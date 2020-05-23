package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import org.jetbrains.annotations.NotNull;

public interface DiagramEventListener {
    void updated(@NotNull final UmlObject umlObject);
}
