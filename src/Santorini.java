import board.Board;
import game.GameComponentUI;
import game.GameController;
import game.World;
import god.Artemis;
import god.Demeter;
import god.GodCard;
import god.Zeus;
import game.GameManager;
import leaderboard.LeaderboardUI;
import player.Player;
import player.Worker;
import utility.MultiplayerUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main entry point for the Santorini game.
 *
 * @author Wilson Tan
 *
 * @ModifiedBy King Jian Rong
 *
 * @version 2.0
 */
public class Santorini {
    public static void main(String[] args) throws IOException, URISyntaxException {

        // Button size
        int buttonWidth = 120;
        int buttonHeight = 120;

        int frameHeight = 300;
        int frameWidth = 250;
        int optionalSpacing = 10;

        // Set the size of the location button
        GameComponentUI.setLocationButtonWidth(buttonWidth);
        GameComponentUI.setLocationButtonHeight(buttonHeight);

        LeaderboardUI.setFrameHeight(frameHeight);
        LeaderboardUI.setFrameWidth(frameWidth);
        LeaderboardUI.setOptionalSpacing(optionalSpacing);

        // Set the URL for the player icons
        URL playerIconPath1 = Santorini.class.getResource("/resources/player1.png");
        URL playerIconPath2 = Santorini.class.getResource("/resources/player2.png");
        URL playerIconPath3 = Santorini.class.getResource("/resources/player3.png");
        URL playerIconPath4 = Santorini.class.getResource("/resources/player4.png");
        URL playerIconPath5 = Santorini.class.getResource("/resources/player5.png");
        URL playerIconPath6 = Santorini.class.getResource("/resources/player6.png");
        URL playerIconPath7 = Santorini.class.getResource("/resources/player7.png");
        URL playerIconPath8 = Santorini.class.getResource("/resources/player8.png");

        // List of Players for this round of game
        List<Player> fullPlayerList = List.of(
                new Player(1, playerIconPath1),
                new Player(2, playerIconPath2),
                new Player(3, playerIconPath3),
                new Player(4, playerIconPath4),
                new Player(5, playerIconPath5),
                new Player(6, playerIconPath6),
                new Player(7, playerIconPath7),
                new Player(8, playerIconPath8)
        );

        int minPlayer = 2;
        int maxPlayer = 8;


        int numPlayers = MultiplayerUtils.askPlayerNumber(minPlayer,maxPlayer);

        if (numPlayers == -1) {
            return;
        }


        List<Player> playerList = new ArrayList<>(fullPlayerList.subList(0, numPlayers));


        // Number of Workers for each player on this round
        int numOfWorkers = 2;

        // Create workers for each player
        for (Player player : playerList) {
            for (int i = 0; i < numOfWorkers; i++) {
                Worker worker = new Worker(player);
                player.addWorker(worker);
            }
        }

        // Set the URL for the God Card icons
        URL godCardIconPath3 = Santorini.class.getResource("/resources/artemis.png");
        URL godCardIconPath4 = Santorini.class.getResource("/resources/demeter.png");
        URL godCardIconPath5 = Santorini.class.getResource("/resources/zeus.png");

        // List of God Cards for this round of game
        List<GodCard> godCards = List.of(
                new Artemis(godCardIconPath3),
                new Demeter(godCardIconPath4),
                new Zeus(godCardIconPath5)
        );

        URL mapPath = Santorini.class.getResource("/resources/map.txt");
        URL statsPath = Santorini.class.getResource("/resources/Stats.txt");


        // Create the game world and start the game
        Board board = new Board(mapPath, numPlayers);
        GameManager gameManager = new GameManager(numPlayers, statsPath);

        World world = new World(board, gameManager);



        // GameManager will handle the game entry flow
        gameManager.startGame(
                () -> {
            JFrame gameFrame = GameComponentUI.createGameFrame(80, 80);
            new GameController(world, playerList, numOfWorkers, godCards, gameFrame, gameManager);
        }
        );




    }

}
