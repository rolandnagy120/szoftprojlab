package szoftprojlab.entity;

import szoftprojlab.Asteroid;
import szoftprojlab.Main;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Entity implements Miner {
    private List<Resource> inventory = new ArrayList<>();
    private int idx;
    private static int id = 0;

    public Alien(Asteroid asteroid) {
        asteroid.Accept(this);
        this.asteroid = asteroid;
        idx = id++;
    }

    public static void resetId() {
        id = 0;
    }

    public int GetId() {
        return idx;
    }

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

    @Override
    public boolean Drill() {
        return false;
    }

    @Override
    public boolean Mine() {
        Main.println("Mining Asteroid " + asteroid.GetId());
        asteroid.Mine(this);
        return true;
    }

    @Override
    public boolean AddResource(Resource resource) {
        inventory.add(resource);
        return true;
    }

    public String toString() {
        return "\tAlien "+idx;
    }
}
