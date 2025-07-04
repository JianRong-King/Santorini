package game;

import leaderboard.LeaderboardData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.net.URL;
import java.nio.file.Files;

import static leaderboard.LeaderboardUI.getLeaderboardFrame;


/**
 * This class manages the start game logic for the Santorini game, specifically handling the leaderboard functionality.
 *
 * @author King Jian Rong
 * @version 1.1
 */
public class GameManager {

    private final DefaultTableModel LEADERBOARD_TABLE_MODEL;
    private final LeaderboardData LEADERBOARD_DATA;


    /**
     * Private constructor to initialize the leaderboard GUI.
     * It sets up the table model, loads existing scores, and creates the GUI components.
     *
     * @param numberOfPlayers The total number of players in the game.
     * @param fileName The name of the file where leaderboard data is stored.
     */
    public GameManager(int numberOfPlayers, URL fileName) {
        this.LEADERBOARD_DATA = new LeaderboardData(fileName, numberOfPlayers);
        this.LEADERBOARD_TABLE_MODEL = new DefaultTableModel(new String[]{"Player ID", "Score"}, 0);

        initializeOrLoad();
        LEADERBOARD_TABLE_MODEL.setRowCount(0);
        LEADERBOARD_DATA.setInitialDataTable(LEADERBOARD_TABLE_MODEL);
    }



    /**
     * Returns the leaderboard table model.
     *
     * @return The DefaultTableModel containing the leaderboard data.
     */
    public DefaultTableModel getLeaderboardTableModel() {
        return LEADERBOARD_TABLE_MODEL;
    }


    /**
     * Updates the score for a player and refreshes the leaderboard table model.
     *
     * @param playerId The ID of the player whose score is to be updated.
     * @param scoreToAdd The score to add to the player's current score.
     */
    public void updateScore(int playerId, int scoreToAdd) {
        LEADERBOARD_DATA.updateScore(playerId, scoreToAdd);

        LEADERBOARD_TABLE_MODEL.setRowCount(0);
        LEADERBOARD_DATA.getLeaderboard().forEach((id, score) -> LEADERBOARD_TABLE_MODEL.addRow(new Object[]{id, score}));
    }


    /**
     * Initializes the leaderboard or loads existing scores from the file.
     * If the file exists, it prompts the user to refresh scores or load existing scores.
     * If the file does not exist, it initializes a new leaderboard.
     */
    public void initializeOrLoad() {
        // Check if the external file exists (not the resource inside the JAR)
        if (Files.exists(LEADERBOARD_DATA.getExternalSavePath())) {
            int refresh = JOptionPane.showConfirmDialog(
                    null,
                    "Leaderboard file found. Do you want to refresh scores?",
                    "Refresh?",
                    JOptionPane.YES_NO_OPTION
            );

            if (refresh == JOptionPane.YES_OPTION) {
                LEADERBOARD_DATA.initializeLeaderboard();
                LEADERBOARD_DATA.saveLeaderboardToFile();
                JOptionPane.showMessageDialog(null, "Leaderboard has been reset.");
            } else {
                LEADERBOARD_DATA.loadLeaderboard();
            }
        } else {
            LEADERBOARD_DATA.initializeLeaderboard();
            LEADERBOARD_DATA.saveLeaderboardToFile();
        }
    }




    /**
     * Starts the game by displaying the leaderboard frame.
     * It centers the frame on the screen and makes it visible.
     *
     * @param onStartGame A Runnable that is executed when the game starts.
     */
    public  void startGame(Runnable onStartGame) {

        JFrame leaderboardFrame = getLeaderboardFrame(onStartGame, LEADERBOARD_TABLE_MODEL);

        // Center the frame on screen
        leaderboardFrame.setLocationRelativeTo(null);
        leaderboardFrame.setVisible(true);


    }

}
