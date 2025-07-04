package ground;

import board.Location;
import capability.Capability;
import player.Worker;

import java.util.List;

/**
 * Represents a Ground object in the game board.
 * Each Ground can affect a Worker's capabilities and may have structures like a dome.
 * This is an abstract class to be extended by specific Ground types.
 *
 * @author King Jian Rong
 * @version 1.3
 */
public abstract class Ground{
    /**
     * Holds the list of capabilities granted to a Worker while standing on Tower Level 1.
     */
    protected List<Enum<?>> capabilityHolds;


    /**
     * Holds the list of capabilities lost by a Worker while standing on Tower Level 1.
     */
    protected List<Enum<?>> lostCapability;


    /**
     * Indicates whether a dome is present on this ground.
     */
    protected boolean hasDome;

    protected int level;


    /**
     * Constructs a new Ground instance without a dome.
     */
    public Ground() {
        this.hasDome = false;
    }

    /**
     * Returns a list of capabilities granted to a Worker while on this Ground.
     *
     * @return list of granted capabilities as Enum types
     */
    public List<Enum<?>> getCapabilities() {
        return capabilityHolds;
    }

    public int getLevel() {
        return level;
    }


    /**
     * Returns a list of capabilities that are lost by a Worker while on this ground.Ground.
     *
     * @return list of lost capabilities as Enum types
     */
    public List<Enum<?>> getLostCapabilities() {
        return lostCapability;
    }



    /**
     * Executes logic to build or upgrade the Ground at a specified location.
     *
     * @param location the Location on the game board where the build occurs
     */
    public abstract void buildUp(Location location);


    /**
     * Determines whether the given Worker is allowed to travel to this Ground.
     * By default, all Workers are allowed.
     *
     * @param worker the player.Worker attempting to move to this Ground
     * @return true if the player.Worker can travel here
     */
    public boolean canWorkerTravel(Worker worker) {
        return true;
    }


    /**
     * Determines whether the given Worker is allowed to build on this Ground.
     * The Worker must have the BUILD capability and the ground.Ground must not have a dome.
     *
     * @param worker the Worker attempting to build
     * @return true if the Worker can build here
     */
    public boolean canWorkerBuild(Worker worker) {return worker.getCAPABILITIES_CONTROLLER().hasCapability(Capability.BUILD) && !this.hasDome;}


    /**
     * Grants this Ground's capabilities to the specified Worker.
     * Called when the Worker steps onto this Ground.
     *
     * @param worker the Worker who is gaining the capabilities
     */
    public void addCap(Worker worker) {
        for (Enum<?> cap : getCapabilities()) {
            worker.getCAPABILITIES_CONTROLLER().addCapability(cap);
        }
    }


    /**
     * Checks if this Ground has a dome.
     *
     * @return true if there is a dome on this Ground, false otherwise
     */
    public boolean hasDome(){
        return this.hasDome;
    }


    /**
     * Removes this Ground's lost capabilities from the specified Worker.
     * Called when the Worker steps onto thisGround.
     *
     * @param worker the Worker who is losing the capabilities
     */
    public void removeCap(Worker worker) {
        for (Enum<?> capability : getLostCapabilities()) {
            worker.getCAPABILITIES_CONTROLLER().removeCapability(capability);
        }
    }
}
