// SurvivorBrain.java
package src;

import src.brain.brain;

public class survivorBrain implements brain {

    @Override
    public void makeMove(Player player, map map) {
        Position current = player.getPosition();
        int x = current.getX();
        int y = current.getY();

        boolean lowFood = player.getFood() <= 3;
        boolean lowWater = player.getWater() <= 3;
        boolean lowStrength = player.getStrength() <= 3;

        Position[] directions = {
            new Position(x, y - 1), // North
            new Position(x, y + 1), // South
            new Position(x + 1, y), // East
            new Position(x - 1, y)  // West
        };

        // Priority: seek nearby food/water if low
        for (Position pos : directions) {
            TerrainSquare square = map.getSquare(pos);
            if (square != null && player.canEnter(square)) {
                boolean hasFood = square.getItems().stream().anyMatch(i -> i instanceof FoodBonus);
                boolean hasWater = square.getItems().stream().anyMatch(i -> i instanceof WaterBonus);

                if ((lowFood && hasFood) || (lowWater && hasWater)) {
                    player.enter(square);
                    player.printStatus();
                    return;
                }
            }
        }

        // Rest more aggressively when strength is low
        if (lowStrength) {
            player.rest();
            player.printStatus();
            return;
        }

        // Move east if safe
        Position east = new Position(x + 1, y);
        TerrainSquare eastSquare = map.getSquare(east);

        if (eastSquare != null && player.canEnter(eastSquare)) {
            player.enter(eastSquare);
        } else {
            player.rest();
        }

        player.printStatus();
    }
}
