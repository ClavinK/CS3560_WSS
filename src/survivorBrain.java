package src;

import java.util.List;

public class survivorBrain implements Brain {

    @Override
    public void makeMove(Player player, Map map) {
        Vision vision = new Vision(player, map);

        boolean lowFood = player.getFoodAmount() <= 3;
        boolean lowWater = player.getWaterAmount() <= 3;
        boolean lowStrength = player.getStrengthAmount() <= 3;

        // Step 1: If low on food, try to get food
        if (lowFood) {
            List<TerrainSquare> foodSquares = vision.getSquaresWithItem(FoodBonus.class);
            for (TerrainSquare square : foodSquares) {
                if (player.canEnter(square)) {
                    player.enter(square);
                    player.printStatus();
                    return;
                }
            }
        }

        // Step 2: If low on water, try to get water
        if (lowWater) {
            List<TerrainSquare> waterSquares = vision.getSquaresWithItem(WaterBonus.class);
            for (TerrainSquare square : waterSquares) {
                if (player.canEnter(square)) {
                    player.enter(square);
                    player.printStatus();
                    return;
                }
            }
        }

        // Step 3: Rest if strength is too low
        if (lowStrength) {
            player.rest();
            player.printStatus();
            return;
        }

        // Step 4: Move east if possible
        Position east = new Position(player.getPosition().getX() + 1, player.getPosition().getY());
        TerrainSquare eastSquare = map.getSquare(east);
        if (eastSquare != null && player.canEnter(eastSquare)) {
            player.enter(eastSquare);
        } else {
            player.rest();
        }

        player.printStatus();
    }
} 


/*package src;
import java.util.List;

public class survivorBrain implements Brain {

    @Override
    public void makeMove(Player player, Map map) {
        Position current = player.getPosition();
        int x = current.getX();
        int y = current.getY();

        //  Use returning getters instead of print-only methods
        boolean lowFood = player.getFoodAmount() <= 3;
        boolean lowWater = player.getWaterAmount() <= 3;
        boolean lowStrength = player.getStrengthAmount() <= 3;

        Position[] directions = {
            new Position(x, y - 1), // North
            new Position(x, y + 1), // South
            new Position(x + 1, y), // East
            new Position(x - 1, y)  // West
        };

        //  Priority: seek nearby food/water if low
        for (Position pos : directions) {
            TerrainSquare square = map.getSquare(pos);
            if (square != null && player.canEnter(square)) {
                List<Item> items = square.getItems();
                boolean hasFood = items.stream().anyMatch(i -> i instanceof FoodBonus);
                boolean hasWater = items.stream().anyMatch(i -> i instanceof WaterBonus);

                if ((lowFood && hasFood) || (lowWater && hasWater)) {
                    player.enter(square);
                    player.printStatus();
                    return;
                }
            }
        }

        //  Rest more aggressively when strength is low
        if (lowStrength) {
            player.rest();
            player.printStatus();
            return;
        }

        //  Move east if safe, otherwise rest
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
 */