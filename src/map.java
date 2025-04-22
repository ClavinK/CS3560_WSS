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
        TerrainType[] allTypes = TerrainType.values();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TerrainType type = allTypes[rand.nextInt(allTypes.length)];
                TerrainSquare square = new TerrainSquare(type, new Position(x, y));

                // 20% chance to place an item
                if (rand.nextDouble() < 0.2) {
                    int choice = rand.nextInt(3);
                    switch (choice) {
                        case 0 -> square.addItem(new FoodBonus(rand.nextInt(3) + 1, false));
                        case 1 -> square.addItem(new WaterBonus(rand.nextInt(3) + 1, false));
                        case 2 -> square.addItem(new GoldBonus(rand.nextInt(2) + 1, false));
                    }
                }

                grid[x][y] = square;
            }
        }

        System.out.println("Map generated with size " + width + "x" + height + " (" + difficulty + ")");
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