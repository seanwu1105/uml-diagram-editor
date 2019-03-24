package ude.backend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompositeObjectTest {

    @Test
    void add() {
        BasicObject b1 = new BasicObject();
        BasicObject b2 = new BasicObject();
        BasicObject b3 = new BasicObject();
        CompositeObject g1 = new CompositeObject(b3);
        g1.add(b2);
        CompositeObject g2 = new CompositeObject(b1);
        g2.add(g1);

        Set<UmlObject> expect = new HashSet<>();
        expect.addAll(Arrays.asList(b1, g1));

        assertEquals(expect, g2.getChildren());
    }

    @Test
    void add1() {
        Set<UmlObject> children = new LinkedHashSet<>();
        children.add(new BasicObject());
        children.add(new BasicObject());
        CompositeObject g1 = new CompositeObject(children);

        g1.add(children);

        assertEquals(children, g1.getChildren());
    }

    @Test
    void ungroup() {
        UmlObject a = new BasicObject();
        UmlObject b = new BasicObject();
        UmlObject c = new BasicObject();
        UmlObject g1 = new CompositeObject(Arrays.asList(a, b));
        UmlObject g2 = new CompositeObject(Arrays.asList(g1, c));

        ((CompositeObject) g2).ungroup();

        assertNull(g1.parent);
        assertNull(c.parent);
        assertEquals(g1, a.parent);
        assertEquals(g1, b.parent);
        assertTrue(((CompositeObject) g2).getChildren().isEmpty());
    }
}