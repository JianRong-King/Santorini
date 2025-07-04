package player;

import capability.Capability;
import capability.CapabilityController;

import javax.swing.*;

/**
 * This class represents a worker in the game. It contains methods to perform actions
 * and interact with the game board.
 *
 * @author Yong Han Lee
 * @version 2.0
 * @ModifiedBy: King Jian Rong
 */
public class Worker{

    private final Player OWNER;
    private final ImageIcon ICON;
    private final CapabilityController CAPABILITIES_CONTROLLER;

    /**
     * Constructor for Worker.
     * @param owner The player who owns this worker.
     */
    public Worker(Player owner) {
        this.OWNER = owner;
        this.ICON = new ImageIcon(this.OWNER.getICON_PATH());
        this.CAPABILITIES_CONTROLLER = new CapabilityController();
        // grant all basic capabilities initially
        CAPABILITIES_CONTROLLER.addCapability(Capability.MOVE_TO_TOWER_1);
        CAPABILITIES_CONTROLLER.addCapability(Capability.BUILD);
        CAPABILITIES_CONTROLLER.addCapability(Capability.MOVE_TO_FLOOR);
    }

    /**
     * Accessor for the icon attribute.
     * @return the icon of the worker
     */
    public ImageIcon getICON() {
        return this.ICON;
    }

    /**
     * Accessor for the player (owner) property.
     * @return the player who owns this worker
     */
    public Player getPlayer() {
        return this.OWNER;
    }

    /**
     * Accessor for the capabilities' property.
     * @return the capabilities of the worker
     */
    public CapabilityController getCAPABILITIES_CONTROLLER() {
        return this.CAPABILITIES_CONTROLLER;
    }
}
