package ground;

import board.Location;
import capability.Capability;
import player.Worker;

import java.util.ArrayList;

/**
 * Represents a Floor object that inherits from the abstract Ground class.
 * A Floor represents a basic walkable ground where no tower has been built.
 * Workers can walk on this ground and gain or lose certain capabilities.
 *
 * @author King Jian Rong
 * @version 1.3
 */
public class Floor extends Ground {

    /**
     * Constructs a Floor object with default capabilities.
     * Grants MOVE_TO_FLOOR and MOVE_TO_TOWER_1 capabilities,
     * and loses MOVE_TO_TOWER_2 and MOVE_TO_TOWER_3 capabilities.
     */
    public Floor() {
        this.level = 0; // Floor level is 0
        this.capabilityHolds = new ArrayList<>();
        this.capabilityHolds.add(Capability.MOVE_TO_FLOOR);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_1);
        this.lostCapability = new ArrayList<>();
        this.lostCapability.add(Capability.MOVE_TO_TOWER_2);
        this.lostCapability.add(Capability.MOVE_TO_TOWER_3);
    }


    /**
     * Determines if a Worker is allowed to travel onto this Floor.
     * A Worker cannot travel if there is a dome, and must have MOVE_TO_FLOOR capability.
     *
     * @param worker the Worker attempting to move
     * @return true if the Worker can travel to this Floor; false otherwise
     */
    @Override
    public boolean canWorkerTravel(Worker worker) {
        if (this.hasDome) {
            return false;
        }
        return worker.getCAPABILITIES_CONTROLLER().hasCapability(Capability.MOVE_TO_FLOOR);
    }


    /**
     * Upgrades the Floor by building a Tower1 on the specified Location.
     *
     * @param location the Location where the build happens
     */
    @Override
    public void buildUp(Location location) {
        location.setGround(new Tower1());
    }


    /**
     * Returns a string representation of this Floor.
     *
     * @return the string "Floor"
     */
    @Override
    public String toString() {
        return "Floor";
    }
}
