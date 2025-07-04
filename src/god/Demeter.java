package god;

import action.BuildAction;
import action.DoNothingAction;
import board.Board;
import board.Location;
import game.GameController;
import player.Player;
import player.Worker;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Represents the Demeter God card in the game.
 * Allows a player to build again
 *
 * @author  Yong Han Lee
 * @version 1.0
 */
public class Demeter extends GodCard {

    /**
     * Constructor for the Demeter God card.
     *
     * @param imagePath The path to the image representing the god.Demeter card.
     */
    public Demeter(URL imagePath){
        super(Timing.AFTER_BUILD, "Demeter", imagePath);
    }

    /**
     * Override the abstract method to activate god.Demeter's ability to allow a player to build again.
     *
     * @param from The location of the worker.
     * @param to The location where the worker is building.
     * @param board The game board.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public void activateAbility(Location from, Location to, Board board, GameController gameController){
        new BuildAction(to).execute(board.getWorkerAt(from), board, from);
        board.refreshBoardUI();
        gameController.checkLosingPlayer();
        gameController.nextPlayer();
    }

    /**
     * Override the abstract method to deactivate Demeter's ability.
     *
     * @param from The location of the worker.
     * @param to The location where the worker is building.
     * @param board The game board.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public void deactivateAbility(Location from, Location to, Board board, GameController gameController){
        new DoNothingAction().execute(board.getWorkerAt(from), board, from);
        board.refreshBoardUI();
        gameController.checkLosingPlayer();
        gameController.nextPlayer();
    }

    /**
     * Override the abstract method to perform Demeter's ability to allow a player to build again.
     *
     * @param from The location of the worker.
     * @param builtAt The location where the worker has built in previous phase.
     * @param selectedWorker The worker selected by the player.
     * @param godDisableAbilityButton The button to disable the God card's ability.
     * @param board The game board.
     * @param gameController The game controller managing the game state.
     */
    @Override
    public boolean performAbility(Location from, Location builtAt, Worker selectedWorker, JButton godDisableAbilityButton, Board board, GameController gameController){
        Player player = selectedWorker.getPlayer();
        gameController.updateStatus("Player " + player.getID() + ": " + player.getGodCard().getName()  + " - You can build again if you want!");
        for (Location loc : from.getExits()) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.canWorkerBuild(selectedWorker) && !(loc == (builtAt))) { // prevent building at the same location
                b.addActionListener(e -> activateAbility(from, loc, board, gameController));
                b.setBackground(Color.ORANGE);
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == selectedWorker.getPlayer() && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            } else {
                b.setBackground(null);
            }
        }

        godDisableAbilityButton.setEnabled(true);
        godDisableAbilityButton.addActionListener(e -> deactivateAbility(from, builtAt, board, gameController));

        return true;
    }
}
