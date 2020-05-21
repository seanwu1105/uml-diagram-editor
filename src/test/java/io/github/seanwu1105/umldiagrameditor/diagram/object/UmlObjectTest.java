package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.model.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UmlObjectTest {

    @NotNull
    private List<BasicObject> allBasicObjects;
    @NotNull
    private CompositeObject parent;
    @NotNull
    private Iterable<UmlObject> objects;

    @BeforeEach
    void buildUp() {
        allBasicObjects = List.of(new BasicObject(), new BasicObject(), new BasicObject());
        final Iterable<UmlObject> nestedObjects = Set.of(allBasicObjects.get(0), allBasicObjects.get(1));
        final var nested = new CompositeObject();
        nested.group(nestedObjects);
        objects = Set.of(allBasicObjects.get(2), nested);
        parent = new CompositeObject();
        parent.group(objects);
    }

    @Test
    void testGroup() {
        assertAll(
                () -> assertEquals(objects, parent.getChildren()),
                () -> objects.forEach(child -> assertEquals(parent, child.getParent()))
        );
    }

    @Test
    void testUngroup() {
        final var individuals = parent.ungroup();

        assertAll(
                () -> assertEquals(individuals, objects),
                () -> individuals.forEach(umlObject -> assertNull(umlObject.getParent())),
                () -> assertEquals(Set.of(), parent.getChildren())
        );
    }

    @Test
    void testMove() {
        final var original = 11;
        allBasicObjects.forEach(basicObject -> basicObject.setPosition(new Position(original, original)));

        final var offset = 8;
        parent.move(offset, offset);

        final var moved = new Position(original + offset, original + offset);
        allBasicObjects.forEach(basicObject -> assertEquals(moved, basicObject.getPosition()));
    }

    @Test
    void testGetTopObject() {
        assertAll(
                () -> assertEquals(parent, parent.getTopObject()),
                () -> allBasicObjects.forEach(basicObject -> assertEquals(parent, basicObject.getTopObject()))
        );
    }
}