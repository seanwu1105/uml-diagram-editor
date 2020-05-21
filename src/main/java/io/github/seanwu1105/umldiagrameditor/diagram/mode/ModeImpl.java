package io.github.seanwu1105.umldiagrameditor.diagram.mode;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class ModeImpl implements Mode {

    private final String name = "select";

    @NotNull
    @Override
    public Image getIcon() {
        return new Image(getClass().getResourceAsStream(IMAGE_PATH + name + ".png"));
    }
}
