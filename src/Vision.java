/**
 * The Vision class represents the player's field of vision within a map.
 * It provides methods to determine visible terrain squares, locate items,
 * and find paths to specific resources or traders.
 */
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

    // Scans area relative to the player based on vision type.
    public List<TerrainSquare> getVisibleSquares(){
        List<TerrainSquare> visible = new ArrayList<>();
        Position pos =player.getPosition();
  
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue; // Skips current position of the player
                Position checkPos = new Position(pos.getX() + dx, pos.getY() + dy);
                TerrainSquare square = map.getSquare(checkPos);
                if (square != null) {
                    visible.add(square);
                }
            }
        }
        
        return visible;
    }

    /**
     * Returns the player associated with this Vision instance.
     * @return The Player object.
     */
    protected Player getPlayer() {
        return player;
    }

    /**
     * Returns the map associated with this Vision instance.
     * @return The Map object.
     */
    protected Map getMap() {
        return map;
    }

    /**
     * Finds all visible TerrainSquare objects that contain a specific type of item.
     * @param itemType The class of the item to search for.
     * @return A list of TerrainSquare objects containing the specified item type.
     */
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
    /**
     * Finds the closest TerrainSquare containing food within the player's vision.
     * @return A Path object to the closest food, or null if no food is found.
     */
    public Path closestFood() {
        return findBestPathTo(FoodBonus.class, 1);
    }

    /**
     * Finds the second closest TerrainSquare containing food within the player's vision.
     * @return A Path object to the second closest food, or null if no such food is found.
     */
    public Path secondClosestFood() {
        return findBestPathTo(FoodBonus.class, 2);
    }

    /**
     * Finds the closest TerrainSquare containing water within the player's vision.
     * @return A Path object to the closest water, or null if no water is found.
     */
    public Path closestWater() {
        return findBestPathTo(WaterBonus.class, 1);
    }

    /**
     * Finds the second closest TerrainSquare containing water within the player's vision.
     * @return A Path object to the second closest water, or null if no such water is found.
     */
    public Path secondClosestWater() {
        return findBestPathTo(WaterBonus.class, 2);
    }

    /**
     * Finds the closest TerrainSquare containing gold within the player's vision.
     * @return A Path object to the closest gold, or null if no gold is found.
     */
    public Path closestGold() {
        return findBestPathTo(GoldBonus.class, 1);
    }

    /**
     * Finds the second closest TerrainSquare containing gold within the player's vision.
     * @return A Path object to the second closest gold, or null if no such gold is found.
     */
    public Path secondClosestGold() {
        return findBestPathTo(GoldBonus.class, 2);
    }

    /**
     * Finds the closest TerrainSquare containing a trader within the player's vision.
     * @return A Path object to the closest trader, or null if no trader is found.
     */
    public Path closestTrader() {
        return findBestPathToTraders(1);
    }

    /**
     * Finds the second closest TerrainSquare containing a trader within the player's vision.
     * @return A Path object to the second closest trader, or null if no such trader is found.
     */
    public Path secondClosestTrader() {
        return findBestPathToTraders(2);
    }

    
    // Returns a path that has the sqaure with the lowest movement cost.
    /**
     * Finds the TerrainSquare with the lowest movement cost within the player's vision.
     * @return A Path object to the easiest square, or null if no valid square is found.
     */
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
    /**
     * Finds the best path to a TerrainSquare containing a specific item type.
     * @param itemType The class of the item to search for.
     * @param rank The rank of the desired path (e.g., 1 for closest, 2 for second closest).
     * @return A Path object to the desired square, or null if no such square is found.
     */
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

    /**
     * Finds the best path to a TerrainSquare containing a trader.
     * @param rank The rank of the desired path (e.g., 1 for closest, 2 for second closest).
     * @return A Path object to the desired square, or null if no such square is found.
     */
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

    /**
     * Builds a path from the player's current position to a destination position.
     * @param dest The destination position.
     * @return A Path object representing the sequence of moves and associated costs, or null if the path is invalid.
     */
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
