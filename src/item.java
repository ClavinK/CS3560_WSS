// Item.java and subclasses
package src;

/**
 * Abstract base class for items that can be found in a TerrainSquare.
 */
public abstract class item {
    protected boolean repeating;

    public item(boolean repeating) {
        this.repeating = repeating;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public abstract void collect(Player p);
}

class FoodBonus extends item {
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

class WaterBonus extends item {
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

class GoldBonus extends item {
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
