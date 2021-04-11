package szoftprojlab;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Asteroid.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Entity;
import szoftprojlab.entity.Miner;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asteroid {
    private int idx;
    private int layers;
    private boolean isEmpty = true;
    private boolean nearSun = false;
    private Resource resource;
    private List<Asteroid> neighbors = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<TeleportGate> gates = new ArrayList<>();
    private Asteroid explosionNeighbour = null;
    private boolean exploded=false;


    public Asteroid(int ID) {
        idx = ID;
        nearSun = false;
    }

    public Asteroid(int ID, int numberOfLayers) {
        idx = ID;
        layers = numberOfLayers;
        nearSun = false;
    }

    /**
     * Gets the entities which are on this asteroid
     *
     * @return - entities on the asteroid
     */
    public List<Entity> GetEntities() {
        return entities;
    }

    /**
     * Adds the given asteroid to the neighbors of this asteroid
     *
     * @param newNeighbor
     */
    public void AddNeighbor(Asteroid newNeighbor) {
        neighbors.add(newNeighbor);
        if (!newNeighbor.neighbors.contains(this))
            newNeighbor.AddNeighbor(this);
    }

    /**
     * Returns the number of the layers on the asteroid
     * Only for unit testing
     *
     * @return - number of layers
     */
    public int GetLayerThickness() {
        return layers;
    }

    /**
     * A sunstorm hits the asteroid
     * Calls sunstorm on all the entities if they cannot
     * hid in the core
     * To hide in the core, the layers should be 0,
     * and the resource should be null
     * Moves the teleport gates to a neighbor
     */
    public void SunStorm() {
        if (layers > 0 || resource != null) {
            List<Entity> entitiesCopy = new ArrayList<>(entities);
            entitiesCopy.forEach(Entity::SunStorm);
        }

        gates.forEach(TeleportGate::StartMoving);
    }

    /**
     * The sun gets close
     */
    public void ChangeNearSun() {
        this.nearSun = !this.nearSun;
        SeeSunIfNeeded();
    }

    public void SetCloseToSun() {
        nearSun = true;
    }

    private void SeeSunIfNeeded() {
        if (resource != null && layers == 0 && nearSun)
            resource.SeeSun(this);
    }

    /**
     * The asteroid explodes from the inside
     * Calls the Explode function on every entity,
     * thats on this asteroid
     */
    public void Explode() {
        List<Entity> entitiesCopy = new ArrayList<Entity>(entities);
        entitiesCopy.forEach(Entity::Explode);
        //TODO
        for (TeleportGate g : gates)
            g.Explode();
        gates.clear();
        exploded = true;
        for(Asteroid a : neighbors)
            a.neighbors.remove(a);


    }

    /**
     * An entity drills the asteroid
     * The number of layers is decresed be one
     * If the layers hit 0, and the sun is near,
     * the SeeSun is called for the resource stored
     * in this asteroid
     */
    public void Drill() {
        if (layers > 0)
            layers--;
        if (layers == 0)
            Main.println("Asteroid breakthrough!");
        SeeSunIfNeeded();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.SetAsteroid(this);
    }

    /**
     * Accepts an entity
     * The entity will be on this asteroid from this point
     *
     * @param entity - the entity that moved here
     */
    public void Accept(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
            ChangeNearSun();
        }
        entity.SetAsteroid(this);
    }

    private void CheckForVictory() {
        List<Resource> inventorySum = new ArrayList<>();
        entities.forEach(entity -> {
            inventorySum.addAll(entity.GetInventory());
        });

        Game.getInstance().CheckForVictory(inventorySum);
    }

    /**
     * Removes an entity from the asteroid
     * The entity wont be on this asteroid from this point on
     *
     * @param entity - the entity that will be removed
     */
    public void Remove(Entity entity) {
        entities.remove(entity);
    }

    /**
     * A miner mines the resource stored in this asteroid
     * The resource is removed from the asteroid,
     * and then added to the inventory of the player
     *
     * @param miner - the miner that is doing the mining
     */
    public void Mine(Miner miner) {
        if (layers == 0 && resource != null) {
            miner.AddResource(resource);
            Main.println("Mine successful\nResource " + resource.toString() + " added to inventory");
            resource = null;
            CheckForVictory();
        }
    }

    /**
     * The player places back a resource
     * The resource will be in the core of the asteroid
     *
     * @param resource - the resource that will be placed back
     */
    public void Place(Resource resource) {
        if (this.resource == null && layers == 0) {
            this.resource = resource;
            Main.println("Resource placed");
            SeeSunIfNeeded();
        } else {
            Main.println("Couldn't place resource");
        }
    }

    /**
     * Gets a neightbor of this asteroid
     * Only for unit testing
     *
     * @param idx - the id of the wanted asteroid
     * @return - return the neighbor if found, else null
     */
    public Asteroid GetNeighbor(int idx) {
        for (Asteroid neighbor : neighbors) {
            if (neighbor.idx == idx) {
                return neighbor;
            }
        }
        return null;
    }

    /**
     * Gets the teleportgate with the given id
     * which is on this asteroid
     * Only for testing
     *
     * @param idx
     * @return
     */
    public TeleportGate GetTeleportGate(int idx) {
        for (TeleportGate gate : gates) {
            if (gate.GetId() == idx) {
                return gate;
            }
        }
        return null;
    }

    /**
     * Gets the list of teleportgates on the asteroid
     * Only for unit testing
     *
     * @return
     */
    public List<TeleportGate> GetTeleportGates() {
        return gates;
    }

    /**
     * Adds a resource to the core of the asteroid
     * The controller calls this function
     *
     * @param resource - the resource that should be added
     */
    public boolean AddResource(Resource resource) {
        if (this.resource == null) {
            this.resource = resource;
            return true;
        }
        return false;
    }

    /**
     * Destroy the resource stored in this asteroid
     */
    public void DestroyResource() {
        resource = null;
    }

    /**
     * The player places a teleportgate on this asteroid
     *
     * @param gate - the gate that will be placed
     */
    public void PlaceTeleportGate(TeleportGate gate) {
        AcceptTeleportGate(gate);
    }

    /**
     * Accepts a teleportgate
     *
     * @param gate - the gate that will be placed
     */
    private void AcceptTeleportGate(TeleportGate gate) {
        if (!gates.contains(gate)) {
            gates.add(gate);
            gate.Place(this);
        }
    }

    public void RemoveTeleportGate(TeleportGate gate) {
        gates.remove(gate);
    }

    /**
     * Removes a teleportgate
     */
    public void RemoveAllTeleportGates() {
        gates.clear();
    }

    /**
     * Gets a random neighbor of this asteroid
     *
     * @return - a random neighbor
     */
    public Asteroid GetRandomNeighbor() {
        if (neighbors.size() == 0)
            return null;
        if (explosionNeighbour != null)
            return explosionNeighbour;
        Random rnd = new Random();
        int randomIndex = rnd.ints(0, neighbors.size())
                .findFirst()
                .getAsInt();

        return neighbors.get(randomIndex);
    }

    public void SetExplosionNeighbour(int neighbourId) {
        for (Asteroid a : neighbors)
            if (a.GetId() == neighbourId)
                explosionNeighbour = a;
    }

    public int GetId() {
        return idx;
    }

    public void SendSunStorm(int remainingDepth) {
        SunStorm();
        if (remainingDepth > 0) {
            for (Asteroid neighbor : neighbors) {
                neighbor.SendSunStorm(remainingDepth - 1);
            }
        }
    }

    public int NeighborCount() {
        return neighbors.size();
    }

    public String GetResourceName() {
        if (layers > 0)
            return "Unknown";
        if (resource == null)
            return "Empty";
        return resource.getClass().getSimpleName();
    }

    public int[] GetNeighborsIds() {
        return neighbors.stream().mapToInt(neighbor -> neighbor.idx).toArray();
    }

    public void SetLayers(int layers) {
        this.layers = layers;
    }

    @Override
    public String toString() {
        if(exploded)
            return "";
        String ret = "Asteroid " + idx + ":\n" +
                "Neighbours: ";
        for (Asteroid n : neighbors)
            ret += n.idx + " ";
        ret += "\nlayers: " + layers + "\n" +
                "resource: " + (resource == null ? "empty" : resource) + "\n" +
                "nearsun: "+nearSun+"\n"+
                "Gates:" + (gates.isEmpty() ? "No gates on asteroid\n" : "\n");
        for (TeleportGate g : gates)
            ret += g.toString();
        ret += "Entities: " + (entities.isEmpty() ? "No entities on asteroid\n" : "\n");
        for (Entity e : entities)
            ret += e.toString();
        return ret + "\n";
    }

    public void SetDistantToSun() {
        nearSun = false;
    }

}