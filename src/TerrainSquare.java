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
    private List<Item> items;
    private Trader trader;

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

    public List<Item> getItems() {
        return items;
    }

    public Trader getTrader() {
        return trader;
    }

    public boolean hasTrader() {
        return trader != null;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void collectItems(Player p) {
        List<Item> toRemove = new ArrayList<>();
        for (Item item : items) {
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
