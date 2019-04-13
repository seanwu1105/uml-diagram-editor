package ude.diagram.object;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class UmlBasicObject extends Rectangle implements UmlBaseObject {
    private final static double portLength = 10;
    private final Map<Side, Rectangle> ports = Map.of(
            Side.TOP, new Rectangle(portLength, portLength),
            Side.RIGHT, new Rectangle(portLength, portLength),
            Side.BOTTOM, new Rectangle(portLength, portLength),
            Side.LEFT, new Rectangle(portLength, portLength)
    );
    private final BooleanProperty isSelected = new SimpleBooleanProperty(false);
    public Shape shape;
    private UmlCompositeObject group = null;
    private double originalX, originalY;    // for the drag event, the original coordinates of the pressed mouse

    UmlBasicObject(Shape shape, double x, double y, double width, double height) {
        super(x, y, width, height);
        setFill(Color.TRANSPARENT);

        this.shape = shape;
        shape.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            select();
            originalX = e.getX();
            originalY = e.getY();
        });
        shape.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            double offsetX = e.getX() - originalX;
            double offsetY = e.getY() - originalY;
            setX(getX() + offsetX);
            setY(getY() + offsetY);
            originalX = e.getX();
            originalY = e.getY();
        });
        bindShapeToHolder();

        parentProperty().addListener((observableValue, oldParent, newParent) -> {
            if (newParent != null)
                ((Pane) getParent()).getChildren().addAll(shape);
            else
                ((Pane) oldParent).getChildren().removeAll(shape);
        });
    }


    @Override
    public void select() {
        if (!isSelected.get()) {
            isSelected.set(true);
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
    public UmlCompositeObject getGroup() {
        return group;
    }

    @Override
    public void setGroup(UmlCompositeObject group) {
        this.group = group;
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

    private void showPorts() {
        shape.toFront();

        for (Rectangle port : ports.values()) {
            port.toFront();
        }
        // top port
        ports.get(Side.TOP).xProperty().bind(xProperty().add(0.5 * (getWidth() - portLength)));
        ports.get(Side.TOP).yProperty().bind(yProperty().subtract(portLength));
        // right port
        ports.get(Side.RIGHT).xProperty().bind(xProperty().add(getWidth()));
        ports.get(Side.RIGHT).yProperty().bind(yProperty().add(0.5 * (getHeight() - portLength)));
        // bottom port
        ports.get(Side.BOTTOM).xProperty().bind(xProperty().add(0.5 * (getWidth() - portLength)));
        ports.get(Side.BOTTOM).yProperty().bind(yProperty().add(getHeight()));
        // left port
        ports.get(Side.LEFT).xProperty().bind(xProperty().subtract(portLength));
        ports.get(Side.LEFT).yProperty().bind(yProperty().add(0.5 * (getHeight() - portLength)));

        ((Pane) getParent()).getChildren().addAll(ports.values());
    }

    private void hidePorts() {
        ((Pane) getParent()).getChildren().removeAll(ports.values());
    }

    abstract void bindShapeToHolder();

    enum Side {TOP, RIGHT, BOTTOM, LEFT}
}
