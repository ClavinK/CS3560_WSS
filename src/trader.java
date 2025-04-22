// Trader.java and BargainerTrader.java
package src;

public interface trader {
    /**
     * Evaluates a trade offer and returns a counter-offer if declined,
     * or null if the trade is accepted.
     */
    Offer evaluateOffer(Offer playerOffer);
}

class bargainerTrader implements trader {
    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offeredTotal >= requestedTotal) {
            System.out.println("Trader accepts your offer.");
            return null; // accepted
        } else {
            System.out.println("Trader counters your offer.");
            return new Offer(
                playerOffer.getOfferedFood() + 1,
                playerOffer.getOfferedWater() + 1,
                playerOffer.getOfferedGold(),
                playerOffer.getRequestedFood(),
                playerOffer.getRequestedWater(),
                playerOffer.getRequestedGold()
            );
        }
    }
}
