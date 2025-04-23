// Item.java and subclasses
package src;

/**
 * Abstract base class for items that can be found in a TerrainSquare.
 */
public abstract class Item {
    protected boolean repeating;

    public Item(boolean repeating) {
        this.repeating = repeating;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public abstract void collect(Player p);
}

class FoodBonus extends Item {
    private int amount;

    public FoodBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    @Override
    public void collect(Player p) {
        System.out.println("Collected Food Bonus (+" + amount + ")");
        p.addFood(amount);
    }
}

class WaterBonus extends Item {
    private int amount;

    public WaterBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    @Override
    public void collect(Player p) {
        System.out.println("Collected Water Bonus (+" + amount + ")");
        p.addWater(amount);
    }
}

class GoldBonus extends Item {
    private int amount;

    public GoldBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    @Override
    public void collect(Player p) {
        System.out.println("Collected Gold (+" + amount + ")");
        p.addGold(amount);
    }
}
