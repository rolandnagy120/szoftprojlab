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
import szoftprojlab.entity.Player;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Asteroid {
    private int idx;
    private int layers;
    //private boolean isEmpty = true;
    private boolean nearSun = false;
    private Resource resource;
    private List<Asteroid> neighbors = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<TeleportGate> gates = new ArrayList<>();
    private boolean exploded = false;

    private int x = 0;
    private int y = 0;
    public static final int size = 50;
    public boolean canMoveHere = false;

    /**
     * Creates an asteroid with the given id in the given position
     * @param ID    the id of the asteroid
     * @param x     the x position of the asteroid
     * @param y     the y position of the asteroid
     */
    public Asteroid(int ID, int x, int y) {
        idx = ID;
        nearSun = false;
        this.x = x;
        this.y = y;
        System.out.println(this.x);
        System.out.println(this.y);
    }

    /**
     * Creates an asteroid with the given number of layers
     * @param ID                the id of the asteroid
     * @param numberOfLayers    the number of layers on the asteroid
     */
    public Asteroid(int ID, int numberOfLayers) {
        idx = ID;
        layers = numberOfLayers;
        nearSun = false;
    }

    /**
     * Calls the draw function in the view for
     * the asteroid, and everything on it
     *
     * @param activePlayer the current active player
     * @param view         the view
     */
    public void draw(Player activePlayer, View view) {
        view.drawAsteroid(this);

        gates.forEach(gate -> gate.draw(view));
        entities.forEach(entity -> entity.draw(activePlayer, view));
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
     * @param newNeighbor   the asteroid that will be the new neighbor
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
        for (Asteroid a : neighbors)
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

    /**
     * Accepts an entity
     * The entity will be on this asteroid from this point
     *
     * @param entity - the entity that moved here
     */
    public void Accept(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
            CheckForVictory();
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
     * @param idx   id of the wanted teleport gate
     * @return      the teleport gate with the given id, or null
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
     * @return  the teleport gates on the asteroid
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

    /**
     * Removes the given teleport
     *
     * @param gate the gate than needs the removing
     */
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
        Random rnd = new Random();
        int randomIndex = rnd.ints(0, neighbors.size())
                .findFirst()
                .getAsInt();

        return neighbors.get(randomIndex);
    }

    /**
     * Gets the identifier of the asteroid
     *
     * @return asteroid idx
     */
    public int GetId() {
        return idx;
    }

    /**
     * Sunstorm hits this asteroid, and it continues to
     * the neighbors, depending on the remainingDepth
     *
     * @param remainingDepth the remaining number of recursive iterations
     */
    public void SendSunStorm(int remainingDepth) {
        SunStorm();
        if (remainingDepth > 0) {
            for (Asteroid neighbor : neighbors) {
                neighbor.SendSunStorm(remainingDepth - 1);
            }
        }
    }

    public String Save() {
        StringBuilder save = new StringBuilder();
        save.append("create asteroid ").append(idx).append(" ").append(x).append(" ").append(y).append("\n");
        save.append("set asteroid ").append(idx).append(" layer ").append(layers).append("\n");
        if (resource != null)
            save.append("set asteroid ").append(idx).append(" resource ").append(resource.save()).append("\n");
        //set asteroid 1 close to sun
        //set asteroid 1 distant to sun
        save.append("set asteroid ").append(idx);
        if (nearSun)
            save.append(" close to sun\n");
        else
            save.append(" distant to sun\n");
        return save.toString();
    }

    /**
     * Gets the name of the resource
     *
     * @return name of the resource
     */
    public String GetResourceName() {
        if (layers > 0)
            return "Unknown";
        if (resource == null)
            return "Empty";
        return resource.getClass().getSimpleName();
    }

    /**
     * Gets the identifiers of the neighbor asteroids
     *
     * @return the ids of the neighbors
     */
    public int[] GetNeighborsIds() {
        return neighbors.stream().mapToInt(neighbor -> neighbor.idx).toArray();
    }

    /**
     * Sets the layers of the asteroid
     *
     * @param layers the number of layers the asteroid should have
     */
    public void SetLayers(int layers) {
        this.layers = layers;
    }

    @Override
    public String toString() {
        //if (exploded)
        //    return "";
        //String ret = "create asteroid "+idx+" "+x+" "+y;

        /*String ret = "Asteroid " + idx + ":\n" +
                "Neighbours: ";
        for (Asteroid n : neighbors)
            ret += n.idx + " ";
        ret += "\nlayers: " + layers + "\n" +
                "resource: " + (resource == null ? "empty" : resource) + "\n" +
                "nearsun: " + nearSun + "\n" +
                "Gates:" + (gates.isEmpty() ? "No gates on asteroid\n" : "\n");
        for (TeleportGate g : gates)
            ret += g.toString();
        ret += "Entities: " + (entities.isEmpty() ? "No entities on asteroid\n" : "\n");
        for (Entity e : entities)
            ret += e.toString();
        return ret + "\n";*/
        return "";
    }

    /**
     * Sets the asteroid distant to the sun
     */
    public void SetDistantToSun() {
        nearSun = false;
    }

    /**
     * Returns the x coordinate of the asteroid
     *
     * @return x coordinate
     */
    public int GetX() {
        return this.x;
    }

    /**
     * Returns the y coorinate of the asteroid
     *
     * @return y coorinate
     */
    public int GetY() {
        return this.y;
    }

    /**
     * Returns true if the given position is on the asteroid
     *
     * @param clickX the x coordinate of the click
     * @param clickY the y coordinate of the click
     * @return The clicks is on the asteroid
     */
    public boolean IsClicked(int clickX, int clickY) {
        return (clickX >= x && clickX <= x + size) && (clickY >= y && clickY <= y + size);
    }

    /**
     * Returns the picture thats needs to be painted on screen
     *
     * @return the path to the picture
     */
    public String GetCurrentPicture() {
        if (layers > 0)
            return canMoveHere ? "assets/asteroid_outlined.png" : "assets/asteroid.png";
        if (resource == null)
            return canMoveHere ? "assets/asteroid_empty_outlined.png" : "assets/asteroid_empty.png";
        String resourcePart = resource.GetImagePart();
        return canMoveHere ?
                "assets/asteroid_with_" + resourcePart + "_outlined.png" :
                "assets/asteroid_with_" + resourcePart + ".png";
    }

    public String save_neighbours() {
        StringBuilder save = new StringBuilder();
        for (Asteroid a : neighbors) {
            save.append("set asteroid ").append(idx).append(" neighbour ").append(a.GetId()).append("\n");
        }
        return save.toString();
    }

}