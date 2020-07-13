package io.github.seanwu1105.umldiagrameditor.diagram;

import org.jetbrains.annotations.NotNull;

public class Position {

    private final double x;
    private final double y;

    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @NotNull
    public Position subtract(@NotNull final Position position) {
        return new Position(getX() - position.getX(), getY() - position.getY());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final var position = (Position) o;

        if (Double.compare(position.getX(), getX()) != 0) return false;
        return Double.compare(position.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
