package io.github.seanwu1105.umldiagrameditor.diagram.mode;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public interface Mode {

    String IMAGE_PATH = "/image/";

    @NotNull
    Image getIcon();
}
