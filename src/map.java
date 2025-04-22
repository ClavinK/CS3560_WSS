// Map.java
package src;

import java.util.Random;

public class map {
    private TerrainSquare[][] grid;
    private int width;
    private int height;
    private Random rand = new Random();

    public map(int width, int height, String difficulty) {
        this.width = width;
        this.height = height;
        this.grid = new TerrainSquare[width][height];
        generateMap(difficulty);
    }

    private void generateMap(String difficulty) {
        TerrainType[] terrains = TerrainType.values();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TerrainType type = terrains[rand.nextInt(terrains.length)];
                Position pos = new Position(x, y);
                TerrainSquare square = new TerrainSquare(type, pos);

                // 35% chance to spawn a bonus item
                if (rand.nextDouble() < 0.35) {
                    int itemType = rand.nextInt(3);
                    switch (itemType) {
                        case 0 -> square.addItem(new FoodBonus(rand.nextInt(3) + 2, false));
                        case 1 -> square.addItem(new WaterBonus(rand.nextInt(3) + 2, false));
                        case 2 -> square.addItem(new GoldBonus(rand.nextInt(2) + 1, false));
                    }
                }

                // 10% chance to spawn a trader
                if (rand.nextDouble() < 0.1) {
                    int t = rand.nextInt(3);
                    trader trader = switch (t) {
                        case 0 -> new bargainerTrader();
                        case 1 -> new greedyTrader();
                        case 2 -> new impatientTrader();
                        default -> null;
                    };
                    square.setTrader(trader);
                }

                grid[x][y] = square;
            }
        }

        System.out.println("Map generated with difficulty: " + difficulty);
    }

    public TerrainSquare getSquare(Position pos) {
        return getSquare(pos.getX(), pos.getY());
    }

    public TerrainSquare getSquare(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}