package io.github.seanwu1105.umldiagrameditor.diagram;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PositionTest {

    @Test
    void testEquals() {
        assertAll(
                () -> assertEquals(new Position(4, 5), new Position(4, 5)),
                () -> assertNotEquals(new Position(1, 2), new Position(3, 4))
        );
    }

    @Test
    void testHashCode() {
        assertEquals(new Position(8, 9).hashCode(), new Position(8, 9).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("(2.0, 3.0)", new Position(2, 3).toString());
    }
}