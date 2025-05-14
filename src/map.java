// Map.java
package src;

import java.util.Random;

/**
 * The Map class represents the game world as a 2D grid of TerrainSquares.
 * It is responsible for generating the map, placing items and traders, and providing access to squares.
 */
public class Map {
    private TerrainSquare[][] grid; // 2D array representing the map grid
    private int width;              // Width of the map
    private int height;             // Height of the map
    private Random rand = new Random(); // Random number generator for map generation

    /**
     * Constructs a Map object with the specified width, height, and difficulty.
     * Initializes the grid and generates the map contents.
     *
     * @param width The width of the map.
     * @param height The height of the map.
     * @param difficulty The difficulty setting, which may affect map generation.
     */
    public Map(int width, int height, String difficulty) {
        this.width = width;
        this.height = height;
        this.grid = new TerrainSquare[width][height];
        generateMap(difficulty);
    }

    /**
     * Generates the map by filling each square with a random terrain type,
     * and randomly placing bonus items and traders based on probabilities.
     *
     * @param difficulty The difficulty setting for the map.
     */
    private void generateMap(String difficulty) {
        TerrainType[] terrains = TerrainType.values();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TerrainType type = terrains[rand.nextInt(terrains.length)];
                Position pos = new Position(x, y);
                TerrainSquare square = new TerrainSquare(type, pos);

                // 35% chance to spawn a bonus item (food, water, or gold)
                if (rand.nextDouble() < 0.35) {
                    int itemType = rand.nextInt(3);
                    switch (itemType) {
                        case 0 -> square.addItem(new FoodBonus(rand.nextInt(3) + 2, false));
                        case 1 -> square.addItem(new WaterBonus(rand.nextInt(3) + 2, false));
                        case 2 -> square.addItem(new GoldBonus(rand.nextInt(2) + 1, false));
                    }
                }

                // 15% chance to spawn a trader (random trader type)
                if (rand.nextDouble() < 0.15) {
                    int t = rand.nextInt(3);
                    Trader trader = switch (t) {
                        case 0 -> new bargainerTrader();
                        case 1 -> new greedyTrader();
                        case 2 -> new impatientTrader();
                        default -> null;
                    };
                    square.setTrader(trader);
                }

                grid[x][y] = square; // Place the generated square in the grid
            }
        }

        System.out.println("Map generated with difficulty: " + difficulty);
    }

    /**
     * Returns the TerrainSquare at the specified Position.
     *
     * @param pos The Position to look up.
     * @return The TerrainSquare at the given position, or null if out of bounds.
     */
    public TerrainSquare getSquare(Position pos) {
        return getSquare(pos.getX(), pos.getY());
    }

    /**
     * Returns the TerrainSquare at the specified (x, y) coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The TerrainSquare at (x, y), or null if out of bounds.
     */
    public TerrainSquare getSquare(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return null;
    }

    /**
     * Gets the width of the map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return height;
    }
}