package src;

public class GoldBonus extends Item {
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
