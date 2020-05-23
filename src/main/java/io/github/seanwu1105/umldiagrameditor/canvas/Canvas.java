package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.canvas.node.ClassObject;
import io.github.seanwu1105.umldiagrameditor.canvas.node.UseCaseObject;
import io.github.seanwu1105.umldiagrameditor.diagram.Diagram;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class Canvas extends Pane {

    @NotNull
    private static final Map<BasicObject.ObjectType, NodeFactory> OBJECT_TYPE_TO_FX_NODE_MAP = Map.of(
            BasicObject.ObjectType.CLASS, ClassObject::new,
            BasicObject.ObjectType.USE_CASE, UseCaseObject::new
    );

    @NotNull
    private final Diagram diagram;
    @NotNull
    private Mode mode = ModeFactory.createSelectMode();

    private Canvas(@NotNull final Diagram diagram) {
        this.diagram = diagram;
        initDiagramListeners();
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> mode.onMousePressed(event));
    }

    @NotNull
    public static Canvas getNewInstance() {
        return new Canvas(new Diagram());
    }

    private void initDiagramListeners() {
        diagram.addOnAddedListener(umlObject -> getChildren().add(mapToFxNode((BasicObject) umlObject)));
    }

    private Node mapToFxNode(@NotNull final BasicObject basicObject) {
        return OBJECT_TYPE_TO_FX_NODE_MAP.get(basicObject.getObjectType()).create(basicObject);
    }

    public void setMode(@NotNull final Mode mode) {
        this.mode = mode;
    }

    public void addObject(@NotNull final BasicObject basicObject) {
        diagram.addObject(basicObject);
    }

    private interface NodeFactory {
        Node create(@NotNull final BasicObject basicObject);
    }
}
