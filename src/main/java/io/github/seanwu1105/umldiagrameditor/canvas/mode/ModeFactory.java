package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.CreateClassObjectHandler;
import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.CreateUseCaseObjectHandler;
import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.NoHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModeFactory {

    @Nullable
    private static Mode SELECT_MODE;
    @Nullable
    private static Mode ADD_CLASS_OBJECT_MODE;
    @Nullable
    private static Mode ADD_USE_CASE_OBJECT_MODE;

    @NotNull
    public static Mode getSelectMode() {
        if (SELECT_MODE == null) {
            synchronized (ModeFactory.class) {
                final var mode = new Mode(ModeId.SELECT);
                mode.setMousePressedHandler(new NoHandler());
                SELECT_MODE = mode;
            }
        }
        return SELECT_MODE;
    }

    @NotNull
    public static Mode getAddClassObjectMode() {
        if (ADD_CLASS_OBJECT_MODE == null) {
            synchronized (ModeFactory.class) {
                final var mode = new Mode(ModeId.CLASS);
                mode.setMousePressedHandler(new CreateClassObjectHandler());
                ADD_CLASS_OBJECT_MODE = mode;
            }
        }
        return ADD_CLASS_OBJECT_MODE;
    }

    @NotNull
    public static Mode getAddUseCaseObjectMode() {
        if (ADD_USE_CASE_OBJECT_MODE == null) {
            synchronized (ModeFactory.class) {
                final var mode = new Mode(ModeId.USE_CASE);
                mode.setMousePressedHandler(new CreateUseCaseObjectHandler());
                ADD_USE_CASE_OBJECT_MODE = mode;
            }
        }
        return ADD_USE_CASE_OBJECT_MODE;
    }
}
