package src;
/**
 * The Trader interface defines the behavior for entities that can engage in trades
 * with the player. Implementing classes must provide logic for evaluating trade offers.
 */
public interface Trader {

    /**
     * Evaluates a trade offer made by the player.
     * 
     * @param playerOffer The offer made by the player, specifying what they are giving
     *                    and what they are requesting in return.
     * @return A counter-offer if the trade is declined, or null if the trade is accepted.
     */
    Offer evaluateOffer(Offer playerOffer);
}