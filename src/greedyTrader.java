// GreedyTrader.java
package src;

public class greedyTrader implements trader {

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offered = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requested = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        // Adjusted logic: accept if the player offers at least 1.5x the request
        if (offered >= requested * 1.5) {
            System.out.println("GreedyTrader: Fine, I accept your generous offer.");
            return null;
        } else {
            System.out.println("GreedyTrader: That's a joke. Here's my counter-offer.");
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
