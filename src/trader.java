// Trader.java and BargainerTrader.java
package src;

public interface Trader {
    /**
     * Evaluates a trade offer and returns a counter-offer if declined,
     * or null if the trade is accepted.
     */
    Offer evaluateOffer(Offer playerOffer);
}