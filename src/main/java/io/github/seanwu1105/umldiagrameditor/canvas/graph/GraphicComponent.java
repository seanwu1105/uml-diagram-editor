package io.github.seanwu1105.umldiagrameditor.canvas.graph;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import io.github.seanwu1105.umldiagrameditor.diagram.object.UmlObject;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class GraphicComponent<T extends Shape> extends Group {

    private static final double PORT_SIZE = 10;
    @NotNull
    private final Rectangle box = new Rectangle();
    @NotNull
    private final T shape;
    @NotNull
    private final Map<Side, Rectangle> ports = Map.of(
            Side.TOP, new Rectangle(PORT_SIZE, PORT_SIZE),
            Side.RIGHT, new Rectangle(PORT_SIZE, PORT_SIZE),
            Side.LEFT, new Rectangle(PORT_SIZE, PORT_SIZE),
            Side.BOTTOM, new Rectangle(PORT_SIZE, PORT_SIZE)
    );

    GraphicComponent(@NotNull final UmlObject umlObject) {
        initBox(umlObject);

        shape = initShape(umlObject);
        bindBox(shape);
        getChildren().addAll(box, shape);
    }

    private void initBox(@NotNull final UmlObject umlObject) {
        getBox().setX(umlObject.getPosition().getX());
        getBox().setY(umlObject.getPosition().getY());
        getBox().setWidth(umlObject.getWidth());
        getBox().setHeight(umlObject.getHeight());
        getBox().setFill(Color.TRANSPARENT);
    }

    @NotNull
    abstract T initShape(@NotNull final UmlObject umlObject);

    abstract void bindBox(@NotNull final T shape);

    @NotNull Rectangle getBox() {
        return box;
    }

    public <E extends Event> void addShapeEventFilter(
            @NotNull final EventType<E> eventType,
            @NotNull final EventHandler<? super E> eventFilter) {
        shape.addEventFilter(eventType, eventFilter);
    }

    public void select() {
        showPorts();
    }

    private void showPorts() {
        ports.values().forEach(Node::toFront);
        // top port
        ports.get(Side.TOP).xProperty().bind(getBox().xProperty().add(0.5 * (getBox().getWidth() - PORT_SIZE)));
        ports.get(Side.TOP).yProperty().bind(getBox().yProperty().subtract(PORT_SIZE));
        // right port
        ports.get(Side.RIGHT).xProperty().bind(getBox().xProperty().add(getBox().getWidth()));
        ports.get(Side.RIGHT).yProperty().bind(getBox().yProperty().add(0.5 * (getBox().getHeight() - PORT_SIZE)));
        // bottom port
        ports.get(Side.BOTTOM).xProperty().bind(getBox().xProperty().add(0.5 * (getBox().getWidth() - PORT_SIZE)));
        ports.get(Side.BOTTOM).yProperty().bind(getBox().yProperty().add(getBox().getHeight()));
        // left port
        ports.get(Side.LEFT).xProperty().bind(getBox().xProperty().subtract(PORT_SIZE));
        ports.get(Side.LEFT).yProperty().bind(getBox().yProperty().add(0.5 * (getBox().getHeight() - PORT_SIZE)));

        getChildren().addAll(ports.values());
    }

    public void deselect() {
        hidePorts();
    }

    private void hidePorts() {
        getChildren().removeAll(ports.values());
    }

    public boolean isInside(@NotNull final Position position1, final @NotNull Position position2) {
        final var topLeft = new Position(Math.min(position1.getX(), position2.getX()), Math.min(position1.getY(), position2.getY()));
        final var bottomRight = new Position(Math.max(position1.getX(), position2.getX()), Math.max(position1.getY(), position2.getY()));
        return (getBox().getX() >= topLeft.getX()
                && getBox().getY() >= topLeft.getY()
                && getBox().getX() + getBox().getWidth() <= bottomRight.getX()
                && getBox().getY() + getBox().getHeight() <= bottomRight.getY());
    }
}
