package src;

public class impatientTrader implements Trader {
    private int maxCounterOffers = 2;

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offeredTotal >= requestedTotal) {
            System.out.println("Impatient Trader: 'This is acceptable, let's trade!'");
            return null; // Accepts the offer
        } else if (maxCounterOffers <= 0) {
            System.out.println("Impatient Trader: 'You're wasting my time. Be gone!'");
            return null;
        } else {
            maxCounterOffers--;
            System.out.println("Impatient Trader: That's not enough. Here's my final offer.");

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
