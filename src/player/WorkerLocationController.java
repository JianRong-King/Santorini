package player;

import board.Location;

import java.util.*;
/**
 * The WorkerLocationController class is responsible for managing the mapping.
 * Enforcing one worker per location.
 *
 * @author Yong Han Lee
 * @version 1.0
 */
public class WorkerLocationController {
    private final Map<Worker, Location> WORKER_LOCATION_MAP;
    private final Map<Location, Worker> LOCATION_WORKER_MAP;

    /**
     * Constructor for WorkerLocationController.
     */
    public WorkerLocationController(){
        this.WORKER_LOCATION_MAP = new HashMap<Worker, Location>();
        this.LOCATION_WORKER_MAP = new HashMap<Location, Worker>();
    }

    /**
     * Adds a new Worker at the given Location.
     *
     * @param worker the Worker to place
     * @param location where to place the Worker
     * @throws IllegalArgumentException if the Worker is already placed or there is already a Worker at the target Location
     */
    public void addWorker(Worker worker, Location location){
        if (this.isWorkerExists(worker)) {
            throw new IllegalArgumentException("Worker already exists in the map.");
        }
        if (this.isWorkerAt(location)) {
            throw new IllegalArgumentException("Location already has a worker.");
        }

        WORKER_LOCATION_MAP.put(worker, location);
        LOCATION_WORKER_MAP.put(location, worker);
    }

    /**
     * Removes a Worker from the Game.
     *
     * @param worker the Worker to remove
     */
    public void removeWorker(Worker worker) {
        Location location = WORKER_LOCATION_MAP.get(worker);
        WORKER_LOCATION_MAP.remove(worker);
        LOCATION_WORKER_MAP.remove(location);
    }

    /**
     * Moves the worker to a new location and updates the mapping accordingly.
     * @param worker the Worker to move
     * @param location the new Location for the Worker
     */
    public void moveWorker(Worker worker, Location location){
        Location previousLocation = this.WORKER_LOCATION_MAP.get(worker);
        if (this.isWorkerAt(location)) {
            throw new IllegalArgumentException("Location already has a worker.");
        }

        this.WORKER_LOCATION_MAP.put(worker, location);
        this.LOCATION_WORKER_MAP.remove(previousLocation);
        this.LOCATION_WORKER_MAP.put(location, worker);
    }

    /**
     * Checks if a worker is at the given location.
     * @param location the Location to check
     * @return true if a worker is at the location, false otherwise
     */
    public boolean isWorkerAt(Location location){
        return this.LOCATION_WORKER_MAP.containsKey(location);
    }

    /**
     * Checks if a worker exists in the Board.
     * @param worker the Worker to check
     * @return true if the Worker exists, false otherwise
     */
    public boolean isWorkerExists(Worker worker){
        return this.WORKER_LOCATION_MAP.containsKey(worker);
    }

    /**
     * Returns the Worker at the given Location.
     * @param location the Location to check
     * @return the Worker at the location
     */
    public Worker getWorkerAt(Location location){
        return this.LOCATION_WORKER_MAP.get(location);
    }

    /**
     * Returns the Location of the given Worker.
     * @param worker the Worker to check
     * @return the Location of the Worker
     */
    public Location locationOfWorker(Worker worker){
        return this.WORKER_LOCATION_MAP.get(worker);
    }
}
