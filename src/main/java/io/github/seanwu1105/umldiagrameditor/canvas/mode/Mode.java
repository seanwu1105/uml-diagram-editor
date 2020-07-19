package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Mode {

    @NotNull
    private final ModeId id;
    @NotNull
    private final Collection<EventHandler<MouseEvent>> mousePressedOnCanvasHandlers = new HashSet<>();
    @NotNull
    private final Collection<EventHandler<MouseEvent>> mouseDraggedOnCanvasHandlers = new HashSet<>();
    @NotNull
    private final Collection<EventHandler<MouseEvent>> mouseReleasedOnCanvasHandlers = new HashSet<>();
    @NotNull
    private final Collection<EventHandler<MouseEvent>> mousePressedOnGraphicComponentHandlers = new HashSet<>();
    @NotNull
    private final Collection<EventHandler<MouseEvent>> mouseDraggedOnGraphicComponentHandlers = new HashSet<>();

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
        mousePressedOnCanvasHandlers.forEach(handler -> handler.handle(mouseEvent));
    }

    public void onMouseDraggedOnCanvas(@NotNull final MouseEvent mouseEvent) {
        mouseDraggedOnCanvasHandlers.forEach(handler -> handler.handle(mouseEvent));
    }

    public void onMouseReleasedOnCanvas(@NotNull final MouseEvent mouseEvent) {
        mouseReleasedOnCanvasHandlers.forEach(handler -> handler.handle(mouseEvent));
    }

    @SafeVarargs
    final void setMousePressedOnCanvasHandlers(@NotNull final EventHandler<MouseEvent>... handlers) {
        mousePressedOnCanvasHandlers.clear();
        mousePressedOnCanvasHandlers.addAll(Arrays.asList(handlers));
    }

    @SafeVarargs
    final void setMouseDraggedOnCanvasHandlers(@NotNull final EventHandler<MouseEvent>... handlers) {
        mouseDraggedOnCanvasHandlers.clear();
        mouseDraggedOnCanvasHandlers.addAll(Arrays.asList(handlers));
    }

    @SafeVarargs
    final void setMouseReleasedOnCanvasHandlers(@NotNull final EventHandler<MouseEvent>... handlers) {
        mouseReleasedOnCanvasHandlers.clear();
        mouseReleasedOnCanvasHandlers.addAll(Arrays.asList(handlers));
    }

    public void onMousePressedOnGraphicComponent(@NotNull final MouseEvent mouseEvent) {
        mousePressedOnGraphicComponentHandlers.forEach(handler -> handler.handle(mouseEvent));
    }

    public void onMouseDraggedOnGraphicComponent(@NotNull final MouseEvent mouseEvent) {
        mouseDraggedOnGraphicComponentHandlers.forEach(handler -> handler.handle(mouseEvent));
    }

    @SafeVarargs
    final void setMousePressedOnGraphicComponentHandlers(@NotNull final EventHandler<MouseEvent>... handlers) {
        mousePressedOnGraphicComponentHandlers.clear();
        mousePressedOnGraphicComponentHandlers.addAll(Arrays.asList(handlers));
    }

    @SafeVarargs
    final void setMouseDraggedOnGraphicComponentHandlers(@NotNull final EventHandler<MouseEvent>... handlers) {
        mouseDraggedOnGraphicComponentHandlers.clear();
        mouseDraggedOnGraphicComponentHandlers.addAll(Arrays.asList(handlers));
    }
}
