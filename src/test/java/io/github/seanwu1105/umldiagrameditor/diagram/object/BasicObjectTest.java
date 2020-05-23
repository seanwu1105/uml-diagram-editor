package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicObjectTest {

    @NotNull
    private BasicObject basicObject;

    @BeforeEach
    void buildUp() {
        basicObject = new BasicObject();
    }

    @Test
    void testPosition() {
        final var expectedPosition = new Position(1, 2);
        basicObject.setPosition(expectedPosition);
        assertEquals(expectedPosition, basicObject.getPosition());
    }

    @Test
    void testWidth() {
        final var expectedWidth = 3;
        basicObject.setWidth(expectedWidth);
        assertEquals(expectedWidth, basicObject.getWidth());
    }

    @Test
    void testIllegalWidth() {
        assertThrows(IllegalArgumentException.class, () -> basicObject.setWidth(-2));
    }

    @Test
    void testHeight() {
        final var expectedHeight = 4;
        basicObject.setHeight(expectedHeight);
        assertEquals(expectedHeight, basicObject.getHeight());
    }

    @Test
    void testIllegalHeight() {
        assertThrows(IllegalArgumentException.class, () -> basicObject.setHeight(-5));
    }

    @Test
    void testMove() {
        final var original = 11;
        basicObject.setPosition(new Position(original, original));

        final var offset = 8;
        basicObject.move(offset, offset);

        assertEquals(new Position(original + offset, original + offset), basicObject.getPosition());
    }
}