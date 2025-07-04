package capability;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CapabilityController manages a set of capabilities represented as Enums.
 * It allows adding, removing, and checking for capabilities.
 *
 * @author Yong Han Lee
 * @version 2.0
 * @Modified by: Louis Jeremie Ing
 */
public class CapabilityController {
    private Set<Enum<?>> capabilities;

    /**
     * Constructor for the CapabilityController class.
     * Initializes an empty set of capabilities.
     */
    public CapabilityController() {
        this.capabilities = new HashSet<>();
    }

    /**
     * Adds a capability to the set of capabilities.
     *
     * @param capability The capability to add.
     */
    public void addCapability(Enum<?> capability) {
        this.capabilities.add(capability);
    }

    /**
     * Adds a list of capabilities to the set of capabilities.
     *
     * @param capabilities The list of capabilities to add.
     */
    public void addCapability(List<Enum<?>> capabilities) {
        this.capabilities.addAll(capabilities);
    }

    /**
     * Removes a capability from the set of capabilities.
     *
     * @param capability The capability to remove.
     */
    public void removeCapability(Enum<?> capability) {
        this.capabilities.remove(capability);
    }

    /**
     * Removes a list of capabilities from the set of capabilities.
     *
     * @param capabilities The list of capabilities to remove.
     */
    public void removeCapability(List<Enum<?>> capabilities) {
        this.capabilities.removeAll(capabilities);
    }

    /**
     * Checks if the set of capabilities contains a specific capability.
     *
     * @param capability The capability to check for.
     * @return true if the capability is present, false otherwise.
     */
    public boolean hasCapability(Enum<?> capability) {
        return this.capabilities.contains(capability);
    }
}
