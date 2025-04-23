package src;

/**
 * Brain interface to control how a player makes decisions each turn.
 */
public interface brain {
    /**
     * Makes a decision for the player each turn.
     * @param player the player to control
     * @param map the game map
     */
    void makeMove(Player player, Map map);
}
