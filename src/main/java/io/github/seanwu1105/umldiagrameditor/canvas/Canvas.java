package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.graph.ClassObject;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.GraphicComponent;
import io.github.seanwu1105.umldiagrameditor.canvas.graph.UseCaseObject;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.diagram.Diagram;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class Canvas extends Pane {

    @NotNull
    private static final Map<BasicObject.ObjectType, ShapeFactory> OBJECT_TYPE_TO_GRAPHIC_COMPONENT_MAP = Map.of(
            BasicObject.ObjectType.CLASS, ClassObject::new,
            BasicObject.ObjectType.USE_CASE, UseCaseObject::new
    );

    @NotNull
    private final Diagram diagram;
    @NotNull
    private Mode mode = ModeFactory.getSelectMode();

    private Canvas(@NotNull final Diagram diagram) {
        this.diagram = diagram;
        initDiagramListeners();
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressedOnCanvas(event));
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

    public void deselectAll() {
        getChildrenUnmodifiable().forEach(child -> {
            final var graphicComponent = (GraphicComponent<? extends Shape>) child;
            graphicComponent.deselect();
        });
    }

    private interface ShapeFactory {
        GraphicComponent<? extends Shape> create(@NotNull final BasicObject basicObject);
    }
}
