package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import org.jetbrains.annotations.NotNull;

public enum ModeId {
    SELECT, CLASS;

    @NotNull
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
