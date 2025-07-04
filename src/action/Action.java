package action;

import board.Board;
import board.Location;
import player.Worker;

/**
 * This is an abstract class representing an action that can be performed for each game turn.
 *
 * @author Wilson Tan
 *
 * @version 2.0
 */
public abstract class Action {
    abstract void execute(Worker worker, Board board, Location fromLocation);
}
