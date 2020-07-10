package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject.ObjectType;

public class CreateClassObjectHandler extends CreateObjectHandler {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 80;

    @Override
    BasicObject createObject() {
        final var newObject = new BasicObject(ObjectType.CLASS);
        newObject.setWidth(WIDTH);
        newObject.setHeight(HEIGHT);
        return newObject;
    }
}
