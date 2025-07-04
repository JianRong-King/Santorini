package god;

import action.BuildAction;
import action.DoNothingAction;
import board.Board;
import board.Location;
import capability.CapabilityController;
import game.GameController;
import ground.Ground;
import player.Player;
import player.Worker;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import static capability.Capability.BUILD_BELOW_WORKER_ON_TOWER;
import static capability.Capability.WIN;


public class Zeus extends GodCard {
/**
     * Constructor for the Zeus God card.
     * @param imagePath the icon path for god card
     */
    public Zeus(URL imagePath) {
        super(Timing.BEFORE_BUILD, "Zeus", imagePath);
    }



    /**
     * Overriding the abstract method to activate the Zeus ability, allowing the player to build on a dome.
     * @param from The original location of the worker.
     * @param to The new location of the worker.
     * @param board The game board.
     * @param gameController The game controller.
     */
    @Override
    public void activateAbility(Location from, Location to, Board board, GameController gameController) {
        new BuildAction(to).execute(board.getWorkerAt(from), board, from);

        CapabilityController workerCap = from.getWorker().getCAPABILITIES_CONTROLLER();

        Ground toGround = from.getGround();
        workerCap.addCapability(toGround.getCapabilities());
        workerCap.removeCapability(toGround.getLostCapabilities());
        workerCap.removeCapability(WIN);  // remove the win capability so that the worker eventually not win the game by building below itself

        board.refreshBoardUI();

        gameController.checkLosingPlayer();
        gameController.nextPlayer();
    }



    /**
     * Overriding the abstract method to deactivate the Zeus ability.
     * @param from The original location of the worker.
     * @param to The new location of the worker.
     * @param board The game board.
     * @param gameController The game controller.
     */
    @Override
    public void deactivateAbility(Location from, Location to, Board board, GameController gameController) {

        new DoNothingAction().execute(board.getWorkerAt(from), board, from);
        board.refreshBoardUI();

        gameController.checkLosingPlayer();
        gameController.nextPlayer();

    }


    @Override
    public boolean performAbility(Location from, Location to, Worker selectedWorker, JButton godDisableAbilityButton, Board board, GameController gameController){
        Player player = selectedWorker.getPlayer();
        gameController.updateStatus("Player " + player.getID() + ": " + player.getGodCard().getName() + " - You can Build a block under yourself !");


        // Add Zeus Capability to selected worker
        selectedWorker.getCAPABILITIES_CONTROLLER().addCapability(BUILD_BELOW_WORKER_ON_TOWER);
        from.getGround().addCap(selectedWorker);

        ArrayList<Location> allExits = new ArrayList<>(from.getExits());
        allExits.add(from);

        for (Location loc : allExits) {
            JButton b = loc.getLOCATION_BUTTON();
            if (loc.canWorkerBuild(selectedWorker)) {
                b.addActionListener(e -> activateAbility(from, loc, board, gameController));
                b.setBackground(Color.ORANGE);
            } else if (loc.hasWorker() && loc.getWorker().getPlayer() == selectedWorker.getPlayer() && selectedWorker == loc.getWorker()){
                b.setBackground(Color.PINK);
            } else {
                b.setBackground(null);
            }
        }




        godDisableAbilityButton.setEnabled(false);

        return true;


    }
}
