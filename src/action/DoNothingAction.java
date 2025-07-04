package action;

import board.Board;
import board.Location;
import player.Worker;

/**
 * This class represents an action that does nothing when executed.
 *
 * @author Wilson Tan
 *
 * @version 2.0
 */
public class DoNothingAction extends Action {
    /**
     * This method does nothing when called.
     */
    @Override
    public void execute(Worker worker, Board board, Location fromLocation) {
        // No action performed
    }
}
