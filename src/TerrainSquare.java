// TerrainSquare.java
package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one square of the map grid, including terrain, items, and trader.
 */
public class TerrainSquare {
    private TerrainType type;
    private Position position;
    private List<item> items;
    private trader trader;

    public TerrainSquare(TerrainType type, Position position) {
        this.type = type;
        this.position = position;
        this.items = new ArrayList<>();
        this.trader = null;
    }

    public TerrainType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public List<item> getItems() {
        return items;
    }

    public trader getTrader() {
        return trader;
    }

    public void setTrader(trader trader) {
        this.trader = trader;
    }

    public void addItem(item item) {
        items.add(item);
    }

    public void collectItems(Player p) {
        List<item> toRemove = new ArrayList<>();
        for (item item : items) {
            item.collect(p);
            if (!item.isRepeating()) {
                toRemove.add(item);
            }
        }
        items.removeAll(toRemove);
    }

    public int getMovementCost() {
        return type.getMovementCost();
    }

    public int getFoodCost() {
        return type.getFoodCost();
    }

    public int getWaterCost() {
        return type.getWaterCost();
    }

    @Override
    public String toString() {
        return type.name();
    }
}
