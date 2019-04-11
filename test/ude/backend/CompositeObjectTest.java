package ude.backend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompositeObjectTest {
    @Test
    void add() {
        /*Set<UmlObject> children = new LinkedHashSet<>();
        children.add(new BasicObject());
        children.add(new BasicObject());
        CompositeObject g1 = new CompositeObject(children);

        g1.add(children);

        assertEquals(children, g1.children);*/
    }

    @Test
    void ungroup() {
/*        UmlObject a = new BasicObject();
        UmlObject b = new BasicObject();
        UmlObject c = new BasicObject();
        UmlObject g1 = new CompositeObject(Arrays.asList(a, b));
        CompositeObject g2 = new CompositeObject(Arrays.asList(g1, c));

        g2.ungroup();

        assertNull(g1.parent);
        assertNull(c.parent);
        assertEquals(g1, a.parent);
        assertEquals(g1, b.parent);
        assertTrue(g2.children.isEmpty());*/
    }
}