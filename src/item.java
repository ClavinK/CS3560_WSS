// Item.java and subclasses
package src;

/**
 * Abstract base class for items that can be found in a TerrainSquare.
 */
public abstract class Item {
    protected boolean repeating;

    /**
     * Constructs an Item.
     * @param repeating Whether the item can be collected multiple times.
     */
    public Item(boolean repeating) {
        this.repeating = repeating;
    }

    /**
     * Checks if the item is repeating.
     * @return True if the item can be collected multiple times, false otherwise.
     */
    public boolean isRepeating() {
        return repeating;
    }

    /**
     * Defines how the item is collected by the player.
     * @param p The player collecting the item.
     */
    public abstract void collect(Player p);
}
