package io.github.seanwu1105.umldiagrameditor.canvas.mousehandler;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject.ObjectType;
import org.jetbrains.annotations.NotNull;

public class CreateUseCaseObjectHandler extends CreateObjectHandler {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    @NotNull
    @Override
    BasicObject createObject() {
        final var newObject = new BasicObject(ObjectType.USE_CASE);
        newObject.setWidth(WIDTH);
        newObject.setHeight(HEIGHT);
        return newObject;
    }
}
