package action;

import board.Board;
import board.Location;
import ground.Ground;
import player.Worker;

/**
 * This class is responsible for building a block of tower when called.
 *
 * @author Wilson Tan
 *
 * @ModifiedBy King Jian Rong
 *
 * @version 2.0
 */
public class BuildAction extends Action {
    private Location toLocation;

    /**
     * Constructor for BuildAction.
     * @param toLocation The location to build the block of tower.
     */
    public BuildAction(Location toLocation) {
        this.toLocation = toLocation;

        if (toLocation == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
    }

    /**
     * This method executes the build action.
     * @param worker The worker that is performing the action.
     * @param board The game board.
     */
    @Override
    public void execute(Worker worker, Board board, Location fromLocation) {
        if (worker == null || board == null) {
            throw new IllegalArgumentException("Worker and board cannot be null");
        }

//        if (!board.isWorkerAt(toLocation)) {
            Ground groundToBuild = toLocation.getGround();
            groundToBuild.buildUp(toLocation);
//        }
    }
}
