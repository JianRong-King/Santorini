package action;

import board.Board;
import board.Location;
import capability.Capability;
import capability.CapabilityController;
import ground.Ground;
import player.Worker;

/**
 * This class is responsible for moving a worker.
 *
 * @author Wilson Tan
 *
 * @version 2.0
 */
public class MoveAction extends Action {
    private Location toLocation;

    /**
     * Constructor for MoveAction.
     * @param toLocation The location to move the worker to.
     */
    public MoveAction(Location toLocation) {
        this.toLocation = toLocation;

        if (toLocation == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
    }

    /**
     * This method executes the move action.
     * @param worker The worker that is performing the action.
     * @param board The game board.
     */
    @Override
    public void execute(Worker worker, Board board, Location fromLocation) {
        // Check for null values
        if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (board.isWorkerAt(toLocation)) {
            throw new IllegalStateException("Invalid move");
        }

        // Move the worker to the new location
        board.moveWorker(worker, toLocation);

        CapabilityController workerCap = worker.getCAPABILITIES_CONTROLLER();
        Ground toGround = toLocation.getGround();
        workerCap.addCapability(toGround.getCapabilities());
        workerCap.removeCapability(toGround.getLostCapabilities());


        if (fromLocation.getGround().getLevel() == 3 && toLocation.getGround().getLevel() == 3) {
            workerCap.removeCapability(Capability.WIN);
        }



    }
}
