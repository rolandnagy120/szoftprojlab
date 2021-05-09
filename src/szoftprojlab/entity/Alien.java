package szoftprojlab.entity;

import szoftprojlab.Asteroid;
import szoftprojlab.Main;
import szoftprojlab.View;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Entity implements Miner {
    private List<Resource> inventory = new ArrayList<>();
    private int idx;
    private static int id = 0;

    /**
     * Creates the alien on the given asteroid
     * @param asteroid  the asteroid where the alien will be on
     */
    public Alien(Asteroid asteroid) {
        asteroid.Accept(this);
        this.asteroid = asteroid;
        idx = id++;
    }

    /**
     * Resets the alien id counter
     */
    public static void resetId() {
        id = 0;
    }

    /**
     * Gets the id of the alien
     * @return  the id of the alien
     */
    public int GetId() {
        return idx;
    }

    /**
     * The alien steps in the round
     * It will try to mine the current asteroid,
     * if it cannot, then it will move on
     */
    @Override
    public void Step() {
        Main.println("Alien " + idx + " steps:");
        String resource = asteroid.GetResourceName();
        if ((resource.equals("Unknown")) || (resource.equals("Empty"))) {
            if (nextAsteroid.isEmpty()) {
                MoveTo(asteroid.GetRandomNeighbor());
            } else {
                MoveTo(nextAsteroid.get(0));
                nextAsteroid.remove(0);
            }
        } else {
            Mine();
        }
    }

    /**
     * The alien cannot drill, so its removed
     * @return  the mine was not successful
     */
    @Override
    public boolean Drill() {
        return false;
    }

    /**
     * Draws the aliend to the game view
     * @param activePlayer  the currently active player
     * @param view          the game view
     */
    @Override
    public void draw(Player activePlayer, View view) {
        view.drawAlien(this);
    }

    /**
     * The alien mines the current asteroid
     * @return  the mine was successful
     */
    @Override
    public boolean Mine() {
        Main.println("Mining Asteroid " + asteroid.GetId());
        asteroid.Mine(this);
        return true;
    }

    /**
     * Adds a resource to the asteroid
     * @param resource  the resource that will be added
     * @return  the add was successful
     */
    @Override
    public boolean AddResource(Resource resource) {
        inventory.add(resource);
        return true;
    }

    public String toString() {
        return "\tAlien "+idx;
    }
}
