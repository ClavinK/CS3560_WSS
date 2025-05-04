package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vision {
    private final Player player;
    private final Map map;
    private final int radius =1;

    public Vision(Player player, Map map){
        this.player=player;
        this.map =map;
    }

    public List<TerrainSquare> getVisibleSquares(){
        List<TerrainSquare> visible = new ArrayList<>();
        Position pos =player.getPosition();
  
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue; // skip current position
                Position checkPos = new Position(pos.getX() + dx, pos.getY() + dy);
                TerrainSquare square = map.getSquare(checkPos);
                if (square != null) {
                    visible.add(square);
                }
            }
        }
        
        return visible;
    }


    public List<TerrainSquare> getSquaresWithItem(Class<? extends Item> itemType) {
        List<TerrainSquare> matches = new ArrayList<>();
        for (TerrainSquare square : getVisibleSquares()) {
            for (Item item : square.getItems()) {
                if (itemType.isInstance(item)) {
                    matches.add(square);
                    break;

                }
            }
        }
        return matches;
    }

    // Path methods for closest resources
    public Path closestFood() {
        return findBestPathTo(FoodBonus.class, 1);
    }

    public Path secondClosestFood() {
        return findBestPathTo(FoodBonus.class, 2);
    }

    public Path closestWater() {
        return findBestPathTo(WaterBonus.class, 1);
    }

    public Path secondClosestWater() {
        return findBestPathTo(WaterBonus.class, 2);
    }

    public Path closestGold() {
        return findBestPathTo(GoldBonus.class, 1);
    }

    public Path secondClosestGold() {
        return findBestPathTo(GoldBonus.class, 2);
    }

    public Path closestTrader() {
        return findBestPathToTraders(1);
    }

    public Path secondClosestTrader() {
        return findBestPathToTraders(2);
    }

    
    // Returns a path that has the sqaure with the lowest movement cost.
    public Path easiestPath() {
        List<TerrainSquare> visible = getVisibleSquares();
        TerrainSquare easiest = null;
        int minCost = Integer.MAX_VALUE;

        for (TerrainSquare square : visible) {
            if(!player.canEnter(square)) continue;
            int cost = square.getMovementCost();
            if(cost < minCost) {
                minCost = cost;
                easiest = square;
            }
        }
        return easiest != null ? buildPathTo(easiest.getPosition()) : null;
    }


    // Finds the closest square that contains the given item type.
    private Path findBestPathTo(Class<? extends Item> itemType, int rank) {
        List<TerrainSquare> targets = getSquaresWithItem(itemType);

        List<Path> paths = new ArrayList<>();
        for (TerrainSquare square : targets) {
            if(player.canEnter(square)) {
                Path path = buildPathTo(square.getPosition());
                if (path != null) paths.add(path);
            }
        }
        paths.sort(Comparator
            .comparingInt(Path::getLength)
            .thenComparingInt(Path::getTotalMovementCost)
            .thenComparing((Path p) -> p.getEndPosition().getX(), Comparator.reverseOrder()));

        return (paths.size() >= rank) ? paths.get(rank - 1) : null;
    }

    private Path findBestPathToTraders(int rank) {
        List<TerrainSquare> trader = new ArrayList<>();
        for (TerrainSquare square : getVisibleSquares()) {
            if(square.hasTrader()) {
                trader.add(square);
            }
        }

        List<Path> paths = new ArrayList<>();
        for (TerrainSquare square : trader) {
            if(player.canEnter(square)) {
                Path path = buildPathTo(square.getPosition());
                if (path != null) paths.add(path);
            }
        }
        paths.sort(Comparator
            .comparingInt(Path::getLength)
            .thenComparingInt(Path::getTotalMovementCost)
            .thenComparing((Path p) -> p.getEndPosition().getX(), Comparator.reverseOrder()));

        return (paths.size() >= rank) ? paths.get(rank - 1) : null;
    }

    private Path buildPathTo(Position dest) {
        Position start = player.getPosition();
        List<Move> moves = new ArrayList<>();

        int dx = Integer.compare(dest.getX(), start.getX());
        int dy = Integer.compare(dest.getY(), start.getY());

        Position current = start;
        int totalMoveCost = 0, totalFoodCost = 0, totalWaterCost = 0;

        while (!current.equals(dest)) {
            Position next = new Position(current.getX() + dx, current.getY() + dy);
            TerrainSquare square = map.getSquare(next);
            if (square == null || !player.canEnter(square)) return null;

            moves.add(Move.fromDelta(dx, dy));
            totalMoveCost += square.getMovementCost();
            totalFoodCost += square.getFoodCost();
            totalWaterCost += square.getWaterCost();

            current = next;
        }

        return new Path(dest, moves, totalMoveCost, totalFoodCost, totalWaterCost);
    }
}

// Path class representing a sequence of moves and associated costs
class Path {
    private final Position end;
    private final List<Move> moves;
    private final int totalMoveCost;
    private final int totalFoodCost;
    private final int totalWaterCost;

    public Path(Position end, List<Move> moves, int totalMoveCost, int food, int water) {
        this.end = end;
        this.moves = moves;
        this.totalMoveCost = totalMoveCost;
        this.totalFoodCost = food;
        this.totalWaterCost = water;
    }

    public Position getEndPosition() {
        return end;
    }

    public int getLength() {
        return moves.size();
    }

    public int getTotalMovementCost() {
        return totalMoveCost;
    }

    public int getTotalFoodCost() {
        return totalFoodCost;
    }

    public int getTotalWaterCost() {
        return totalWaterCost;
    }

    public List<Move> getMoves() {
        return moves;
    }
}

// Enum representing move directions
enum Move {
    NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1),
    NORTHEAST(-1, 1), NORTHWEST(-1, -1), SOUTHEAST(1, 1), SOUTHWEST(1, -1);

    final int dx;
    final int dy;

    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Move fromDelta(int dx, int dy) {
        for (Move move : Move.values()) {
            if (move.dx == dx && move.dy == dy) return move;
        }
        throw new IllegalArgumentException("Invalid move delta: " + dx + ", " + dy);
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
