package szoftprojlab.entity;

import szoftprojlab.Asteroid;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Entity implements Miner {
    private List<Resource> inventory = new ArrayList<>();

    public Alien(Asteroid asteroid) {
        asteroid.Accept(this);
        this.asteroid = asteroid;
    }

    @Override
    public void Step() {
        asteroid.Mine(this);

        var newAsteroid = asteroid.GetRandomNeighbor();
        asteroid.Remove(this);
        newAsteroid.Accept(this);
    }

    @Override
    public boolean Drill() {
        return false;
    }

    @Override
    public boolean Mine() {
        asteroid.Mine(this);
        return true;
    }

    @Override
    public boolean AddResource(Resource resource) {
        inventory.add(resource);
        return true;
    }
}
