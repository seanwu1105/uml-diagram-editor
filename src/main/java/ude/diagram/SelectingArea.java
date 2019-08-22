package ude.diagram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class SelectingArea extends Rectangle {
    double initX, initY;

    SelectingArea(double initX, double initY) {
        super(initX, initY, 0, 0);
        setFill(Color.TRANSPARENT);
        setStroke(Color.STEELBLUE);
        this.initX = initX;
        this.initY = initY;
    }

    void setCursorX(double x) {
        double xOffset = x - initX;
        if (xOffset >= 0)
            setWidth(xOffset);
        else {
            setX(x);
            setWidth(-xOffset);
        }
    }

    void setCursorY(double y) {
        double yOffset = y - initY;
        if (yOffset >= 0)
            setHeight(yOffset);
        else {
            setY(y);
            setHeight(-yOffset);
        }
    }
}
