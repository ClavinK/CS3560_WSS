// GreedyTrader.java
package src;

/**
 * The greedyTrader class implements the Trader interface and defines the behavior
 * for a trader who always wants more than what is offered. This trader will only
 * accept very generous offers and will make several counter-offers before refusing to trade.
 */
public class greedyTrader implements Trader {

    private int maxCounterOffers = 4; // Maximum number of counter-offers allowed

    /**
     * Evaluates a trade offer made by the player.
     * The greedy trader only accepts offers that are at least 1.5 times the value of their request.
     * Otherwise, the trader will make a counter-offer, but after a set number of counter-offers,
     * will refuse to trade further.
     *
     * @param playerOffer The offer made by the player.
     * @return Null if the trade is accepted or refused, or a counter-offer if the trade is declined.
     */
    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        // Accept the offer if the player offers at least 1.5 times the requested total
        if (offeredTotal >= requestedTotal * 1.5) {
            System.out.println("Greedy Trader: Fine, I accept your generous offer.");
            return null;
        } 
        // Refuse to trade if out of counter-offers
        else if (maxCounterOffers <= 0) {
            System.out.println("Greedy Trader: 'You cheap scum, get outta here!'");
            return null;
        }
        // Otherwise, make a counter-offer and decrease the number of remaining counter-offers
        else {
            System.out.println("Greedy Trader: That's a joke. Here's my counter-offer.");
            maxCounterOffers--;
            // Counter-offer: increase the required offer from the player
            return new Offer(
                playerOffer.getOfferedFood() + 2,
                playerOffer.getOfferedWater() + 2,
                playerOffer.getOfferedGold() + 1,
                playerOffer.getRequestedFood(),
                playerOffer.getRequestedWater(),
                playerOffer.getRequestedGold()
            );
        }
    }
}
