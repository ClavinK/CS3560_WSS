package src;

import src.gui.wssGUI;

/**
 * The Player class represents the player in the Wilderness Survival Simulation.
 * It tracks the player's resources (strength, food, water, gold), position, and interactions
 * with the game world, such as moving, resting, and trading.
 */
public class Player {
    private int maxStrength, maxWater, maxFood; // Maximum resource capacities
    private int currentStrength, currentWater, currentFood, gold; // Current resource levels
    private Position position; // Player's current position on the map
    private wssGUI gui; // Link to the GUI for user input

    /**
     * Constructs a Player object with the specified maximum resources and GUI link.
     *
     * @param maxStrength The maximum strength the player can have.
     * @param maxWater The maximum water the player can have.
     * @param maxFood The maximum food the player can have.
     * @param gui The GUI object for user interaction.
     */
    public Player(int maxStrength, int maxWater, int maxFood, wssGUI gui) {
        this.maxStrength = maxStrength;
        this.maxWater = maxWater;
        this.maxFood = maxFood;
        this.currentStrength = maxStrength;
        this.currentWater = maxWater;
        this.currentFood = maxFood;
        this.gold = 0;
        this.position = new Position(0, 0); // Default starting position
        this.gui = gui;
    }

    /**
     * Gets the player's current position.
     *
     * @return The player's position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the player's position.
     *
     * @param position The new position for the player.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Checks if the player can enter a given TerrainSquare based on resource costs.
     *
     * @param square The TerrainSquare to check.
     * @return True if the player can enter the square, false otherwise.
     */
    public boolean canEnter(TerrainSquare square) {
        return currentStrength >= square.getMovementCost()
            && currentFood >= square.getFoodCost()
            && currentWater >= square.getWaterCost();
    }

    /**
     * Moves the player to a given TerrainSquare, deducting resource costs and collecting items.
     *
     * @param square The TerrainSquare to enter.
     */
    public void enter(TerrainSquare square) {
        currentStrength = Math.max(0, currentStrength - square.getMovementCost());
        currentFood -= square.getFoodCost();
        currentWater -= square.getWaterCost();
        position = square.getPosition();
        square.collectItems(this);

        System.out.println("Moved to square " + position + " (" + square.getType() + ")");
        this.engageTrade(square); // Engage in trade if a trader is present
    }

    /**
     * Allows the player to rest, recovering strength but consuming food and water.
     */
    public void rest() {
        System.out.println("Resting... (+2 Strength, -1 Food/Water)");
        currentStrength = Math.min(maxStrength, currentStrength + 2);
        currentFood = Math.max(0, currentFood - 1);
        currentWater = Math.max(0, currentWater - 1);
    }

    /**
     * Engages the player in a trade with a trader on the current TerrainSquare.
     *
     * @param square The TerrainSquare containing the trader.
     */
    public void engageTrade(TerrainSquare square) {
        Trader trader = square.getTrader();
        if (trader != null) {
            System.out.println("Player encounters a trader!");
            printStatus();

            Offer playerOffer;
            Offer counterOffer;

            do {
                // Get the player's offer for food
                int offerFood;
                do {
                    offerFood = getIntInput("Enter amount of FOOD to OFFER:");
                    if (offerFood > currentFood) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                } while (offerFood > currentFood);

                // Get the player's offer for water
                int offerWater;
                do {
                    offerWater = getIntInput("Enter amount of WATER to OFFER:");
                    if (offerWater > currentWater) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                } while (offerWater > currentWater);

                // Get the player's offer for gold
                int offerGold;
                do {
                    offerGold = getIntInput("Enter amount of GOLD to OFFER:");
                    if (offerGold > gold) {
                        System.out.println("Choose a value that is within your budget!");
                    }
                } while (offerGold > gold);

                // Get the player's request for resources
                int requestFood = getIntInput("Enter amount of FOOD you want to RECEIVE:");
                int requestWater = getIntInput("Enter amount of WATER you want to RECEIVE:");
                int requestGold = getIntInput("Enter amount of GOLD you want to RECEIVE:");

                // Create the player's offer
                playerOffer = new Offer(
                    offerFood, offerWater, offerGold,
                    requestFood, requestWater, requestGold
                );

                // Print the offer summary
                System.out.println("You offered: " + offerFood + " Food, " +
                                                  offerWater + " Water, " +
                                                  offerGold + " Gold");
                System.out.println("You requested: " + requestFood + " Food, " +
                                                    requestWater + " Water, " +
                                                    requestGold + " Gold");

                // Evaluate the offer with the trader
                counterOffer = trader.evaluateOffer(playerOffer);

                if (counterOffer == null) {
                    applyTrade(playerOffer); // Apply the trade if accepted
                    System.out.println("Trade accepted.");
                } else {
                    // Print the trader's counter-offer
                    System.out.println("Trader counter-offered:");
                    System.out.println("Offer: " + counterOffer.getOfferedFood() + " Food, " +
                                               counterOffer.getOfferedWater() + " Water, " +
                                               counterOffer.getOfferedGold() + " Gold");
                    System.out.println("Wants: " + counterOffer.getRequestedFood() + " Food, " +
                                               counterOffer.getRequestedWater() + " Water, " +
                                               counterOffer.getRequestedGold() + " Gold");
                }

            } while (counterOffer != null); // Continue until the trade is accepted or rejected
        }
    }

    /**
     * Applies the results of a trade to the player's resources.
     *
     * @param offer The trade offer to apply.
     */
    public void applyTrade(Offer offer) {
        currentFood = Math.max(0, currentFood - offer.getOfferedFood());
        currentWater = Math.max(0, currentWater - offer.getOfferedWater());
        gold = Math.max(0, gold - offer.getOfferedGold());

        currentFood = Math.min(maxFood, currentFood + offer.getRequestedFood());
        currentWater = Math.min(maxWater, currentWater + offer.getRequestedWater());
        gold += offer.getRequestedGold();
    }

    /**
     * Adds food to the player's inventory, up to the maximum capacity.
     *
     * @param amount The amount of food to add.
     */
    public void addFood(int amount) {
        currentFood = Math.min(maxFood, currentFood + amount);
    }

    /**
     * Adds water to the player's inventory, up to the maximum capacity.
     *
     * @param amount The amount of water to add.
     */
    public void addWater(int amount) {
        currentWater = Math.min(maxWater, currentWater + amount);
    }

    /**
     * Adds gold to the player's inventory.
     *
     * @param amount The amount of gold to add.
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Checks if the player is still alive (has food and water).
     *
     * @return True if the player is alive, false otherwise.
     */
    public boolean isAlive() {
        return currentFood > 0 && currentWater > 0;
    }

    /**
     * Checks if the player has won by reaching the east edge of the map.
     *
     * @param mapWidth The width of the map.
     * @return True if the player has won, false otherwise.
     */
    public boolean hasWon(int mapWidth) {
        return position.getX() == mapWidth - 1;
    }

    /**
     * Prints the player's current status, including position and resources.
     */
    public void printStatus() {
        System.out.println("Position: " + position +
                           " | Strength: " + currentStrength +
                           " | Food: " + currentFood +
                           " | Water: " + currentWater +
                           " | Gold: " + gold);
    }

    /**
     * Gets the player's current food amount.
     *
     * @return The current food amount.
     */
    public int getFoodAmount() {
        return currentFood;
    }

    /**
     * Gets the player's current water amount.
     *
     * @return The current water amount.
     */
    public int getWaterAmount() {
        return currentWater;
    }

    /**
     * Gets the player's current strength amount.
     *
     * @return The current strength amount.
     */
    public int getStrengthAmount() {
        return currentStrength;
    }

    /**
     * Prints the player's current food amount.
     */
    public void printFood() {
        System.out.println("Current Food: " + currentFood);
    }

    /**
     * Prints the player's current water amount.
     */
    public void printWater() {
        System.out.println("Current Water: " + currentWater);
    }

    /**
     * Prints the player's current gold amount.
     */
    public void printGold() {
        System.out.println("Current Gold: " + gold);
    }

    /**
     * Prints the player's current strength amount.
     */
    public void printStrength() {
        System.out.println("Current Strength: " + currentStrength);
    }

    /**
     * Gets an integer input from the GUI.
     *
     * @param prompt The prompt to display to the user.
     * @return The integer input from the user.
     */
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