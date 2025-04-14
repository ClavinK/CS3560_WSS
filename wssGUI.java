import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class wssGUI {

    JFrame mainFrame;
    JTextArea consoleOutput;
    PrintStream originalOut;
    Queue<String> messageQueue = new LinkedList<>();
    boolean isPrinting = false;

    public wssGUI() {
        // Frame setup
        mainFrame = new JFrame("Wilderness Survival System");
        mainFrame.setSize(900, 510);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(new ImageIcon("WSS_IconImage.png").getImage());
        


        // Console output area
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setBackground(Color.BLACK);
        consoleOutput.setForeground(Color.GREEN);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consoleOutput.setBorder(new EmptyBorder(20, 20, 20, 20));
        consoleOutput.setLineWrap(true); //wrap long lines
        consoleOutput.setWrapStyleWord(true); //wrap at word boundaries

        // Hide the blinking cursor
        consoleOutput.setCaret(new DefaultCaret() {
            {
                setUpdatePolicy(NEVER_UPDATE);
            }

            @Override
            public void setVisible(boolean b) {}
            @Override
            public void setSelectionVisible(boolean b) {}
        });
        
        //This scrollpane is for any text printed out
        //Text will be printed character by character and line by line
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        scrollPane.setBackground(Color.BLACK); // match terminal style
        scrollPane.getViewport().setBackground(Color.BLACK); // very important
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainFrame.add(scrollPane);

        // Redirect System.out to JTextArea
        redirectSystemOutToTextArea(consoleOutput);

        // Show frame
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Test prints
        System.out.println("Wilderness Survival System Initialized...");
        System.out.println("Terminal interface online.");
        //test for word wrap
        System.out.println("Loading modules: Weather Scanner, Terrain Mapper, Inventory Tracker...Loading modules: Weather Scanner, Terrain Mapper, Inventory Tracker...");
        System.out.println("Systems online. Welcome, Operator.");
  
    }

    // Redirect System.out to animated JTextArea writer
    private void redirectSystemOutToTextArea(JTextArea textArea) {
        originalOut = System.out;

        OutputStream typingStream = new OutputStream() {
            private final StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                char c = (char) b;
                if (c == '\n') {
                    synchronized (messageQueue) {
                        buffer.append('\n');
                        messageQueue.add(buffer.toString());
                        buffer.setLength(0);
                        if (!isPrinting) {
                            printNextMessage();
                        }
                    }
                } else {
                    buffer.append(c);
                }
            }

            @Override
            public void flush() {
                synchronized (messageQueue) {
                    if (buffer.length() > 0) {
                        messageQueue.add(buffer.toString());
                        buffer.setLength(0);
                        if (!isPrinting) {
                            printNextMessage();
                        }
                    }
                }
            }
        };

        System.setOut(new PrintStream(typingStream, true));
    }

    // Animate messages one at a time
    private void printNextMessage() {
        synchronized (messageQueue) {
            String message = messageQueue.poll();
            if (message == null) {
                isPrinting = false;
                return;
            }
            isPrinting = true;

            Timer timer = new Timer(15, null); // 15ms delay between chars
            final int[] index = {0};
            timer.addActionListener(e -> {
                if (index[0] < message.length()) {
                    char ch = message.charAt(index[0]++);
                    consoleOutput.append(String.valueOf(ch));
                    consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
                } else {
                    timer.stop();
                    printNextMessage(); // move to next message
                }
            });
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new wssGUI());
    }
}
