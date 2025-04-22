// Player.java
package src;

public class Player {
    private int maxStrength, maxWater, maxFood;
    private int currentStrength, currentWater, currentFood, gold;
    private Position position;

    public Player(int maxStrength, int maxWater, int maxFood) {
        this.maxStrength = maxStrength;
        this.maxWater = maxWater;
        this.maxFood = maxFood;
        this.currentStrength = maxStrength;
        this.currentWater = maxWater;
        this.currentFood = maxFood;
        this.gold = 0;
        this.position = new Position(0, 0); // default start
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean canEnter(TerrainSquare square) {
        return currentStrength >= square.getMovementCost()
            && currentFood >= square.getFoodCost()
            && currentWater >= square.getWaterCost();
    }

    public void enter(TerrainSquare square) {
        currentStrength -= square.getMovementCost();
        currentFood -= square.getFoodCost();
        currentWater -= square.getWaterCost();
        position = square.getPosition();
        square.collectItems(this);
        System.out.println("Moved to square " + position + " (" + square.getType() + ")");
    }

    public void rest() {
        System.out.println("Resting... (+2 Strength, -0.5 Food/Water)");
        currentStrength = Math.min(maxStrength, currentStrength + 2);
        currentFood = Math.max(0, currentFood - 1);
        currentWater = Math.max(0, currentWater - 1);
    }

    public void addFood(int amount) {
        currentFood = Math.min(maxFood, currentFood + amount);
    }

    public void addWater(int amount) {
        currentWater = Math.min(maxWater, currentWater + amount);
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public boolean isAlive() {
        return currentFood > 0 && currentWater > 0 && currentStrength > 0;
    }

    public boolean hasWon(int mapWidth) {
        return position.getX() == mapWidth - 1;
    }

    public void printStatus() {
        System.out.println("Position: " + position +
                           " | Strength: " + currentStrength +
                           " | Food: " + currentFood +
                           " | Water: " + currentWater +
                           " | Gold: " + gold);
    }
}
