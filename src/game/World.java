package game;

import action.BuildAction;
import action.MoveAction;
import board.Board;
import board.Location;
import capability.Capability;
import god.GodCard;

import player.Player;
import player.Worker;

import java.util.*;

/**
 * This class represents the game world logic.
 * It manages the game state, including worker locations and turns.
 *
 * @author Yong Han Lee
 *
 * @ModifiedBy King Jian Rong
 * @version 2.0
 */
public class World {
    private final List<Player> PLAYERS;
    private final Board BOARD;
    private final Map<Integer, GodCard> AVAILABLE_GOD_CARD;
    private int godCardId = 1;
    private int currentPlayerIndex = -1;
    private GameManager gameManager;

    /**
     * Constructor for the World class.
     *
     * @param board The game board.
     */
    public World(Board board, GameManager gameManager) {
        this.AVAILABLE_GOD_CARD = new HashMap<>();
        this.BOARD = board;
        this.PLAYERS = new ArrayList<>();
        this.gameManager = gameManager;
    }

    /**
     * Adds a god cards to the game.
     *
     * @param godCard The god card to be added.
     */
    public void addGodCards(GodCard godCard){
        this.AVAILABLE_GOD_CARD.put(this.godCardId, godCard);
        this.godCardId += 1;
    }

    /**
     * Accessor for the board.
     *
     * @return the board of the game
     */
    public Board getBOARD() {
        return this.BOARD;
    }

    /**
     * Accessor for the number of players.
     *
     * @return the number of players
     */
    public int getPlayerAmount() {
        return this.PLAYERS.size();
    }

    /**
     * Accessor for the players.
     *
     * @return the list of players
     */
    public List<Player> getPLAYERS() {
        return this.PLAYERS;
    }

    /**
     * Randomly assigns god cards to players.
     * This method shuffles the available god cards and assigns them to players. When the number of players exceeds the number of available god cards, it will only assign cards to the first few players.
     */
    public void randomlyAssignGodCards(){

        List<Integer> keys = new ArrayList<>(this.AVAILABLE_GOD_CARD.keySet());
        Collections.shuffle(keys);
        Collections.shuffle(this.PLAYERS);


        for (int i = 0; i < PLAYERS.size() && i < this.godCardId - 1; i++){
            int key = keys.get(i);
            Player player = PLAYERS.get(i);
            GodCard godCard = this.AVAILABLE_GOD_CARD.get(key);
            player.setGodCard(godCard);
        }

        this.PLAYERS.sort(Comparator.comparingInt(Player::getID));


    }

    /**
     * Randomly assigns workers to random locations on the board.
     */
    public void randomlyAssignWorker(int numOfWorkers){
        // must do it after the creation of board
        List<Location> availableLocations = this.BOARD.allLocations();

        if (availableLocations.size() < this.PLAYERS.size()*numOfWorkers){
            throw new IllegalArgumentException("Not enough locations available");
        }

        Collections.shuffle(availableLocations);

        int index = 0;
        for (Player player: this.PLAYERS){
            List<Worker> workers = player.getWorkers();

            for (Worker worker: workers){
                Location assignedLocation = availableLocations.get(index);
                assignedLocation.addWorker(worker);
                index += 1;
            }
        }
    }

    /**
     * Initializes the game with players and their workers, according to the number of workers and god cards provided.
     *
     * @param players the list of players to be added.
     * @param numOfWorkers the number of workers for each player.
     * @param godCards the list of god cards to be added.
     */
    public void initializeGame(List<Player> players, int numOfWorkers, List<GodCard> godCards) {
        // Setup players
        this.PLAYERS.addAll(players);

        // Add and assign God cards
        for (GodCard godCard: godCards) {
            addGodCards(godCard);
        }

        // Randomly assign God cards to player
        randomlyAssignGodCards();

        // Randomly assign workers to locations
        randomlyAssignWorker(numOfWorkers);
        BOARD.refreshBoardUI();
    }

    /**
     * Processes a move action.
     *
     * @param from The location from which the worker is moving.
     * @param to The location to which the worker is moving.
     */
    public void processMove(Location from, Location to) {
        new MoveAction(to).execute(BOARD.getWorkerAt(from), BOARD, from);
    }

    /**
     * Processes a build action.
     *
     * @param from The location from which the worker is building.
     * @param to The location to which the worker is building.
     */
    public void processBuild(Location from, Location to) {
        Worker worker = BOARD.getWorkerAt(from);
        new BuildAction(to).execute(worker, BOARD, from);
    }

    /**
     * Processes a turn for the current player.
     */
    public Player nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % PLAYERS.size();
        return PLAYERS.get(currentPlayerIndex);
    }

    /**
     * Removes a player including player's workers from the game.
     * @param player the player to be removed.
     */
    public void removePlayer(Player player) {
        this.PLAYERS.remove(player);
        for (Worker worker : player.getWorkers()) {
            BOARD.removeWorker(worker);
        }
        BOARD.refreshBoardUI();
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        for (Player p : PLAYERS) {
            for (Worker w : p.getWorkers()) {
                if (w.getCAPABILITIES_CONTROLLER().hasCapability(Capability.WIN)) {

                    gameManager.updateScore(p.getID(), 1);

                    return true;
                }
            }
        }
        return false;
    }
}
