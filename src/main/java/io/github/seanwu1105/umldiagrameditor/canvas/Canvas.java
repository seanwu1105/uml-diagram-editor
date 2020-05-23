package io.github.seanwu1105.umldiagrameditor.canvas;

import io.github.seanwu1105.umldiagrameditor.canvas.mode.Mode;
import io.github.seanwu1105.umldiagrameditor.canvas.mode.ModeFactory;
import io.github.seanwu1105.umldiagrameditor.canvas.node.ClassObject;
import io.github.seanwu1105.umldiagrameditor.diagram.Diagram;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public final class Canvas extends Pane {

    @NotNull
    private Diagram diagram;
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
        diagram.addOnAddedListener(umlObject -> getChildren().add(new ClassObject(
                umlObject.getPosition(),
                umlObject.getWidth(),
                umlObject.getHeight()
        )));
    }

    public void setMode(@NotNull final Mode mode) {
        this.mode = mode;
    }

    public void addObject(@NotNull final BasicObject basicObject) {
        diagram.addObject(basicObject);
    }
}
