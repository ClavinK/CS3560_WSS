package src;

/**
 * The GoldBonus class represents a gold item that can be collected by the player.
 * When collected, it increases the player's gold amount.
 */
public class GoldBonus extends Item {
    private int amount; // The amount of gold this bonus provides

    /**
     * Constructs a GoldBonus item with the specified amount and repeat status.
     *
     * @param amount The amount of gold this bonus provides.
     * @param repeating Whether the item can be collected multiple times.
     */
    public GoldBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    /**
     * Collects the gold bonus, adding the gold to the player's inventory.
     *
     * @param p The player collecting the gold.
     */
    @Override
    public void collect(Player p) {
        System.out.println("Collected Gold (+" + amount + ")");
        p.addGold(amount);
    }
}
