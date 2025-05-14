package src;

/**
 * The impatientTrader class implements the Trader interface and defines the behavior
 * for a trader who quickly loses patience. This trader will only make a limited number
 * of counter-offers before refusing to trade further.
 */
public class impatientTrader implements Trader {
    private int maxCounterOffers = 2; // Maximum number of counter-offers allowed

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
            System.out.println("Impatient Trader: 'This is acceptable, let's trade!'");
            return null; // Accepts the offer
        // Refuse to trade if out of counter-offers
        } else if (maxCounterOffers <= 0) {
            System.out.println("Impatient Trader: 'You're wasting my time. Be gone!'");
            return null;
        } else {
            // Make a final counter-offer and decrease patience
            maxCounterOffers--;
            System.out.println("Impatient Trader: That's not enough. Here's my final offer.");

            // Counter-offer: slightly increase the required offer from the player
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
