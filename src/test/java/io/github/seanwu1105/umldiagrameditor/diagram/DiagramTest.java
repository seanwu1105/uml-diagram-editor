package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject;
import io.github.seanwu1105.umldiagrameditor.diagram.object.BasicObject.ObjectType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DiagramTest {

    @NotNull
    private Diagram diagram;

    @BeforeEach
    void buildUp() {
        diagram = new Diagram();
    }

    @Test
    void testAddObject() {
        final var listener = mock(DiagramEventListener.class);
        final var newObject = new BasicObject(ObjectType.CLASS);
        diagram.addOnAddedListener(listener);
        diagram.addObject(newObject);
        verify(listener).updated(newObject);
    }
}