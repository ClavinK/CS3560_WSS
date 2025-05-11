package src;

/**
 * The WaterBonus class represents a collectible item in the game that provides
 * a water bonus to the player upon collection. It extends the Item class and
 * includes functionality to specify the amount of water provided and whether
 * the item is repeating.
 */
public class WaterBonus extends Item {
    private int amount;

    public WaterBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    // Collects water and updates the player stat for water.
    @Override
    public void collect(Player p) {
        System.out.println("Collected Water Bonus (+" + amount + ")");
        p.addWater(amount);
    }
}
