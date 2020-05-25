package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import io.github.seanwu1105.umldiagrameditor.canvas.mousehandler.NoHandler;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class Mode {

    @NotNull
    private final ModeId id;
    @NotNull
    private EventHandler<MouseEvent> mousePressedOnCanvasHandler = new NoHandler();
    @NotNull
    private EventHandler<MouseEvent> mousePressedOnGraphicComponentHandler = new NoHandler();

    Mode(@NotNull final ModeId id) {
        this.id = id;
    }

    @NotNull
    public ModeId getId() {
        return id;
    }

    @NotNull
    public Image getIcon() {
        final var imagePath = "/image/";
        final var imageFileExtension = ".png";
        return new Image(getClass().getResourceAsStream(imagePath + id + imageFileExtension));
    }

    public void onMousePressedOnCanvas(@NotNull final MouseEvent mouseEvent) {
        mousePressedOnCanvasHandler.handle(mouseEvent);
    }

    void setMousePressedOnCanvasHandler(@NotNull final EventHandler<MouseEvent> handler) {
        this.mousePressedOnCanvasHandler = handler;
    }

    public void onMousePressedOnGraphicComponent(@NotNull final MouseEvent mouseEvent) {
        mousePressedOnGraphicComponentHandler.handle(mouseEvent);
    }

    void setMousePressedOnGraphicComponentHandler(@NotNull final EventHandler<MouseEvent> handler) {
        this.mousePressedOnGraphicComponentHandler = handler;
    }
}
