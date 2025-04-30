package src;
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
