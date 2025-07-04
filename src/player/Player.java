package player;

import god.GodCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the game.
 * It contains a list of workers (2 for Sprint Two) and a god card.
 *
 * @author Wilson Tan
 *
 * @version 1.5
 */
public class Player {
    private final int ID;
    private final URL ICON_PATH;
    private List<Worker> workers = new ArrayList<>();
    private GodCard godCard;

    /**
     * Constructor for the Player class.
     * Initializes the player with no workers and no god cards.
     */
    public Player(int id, URL iconPath) {
        this.ID = id;
        this.ICON_PATH = iconPath;
    }

    /**
     * This method gets the player's id.
     * @return The player's id.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * This method gets the player's icon path.
     * @return The player's icon path.
     */
    public URL getICON_PATH() {
        return this.ICON_PATH;
    }

    /**
     * This method gets the player's god card.
     * @return The player's god card.
     */
    public GodCard getGodCard() {
        return this.godCard;
    }

    /**
     * This method sets the player's god card.
     * @param godCard The god card to be set.
     */
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    /**
     * This method is used to add a worker to the player's list of workers.
     * @param worker The worker to be added.
     */
    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    /**
     * This method is used to get the player's list of workers.
     * @return The player's list of workers.
     */
    public List<Worker> getWorkers(){
        return this.workers;
    }
}
