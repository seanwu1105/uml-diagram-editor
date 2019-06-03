package ude.diagram.object;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;


public abstract class UmlBasicObject<T extends Shape> extends Rectangle implements UmlBaseObject {
    private final static double PORT_LENGTH = 10;
    private final Map<Side, Rectangle> ports = Map.of(
            Side.TOP, new Rectangle(PORT_LENGTH, PORT_LENGTH),
            Side.RIGHT, new Rectangle(PORT_LENGTH, PORT_LENGTH),
            Side.BOTTOM, new Rectangle(PORT_LENGTH, PORT_LENGTH),
            Side.LEFT, new Rectangle(PORT_LENGTH, PORT_LENGTH)
    );
    private final BooleanProperty isSelected = new SimpleBooleanProperty(false);
    Set<Shape> decorations = new HashSet<>();
    private T shape;
    public Text name = new Text();
    private UmlCompositeObject group = null;
    // for the drag event, the original coordinates of when pressed the mouse
    private double draggingOriginalX, draggingOriginalY;

    UmlBasicObject(T shape, double x, double y, double width, double height) {
        super(x, y, width, height);
        setFill(Color.TRANSPARENT);

        this.shape = shape;
        initShapeEventListeners();

        bindShapeToHolder();

        initName();

        parentProperty().addListener((observableValue, oldParent, newParent) -> {
            if (newParent != null) {
                ((Pane) getParent()).getChildren().add(shape);
                ((Pane) getParent()).getChildren().addAll(decorations);
            }
            else {
                ((Pane) getParent()).getChildren().remove(shape);
                ((Pane) oldParent).getChildren().removeAll(decorations);
            }
        });
    }

    @Override
    public void select() {
        if (!isSelected.get()) {
            isSelected.set(true);
            shape.toFront();
            decorations.forEach(Node::toFront);
            showPorts();
            if (group != null)
                group.select();
        }
    }

    @Override
    public void deselect() {
        if (isSelected.get()) {
            isSelected.set(false);
            hidePorts();
            if (group != null)
                group.deselect();
        }
    }

    @Override
    public BooleanProperty selectedProperty() {
        return isSelected;
    }

    @Override
    public void setDraggingOriginal(double draggingOriginalX, double draggingOriginalY, UmlBaseObject caller) {
        if (group != null && group != caller)
            group.setDraggingOriginal(draggingOriginalX, draggingOriginalY, this);
        else {
            this.draggingOriginalX = draggingOriginalX;
            this.draggingOriginalY = draggingOriginalY;
        }
    }


    @Override
    public UmlCompositeObject getGroup() {
        return group;
    }

    @Override
    public void setGroup(UmlCompositeObject group) {
        this.group = group;
    }

    @Override
    public void move(double offsetX, double offsetY, UmlBaseObject caller) {
        if (group != null && group != caller)
            group.move(offsetX, offsetY, this);
        else {
            setX(getX() + offsetX);
            setY(getY() + offsetY);
        }
    }

    public T getShape() {
        return shape;
    }

    public boolean isInside(double x1, double y1, double x2, double y2) {
        double[] topLeft = {Math.min(x1, x2), Math.min(y1, y2)};
        double[] bottomRight = {Math.max(x1, x2), Math.max(y1, y2)};
        return (getX() >= topLeft[0]
                && getY() >= topLeft[1]
                && getX() + getWidth() <= bottomRight[0]
                && getY() + getHeight() <= bottomRight[1]);
    }

    public List<ObservableValue<Number>> getClosestPortPositionProperty(double x, double y) {
        double shortest = Double.POSITIVE_INFINITY;
        List<ObservableValue<Number>> ret = null;
        for (Side side : Side.values()) {
            double xDiff = x - (double) getPortPositionProperty(side).get(0).getValue();
            double yDiff = y - (double) getPortPositionProperty(side).get(1).getValue();
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            if (distance < shortest) {
                shortest = distance;
                ret = getPortPositionProperty(side);
            }
        }
        if (ret == null)
            throw new NullPointerException("All ports' positions are POSITIVE_INFINITY.");
        return ret;
    }

    private List<ObservableValue<Number>> getPortPositionProperty(Side side) {
        List<ObservableValue<Number>> pair = new ArrayList<>();
        if (side == Side.TOP) {
            pair.add(xProperty().add(0.5 * getWidth()));
            pair.add(yProperty());
        } else if (side == Side.RIGHT) {
            pair.add(xProperty().add(getWidth()));
            pair.add(yProperty().add(0.5 * getHeight()));
        } else if (side == Side.BOTTOM) {
            pair.add(xProperty().add(0.5 * getWidth()));
            pair.add(yProperty().add(getHeight()));
        } else {
            pair.add(xProperty());
            pair.add(yProperty().add(0.5 * getHeight()));
        }
        return pair;
    }

    private void initShapeEventListeners() {
        shape.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            select();
            setDraggingOriginal(e.getX(), e.getY(), this);
        });
        shape.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            double offsetX = e.getX() - draggingOriginalX;
            double offsetY = e.getY() - draggingOriginalY;
            move(offsetX, offsetY, this);
            setDraggingOriginal(e.getX(), e.getY(), this);
        });
    }

    private void initName() {
        name.setTextAlignment(TextAlignment.CENTER);
        name.xProperty().bind(xProperty());
        name.yProperty().bind(yProperty().subtract(15));
        decorations.add(name);
    }

    private void showPorts() {
        ports.values().forEach(Node::toFront);
        // top port
        ports.get(Side.TOP).xProperty().bind(xProperty().add(0.5 * (getWidth() - PORT_LENGTH)));
        ports.get(Side.TOP).yProperty().bind(yProperty().subtract(PORT_LENGTH));
        // right port
        ports.get(Side.RIGHT).xProperty().bind(xProperty().add(getWidth()));
        ports.get(Side.RIGHT).yProperty().bind(yProperty().add(0.5 * (getHeight() - PORT_LENGTH)));
        // bottom port
        ports.get(Side.BOTTOM).xProperty().bind(xProperty().add(0.5 * (getWidth() - PORT_LENGTH)));
        ports.get(Side.BOTTOM).yProperty().bind(yProperty().add(getHeight()));
        // left port
        ports.get(Side.LEFT).xProperty().bind(xProperty().subtract(PORT_LENGTH));
        ports.get(Side.LEFT).yProperty().bind(yProperty().add(0.5 * (getHeight() - PORT_LENGTH)));

        ((Pane) getParent()).getChildren().addAll(ports.values());
    }

    private void hidePorts() {
        ((Pane) getParent()).getChildren().removeAll(ports.values());
    }

    abstract void bindShapeToHolder();

    enum Side {TOP, RIGHT, BOTTOM, LEFT}
}
