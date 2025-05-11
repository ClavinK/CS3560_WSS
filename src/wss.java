// WSS.java
package src;

import javax.swing.*;
import src.gui.wssGUI;

public class wss {
    public static void main(String[] args) {
        // Launch the animated GUI terminal
    	wssGUI gui = new wssGUI();

        // Wait for GUI to load
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Prompt for difficulty
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(
                null,
                "Select Difficulty:",
                "Game Setup",
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,
                "Easy"
        );

        // Prompt for brain type
        String[] brains = {"Greedy", "Explorer", "Survivor"};
        String brainChoice = (String) JOptionPane.showInputDialog(
                null,
                "Select Brain Type:",
                "Game Setup",
                JOptionPane.QUESTION_MESSAGE,
                null,
                brains,
                "Explorer"
        );
        
        
        // Create map and player
        int width = 0;
        int height = 0;
        switch(difficulty) {
            case "Medium" -> {
                width = 10;
                height = 10;
            }
            case "Hard" -> {
                width = 15;
                height = 15;
            }
            default -> {
                width = 5;
                height = 5;
            }
        }
        Map map = new Map(width, height, difficulty);
        Player player = new Player(10, 10, 10, gui);
        player.setPosition(new Position(0, height / 2));

        // Select brain
    Brain brain = switch (brainChoice) {
            case "Explorer" -> new explorerBrain();
            case "Survivor" -> new survivorBrain();
            default -> new greedyBrain();
        };

        // Select Vision type

        // Start simulation
        System.out.println("Starting Wilderness Survival Simulation...");
        System.out.println("Difficulty: " + difficulty + " | Brain: " + brainChoice);
        System.out.println("Goal: Reach the east edge without running out of strength, food, or water.");

        int turn = 1;
        while (player.isAlive() && !player.hasWon(map.getWidth())) {
            System.out.println("\n--- Turn " + turn + " ---");
            brain.makeMove(player, map);
            turn++;
        }

        if (player.hasWon(map.getWidth())) {
            System.out.println("\nPlayer has successfully exited the wilderness!");
        } else {
            System.out.println("\nPlayer did not survive the wilderness.");
        }
    }
}
