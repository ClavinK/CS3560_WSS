package src;

public class WaterBonus extends Item {
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
