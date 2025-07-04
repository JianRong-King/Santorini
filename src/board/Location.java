package board;

import game.GameComponentUI;
import ground.Ground;
import player.Worker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static capability.Capability.BUILD_BELOW_WORKER_ON_TOWER;

/**
 * Represent one location on the board.
 *
 * @author Louis Jeremie Ing
 *
 * @ModifiedBy Wilson Tan
 *
 * @version 2.1
 */

public class Location {
    private final JButton LOCATION_BUTTON;
    private final Board BOARD;
    private final Position POSITION;
    private Ground ground;
    private List<Location> exits = new ArrayList<>();

    /**
     * Constructor for the board.Location class.
     * Creates a location with the specified board, position, and ground.
     *
     * @param board The board to which this location belongs.
     * @param position The position of this location on the board.
     * @param ground The ground of this location.
     */
    public Location(Board board, Position position, Ground ground) {
        this.BOARD = board;
        this.POSITION = position;
        this.ground = ground;
        this.LOCATION_BUTTON = GameComponentUI.createLocationButton();
        this.LOCATION_BUTTON.setBounds(
                position.X * GameComponentUI.getLocationButtonWidth(),
                position.Y * GameComponentUI.getLocationButtonHeight(),
                GameComponentUI.getLocationButtonWidth(),
                GameComponentUI.getLocationButtonHeight()
        );
        this.BOARD.getBOARD_PANEL().add(this.LOCATION_BUTTON);
    }

    /**
     * Check if this location has a worker.
     *
     * @return true if this location has a worker, false otherwise.
     */
    public boolean hasWorker() {
        return this.BOARD.isWorkerAt(this);
    }

    /**
     * Add a worker to this location.
     *
     * @param worker The worker to be added.
     */
    public void addWorker(Worker worker){
        this.BOARD.addWorker(worker, this);
    }

    /**
     * Set the ground of this location.
     *
     * @param ground The ground to be set.
     */
    public void setGround(Ground ground){
        this.ground = ground;
    }

    /**
     * Get the ground of this location.
     *
     * @return The ground of this location.
     */
    public Ground getGround() {
        return this.ground;
    }

    /**
     * Check if a worker can travel to this location
     * (worker needs to be used since different workers can have different capabilities - see capability.CapabilityController class).
     *
     * @param worker The worker to be checked.
     * @return true if the worker can travel to this location, false otherwise.
     */
    public boolean canWorkerTravel(Worker worker){
        return !this.hasWorker() && ground.canWorkerTravel(worker);
    }

    /**
     * Check if a worker can build at this location.
     * (worker needs to be used since different workers can have different capabilities - see capability.CapabilityController class).
     * Also check if there is capability to build while the worker is on the tower. (Zeus god card)
     *
     * @param worker The worker to be checked.
     * @return true if the worker can build at this location, false otherwise.
     */
    public boolean canWorkerBuild(Worker worker){

        if (!this.hasWorker() && ground.canWorkerBuild(worker)) {
            return true;
        } else return worker.getCAPABILITIES_CONTROLLER().hasCapability(BUILD_BELOW_WORKER_ON_TOWER) && ground.canWorkerBuild(worker) && getWorker() == worker && ground.getLevel() != 3;
    }

    /**
     * Add an exit location to this location.
     *
     * @param exit The exit to be added.
     */
    public void addExit (Location exit) {
        this.exits.add(exit);
    }

    /**
     * Get the list of exit locations from this location.
     *
     * @return The list of exit locations.
     */
    public List<Location> getExits() {
        return this.exits;
    }

    /**
     * Get the board to which this location belongs.
     *
     * @return The board of this location.
     */
    public Board getBOARD() {
        return this.BOARD;
    }

    /**
     * Get the button associated with this location.
     *
     * @return The button of this location.
     */
    public JButton getLOCATION_BUTTON(){
        return this.LOCATION_BUTTON;
    }

    /**
     * Get the worker at this location.
     *
     * @return The worker at this location.
     */
    public Worker getWorker(){
        return this.BOARD.getWorkerAt(this);
    }

    /**
     * Override the hashCode method to return the hash code of the position.
     *
     * @return The hash code of the position.
     */
    @Override
    public int hashCode() {
        return this.POSITION.hashCode();
    }
}
