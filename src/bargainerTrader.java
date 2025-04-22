package src;

import src.Offer;
import src.trader;

public class bargainerTrader implements Trader {

    @Override
    public Offer evaluateOffer(Offer playerOffer) {
        int offeredTotal = playerOffer.getOfferedFood() + playerOffer.getOfferedWater() + playerOffer.getOfferedGold();
        int requestedTotal = playerOffer.getRequestedFood() + playerOffer.getRequestedWater() + playerOffer.getRequestedGold();

        if (offeredTotal >= requestedTotal) {
            System.out.println("Trader accepts your offer.");
            return null;
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
