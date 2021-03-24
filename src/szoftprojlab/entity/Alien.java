package szoftprojlab.entity;

import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Entity implements Miner {
    private List<Resource> inventory = new ArrayList<>();

    @Override
    public void Step() {

    }

    @Override
    public void Drill() {

    }

    @Override
    public void Mine() {
        asteroid.Mine(this);
    }

    @Override
    public void AddResource(Resource resource) {
        inventory.add(resource);
    }
}