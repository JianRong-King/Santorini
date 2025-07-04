package game;

import board.Location;
import god.GodCard;
import god.Timing;
import leaderboard.LeaderboardUI;
import player.Player;
import player.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is the controller for the Santorini game.
 * It handles the game logic and user interactions.
 *
 * @author Wilson Tan
 *
 * @ModifiedBy Yong Han Lee, King Jian Rong
 *
 * @version 1.5
 */
public class GameController {
    private final World WORLD;
    private Player currentPlayer;
    private Worker selectedWorker;

    private final JLabel GOD_CARD_LABEL;
    private final JLabel STATUS_LABEL;
    private final JButton GOD_ABILITY_DISABLE_BUTTON;
    private final GameManager gameManager;

    /**
     * Constructor for the game.GameController class.
     * Initializes the game world and UI components.
     *
     * @param world The game world.
     * @param playerList The list of players.
     * @param numOfWorkers The number of workers for each player.
     * @param godCards The list of god cards.
     */
    public GameController(World world, List<Player> playerList, int numOfWorkers, List<GodCard> godCards, JFrame frame, GameManager gameManager) {
        this.WORLD = world;
        this.WORLD.initializeGame(playerList, numOfWorkers, godCards);
        this.gameManager = gameManager;

        // title text
        JLabel titleTextLabel = new JLabel("Santorini", SwingConstants.CENTER);

        // god card display
        GOD_CARD_LABEL = new JLabel();
        GOD_CARD_LABEL.setHorizontalAlignment(SwingConstants.CENTER);

        // god card perform ability button
        GOD_ABILITY_DISABLE_BUTTON = new JButton("Not using ability");
        GOD_ABILITY_DISABLE_BUTTON.setEnabled(false);


        JButton SHOW_LEADERBOARD_BUTTON = LeaderboardUI.getLeaderboardButton(gameManager.getLeaderboardTableModel());


        // status label
        STATUS_LABEL = new JLabel();
        STATUS_LABEL.setHorizontalAlignment(SwingConstants.CENTER);

        // set up the frame
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));
        top.add(titleTextLabel); // title text
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));
        mid.add(world.getBOARD().getSCROLL_PANE());
        JPanel low = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));
        low.add(GOD_CARD_LABEL); // centred image
        low.add(STATUS_LABEL); // game status
        low.add(GOD_ABILITY_DISABLE_BUTTON);   // button on right
        low.add(SHOW_LEADERBOARD_BUTTON);      // new button for leaderboard

        frame.setLayout(new BorderLayout());
        frame.add(top, BorderLayout.NORTH);
        frame.add(mid, BorderLayout.CENTER);
        frame.add(low, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        nextPlayer();
    }

    /**
     * This method handle the switching of players.
     */
    public void nextPlayer() {
        currentPlayer = WORLD.nextPlayer();
        updateStatus("Player " + currentPlayer.getID() + ": Select a worker");
        updateGodCardDisplay();

        // check if there are more than 1 player
        if (this.WORLD.getPlayerAmount() <= 1){
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getID() + " wins!");
            System.exit(0);
            return;
        }
        attachSelectWorkerListeners();
    }

    /**
     * This method updates the display of the god card.
     */
    private void updateGodCardDisplay() {

        GodCard godCard = currentPlayer.getGodCard();

        if (godCard == null) {
            GOD_CARD_LABEL.setIcon(null);
            GOD_ABILITY_DISABLE_BUTTON.setEnabled(false);
            ImageIcon icon = new ImageIcon((Objects.requireNonNull(GameController.class.getResource("/resources/NoGodCard.png"))));
            Image scaled = icon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
            GOD_CARD_LABEL.setIcon(new ImageIcon(scaled));
            return;
        }

        URL imgPath = currentPlayer.getGodCard().getIMAGE_PATH();
        ImageIcon icon = new ImageIcon(imgPath);
        Image scaled = icon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        GOD_CARD_LABEL.setIcon(new ImageIcon(scaled));
    }

    /**
     * This method attaches listeners to the worker selection buttons.
     */
    private void attachSelectWorkerListeners() {
        clearAllListeners();
        for (Location loc : WORLD.getBOARD().allLocations()) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.hasWorker() && loc.getWorker().getPlayer() == currentPlayer) {
                b.addActionListener(e -> onWorkerSelected(loc));
                b.setBackground(Color.CYAN);
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == currentPlayer && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            } else {
                b.setBackground(null);
            }
        }
    }

    /**
     * This method handles the selection of a worker.
     *
     * @param loc The location of the selected worker.
     */
    private void onWorkerSelected(Location loc) {
        selectedWorker = loc.getWorker();
        attachMoveListeners(loc);
    }

    /**
     * This method attaches listeners to the move buttons.
     *
     * @param from The location of the selected worker.
     */
    protected void attachMoveListeners(Location from) {
        updateStatus("Player " + currentPlayer.getID() + ": Select move location");
        boolean canMove = false;
        clearAllListeners();
        for (Location loc : from.getExits()) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.canWorkerTravel(selectedWorker)) {
                b.addActionListener(e -> onMove(from, loc));
                b.setBackground(Color.GREEN);
                canMove = true;
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == currentPlayer && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            }
            else {
                b.setBackground(null);
            }
        }

        // implementing the touch-rule here
        if (!canMove){
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getID() + " loses! As you picked a worker that can't move haha!");
            this.WORLD.removePlayer(currentPlayer);
            WORLD.getBOARD().refreshBoardUI();
            nextPlayer();
        }
    }

    /**
     * This method handles the move action.
     *
     * @param from The location of the selected worker.
     * @param to The location to move to.
     */
    private void onMove(Location from, Location to) {
        WORLD.processMove(from, to);
        WORLD.getBOARD().refreshBoardUI();

        // check win immediately if ground.Tower3 reached
        if (WORLD.isGameOver()) {
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getID() + " wins!");



            JFrame leaderboardFrame = LeaderboardUI.showLeaderBoard(gameManager.getLeaderboardTableModel());
            leaderboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.exit(0); // Exit only after leaderboard window is closed
                }
            });


            return;
        }

        // perform checking on losing player
        this.checkLosingPlayer();



        // god after-move ability (e.g. god.Artemis allows a second move)
        if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().getActivateTiming() == Timing.AFTER_MOVE) {
            clearAllListeners();
            currentPlayer.getGodCard().performAbility(from, to, selectedWorker, GOD_ABILITY_DISABLE_BUTTON, WORLD.getBOARD(), this);

            return;
        }

        attachBuildListeners(to);
    }

    /**
     * This method attaches listeners to the build buttons.
     *
     * @param from The location of the selected worker.
     */
    public void attachBuildListeners(Location from) {
        // perform checking on losing player
        this.checkLosingPlayer();

        updateStatus("Player " + currentPlayer.getID() + ": Select build location");
        if (WORLD.isGameOver()) {
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getID() + " wins!");


            JFrame leaderboardFrame = LeaderboardUI.showLeaderBoard(gameManager.getLeaderboardTableModel());

            leaderboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.exit(0); // Exit only after leaderboard window is closed
                }
            });

            return;
        }

        clearAllListeners();


        // God card that Has Ability of Before Building
        if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().getActivateTiming() == Timing.BEFORE_BUILD) {
            // after build

            currentPlayer.getGodCard().performAbility(from, from, selectedWorker, GOD_ABILITY_DISABLE_BUTTON, WORLD.getBOARD(), this);

            return;
        }

        for (Location loc : from.getExits()) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.canWorkerBuild(selectedWorker)) {
                b.addActionListener(e -> onBuild(from, loc));
                b.setBackground(Color.ORANGE);
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == currentPlayer && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            } else {
                b.setBackground(null);
            }
        }
    }

    /**
     * This method handles the build action.
     *
     * @param from The location of the selected worker.
     * @param at The location to build at.
     */
    private void onBuild(Location from, Location at) {
        WORLD.processBuild(from, at);
        WORLD.getBOARD().refreshBoardUI();

        // perform checking on losing player
        this.checkLosingPlayer();

        // god after-build ability (e.g. god.Demeter allows second build)
        if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().getActivateTiming() == Timing.AFTER_BUILD) {
            // after build
            clearAllListeners();
            currentPlayer.getGodCard().performAbility(from, at, selectedWorker, GOD_ABILITY_DISABLE_BUTTON, WORLD.getBOARD(), this);
            return;
        }

        nextPlayer();
    }

    /**
     * This method removes all action listeners from the buttons.
     */
    private void clearAllListeners() {
        for (Location loc : WORLD.getBOARD().allLocations()) {
            JButton b = loc.getLOCATION_BUTTON();
            for (ActionListener al : b.getActionListeners()) {
                b.removeActionListener(al);
            }
            b.setBackground(null);
        }
        // remove action listeners from godDisableAbilityButton
        for (ActionListener al : GOD_ABILITY_DISABLE_BUTTON.getActionListeners()) {
            GOD_ABILITY_DISABLE_BUTTON.removeActionListener(al);
        }
        GOD_ABILITY_DISABLE_BUTTON.setEnabled(false); // reset
    }

    /**
     * This method updates the status label.
     *
     * @param text The text to display.
     */
    public void updateStatus(String text) {
        STATUS_LABEL.setText(text);
    }

    /**
     * This method checks for the lost & win condition of the game.
     * Especially on the scenario of workers being trapped by buildings.
     */
    public void checkLosingPlayer(){
        List<Player> remainingPlayers = new ArrayList<>();
        List<Player> losingPlayers = new ArrayList<>();
        for (Player player : WORLD.getPLAYERS()){
            boolean lose = true;

            for (Worker worker : player.getWorkers()){
                Location workerLocation = this.WORLD.getBOARD().locationOfWorker(worker);
                for (Location loc : workerLocation.getExits()){
                    if (loc.canWorkerTravel(worker)){
                        lose = false;
                    }
                }
            }
            if (lose){
                losingPlayers.add(player);
            }
            else{
                remainingPlayers.add(player);
            }
        }
        // check if there are more than 1 player
        if (!losingPlayers.isEmpty()){
            for (Player player : losingPlayers){
                JOptionPane.showMessageDialog(null, "Player " + player.getID() + " loses! As your workers can't move anymore haha!");
                this.WORLD.removePlayer(player);
                WORLD.getBOARD().refreshBoardUI();
            }
        }

        if (remainingPlayers.size() <= 1){
            if (remainingPlayers.size() == 1){
                JOptionPane.showMessageDialog(null, "Player " + remainingPlayers.getFirst().getID() + " wins!");

                JFrame leaderboardFrame = LeaderboardUI.showLeaderBoard(gameManager.getLeaderboardTableModel());

                leaderboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        System.exit(0); // Exit only after leaderboard window is closed
                    }
                });

            }

            else{ // every player's workers are trapped
                JOptionPane.showMessageDialog(null, "No Players left! Draw! Game Over!");

                JFrame leaderboardFrame = LeaderboardUI.showLeaderBoard(gameManager.getLeaderboardTableModel());

                leaderboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        System.exit(0); // Exit only after leaderboard window is closed
                    }
                });
            }
        }

        if (!this.WORLD.getPLAYERS().contains(currentPlayer)){
            clearAllListeners();
            this.WORLD.getBOARD().refreshBoardUI();
            nextPlayer();
        }
    }
}
