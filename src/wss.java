// WSS.java
package src;

import javax.swing.*;
import src.gui.wssGUI;

/**
 * The main class for the Wilderness Survival Simulation (WSS).
 * This class initializes the game, sets up the GUI, prompts the user for game settings,
 * and runs the simulation loop.
 */
public class wss {
    public static void main(String[] args) {
        // Launch the animated GUI terminal
        wssGUI gui = new wssGUI();

        // Wait for the GUI to load
        try {
            Thread.sleep(1000); // Pause for 1 second to ensure the GUI is ready
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print stack trace if the thread is interrupted
        }

        // Prompt the user to select the game difficulty
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(
                null,
                "Select Difficulty:", // Message displayed to the user
                "Game Setup",         // Title of the dialog box
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,         // Options for the user to choose from
                "Easy"                // Default selection
        );

        // Prompt the user to select the brain type (AI behavior)
        String[] brains = {"Greedy", "Explorer", "Survivor"};
        String brainChoice = (String) JOptionPane.showInputDialog(
                null,
                "Select Brain Type:", // Message displayed to the user
                "Game Setup",         // Title of the dialog box
                JOptionPane.QUESTION_MESSAGE,
                null,
                brains,               // Options for the user to choose from
                "Explorer"            // Default selection
        );

        // Prompt the user to select the brain type (AI behavior)
        String[] visions = {"Focus", "Cautious", "Far-Sighted", "Keen-Eyed"};
        String visionChoice = (String) JOptionPane.showInputDialog(
                null,
                "Select Vision Type:", // Message displayed to the user
                "Game Setup",         // Title of the dialog box
                JOptionPane.QUESTION_MESSAGE,
                null,
                visions,               // Options for the user to choose from
                "Focus"            // Default selection
        );        

        // Create the map and player based on the selected difficulty
        int width = 0;
        int height = 0;
        switch (difficulty) {
            case "Medium" -> {
                width = 10;
                height = 10;
            }
            case "Hard" -> {
                width = 15;
                height = 15;
            }
            default -> { // Default to "Easy"
                width = 5;
                height = 5;
            }
        }
        Map map = new Map(width, height, difficulty); // Initialize the map
        Player player = new Player(10, 10, 10, gui);  // Create the player with default stats
        player.setPosition(new Position(0, height / 2)); // Start the player at the west edge

        // Select the brain (AI behavior) based on the user's choice
        Brain brain = switch (brainChoice) {
            case "Explorer" -> new explorerBrain(); // AI that explores the map
            case "Survivor" -> new survivorBrain(); // AI that prioritizes survival
            default -> new greedyBrain();           // Default AI that prioritizes resources
        };

        // Select Vision type (currently not implemented)
        Vision vision = switch (visionChoice) {
            case "Focus" -> new focusVision(player, map); // AI that explores the map
            case "Cautious" -> new cautiousVision(player, map); // AI that prioritizes survival
            case "Far-Sighted" -> new farsightVision(player, map);
            default -> new keeneyedVision(player, map);           // Default AI that prioritizes resources
        };

        // Start the simulation
        System.out.println("Starting Wilderness Survival Simulation...");
        System.out.println("Difficulty: " + difficulty + " | Brain: " + brainChoice + " | Vision: " + visionChoice);
        System.out.println("Goal: Reach the east edge without running out of strength, food, or water.");

        // Simulation loop
        int turn = 1;
        while (player.isAlive() && !player.hasWon(map.getWidth())) {
            System.out.println("\n--- Turn " + turn + " ---");
            brain.makeMove(player, map); // AI makes a move based on the current state
            turn++;
        }

        // Check the outcome of the simulation
        if (player.hasWon(map.getWidth())) {
            System.out.println("\nPlayer has successfully exited the wilderness!");
        } else {
            System.out.println("\nPlayer did not survive the wilderness.");
        }
    }
}
