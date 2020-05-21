package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.CreateClassObjectHandler;
import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.NoHandler;
import org.jetbrains.annotations.NotNull;

public class ModeFactory {

    @NotNull
    public static Mode createSelectMode() {
        final var mode = new Mode("select");
        mode.setMousePressedHandler(new NoHandler());
        return mode;
    }

    @NotNull
    public static Mode createAddClassObjectMode() {
        final var mode = new Mode("class");
        mode.setMousePressedHandler(new CreateClassObjectHandler());
        return mode;
    }
}
