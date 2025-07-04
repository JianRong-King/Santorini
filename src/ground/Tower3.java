package ground;

import board.Location;
import capability.Capability;
import player.Worker;

import java.util.ArrayList;


/**
 * Represents a Tower Level 3 class that inherits from the abstract Ground class.
 * This class models the third level of a tower in the game.
 * Workers standing on this level may gain the ability to win the game.
 *
 * @author King Jian Rong
 * @version 1.3
 */


public class Tower3 extends Ground {
    /**
     * Constructs a Tower Level 3 object with default granted and lost capabilities.
     * Grants MOVE_TO_FLOOR, MOVE_TO_TOWER_1, MOVE_TO_TOWER_2, MOVE_TO_TOWER_3, and WIN capabilities.
     */
    public Tower3() {
        this.level = 3; // Tower Level 3
        this.capabilityHolds = new ArrayList<>();
        this.capabilityHolds.add(Capability.MOVE_TO_FLOOR);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_1);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_2);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_3);
        this.capabilityHolds.add(Capability.WIN);
        this.lostCapability = new ArrayList<>();
    }


    /**
     * Determines if a Worker is allowed to travel onto Tower Level 3.
     * A Worker cannot travel if a dome is present,
     * and must have the MOVE_TO_TOWER_3 capability.
     *
     * @param worker the Worker attempting to move
     * @return true if the Worker can travel to this Tower Level 3; false otherwise
     */
    @Override
    public boolean canWorkerTravel(Worker worker) {
        if (this.hasDome) {
            return false;
        }
        return worker.getCAPABILITIES_CONTROLLER().hasCapability(Capability.MOVE_TO_TOWER_3);
    }


    /**
     * Upgrades the Tower Level 3 by adding a dome to the structure.
     * Once the dome is placed, no further building is allowed.
     *
     * @param location the Location where the build occurs
     */
    @Override
    public void buildUp(Location location) {
        this.hasDome = true;
    }


    /**
     * Returns a string representation of this Tower Level 3.
     * If a dome is present, returns "Dome"; otherwise, returns "Tower 3".
     *
     * @return "Dome" or "Tower 3"
     */
    @Override
    public String toString() {
        if (this.hasDome) {
            return "Dome";
        }
        return "Tower 3";
    }
}
