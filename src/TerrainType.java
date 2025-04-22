// TerrainType.java
package src;

/**
 * Enum representing different types of terrain, each with its own cost.
 */
public enum TerrainType {
    PLAINS(1, 1, 1),
    MOUNTAIN(2, 2, 2),      // reduced movement cost
    DESERT(2, 1, 2),        // reduced water cost
    SWAMP(2, 2, 2),         // reduced food cost
    FOREST(2, 1, 1);        // survival-friendly

    private final int movementCost;
    private final int foodCost;
    private final int waterCost;

    TerrainType(int movementCost, int foodCost, int waterCost) {
        this.movementCost = movementCost;
        this.foodCost = foodCost;
        this.waterCost = waterCost;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public int getFoodCost() {
        return foodCost;
    }

    public int getWaterCost() {
        return waterCost;
    }

    @Override
    public String toString() {
        return name() + " (M:" + movementCost + ", F:" + foodCost + ", W:" + waterCost + ")";
    }
}
