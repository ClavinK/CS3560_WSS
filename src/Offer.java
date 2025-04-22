package src;

/**
 * Represents a trade offer made by the player or a trader.
 */
public class Offer {
    private int offeredFood;
    private int offeredWater;
    private int offeredGold;
    private int requestedFood;
    private int requestedWater;
    private int requestedGold;

    public Offer(int offeredFood, int offeredWater, int offeredGold,
        int requestedFood, int requestedWater, int requestedGold) {
        this.offeredFood = offeredFood;
        this.offeredWater = offeredWater;
        this.offeredGold = offeredGold;
        this.requestedFood = requestedFood;
        this.requestedWater = requestedWater;
        this.requestedGold = requestedGold;
    }

    public int getOfferedFood() { return offeredFood; }
    public int getOfferedWater() { return offeredWater; }
    public int getOfferedGold() { return offeredGold; }

    public int getRequestedFood() { return requestedFood; }
    public int getRequestedWater() { return requestedWater; }
    public int getRequestedGold() { return requestedGold; }

    public int getTotalOffered() {
        return offeredFood + offeredWater + offeredGold;
    }

    public int getTotalRequested() {
        return requestedFood + requestedWater + requestedGold;
    }

    @Override
    public String toString() {
        return "Offer: [Give → " +
                offeredFood + " food, " +
                offeredWater + " water, " +
                offeredGold + " gold | Receive → " +
                requestedFood + " food, " +
                requestedWater + " water, " +
                requestedGold + " gold]";
    }
}
