package src;

public class impatientTrader implements trader {
    private int counterCount = 0;
    private static final int MAX_COUNTERS = 2;

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offered = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requested = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offered >= requested) {
            System.out.println("ImpatientTrader: Okay, I'll accept that.");
            return null; // Accepts the offer
        }

        if (counterCount >= MAX_COUNTERS) {
            System.out.println("ImpatientTrader: Enough! I'm done talking to you.");
            return new Offer(0, 0, 0, 0, 0, 0); // Special signal for rejection
        }

        counterCount++;
        System.out.println("ImpatientTrader: That's not enough. Here's my final offer (attempt " + counterCount + ").");

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
