package ground;

import board.Location;
import capability.Capability;
import player.Worker;

import java.util.ArrayList;

/**
 * Represents a Tower Level 2 class that inherits from the abstract ground.Ground class.
 * This class models the second level of a tower in the game.
 * Workers can gain or lose specific capabilities depending on this ground.
 *
 * @author King Jian Rong
 * @version 1.3
 */

public class Tower2 extends Ground {
    /**
     * Constructs a Tower Level 2 object with default granted and lost capabilities.
     * Grants MOVE_TO_FLOOR, MOVE_TO_TOWER_1, MOVE_TO_TOWER_2, and MOVE_TO_TOWER_3 capabilities.
     * No capabilities are lost at this level.
     */
    public Tower2() {
        this.level = 2; // Tower Level 2
        this.capabilityHolds = new ArrayList<>();
        this.capabilityHolds.add(Capability.MOVE_TO_FLOOR);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_1);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_2);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_3);
        this.lostCapability = new ArrayList<>();
    }


    /**
     * Determines if a Worker is allowed to travel onto Tower Level 2.
     * A Worker cannot travel if a dome is present,
     * and must have the MOVE_TO_TOWER_2 capability.
     *
     * @param worker the Worker attempting to move
     * @return true if the Worker can travel to this Tower Level 2; false otherwise
     */
    @Override
    public boolean canWorkerTravel(Worker worker) {
        if (this.hasDome) {
            return false;
        }
        return worker.getCAPABILITIES_CONTROLLER().hasCapability(Capability.MOVE_TO_TOWER_2);
    }


    /**
     * Upgrades the Tower Level 2 by building a Tower Level 3 on the specified Location.
     *
     * @param location the Location where the build occurs
     */
    @Override
    public void buildUp(Location location) {
        location.setGround(new Tower3());
    }


    /**
     * Returns a string representation of this Tower Level 2.
     *
     * @return the string "Tower 2"
     */
    @Override
    public String toString() {
        return "Tower 2";
    }
}
