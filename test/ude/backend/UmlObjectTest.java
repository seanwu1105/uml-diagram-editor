package ude.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UmlObjectTest {
    @Test
    void shouldPrintFuck() {
        UmlObject s = new UmlObject();
        assertEquals("FUCK", s.print());
    }
}