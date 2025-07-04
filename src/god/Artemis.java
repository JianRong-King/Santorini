package god;

import action.DoNothingAction;
import action.MoveAction;
import board.Board;
import board.Location;
import game.GameController;
import player.Player;
import player.Worker;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Represents the Artemis God card in the game.
 * Allows a player to move a worker and then move again.
 *
 * @author Yong Han Lee
 * @version 2.0
 */
public class Artemis extends GodCard {

    /**
     * * Constructor for the Artemis God card.
     * @param imagePath the icon path for god card
     */
    public Artemis(URL imagePath){
        super(Timing.AFTER_MOVE, "Artemis", imagePath);
    }

    /**
     * Overriding the abstract method to activates the Artemis ability, allowing the player to move a worker.
     * @param from The original location of the worker.
     * @param to The new location of the worker.
     * @param board The game board.
     * @param gameController The game controller.
     */
    @Override
    public void activateAbility(Location from, Location to, Board board, GameController gameController){
        new MoveAction(to).execute(board.getWorkerAt(from), board, from);
        board.refreshBoardUI();
        gameController.attachBuildListeners(to);
    }

    /**
     * Overriding the abstract method to deactivate the Artemis ability.
     * @param from The original location of the worker.
     * @param to The new location of the worker.
     * @param board The game board.
     * @param gameController The game controller.
     */
    @Override
    public void deactivateAbility(Location from, Location to, Board board, GameController gameController){
        new DoNothingAction().execute(board.getWorkerAt(from), board, from);
        board.refreshBoardUI();
        gameController.attachBuildListeners(to);
    }

    /**
     * Overriding the abstract method to perform the Artemis ability.
     * @param from The original location of the worker.
     * @param to The current location of the worker.
     * @param selectedWorker The selected worker.
     * @param godDisableAbilityButton The button to disable the god ability.
     * @param board The game board.
     * @param gameController The game controller.
     */
    @Override
    public boolean performAbility(Location from, Location to, Worker selectedWorker, JButton godDisableAbilityButton, Board board, GameController gameController){
        Player player = selectedWorker.getPlayer();
        gameController.updateStatus("Player " + player.getID() + ": " + player.getGodCard().getName() + " - You can move again if you want!");
        for (Location loc : to.getExits()) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.canWorkerTravel(selectedWorker) && !(loc == from)) { // prevent going back to original location
                b.addActionListener(e -> activateAbility(to, loc, board, gameController));
                b.setBackground(Color.GREEN);
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == selectedWorker.getPlayer() && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            } else {
                b.setBackground(null);
            }
        }

        godDisableAbilityButton.setEnabled(true);
        godDisableAbilityButton.addActionListener(e -> deactivateAbility(from, to, board, gameController));

        return true;
    }
}
