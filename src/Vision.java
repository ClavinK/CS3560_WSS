package src;

import java.util.ArrayList;
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
}