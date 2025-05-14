package src;

/**
 * The FoodBonus class represents a food item that can be collected by the player.
 * When collected, it increases the player's food amount.
 */
public class FoodBonus extends Item {
    private int amount; // The amount of food this bonus provides

    /**
     * Constructs a FoodBonus item with the specified amount and repeat status.
     *
     * @param amount The amount of food this bonus provides.
     * @param repeating Whether the item can be collected multiple times.
     */
    public FoodBonus(int amount, boolean repeating) {
        super(repeating);
        this.amount = amount;
    }

    /**
     * Collects the food bonus, adding the food to the player's inventory.
     *
     * @param p The player collecting the food.
     */
    @Override
    public void collect(Player p) {
        System.out.println("Collected Food Bonus (+" + amount + ")");
        p.addFood(amount);
    }
}
