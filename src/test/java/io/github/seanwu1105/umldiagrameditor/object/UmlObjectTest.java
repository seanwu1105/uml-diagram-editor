package io.github.seanwu1105.umldiagrameditor.object;

import io.github.seanwu1105.umldiagrameditor.model.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UmlObjectTest {

    @NotNull
    private Iterable<UmlObject> objects;
    @NotNull
    private Iterable<UmlObject> nestedObjects;

    @BeforeEach
    void buildUp() {
        nestedObjects = Set.of(new BasicObject(), new BasicObject());
        final var nested = new CompositeObject();
        nested.group(nestedObjects);
        objects = Set.of(new BasicObject(), nested);
    }

    @Test
    void testGroup() {
        final var parent = new CompositeObject();

        parent.group(objects);

        assertAll(
                () -> assertEquals(objects, parent.getChildren()),
                () -> objects.forEach(child -> assertEquals(parent, child.getParent()))
        );
    }

    @Test
    void testUngroup() {
        final var parent = new CompositeObject();
        parent.group(objects);

        final var individuals = parent.ungroup();

        assertAll(
                () -> assertEquals(individuals, objects),
                () -> individuals.forEach(umlObject -> assertNull(umlObject.getParent())),
                () -> assertEquals(Set.of(), parent.getChildren())
        );
    }

    @Test
    void testSetGroupPosition() {
        final var compositeObject = new CompositeObject();
        compositeObject.group(objects);

        final var expect = new Position(122, 122);
        compositeObject.setPosition(expect);

        assertAll(
                () -> objects.forEach(umlObject -> {
                    if (umlObject instanceof BasicObject) assertEquals(expect, ((BasicObject) umlObject).getPosition());
                }),

                () -> nestedObjects.forEach(umlObject -> {
                    if (umlObject instanceof BasicObject) assertEquals(expect, ((BasicObject) umlObject).getPosition());
                })
        );
    }

    @Test
    void testMoveGroupFromParent() {
        final var original = 11;
        objects.forEach(umlObject -> umlObject.setPosition(new Position(original, original)));
        final var compositeObject = new CompositeObject();
        compositeObject.group(objects);

        final var offset = 8;
        compositeObject.move(offset, offset);

        final var moved = new Position(original + offset, original + offset);
        assertAll(
                () -> objects.forEach(umlObject -> {
                    if (umlObject instanceof BasicObject) assertEquals(moved, ((BasicObject) umlObject).getPosition());
                }),

                () -> nestedObjects.forEach(umlObject -> {
                    if (umlObject instanceof BasicObject) assertEquals(moved, ((BasicObject) umlObject).getPosition());
                })
        );
    }

    @Test
    void testMoveGroupFromChild() {}
}