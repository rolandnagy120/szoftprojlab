package szoftprojlab.entity;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Entity.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.*;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Steppable {
    protected Asteroid asteroid;
    protected List<Asteroid> nextAsteroid = new ArrayList<Asteroid>();

    /**
     * Returns the asteroid which the entity is on
     *
     * @return  the asteroid that the entity is on currently
     */
    public Asteroid GetAsteroid() {
        return asteroid;
    }

    /**
     * Sets the current asteroid
     *
     * @param asteroid  the asteroid where the entity will be on next
     */
    public void SetAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * A Sunstorm hits the entity
     */
    public void SunStorm() {
        Timer timer = new Timer();
        timer.RemoveSteppable(this);
        asteroid.Remove(this);
    }

    /**
     * The entity explodes
     */
    public void Explode() {
    }

    /**
     * Moves the entity from the current asteroid to a new asteroid
     *
     * @param newAsteroid - the next asteroid the entity will be on
     */
    public void MoveTo(Asteroid newAsteroid) {
        if (newAsteroid == null || asteroid.GetNeighbor(newAsteroid.GetId()) == null) {
            return;
        }

        if (this.asteroid != null)
            this.asteroid.Remove(this);
        newAsteroid.Accept(this);
        asteroid = newAsteroid;
        Main.println("Moved to Asteroid "+asteroid.GetId());
    }

    /**
     * The entity drills the asteroid its on
     */
    public boolean Drill() {
        Main.println("Drilling asteroid " + asteroid.GetId());
        asteroid.Drill();
        return true;
    }

    /**
     * The entity goes through a teleportgate,
     * and ens up at the asteroid where the gate
     * pair sits
     *
     * @param gate - the gate that the entity goes through
     */
    public void Teleport(TeleportGate gate) {
        Asteroid pairAsteroid = gate.GetPairAsteroid();
        asteroid.Remove(this);
        pairAsteroid.Accept(this);
        Main.println("Teleported to "+pairAsteroid.GetId());
    }

    public List<Resource> GetInventory() {
        return new ArrayList<>();
    }

    public void SetNextAsteroid(Asteroid a) {
        nextAsteroid.add(a);
    }

    public abstract void draw(Player activePlayer, View view);
}
