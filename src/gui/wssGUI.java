package src.gui;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

public class wssGUI {

    JFrame mainFrame;
    JTextArea consoleOutput;
    JTextArea tradeInputArea;
    PrintStream originalOut;
    Queue<String> messageQueue = new LinkedList<>();
    boolean isPrinting = false;
    private Runnable tradeSubmitListener;

    @SuppressWarnings("serial")
    public wssGUI() {
        // Frame setup
        mainFrame = new JFrame("Wilderness Survival System");
        mainFrame.setSize(900, 510);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(new ImageIcon("WSS_IconImage.png").getImage());

        // Panel to stack output and input vertically
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.Y_AXIS));

        // Console output area
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setBackground(Color.BLACK);
        consoleOutput.setForeground(Color.GREEN);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consoleOutput.setBorder(new EmptyBorder(20, 20, 20, 20));
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);

        // Hide blinking caret in console
        consoleOutput.setCaret(new DefaultCaret() {
            {
                setUpdatePolicy(NEVER_UPDATE);
            }

            @Override public void setVisible(boolean b) {}
            @Override public void setSelectionVisible(boolean b) {}
        });

        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(new EmptyBorder(20, 20, 10, 20));
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        consolePanel.add(scrollPane);

        // Trade input area
        tradeInputArea = new JTextArea(1, 50);
        tradeInputArea.setLineWrap(true);
        tradeInputArea.setWrapStyleWord(true);
        tradeInputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        tradeInputArea.setBackground(Color.BLACK);
        tradeInputArea.setForeground(Color.GREEN);
        tradeInputArea.setCaretColor(Color.GREEN);

        // Limit to 2-digit numeric input
        ((AbstractDocument) tradeInputArea.getDocument()).setDocumentFilter(new NumericLimitFilter(2));

        tradeInputArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GREEN),
            "Trade Input",
            0, 0,
            new Font("Monospaced", Font.BOLD, 12),
            Color.GREEN
        ));

        // Submit trade on Enter key
        tradeInputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    e.consume(); // prevent newline
                    triggerTradeSubmit();
                }
            }
        });

        JScrollPane inputScrollPane = new JScrollPane(tradeInputArea);
        inputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        inputScrollPane.setBorder(new EmptyBorder(0, 20, 10, 20));
        inputScrollPane.setBackground(Color.BLACK);
        inputScrollPane.getViewport().setBackground(Color.BLACK);
        inputScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // limit height

        consolePanel.add(inputScrollPane);
        mainFrame.add(consolePanel);

        // Redirect System.out to console
        redirectSystemOutToTextArea(consoleOutput);

        // Show window
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Startup message
        System.out.println("Wilderness Survival System Initialized...");
        System.out.println("Terminal interface online.");
        System.out.println("Loading modules: Weather Scanner, Terrain Mapper, Inventory Tracker...");
        System.out.println("Systems online. Welcome, Operator.");
    }

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

    private void printNextMessage() {
        synchronized (messageQueue) {
            String message = messageQueue.poll();
            if (message == null) {
                isPrinting = false;
                return;
            }
            isPrinting = true;

            Timer timer = new Timer(20, null);
            final int[] index = {0};
            timer.addActionListener(e -> {
                if (index[0] < message.length()) {
                    char ch = message.charAt(index[0]++);
                    consoleOutput.append(String.valueOf(ch));
                    consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
                } else {
                    timer.stop();
                    printNextMessage();
                }
            });
            timer.start();
        }
    }

    public String getTradeInput() {
        return tradeInputArea.getText().trim();
    }

    public void clearTradeInput() {
        tradeInputArea.setText("");
    }

    public void setTradeSubmitListener(Runnable listener) {
        this.tradeSubmitListener = listener;
    }

    public void triggerTradeSubmit() {
        if (tradeSubmitListener != null) {
            tradeSubmitListener.run();
        }
    }

    // Numeric input limiter
    private static class NumericLimitFilter extends DocumentFilter {
        private final int maxLength;

        public NumericLimitFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                if ((fb.getDocument().getLength() - length + string.length()) <= maxLength) {
                    super.replace(fb, offset, length, string, attr);
                }
            } else if (string.isEmpty()) {
                super.replace(fb, offset, length, string, attr); // allow backspace/delete
            }
        }
    }
}
