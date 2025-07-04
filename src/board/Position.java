package board;

import java.util.Objects;

/**
 * This class represents a position of a location on the board.
 * It contains the x and y coordinates of the position.
 *
 * @author Louis Jeremie Ing
 * @version 2.0
 */
public class Position {
    public final int X, Y;

    /**
     * Constructor for the Position class.
     * Creates a position with the specified x and y coordinates.
     *
     * @param x The x coordinate of the position.
     * @param y The y coordinate of the position.
     */
    public Position(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        Position p = (Position) object; return X == p.X && Y == p.Y;
    }

    /**
     * Override the hashCode method to generate a hash code for the Position object.
     *
     * @return The hash code of the Position object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.X, this.Y);
    }
}