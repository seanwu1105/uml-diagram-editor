package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.NoHandler;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class Mode {

    @NotNull
    private final String id;
    @NotNull
    private EventHandler<MouseEvent> mousePressedHandler = new NoHandler();

    Mode(@NotNull final String id) {
        this.id = id;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public Image getIcon() {
        final var imagePath = "/image/";
        final var imageFileExtension = ".png";
        return new Image(getClass().getResourceAsStream(imagePath + id + imageFileExtension));
    }

    public void onMousePressed(@NotNull final MouseEvent mouseEvent) {
        mousePressedHandler.handle(mouseEvent);
    }

    void setMousePressedHandler(@NotNull final EventHandler<MouseEvent> mousePressedHandler) {
        this.mousePressedHandler = mousePressedHandler;
    }
}
