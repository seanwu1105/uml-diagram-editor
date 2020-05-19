package io.github.seanwu1105.umldiagrameditor.object;

import io.github.seanwu1105.umldiagrameditor.model.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicObjectTest {

    @NotNull
    private BasicObject basicObject;

    @BeforeEach
    void buildUp() {
        basicObject = new BasicObject();
    }

    @Test
    void testPosition() {
        final var expect = new Position(1, 2);
        final var basicObject = new BasicObject();
        basicObject.setPosition(expect);
        assertEquals(expect, basicObject.getPosition());
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