package utility;


import javax.swing.*;


/**
 * Utility class for multiplayer-related functionalities.
 * This class provides methods to handle multiplayer game settings.
 *
 * @author King Jian Rong
 * @version 1.0
 */
public final class MultiplayerUtils {

    /**
     * Constructor for the MultiplayerUtils class.
     * This constructor is private to prevent instantiation,
     */
    private MultiplayerUtils() {
        throw new UnsupportedOperationException("Utility class");
    }



    /**
     * Asks the user for the number of players in the game.
     * Validates that the number is between 2 and 8.
     *
     * @return The number of players, or -1 if the user cancels the input.
     */
    public static int askPlayerNumber(int minPlayer, int maxPlayer) {
        int numPlayers = 0;
        while (numPlayers < minPlayer || numPlayers > maxPlayer) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter number of players between " + minPlayer + " and " + maxPlayer + ":",
                    "Player Count",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                JOptionPane.showMessageDialog(null, "Game canceled.");
                return -1; // Exit if user cancels
            }

            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers < minPlayer || numPlayers > maxPlayer) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a number between " + minPlayer + " and " + maxPlayer + ".");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        }

        return numPlayers;
    }



}
