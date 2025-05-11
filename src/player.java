package src;

import src.gui.wssGUI;

public class Player {
    private int maxStrength, maxWater, maxFood;
    private int currentStrength, currentWater, currentFood, gold;
    private Position position;
    private wssGUI gui; //Link to GUI

    public Player(int maxStrength, int maxWater, int maxFood, wssGUI gui) {
        this.maxStrength = maxStrength;
        this.maxWater = maxWater;
        this.maxFood = maxFood;
        this.currentStrength = maxStrength;
        this.currentWater = maxWater;
        this.currentFood = maxFood;
        this.gold = 0;
        this.position = new Position(0, 0);
        this.gui = gui;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean canEnter(TerrainSquare square) {
        return currentStrength >= square.getMovementCost()
            && currentFood >= square.getFoodCost()
            && currentWater >= square.getWaterCost();
    }

    public void enter(TerrainSquare square) {
        currentStrength = Math.max(0, currentStrength - square.getMovementCost());
        currentFood -= square.getFoodCost();
        currentWater -= square.getWaterCost();
        position = square.getPosition();
        square.collectItems(this);

        System.out.println("Moved to square " + position + " (" + square.getType() + ")");
        this.engageTrade(square);
    }

    public void rest() {
        System.out.println("Resting... (+2 Strength, -0.5 Food/Water)");
        currentStrength = Math.min(maxStrength, currentStrength + 2);
        currentFood = Math.max(0, currentFood - 1);
        currentWater = Math.max(0, currentWater - 1);
    }

    public void engageTrade(TerrainSquare square) {
        Trader trader = square.getTrader();
        if (trader != null) {
            System.out.println("Player encounters a trader!");
            printStatus();

            Offer playerOffer;
            Offer counterOffer;

            do {
                int offerFood;
                do {
                    offerFood = getIntInput("Enter amount of FOOD to OFFER:");
                    if (offerFood > currentFood) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                
                } while (offerFood > currentFood);

                int offerWater;
                do {
                    offerWater = getIntInput("Enter amount of WATER to OFFER:");
                    if (offerWater > currentWater) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                
                } while (offerWater > currentWater);

                int offerGold;
                do {
                    offerGold = getIntInput("Enter amount of GOLD to OFFER:");
                    if (offerGold > gold) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                
                } while (offerGold > gold);

                int requestFood = getIntInput("Enter amount of FOOD you want to RECEIVE:");
                int requestWater = getIntInput("Enter amount of WATER you want to RECEIVE:");
                int requestGold = getIntInput("Enter amount of GOLD you want to RECEIVE:");

                playerOffer = new Offer(
                    offerFood, offerWater, offerGold,
                    requestFood, requestWater, requestGold
                );

                // Print offer summary
                System.out.println("You offered: " + offerFood + " Food, " +
                                                  offerWater + " Water, " +
                                                  offerGold + " Gold");
                System.out.println("You requested: " + requestFood + " Food, " +
                                                    requestWater + " Water, " +
                                                    requestGold + " Gold");

                counterOffer = trader.evaluateOffer(playerOffer);

                if (counterOffer == null) {
                    applyTrade(playerOffer);
                    System.out.println("Trade accepted.");
                } else {
                    System.out.println("Trader counter-offered:");
                    System.out.println("Offer: " + counterOffer.getOfferedFood() + " Food, " +
                                               counterOffer.getOfferedWater() + " Water, " +
                                               counterOffer.getOfferedGold() + " Gold");
                    System.out.println("Wants: " + counterOffer.getRequestedFood() + " Food, " +
                                               counterOffer.getRequestedWater() + " Water, " +
                                               counterOffer.getRequestedGold() + " Gold");
                }

            } while (counterOffer != null);
        }
    }




    public void applyTrade(Offer offer) {
        currentFood = Math.max(0, currentFood - offer.getOfferedFood());
        currentWater = Math.max(0, currentWater - offer.getOfferedWater());
        gold = Math.max(0, gold - offer.getOfferedGold());

        currentFood = Math.min(maxFood, currentFood + offer.getRequestedFood());
        currentWater = Math.min(maxWater, currentWater + offer.getRequestedWater());
        gold += offer.getRequestedGold();
    }

    public void addFood(int amount) {
        currentFood = Math.min(maxFood, currentFood + amount);
    }

    public void addWater(int amount) {
        currentWater = Math.min(maxWater, currentWater + amount);
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public boolean isAlive() {
        return currentFood > 0 && currentWater > 0;
    }

    public boolean hasWon(int mapWidth) {
        return position.getX() == mapWidth - 1;
    }

    public void printStatus() {
        System.out.println("Position: " + position +
                           " | Strength: " + currentStrength +
                           " | Food: " + currentFood +
                           " | Water: " + currentWater +
                           " | Gold: " + gold);
    }

    public int getFoodAmount() {
        return currentFood;
    }

    public int getWaterAmount() {
        return currentWater;
    }

    public int getStrengthAmount() {
        return currentStrength;
    }

    public void printFood() {
        System.out.println("Current Food: " + currentFood);
    }

    public void printWater() {
        System.out.println("Current Water: " + currentWater);
    }

    public void printGold() {
        System.out.println("Current Gold: " + gold);
    }

    public void printStrength() {
        System.out.println("Current Strength: " + currentStrength);
    }

    //Get input from GUI
    private int getIntInput(String prompt) {
        System.out.println(prompt);

        final Object lock = new Object();
        final String[] inputHolder = new String[1];

        gui.setTradeSubmitListener(() -> {
            synchronized (lock) {
                inputHolder[0] = gui.getTradeInput();
                gui.clearTradeInput();
                lock.notify();
            }
        });

        synchronized (lock) {
            try {
                lock.wait(); // Wait for submit
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }

        try {
            return Integer.parseInt(inputHolder[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Defaulting to 0.");
            return 0;
        }
    }
}
