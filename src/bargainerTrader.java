package src;

/**
 * The bargainerTrader class implements the Trader interface and defines the behavior
 * for a trader who is willing to negotiate but expects a fair deal. This trader will
 * make several counter-offers before refusing to trade further.
 */
public class bargainerTrader implements Trader {

    private int maxCounterOffers = 5; // Maximum number of counter-offers allowed

    /**
     * Evaluates a trade offer made by the player.
     * If the offer is sufficient, the trader accepts. Otherwise, the trader may make a counter-offer,
     * but will refuse to trade after a set number of counter-offers.
     *
     * @param playerOffer The offer made by the player.
     * @return Null if the trade is accepted or refused, or a counter-offer if the trade is declined.
     */
    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        // Accept the offer if the player offers enough
        if (offeredTotal >= requestedTotal) {
            System.out.println("Bargainer Trader: 'I accept your offer.'");
            return null; // Accepts the trade
        }
        // Refuse to trade if out of counter-offers
        else if (maxCounterOffers <= 0) {
            System.out.println("Bargainer Trader: 'Aw bugger off! I don't want to see your face again!'");
            return null;
        }
        // Otherwise, make a counter-offer and decrease the number of remaining counter-offers
        else {
            System.out.println("Bargainer Trader: 'How about this deal instead?'");
            maxCounterOffers--;
            // Returns a slightly more demanding counter-offer
            return new Offer(
                playerOffer.getOfferedFood() + (int)((Math.random() * (2 - 1)) + 1),
                playerOffer.getOfferedWater() + (int)((Math.random() * (2 - 1)) + 1),
                playerOffer.getOfferedGold() + (int)((Math.random() * (2 - 1)) + 1),
                playerOffer.getRequestedFood(),
                playerOffer.getRequestedWater(),
                playerOffer.getRequestedGold()
            );
        }
    }
}
