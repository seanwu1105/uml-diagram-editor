package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.graph.ClassObject;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.UseCaseObject;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.diagram.Diagram;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject.ObjectType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class Canvas extends Pane {

    @NotNull
    private static final Map<ObjectType, ShapeFactory> OBJECT_TYPE_TO_GRAPHIC_COMPONENT_MAP = Map.of(
            ObjectType.CLASS, ClassObject::new,
            ObjectType.USE_CASE, UseCaseObject::new
    );

    @NotNull
    private final Diagram diagram;
    @NotNull
    private Mode mode = ModeFactory.getSelectMode();
    @Nullable
    private SelectingArea selectingArea;

    private Canvas(@NotNull final Diagram diagram) {
        this.diagram = diagram;
        initDiagramListeners();
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressedOnCanvas(event));
        addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> mode.onMouseDraggedOnCanvas(event));
        addEventFilter(MouseEvent.MOUSE_RELEASED, event -> mode.onMouseReleasedOnCanvas(event));
    }

    @NotNull
    public static Canvas getNewInstance() {
        return new Canvas(new Diagram());
    }

    private void initDiagramListeners() {
        diagram.addOnAddedListener(umlObject -> {
            final var graphic = mapToGraphicComponent((BasicObject) umlObject);
            graphic.addShapeEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressedOnGraphicComponent(event));
            getChildren().add(graphic);
        });
    }

    private GraphicComponent<? extends Shape> mapToGraphicComponent(@NotNull final BasicObject basicObject) {
        return OBJECT_TYPE_TO_GRAPHIC_COMPONENT_MAP.get(basicObject.getObjectType()).create(basicObject);
    }

    public void setMode(@NotNull final Mode mode) {
        this.mode = mode;
    }

    public void addObject(@NotNull final BasicObject basicObject) {
        diagram.addObject(basicObject);
    }

    public void createSelectingArea(final double x, final double y) {
        selectingArea = new SelectingArea(x, y);
        getChildren().add(selectingArea);
    }

    public void resizeSelectingArea(final double x, final double y) {
        if (selectingArea != null) selectingArea.setResizingCursorPosition(x, y);
    }

    public void removeSelectingArea() {
        getChildren().remove(selectingArea);
        selectingArea = null;
    }

    public void deselectAll() {
        getChildrenUnmodifiable().forEach(child -> {
            if (child instanceof GraphicComponent) {
                final var graphicComponent = (GraphicComponent<? extends Shape>) child;
                graphicComponent.deselect();
            }
        });
    }

    private interface ShapeFactory {
        GraphicComponent<? extends Shape> create(@NotNull final BasicObject basicObject);
    }

    private static class SelectingArea extends Rectangle {
        double initialX;
        double initialY;

        SelectingArea(final double initialX, final double initialY) {
            super(initialX, initialY, 0, 0);
            setFill(Color.TRANSPARENT);
            setStroke(Color.STEELBLUE);
            this.initialX = initialX;
            this.initialY = initialY;
        }

        void setResizingCursorPosition(final double x, final double y) {
            final var xOffset = x - initialX;
            final var yOffset = y - initialY;
            if (xOffset >= 0) setWidth(xOffset);
            else {
                setX(x);
                setWidth(-xOffset);
            }
            if (yOffset >= 0) setHeight(yOffset);
            else {
                setY(y);
                setHeight(-yOffset);
            }
        }
    }
}
