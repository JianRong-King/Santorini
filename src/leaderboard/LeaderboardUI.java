package leaderboard;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * This class handles all the UI components for the leaderboard in the Santorini game.
 * It creates button / JPanel / JFrame that allows players to view their scores and start a new game.
 *
 * @author King Jian Rong
 * @version 1.1
 */
public class LeaderboardUI {
    private static int frameHeight;
    private static int frameWidth;
    private static int optionalSpacing;


    /**
     * Set the height of the frame.
     *
     * @param newFrameHeight The new width for the location button.
     */
    public static void setFrameHeight(int newFrameHeight) {
        frameHeight = newFrameHeight;
    }

    /**
     * Set the width of the frame.
     *
     * @param newFrameWidth The new width for the location button.
     */
    public static void setFrameWidth(int newFrameWidth) {
        frameWidth = newFrameWidth;
    }

    /**
     * Set the Spacing height of the frame.
     *
     * @param newOptionalSpacing The new width for the location button.
     */
    public static void setOptionalSpacing(int newOptionalSpacing) {
        optionalSpacing = newOptionalSpacing;
    }


    /**
     * Create a JFrame for the leaderboard.
     *
     * @return A new JFrame instance for the leaderboard.
     */
    public static JFrame createLeaderboardFrame(){

        JFrame frame = new JFrame("Santorini Leaderboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);

        return frame;
    }


    /**
     * Create a JPanel containing the leaderboard table.
     *
     * @param table The JTable to be displayed in the panel.
     * @return A JPanel containing the leaderboard table.
     */
    public static JPanel createLeaderboardPanel(JTable table) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Leaderboard:"));
        panel.add(new JScrollPane(table));
        panel.add(Box.createVerticalStrut(optionalSpacing)); // Optional spacing

        return panel;
    }

    /**
     * Create a JButton to start the game.
     *
     * @param frame The JFrame to be disposed when the button is clicked.
     * @param onStartGame Runnable to execute when the button is clicked.
     * @return A JButton that starts the game when clicked.
     */
    public static JButton createStartGameButton(JFrame frame, Runnable onStartGame) {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        startGameButton.addActionListener(e -> {
            frame.dispose();
            onStartGame.run();
        });
        return startGameButton;
    }



    /**
     * Get a JFrame for the leaderboard with a start game button.
     *
     * @param onStartGame Runnable to execute when the "Start Game" button is clicked.
     * @param leaderboardTableModel The DefaultTableModel containing the leaderboard data.
     * @return A JFrame containing the leaderboard and a start game button.
     */
    public static JFrame getLeaderboardFrame(Runnable onStartGame, DefaultTableModel leaderboardTableModel) {

        JFrame frame = createLeaderboardFrame();
        JPanel leaderboardPanel = createLeaderboardPanel(new JTable(leaderboardTableModel));
        JButton startGameButton = createStartGameButton(frame, onStartGame);


        leaderboardPanel.add(Box.createVerticalStrut(10));
        leaderboardPanel.add(startGameButton);

        frame.add(leaderboardPanel);

        // Center the frame on screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        return frame;
    }


    /**
     * Show the leaderboard in a new JFrame.
     *
     * @param leaderboardTableModel The GameManager instance containing the leaderboard data.
     * @return A JFrame displaying the leaderboard.
     */
    public static JFrame showLeaderBoard(DefaultTableModel leaderboardTableModel) {
        // Get the leaderboard GUI manager and panel

        JTable table = new JTable(leaderboardTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Leaderboard:"));
        panel.add(scrollPane);


        // Show leaderboard in a new JFrame
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(300, 250);
        leaderboardFrame.add(panel);
        leaderboardFrame.setLocationRelativeTo(null); // Center on screen
        leaderboardFrame.setVisible(true);

        // Add a WindowListener to exit after leaderboard is closed
        return leaderboardFrame;
    }


    /**
     * Get a JButton to show the leaderboard.
     *
     * @param leaderboardTableModel The GameManager instance containing the leaderboard data.
     * @return A JButton that shows the leaderboard when clicked.
     */
    public static JButton getLeaderboardButton(DefaultTableModel leaderboardTableModel) {

        // leaderboard button
        JButton SHOW_LEADERBOARD_BUTTON = new JButton("Show Leaderboard");


        SHOW_LEADERBOARD_BUTTON.addActionListener(e -> {

            JTable table = new JTable(leaderboardTableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Leaderboard:"));
            panel.add(scrollPane);

            JFrame leaderboardFrame = new JFrame("Leaderboard");
            leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderboardFrame.setSize(300, 250);
            leaderboardFrame.add(panel);
            leaderboardFrame.setLocationRelativeTo(null);
            leaderboardFrame.setVisible(true);
        });
        return SHOW_LEADERBOARD_BUTTON;
    }


}
