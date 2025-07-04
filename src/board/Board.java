package board;


import ground.Floor;
import ground.Ground;
import player.Worker;
import player.WorkerLocationController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represent the board of the Santorini game.
 *
 * @author Louis Jeremie Ing
 *
 * @ModifiedBy Wilson Tan, King Jian Rong
 *
 * @version 2.1
 */

public class Board{
    private final JPanel BOARD_PANEL;
    private final JScrollPane SCROLL_PANE;
    private final Map<Position, Location> BOARD_LOCATIONS;
    private final WorkerLocationController WORKER_LOCATION_CONTROLLER;



    /**
     * Constructor for the board.Board class.
     * Initializes the board with a layout defined in a text file.
     *
     * @param configFilePath The txt config file containing the board layout.
     * @param numberOfPlayers The number of players for current gameplay.
     */
    public Board(URL configFilePath, int numberOfPlayers) throws IOException, URISyntaxException {
        this.BOARD_PANEL = new JPanel();
        this.BOARD_LOCATIONS = new HashMap<>();
        this.WORKER_LOCATION_CONTROLLER = new WorkerLocationController();

        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(configFilePath.openStream()))) {
            lines = reader.lines().toList();
        } catch (IOException e) {
            throw new IOException("Failed to load board configuration from resource.", e);
        }



        // Count the number of available positions ('X') in the board configuration
        long positionCount = lines.stream()
                .flatMapToInt(String::chars)
                .filter(c -> c == 'X')
                .count();


        int height = lines.size();
        int width = lines.stream().mapToInt(String::length).max().orElse(0);


        // Validate the board configuration
        if (positionCount <= numberOfPlayers * 2L) {
            throw new IllegalArgumentException("The board should have at least more than " + (numberOfPlayers * 2L) + " locations.");
        }

        this.BOARD_PANEL.setLayout(new GridLayout(height, width));
        this.SCROLL_PANE = new JScrollPane(
                BOARD_PANEL,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        for (int y_axis = 0; y_axis < height; y_axis++) {
            String line = lines.get(y_axis);
            for (int x_axis = 0; x_axis < line.length(); x_axis++) {
                char tile = line.charAt(x_axis);
                if (tile == 'X') {
                    Position pos = new Position(x_axis, y_axis);
                    createLocation(pos);
                }
            }
        }

        // Add adjacent exits only for existing positions
        for (Position pos : BOARD_LOCATIONS.keySet()) {
            Location from = BOARD_LOCATIONS.get(pos);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    Position to = new Position(pos.X + dx, pos.Y + dy);
                    addExitFrom(from, to);
                }
            }
        }

        // Reconstruct the layout with only valid tiles
        for (int y_axis = 0; y_axis < height; y_axis++) {
            for (int x_axis = 0; x_axis < width; x_axis++) {
                Position pos = new Position(x_axis, y_axis);
                Location loc = BOARD_LOCATIONS.get(pos);
                if (loc != null) {
                    BOARD_PANEL.add(loc.getLOCATION_BUTTON());
                } else {
                    BOARD_PANEL.add(new JLabel()); // filler for missing tiles
                }
            }
        }
    }


    /**
     * Get the JPanel representing the board.
     *
     * @return The JPanel representing the board.
     */
    public JPanel getBOARD_PANEL(){
        return this.BOARD_PANEL;
    }

    /**
     * Get the JScrollPane containing the board panel.
     *
     * @return The JScrollPane containing the board panel.
     */
    public JScrollPane getSCROLL_PANE(){
        return this.SCROLL_PANE;
    }

    /**
     * Get all locations on the board.
     *
     * @return A list of all locations on the board.
     */
    public List<Location> allLocations() {
        return new ArrayList<>(BOARD_LOCATIONS.values());
    }

    /**
     * Add an exit from the specified location to the specified position.
     *
     * @param from The location from which to add the exit.
     * @param position The position to which the exit leads.
     */
    public void addExitFrom(Location from, Position position) {
        if (this.BOARD_LOCATIONS.get(position) != null){
            from.addExit(this.at(position));
        }
    }

    /**
     * Create a new location on the board at the specified position.
     *
     * @param position The position of the new location.
     */
    public void createLocation(Position position){
        Location newLocation = new Location(this, position, new Floor());

        this.BOARD_LOCATIONS.put(position, newLocation);
    }

    /**
     * Get the location at the specified position.
     *
     * @param position The position of the location.
     * @return The location at the specified position.
     */
    private Location at(Position position) {
        return this.BOARD_LOCATIONS.get(position);
    }

    /**
     * Get the worker at the specified location.
     *
     * @param location The location of the worker.
     * @return The worker at the specified location.
     */
    public Worker getWorkerAt(Location location){
        return WORKER_LOCATION_CONTROLLER.getWorkerAt(location);
    }

    /**
     * Move a worker to the specified location.
     *
     * @param worker The worker to move.
     * @param location The location to move the worker to.
     */
    public void moveWorker(Worker worker, Location location){
        WORKER_LOCATION_CONTROLLER.moveWorker(worker, location);
    }

    /**
     * Add a worker to the specified location.
     *
     * @param worker The worker to add.
     * @param location The location to add the worker to.
     */
    protected void addWorker(Worker worker, Location location){
        WORKER_LOCATION_CONTROLLER.addWorker(worker, location);
        location.getGround().addCap(worker);
    }

    /**
     * Remove a worker from the board.
     *
     * @param worker The worker to remove.
     */
    public void removeWorker(Worker worker){
        WORKER_LOCATION_CONTROLLER.removeWorker(worker);
    }

    /**
     * Check if a worker is at the specified location.
     *
     * @param location The location to check.
     * @return True if a worker is at the specified location, false otherwise.
     */
    public boolean isWorkerAt(Location location){
        return WORKER_LOCATION_CONTROLLER.isWorkerAt(location);
    }

    /**
     * Get the location of the specified worker.
     *
     * @param worker The worker whose location to get.
     * @return The location of the specified worker.
     */
    public Location locationOfWorker(Worker worker){
        return WORKER_LOCATION_CONTROLLER.locationOfWorker(worker);
    }

    /**
     * Refresh the UI of the board.
     */
    public void refreshBoardUI() {
        for (Location loc : allLocations()) {
            JButton button = loc.getLOCATION_BUTTON();
            Worker worker = getWorkerAt(loc);
            if (worker!=null) {
                ImageIcon icon = worker.getICON();
                Image img = icon.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH);
                ImageIcon newIcon = new ImageIcon(img);
                button.setIcon(newIcon);
            } else {
                button.setIcon(null);
            }
            Ground ground = loc.getGround();
            String groundText = ground.toString();
            button.setText(groundText);
        }
    }
}
