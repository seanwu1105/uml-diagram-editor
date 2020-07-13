package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.graph.ClassObject;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.UseCaseObject;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.diagram.Diagram;
import io.github.seanwu1105.umldiagrameditor.diagram.Position;
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
        initDiagramClipper();
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressedOnCanvas(event));
        addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> mode.onMouseDraggedOnCanvas(event));
        addEventFilter(MouseEvent.MOUSE_RELEASED, event -> mode.onMouseReleasedOnCanvas(event));
    }

    @NotNull
    public static Canvas getNewInstance() {
        return new Canvas(new Diagram());
    }

    private void initDiagramClipper() {
        final var clipper = new Rectangle();
        clipper.heightProperty().bind(heightProperty());
        clipper.widthProperty().bind(widthProperty());
        setClip(clipper);
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

    public void createSelectingArea(@NotNull final Position position) {
        selectingArea = new SelectingArea(position);
        getChildren().add(selectingArea);
    }

    public void resizeSelectingArea(@NotNull final Position position) {
        if (selectingArea != null) selectingArea.setResizingCursorPosition(position);
    }

    public void removeSelectingArea() {
        getChildren().remove(selectingArea);
        selectingArea = null;
    }

    public void selectInArea(@NotNull final Position position) {
        if (selectingArea != null) {
            getChildrenUnmodifiable().forEach(child -> {
                if (child instanceof GraphicComponent) {
                    final var graphicComponent = (GraphicComponent<? extends Shape>) child;
                    if (graphicComponent.isInside(selectingArea.initialPosition, position)) graphicComponent.select();
                }
            });
        }
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
        @NotNull Position initialPosition;

        SelectingArea(@NotNull final Position position) {
            super(position.getX(), position.getY(), 0, 0);
            setFill(Color.TRANSPARENT);
            setStroke(Color.STEELBLUE);
            initialPosition = position;
        }

        void setResizingCursorPosition(@NotNull final Position position) {
            final var offset = position.subtract(initialPosition);
            if (offset.getX() >= 0) setWidth(offset.getX());
            else {
                setX(position.getX());
                setWidth(-offset.getX());
            }
            if (offset.getY() >= 0) setHeight(offset.getY());
            else {
                setY(position.getY());
                setHeight(-offset.getY());
            }
        }
    }
}
