package io.github.seanwu1105.umldiagrameditor.diagram;

public class Position {

    private final int x;
    private final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final var position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        var result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
