package leaderboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the reading and writing of leaderboard data.
 * Reads initial data from resources bundled in the JAR,
 * saves and updates leaderboard to an external file for persistence.
 *
 * @author King Jian Rong
 * @version 1.2
 */
public class LeaderboardData {
    private final URL RESOURCE_FILE_URL;
    private final Path EXTERNAL_SAVE_PATH;
    private final Map<Integer, Integer> LEADERBOARD;
    private final int NUMBER_OF_PLAYERS;

    /**
     * Constructor for LeaderboardData.
     * @param resourceFileUrl URL of the leaderboard resource inside the JAR.
     * @param numberOfPlayers total number of players in the game.
     */
    public LeaderboardData(URL resourceFileUrl, int numberOfPlayers) {
        this.RESOURCE_FILE_URL = resourceFileUrl;
        this.NUMBER_OF_PLAYERS = numberOfPlayers;
        this.LEADERBOARD = new HashMap<>();
        // Define external save path in user home directory
        this.EXTERNAL_SAVE_PATH = Paths.get(System.getProperty("user.home"), "SantoriniLeaderboard.txt");
    }


    /**
     * Initializes leaderboard with zero scores.
     */
    public void initializeLeaderboard() {
        LEADERBOARD.clear();
        for (int i = 1; i <= NUMBER_OF_PLAYERS; i++) {
            LEADERBOARD.put(i, 0);
        }
    }

    /**
     * Loads the leaderboard data.
     * Tries to load from external file if exists, else loads from bundled resource.
     */
    public void loadLeaderboard() {
        LEADERBOARD.clear();
        InputStream input;

        try {
            if (Files.exists(EXTERNAL_SAVE_PATH)) {
                input = Files.newInputStream(EXTERNAL_SAVE_PATH);
            } else {
                input = RESOURCE_FILE_URL.openStream(); // just for initial loading of leaderboard score from resource file
            }

            if (input == null) {
                JOptionPane.showMessageDialog(null, "Leaderboard resource not found.");
                initializeLeaderboard();
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split(" ");
                    if (parts.length == 2) {
                        int playerId = Integer.parseInt(parts[0]);
                        int score = Integer.parseInt(parts[1]);
                        LEADERBOARD.put(playerId, score);
                    }
                }
            }

            // Ensure all players present with score 0 if missing
            for (int i = 1; i <= NUMBER_OF_PLAYERS; i++) {
                LEADERBOARD.putIfAbsent(i, 0);
            }

            JOptionPane.showMessageDialog(null, "Leaderboard Loaded Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading leaderboard: " + e.getMessage());
            initializeLeaderboard();
        }
    }




    /**
     * Saves the leaderboard data to external file.
     * Shows error if saving fails.
     */
    public void saveLeaderboardToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(EXTERNAL_SAVE_PATH)) {
            for (Map.Entry<Integer, Integer> entry : LEADERBOARD.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing leaderboard: " + e.getMessage());
        }
    }



    /**
     * Returns the current leaderboard map.
     * @return Map of player IDs to their scores.
     */
    public Map<Integer, Integer> getLeaderboard() {
        return LEADERBOARD;
    }



    /**
     * Returns the path where leaderboard data is saved externally.
     * @return Path to the external save file.
     */
    public Path getExternalSavePath() {
        return EXTERNAL_SAVE_PATH;
    }




    /**
     * Updates score and saves immediately.
     * @param playerId player to update
     * @param scoreToAdd score to add
     */
    public void updateScore(int playerId, int scoreToAdd) {
        LEADERBOARD.put(playerId, LEADERBOARD.getOrDefault(playerId, 0) + scoreToAdd);
        saveLeaderboardToFile();
    }

    /**
     * Populate a table model with current leaderboard data.
     * @param leaderboardTableModel table model to fill
     */
    public void setInitialDataTable(DefaultTableModel leaderboardTableModel) {
        getLeaderboard().forEach((id, score) -> leaderboardTableModel.addRow(new Object[]{id, score}));
    }
}
