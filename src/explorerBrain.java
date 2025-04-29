package src;

public class explorerBrain implements brain {

    @Override
    public void makeMove(Player player, Map map) {
        Position current = player.getPosition();
        int x = current.getX();
        int y = current.getY();

        // Preferred directions: EAST → NORTHEAST → SOUTHEAST
        Position[] options = {
            new Position(x + 1, y),     // East
            new Position(x + 1, y - 1), // Northeast
            new Position(x + 1, y + 1)  // Southeast
        };

        for (Position next : options) {
            TerrainSquare square = map.getSquare(next);
            if (square != null && player.canEnter(square)) {
                player.enter(square);
                player.printStatus();
                return;
            }
        }

        // If no preferred direction is available, rest
        player.rest();
        player.printStatus();
        
    }
}
