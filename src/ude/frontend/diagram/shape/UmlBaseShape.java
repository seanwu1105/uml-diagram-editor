package ude.frontend.diagram.shape;

import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.backend.BasicObject;
import ude.frontend.diagram.Paintable;
import ude.frontend.diagram.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class UmlBaseShape extends BasicObject implements Paintable, Selectable {
    private final static double portLength = 10;
    private final Map<Side, Rectangle> ports = Map.of(
            Side.TOP, new Rectangle(portLength, portLength),
            Side.RIGHT, new Rectangle(portLength, portLength),
            Side.BOTTOM, new Rectangle(portLength, portLength),
            Side.LEFT, new Rectangle(portLength, portLength)
    );
    public Shape shape;
    private boolean isSelected = false;
    Rectangle holder;
    double width, height;
    private Pane container;
    private double originalX, originalY;    // for the drag event, the original coordinates of the pressed mouse

    UmlBaseShape(double x, double y, double width, double height) {
        super();
        this.width = width;
        this.height = height;
        holder = new Rectangle(x, y, width, height);
        holder.setFill(Color.TRANSPARENT);
    }

    @Override
    public void paint(Pane container) {
        shape.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            select();
            originalX = e.getX();
            originalY = e.getY();
        });
        shape.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            double offsetX = e.getX() - originalX;
            double offsetY = e.getY() - originalY;
            holder.setX(holder.getX() + offsetX);
            holder.setY(holder.getY() + offsetY);
            originalX = e.getX();
            originalY = e.getY();
        });
        this.container = container;
        this.container.getChildren().addAll(holder, shape);
    }

    @Override
    public void select() {
        if (!isSelected) {
            isSelected = true;
            showPorts();

            if (parent instanceof Selectable)
                ((Selectable) parent).select();
        }
    }

    @Override
    public void deselect() {
        if (isSelected) {
            isSelected = false;
            hidePorts();

            if (parent instanceof Selectable) {
                ((Selectable) parent).deselect();
            }
        }
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public boolean isInside(double x1, double y1, double x2, double y2) {
        double[] topLeft = {Math.min(x1, x2), Math.min(y1, y2)};
        double[] bottomRight = {Math.max(x1, x2), Math.max(y1, y2)};
        return (holder.getX() >= topLeft[0]
                && holder.getY() >= topLeft[1]
                && holder.getX() + holder.getWidth() <= bottomRight[0]
                && holder.getY() + holder.getHeight() <= bottomRight[1]);
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
            pair.add(holder.xProperty().add(0.5 * holder.getWidth()));
            pair.add(holder.yProperty());
        } else if (side == Side.RIGHT) {
            pair.add(holder.xProperty().add(holder.getWidth()));
            pair.add(holder.yProperty().add(0.5 * holder.getHeight()));
        } else if (side == Side.BOTTOM) {
            pair.add(holder.xProperty().add(0.5 * holder.getWidth()));
            pair.add(holder.yProperty().add(holder.getHeight()));
        } else {
            pair.add(holder.xProperty());
            pair.add(holder.yProperty().add(0.5 * holder.getHeight()));
        }
        return pair;
    }

    private void showPorts() {
        shape.toFront();

        for (Rectangle port : ports.values()) {
            port.toFront();
        }
        // top port
        ports.get(Side.TOP).xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
        ports.get(Side.TOP).yProperty().bind(holder.yProperty().subtract(portLength));
        // right port
        ports.get(Side.RIGHT).xProperty().bind(holder.xProperty().add(width));
        ports.get(Side.RIGHT).yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));
        // bottom port
        ports.get(Side.BOTTOM).xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
        ports.get(Side.BOTTOM).yProperty().bind(holder.yProperty().add(height));
        // left port
        ports.get(Side.LEFT).xProperty().bind(holder.xProperty().subtract(portLength));
        ports.get(Side.LEFT).yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));

        container.getChildren().addAll(ports.values());
    }

    private void hidePorts() {
        container.getChildren().removeAll(ports.values());
    }

    enum Side {TOP, RIGHT, BOTTOM, LEFT}
}
