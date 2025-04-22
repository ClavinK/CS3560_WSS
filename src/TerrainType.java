// TerrainType.java
package src;

/**
 * Enum representing different types of terrain, each with its own cost.
 */
public enum TerrainType {
    PLAINS(1, 1, 1),
    MOUNTAIN(3, 2, 2),
    DESERT(2, 1, 3),
    SWAMP(2, 3, 2),
    FOREST(2, 2, 2);

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
}
