import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class TypingTestApp extends JFrame {
    private JTextArea sampleTextArea;
    private JTextArea typingArea;
    private JLabel timerLabel, wpmLabel, accuracyLabel;
    private JButton startButton, resetButton, changeSampleButton;

    private String[] samples = {
        "The quick brown fox jumps over the lazy dog.",
        "Java programming is fun and versatile.",
        "Practice makes perfect in typing tests.",
        "OpenAI's ChatGPT is an AI language model."
    };
    private int sampleIndex = 0;

    private Timer timer;
    private int timeLeft;  // in seconds
    private boolean testRunning = false;
    private long startTime, endTime;

    public TypingTestApp() {
        setTitle("Typing Test Application");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout(10,10));

        // Sample Text Panel
        JPanel samplePanel = new JPanel(new BorderLayout());
        samplePanel.setBorder(BorderFactory.createTitledBorder("Sample Text"));
        sampleTextArea = new JTextArea(samples[sampleIndex], 4, 50);
        sampleTextArea.setLineWrap(true);
        sampleTextArea.setWrapStyleWord(true);
        sampleTextArea.setEditable(false);
        sampleTextArea.setFont(new Font("Serif", Font.PLAIN, 16));
        samplePanel.add(new JScrollPane(sampleTextArea), BorderLayout.CENTER);

        // Typing Area Panel
        JPanel typingPanel = new JPanel(new BorderLayout());
        typingPanel.setBorder(BorderFactory.createTitledBorder("Start Typing Here"));
        typingArea = new JTextArea(6, 50);
        typingArea.setLineWrap(true);
        typingArea.setWrapStyleWord(true);
        typingArea.setFont(new Font("Serif", Font.PLAIN, 16));
        typingArea.setEnabled(false);
        typingPanel.add(new JScrollPane(typingArea), BorderLayout.CENTER);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        timerLabel = new JLabel("Time: 60", SwingConstants.CENTER);
        wpmLabel = new JLabel("WPM: 0", SwingConstants.CENTER);
        accuracyLabel = new JLabel("Accuracy: 0%", SwingConstants.CENTER);
        infoPanel.add(timerLabel);
        infoPanel.add(wpmLabel);
        infoPanel.add(accuracyLabel);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        startButton = new JButton("Start Test");
        resetButton = new JButton("Reset");
        changeSampleButton = new JButton("Change Sample");

        resetButton.setEnabled(false);
        buttonsPanel.add(startButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(changeSampleButton);

        // Add panels to frame
        add(samplePanel, BorderLayout.NORTH);
        add(typingPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        add(buttonsPanel, BorderLayout.PAGE_END);

        // Button listeners
        startButton.addActionListener(e -> startTest());
        resetButton.addActionListener(e -> resetTest());
        changeSampleButton.addActionListener(e -> changeSample());

        // Typing listener
        typingArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (testRunning) {
                    updateMetrics();
                }
            }
        });
    }

    private void startTest() {
        if (testRunning) return;

        typingArea.setEnabled(true);
        typingArea.setText("");
        typingArea.requestFocus();

        timeLeft = 60;  // 60 seconds for test
        timerLabel.setText("Time: " + timeLeft);
        wpmLabel.setText("WPM: 0");
        accuracyLabel.setText("Accuracy: 0%");

        testRunning = true;
        startTime = System.currentTimeMillis();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                SwingUtilities.invokeLater(() -> timerLabel.setText("Time: " + timeLeft));
                if (timeLeft <= 0) {
                    endTest();
                }
            }
        }, 1000, 1000);

        startButton.setEnabled(false);
        resetButton.setEnabled(true);
        changeSampleButton.setEnabled(false);
    }

    private void resetTest() {
        if (timer != null) {
            timer.cancel();
        }
        typingArea.setEnabled(false);
        typingArea.setText("");
        timerLabel.setText("Time: 60");
        wpmLabel.setText("WPM: 0");
        accuracyLabel.setText("Accuracy: 0%");
        testRunning = false;

        startButton.setEnabled(true);
        resetButton.setEnabled(false);
        changeSampleButton.setEnabled(true);
    }

    private void endTest() {
        timer.cancel();
        testRunning = false;
        endTime = System.currentTimeMillis();

        typingArea.setEnabled(false);

        updateMetrics();

        JOptionPane.showMessageDialog(this, "Time's up!\nWPM: " + wpmLabel.getText().substring(5) + "\nAccuracy: " + accuracyLabel.getText().substring(9) + "%",
                "Test Completed", JOptionPane.INFORMATION_MESSAGE);

        startButton.setEnabled(true);
        resetButton.setEnabled(false);
        changeSampleButton.setEnabled(true);
    }

    private void changeSample() {
        sampleIndex = (sampleIndex + 1) % samples.length;
        sampleTextArea.setText(samples[sampleIndex]);
        resetTest();
    }

    private void updateMetrics() {
        String sample = sampleTextArea.getText();
        String typed = typingArea.getText();

        int correctChars = 0;
        int minLength = Math.min(sample.length(), typed.length());

        for (int i = 0; i < minLength; i++) {
            if (sample.charAt(i) == typed.charAt(i)) correctChars++;
        }

        double accuracy = typed.length() == 0 ? 0 : (correctChars * 100.0) / typed.length();

        // Calculate WPM: (chars / 5) / minutes
        long elapsedMillis = System.currentTimeMillis() - startTime;
        double minutes = elapsedMillis / 60000.0;
        int wpm = minutes > 0 ? (int) ((typed.length() / 5) / minutes) : 0;

        wpmLabel.setText("WPM: " + wpm);
        accuracyLabel.setText(String.format("Accuracy: %.2f%%", accuracy));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TypingTestApp app = new TypingTestApp();
            app.setVisible(true);
        });
    }
}
