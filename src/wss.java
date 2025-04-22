// WSS.java
package src;

import src.gui.wssGUI;

public class wss {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new wssGUI());

        // Allow GUI to initialize before output
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Game setup
        map map = new map(10, 5, "Medium");
        Player player = new Player(10, 10, 10);
        brain brain = new greedyBrain();
        player.setPosition(new Position(0, 2));

        System.out.println("Starting simulation...");

        int turn = 1;
        while (player.isAlive() && !player.hasWon(map.getWidth())) {
            System.out.println("\n--- Turn " + turn + " ---");
            brain.makeMove(player, map);
            turn++;
        }

        if (player.hasWon(map.getWidth())) {
            System.out.println("\n🎉 Player has successfully exited the wilderness! 🎉");
        } else {
            System.out.println("\n💀 Player did not survive the wilderness. 💀");
        }
    }
}