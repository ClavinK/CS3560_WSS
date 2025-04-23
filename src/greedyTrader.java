// GreedyTrader.java
package src;

public class greedyTrader implements Trader {

    private int maxCounterOffers = 4;

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        // Adjusted logic: accept if the player offers at least 1.5x the request
        if (offeredTotal >= requestedTotal * 1.5) {
            System.out.println("Greedy Trader: Fine, I accept your generous offer.");
            return null;
        } else if (maxCounterOffers <= 0) {
            System.out.println("Greedy Trader: 'You cheap scum, get outta here!'");
            return null;
        }
        else {
            System.out.println("Greedy Trader: That's a joke. Here's my counter-offer.");
            maxCounterOffers--;
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
