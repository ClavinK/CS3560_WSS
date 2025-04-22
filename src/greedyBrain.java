// GreedyBrain.java
package src;

import src.brain.brain;

public class greedyBrain implements brain {
    @Override
    public void makeMove(Player player, map map) {
        Position current = player.getPosition();
        int x = current.getX();
        int y = current.getY();

        // Try moving east if possible, otherwise rest
        Position nextEast = new Position(x + 1, y);
        TerrainSquare nextSquare = map.getSquare(nextEast);

        if (nextSquare != null && player.canEnter(nextSquare)) {
            player.enter(nextSquare);
        } else {
            player.rest();
        }

        player.printStatus();
    }
}
