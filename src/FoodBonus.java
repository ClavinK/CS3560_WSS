package src;

public class FoodBonus extends Item {
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
