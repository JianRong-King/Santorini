package ground;

import board.Location;
import capability.Capability;
import player.Worker;

import java.util.ArrayList;

/**
 * Represents a Tower Level 1 class that inherits from the abstract Ground class.
 * This class models the first level of a tower in the game.
 * Workers may gain or lose capabilities depending on the ground they are standing on.
 *
 * @author King Jian Rong
 * @version 1.3
 */

public class Tower1 extends Ground {
    /**
     * Constructs a Tower Level 1 object with default granted and lost capabilities.
     * Grants MOVE_TO_FLOOR, MOVE_TO_TOWER_1, and MOVE_TO_TOWER_2 capabilities,
     * and removes MOVE_TO_TOWER_3 capability.
     */
    public Tower1() {
        this.level = 1; // Tower Level 1
        this.capabilityHolds = new ArrayList<>();
        this.capabilityHolds.add(Capability.MOVE_TO_FLOOR);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_1);
        this.capabilityHolds.add(Capability.MOVE_TO_TOWER_2);
        this.lostCapability = new ArrayList<>();
        this.lostCapability.add(Capability.MOVE_TO_TOWER_3);
    }


    /**
     * Determines if a Worker is allowed to travel onto Tower Level 1.
     * A Worker cannot travel if a dome is present,
     * and must have the MOVE_TO_TOWER_1 capability.
     *
     * @param worker the Worker attempting to move
     * @return true if the Worker can travel to this Tower Level 1; false otherwise
     */
    @Override
    public boolean canWorkerTravel(Worker worker) {
        if (this.hasDome) {
            return false;
        }
        return worker.getCAPABILITIES_CONTROLLER().hasCapability(Capability.MOVE_TO_TOWER_1);
    }


    /**
     * Upgrades the Tower Level 1 by building a Tower Level 2 on the specified Location.
     *
     * @param location the Location where the build occurs
     */
    @Override
    public void buildUp(Location location) {
        location.setGround(new Tower2());
    }


    /**
     * Returns a string representation of this Tower Level 1.
     *
     * @return the string "Tower 1"
     */
    @Override
    public String toString() {
        return "Tower 1";
    }
}
