// Position.java
package src;

/**
 * The Position class represents a coordinate on a 2D grid.
 * It is used to track the location of entities (e.g., players, items) on the map.
 */
public class Position {
    private int x; // The x-coordinate of the position
    private int y; // The y-coordinate of the position

    /**
     * Constructs a Position object with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the position.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the position.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the position.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the position.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Compares this position to another position to check if they are equal.
     * Two positions are considered equal if their x and y coordinates are the same.
     *
     * @param other The other position to compare to.
     * @return True if the positions are equal, false otherwise.
     */
    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Returns a string representation of the position in the format "(x, y)".
     *
     * @return A string representing the position.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
