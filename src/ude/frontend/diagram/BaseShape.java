package ude.frontend.diagram;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ude.backend.BasicObject;

public abstract class BaseShape extends BasicObject {
    private final static double portLength = 10;
    Shape shape;
    Rectangle holder;
    double width, height;

    private Pane container;
    private boolean isSelected = false;

    BaseShape(double x, double y, double width, double height) {
        super();
        this.width = width;
        this.height = height;
        holder = new Rectangle(x, y, width, height);
        holder.setFill(Color.TRANSPARENT);
        holder.setStroke(Color.GREEN);
    }

    public void paint(Pane container) {
        shape.setOnMousePressed(e -> {
            select();
        });
        this.container = container;
        this.container.getChildren().addAll(holder, shape);
    }

    void select() {
        if (!isSelected) {
            isSelected = true;
            shape.toFront();

            Rectangle[] ports = new Rectangle[4]; // top, right, bottom, left
            for (int i = 0; i < ports.length; i++) {
                ports[i] = new Rectangle(portLength, portLength);
                ports[i].toFront();
            }

            ports[0].xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
            ports[0].yProperty().bind(holder.yProperty().subtract(portLength));

            ports[1].xProperty().bind(holder.xProperty().add(width));
            ports[1].yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));

            ports[2].xProperty().bind(holder.xProperty().add(0.5 * (width - portLength)));
            ports[2].yProperty().bind(holder.yProperty().add(height));

            ports[3].xProperty().bind(holder.xProperty().subtract(portLength));
            ports[3].yProperty().bind(holder.yProperty().add(0.5 * (height - portLength)));

            container.getChildren().addAll(ports);
        }
    }

    void deselect() {

    }
}
