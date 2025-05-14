package src;

/**
 * The survivorBrain class implements the Brain interface and defines the behavior
 * for a survival-focused AI. This AI prioritizes maintaining the player's food,
 * water, and strength levels while attempting to move east to escape the wilderness.
 */
public class survivorBrain implements Brain {

    /**
     * Makes a move for the player based on their current state and the map.
     * The AI prioritizes survival by seeking food, water, or resting when necessary.
     * If survival needs are met, it attempts to move east.
     *
     * @param player The player whose move is being decided.
     * @param map The map of the game world.
     */
    @Override
    public void makeMove(Player player, Map map) {
        // Create a Vision object to determine visible squares and resources
        Vision vision = new Vision(player, map);

        // Check if the player's resources are critically low
        boolean lowFood = player.getFoodAmount() <= 3;
        boolean lowWater = player.getWaterAmount() <= 3;
        boolean lowStrength = player.getStrengthAmount() <= 3;

        // Step 1: If low on food, try to move towards the closest food
        if (lowFood) {
            Path foodPath = vision.closestFood(); // Find the closest food
            if (foodPath != null) {
                player.enter(map.getSquare(foodPath.getEndPosition())); // Move to the food square
                player.printStatus(); // Print the player's status after the move
                return; // End the turn
            }
        }

        // Step 2: If low on water, try to move towards the closest water
        if (lowWater) {
            Path waterPath = vision.closestWater(); // Find the closest water
            if (waterPath != null) {
                player.enter(map.getSquare(waterPath.getEndPosition())); // Move to the water square
                player.printStatus(); // Print the player's status after the move
                return; // End the turn
            }
        }

        // Step 3: If strength is too low, rest to recover
        if (lowStrength) {
            player.rest(); // Rest to regain strength
            player.printStatus(); // Print the player's status after resting
            return; // End the turn
        }

        // Step 4: Attempt to move east if possible
        Position east = new Position(player.getPosition().getX() + 1, player.getPosition().getY()); // Calculate the east position
        TerrainSquare eastSquare = map.getSquare(east); // Get the square to the east
        if (eastSquare != null && player.canEnter(eastSquare)) {
            player.enter(eastSquare); // Move to the east square
        } else {
            player.rest(); // Rest if moving east is not possible
        }

        // Print the player's status after the move or rest
        player.printStatus();
    }
}
