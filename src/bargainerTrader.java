package src;

public class bargainerTrader implements Trader {

    private int maxCounterOffers = 5;

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
