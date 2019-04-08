package ude.frontend.diagram;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.backend.BasicObject;


public abstract class BaseShape extends BasicObject implements Paintable {
    private final static double portLength = 10;
    Shape shape;
    Rectangle holder;
    double width, height;

    private Pane container;
    private boolean isSelected = false;
    private Rectangle[] ports;
    private double originalX, originalY;    // for the drag event, the original coordinates of the pressed mouse

    BaseShape(double x, double y, double width, double height) {
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

    private void select() {
        if (!isSelected) {
            isSelected = true;
            shape.toFront();

            ports = new Rectangle[4]; // top, right, bottom, left
            for (int i = 0; i < ports.length; i++) {
                ports[i] = new Rectangle(portLength, portLength);
                ports[i].toFront();
            }
            // top port
            ports[0].xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
            ports[0].yProperty().bind(holder.yProperty().subtract(portLength));
            // right port
            ports[1].xProperty().bind(holder.xProperty().add(width));
            ports[1].yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));
            // bottom port
            ports[2].xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
            ports[2].yProperty().bind(holder.yProperty().add(height));
            // left port
            ports[3].xProperty().bind(holder.xProperty().subtract(portLength));
            ports[3].yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));

            container.getChildren().addAll(ports);
        }
    }

    public void deselect() {
        if (isSelected) {
            isSelected = false;

            container.getChildren().removeAll(ports);
        }
    }
}
