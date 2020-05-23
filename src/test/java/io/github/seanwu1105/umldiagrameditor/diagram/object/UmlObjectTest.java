package io.github.seanwu1105.umldiagrameditor.diagram.object;

import io.github.seanwu1105.umldiagrameditor.diagram.Position;
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
    void testGroupPositionAndWidthAndHeight() {
        allBasicObjects.get(0).setPosition(new Position(0, 0));
        allBasicObjects.get(0).setWidth(2);
        allBasicObjects.get(0).setHeight(5);
        allBasicObjects.get(1).setPosition(new Position(-5, 8));
        allBasicObjects.get(1).setWidth(10);
        allBasicObjects.get(1).setHeight(7);
        allBasicObjects.get(2).setPosition(new Position(1, -3));
        allBasicObjects.get(2).setWidth(6);
        allBasicObjects.get(2).setHeight(2);

        final var expectedX = allBasicObjects.stream()
                .mapToInt(basic -> basic.getPosition().getX())
                .min().orElseThrow();
        final var expectedY = allBasicObjects.stream()
                .mapToInt(basic -> basic.getPosition().getY())
                .min().orElseThrow();
        final var expectedWidth = allBasicObjects.stream()
                .mapToInt(basic -> basic.getPosition().getX() + basic.getWidth())
                .max().orElseThrow() - expectedX;
        final var expectedHeight = allBasicObjects.stream()
                .mapToInt(basic -> basic.getPosition().getY() + basic.getHeight())
                .max().orElseThrow() - expectedY;
        assertAll(
                () -> assertEquals(new Position(expectedX, expectedY), parent.getPosition()),
                () -> assertEquals(expectedWidth, parent.getWidth()),
                () -> assertEquals(expectedHeight, parent.getHeight())
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