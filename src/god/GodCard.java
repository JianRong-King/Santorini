package god;

import board.Board;
import board.Location;
import game.GameController;
import player.Worker;

import javax.swing.*;
import java.net.URL;

/**
 * Abstract class representing a God card in the game.
 * Each God card has a name, an image path, and a timing for activation.
 * The class provides methods to perform, activate, and deactivate abilities.
 *
 * @author Yong Han Lee
 * @version 1.0
 */
public abstract class GodCard {

    private Timing activateTiming;
    private final String GOD_NAME;
    private final URL IMAGE_PATH;

    /**
     * Constructor for the GodCard class.
     *
     * @param timing the timing of the ability activation
     * @param godName the name of the God card
     * @param imagePath the path to the image representing the God card
     */
    public GodCard(Timing timing, String godName, URL imagePath) {
        this.activateTiming = timing;
        this.GOD_NAME = godName;
        this.IMAGE_PATH = imagePath;
    }

    /**
     * Gets the timing of the ability activation.
     *
     * @return the timing of the ability activation
     */
    public Timing getActivateTiming() {
        return this.activateTiming;
    }

    /**
     * Gets the name of the God card.
     *
     * @return the name of the God card
     */
    public String getName() {
        return this.GOD_NAME;
    }

    /**
     * Gets the path to the image representing the God card.
     *
     * @return the path to the image
     */
    public URL getIMAGE_PATH() {
        return this.IMAGE_PATH;
    }

    /**
     * Performs the ability of the God card.
     *
     * @param from the location of the worker in the previous phase
     * @param to the location of the worker move/build in the current phase
     * @param selectedWorker the worker selected for the ability
     * @param godDisableAbilityButton the button to disable the ability
     * @param board the game board
     * @param gameController the game controller
     * @return true if the ability was performed successfully, false otherwise
     */
    public abstract boolean performAbility(Location from, Location to, Worker selectedWorker, JButton godDisableAbilityButton, Board board, GameController gameController);

    /**
     * Activates the ability of the God card.
     *
     * @param from the location of the worker in the previous phase
     * @param to the location of the worker move/build in the current phase
     * @param board the game board
     * @param gameController the game controller
     */
    public abstract void activateAbility(Location from, Location to, Board board, GameController gameController);

    /**
     * Deactivates the ability of the God card.
     *
     * @param from the location of the worker in the previous phase
     * @param to the location of the worker move/build in the current phase
     * @param board the game board
     * @param gameController the game controller
     */
    public abstract void deactivateAbility(Location from, Location to, Board board, GameController gameController);
}
