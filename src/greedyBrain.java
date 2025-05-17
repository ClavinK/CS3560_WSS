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
        int maxValue = Integer.MIN_VALUE;
        Position playerPos = player.getPosition();

        for (TerrainSquare square : visible) {
            if (square == null && !player.canEnter(square)) continue;

            Position squarePos = square.getPosition();
            int deltaX = square.getPosition().getX() - playerPos.getX();
            int deltaY = square.getPosition().getY() - playerPos.getY();

            // Skips backward moves
            if (deltaX < 0) continue;

            int value = calculateSquareValue(square, playerPos);

            // Encourages diagonal east moves for bonus
            if (deltaX > 0 && deltaY == 0) {
                value += 1;
            }

            // General eastward movement
            value += deltaX *2;
            
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
                int deltaX = square.getPosition().getX() - playerPos.getX();
                int deltaY = square.getPosition().getY() - playerPos.getY();

                if (deltaX < 0 || deltaY != 0) continue;

                player.enter(square);
                player.printStatus();
                return;
                
            }
            player.rest();
        }

        player.printStatus();
    }

    private int calculateSquareValue(TerrainSquare square, Position playerPos) {
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
            else if (item instanceof Trader) {
                value += 2;
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
        int maxValue = Integer.MIN_VALUE;

        for (TerrainSquare square : visible) {
            if (square == null && !player.canEnter(square)) continue;

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
                if(square != null || player.canEnter(bestSquare)) {
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
}*/
