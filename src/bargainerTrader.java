package src;

public class bargainerTrader implements Trader {

    private int maxCounterOffers = 6;

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offeredTotal >= requestedTotal) {
            System.out.println("Bargainer Trader: 'I accept your offer.'");
            return null; // Accepts the trade
        } else if (maxCounterOffers <= 0) {
            System.out.println("Bargainer Trader: 'Aw bugger off! I don't want to see your face again!'");
            return null;
        } else {
            System.out.println("Bargainer Trader: 'How about this deal instead?'");
            maxCounterOffers--;
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
