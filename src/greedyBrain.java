package src;

import java.util.Collections;
import java.util.List;

public class greedyBrain implements Brain {

    @Override
    public void makeMove(Player player, Map map) {
        Vision vision = new Vision(player, map);
        List<TerrainSquare> visible = vision.getVisibleSquares();

        if(visible.isEmpty()) {
            player.rest();
            player.printStatus();
            return;
        }

        TerrainSquare bestSquare = null;
        int maxValue = 0;

        for (TerrainSquare square : visible) {
            if (square == null || !player.canEnter(square)) continue;

            int value = calculateSquareValue(square);
            if (value > maxValue) {
                maxValue = value;
                bestSquare = square;
            }
        }

        if (bestSquare != null) {
            player.enter(bestSquare);
        } else {
            Collections.shuffle(visible);
            for(TerrainSquare square : visible) {
                if(square == null || player.canEnter(bestSquare)) {
                    player.enter(bestSquare);
                    player.printStatus();
                    return;
                }
            }
            player.rest();
        }

        player.printStatus();
    }

    private int calculateSquareValue(TerrainSquare square) {
        int value = 0;
        int itemCount = 0;

        for (Item item : square.getItems()) {
            if (item instanceof FoodBonus fb) {
                value += fb.isRepeating() ? 3 : 2;
                itemCount++;
            }
            else if (item instanceof WaterBonus wb) {
                value += wb.isRepeating() ? 3 : 2;
                itemCount++;
            }
            else if (item instanceof GoldBonus gb) {
                value += gb.isRepeating() ? 2 : 1;
                itemCount++;
            }
        }
        if(itemCount > 0) {
            value += itemCount;
        }
        return value;
    }               
}


/*package src;

import java.util.List;

public class greedyBrain implements Brain {

    @Override
    public void makeMove(Player player, Map map) {
        Vision vision = new Vision(player, map);
        List<TerrainSquare> visible = vision.getVisibleSquares();

        TerrainSquare bestSquare = null;
        int maxValue = 0;

        for (TerrainSquare square : visible) {
            if (!player.canEnter(square)) continue;

            int value = 0;
            for (Item item : square.getItems()) {
                if (item instanceof FoodBonus fb) value += fb.isRepeating() ? 3 : 2;
                else if (item instanceof WaterBonus wb) value += wb.isRepeating() ? 3 : 2;
                else if (item instanceof GoldBonus gb) value += gb.isRepeating() ? 2 : 1;
            }

            if (value > maxValue) {
                maxValue = value;
                bestSquare = square;
            }
        }

        if (bestSquare != null) {
            player.enter(bestSquare);
        } else {
            player.rest();
        }

        player.printStatus();
    }
}*/

/*package src;

public class greedyBrain implements Brain {

    @Override
    public void makeMove(Player player, Map map) {
        Position current = player.getPosition();
        int x = current.getX();
        int y = current.getY();

        TerrainSquare bestSquare = null;
        int maxValue = 0;

        // Check all 4 cardinal directions: N, S, E, W
        Position[] directions = {
            new Position(x + 1, y), // East
            new Position(x - 1, y), // West
            new Position(x, y + 1), // South
            new Position(x, y - 1)  // North
        };

        for (Position pos : directions) {
            TerrainSquare square = map.getSquare(pos);
            if (square != null && player.canEnter(square)) {
                int value = 0;

                for (Item item : square.getItems()) {
                    if (item instanceof FoodBonus fb) value += fb.isRepeating() ? 3 : 2;
                    else if (item instanceof WaterBonus wb) value += wb.isRepeating() ? 3 : 2;
                    else if (item instanceof GoldBonus gb) value += gb.isRepeating() ? 2 : 1;
                }

                if (value > maxValue) {
                    maxValue = value;
                    bestSquare = square;
                }
            }
        }

        if (bestSquare != null) {
            player.enter(bestSquare);
        } else {
            player.rest();
        }

        player.printStatus();
    }
}
 */
