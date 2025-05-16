package src;

import java.util.ArrayList;
import java.util.List;

public class farsightVision extends Vision {
    public farsightVision(Player player, Map map) {
        super(player, map);
    }

    @Override
    public List<TerrainSquare> getVisibleSquares() {
        List<TerrainSquare> visible = new ArrayList<>();
        Position pos = getPlayer().getPosition();
        int x = pos.getX();
        int y = pos.getY();
        Position[] positions = {
            new Position(x + 1, y),
            new Position(x, y + 1),
            new Position(x, y - 1),
            new Position(x + 2, y),
            new Position(x + 1, y + 1),
            new Position(x + 1, y - 1),
            new Position(x - 1, y),
            new Position(x - 2, y),
            new Position(x, y + 2),
            new Position(x, y - 2)
        };

        for (Position position : positions) {
            TerrainSquare square = getMap().getSquare(position);
            if (square != null && getPlayer().canEnter(square)) {
                visible.add(square);

            }

        }
        return visible;
    }
}
