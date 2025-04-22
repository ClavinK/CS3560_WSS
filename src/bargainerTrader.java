package src;

public class bargainerTrader implements trader {

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offeredTotal >= requestedTotal) {
            System.out.println("Trader accepts your offer.");
            return null; // Accepts the trade
        } else {
            System.out.println("Trader counters your offer.");
            // Returns a slightly more demanding counter-offer
            return new Offer(
                playerOffer.getOfferedFood() + 1,
                playerOffer.getOfferedWater() + 1,
                playerOffer.getOfferedGold(), // gold stays the same
                playerOffer.getRequestedFood(),
                playerOffer.getRequestedWater(),
                playerOffer.getRequestedGold()
            );
        }
    }
}
