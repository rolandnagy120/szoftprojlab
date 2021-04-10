package szoftprojlab.entity;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Robot.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.Asteroid;
import szoftprojlab.Blueprint;
import szoftprojlab.Main;
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Iron;
import szoftprojlab.resource.Resource;
import szoftprojlab.resource.Uranium;

import java.util.List;

public class Robot extends Entity {
    private static Blueprint robotBlueprint = new Blueprint(new Iron(), new Coal(), new Uranium());
    private int idx;
    private static int id = 0;


    public Robot(Asteroid asteroid) {
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

    /**
     * The robot steps
     */
    public void Step() {
        Main.println("Robot " + idx + " steps:");
        if (asteroid.GetLayerThickness() > 0) {
            Drill();
        } else {
            if (nextAsteroid.isEmpty()) {
                MoveTo(asteroid.GetRandomNeighbor());
            } else {
                MoveTo(nextAsteroid.get(0));
                nextAsteroid.remove(0);
            }
        }
    }

    public void SetNextAsteroid(Asteroid a) {
        nextAsteroid.add(a);
    }

    /**
     * An explosion hits the robot
     * The robots is cast aside on a neighbor asteroid
     */
    public void Explode() {
        Asteroid newAsteroid = asteroid.GetRandomNeighbor();
        asteroid.Remove(this);
        if (newAsteroid != null)
            newAsteroid.Accept(this);
    }


    /**
     * Specifies if the robot can be crafted
     *
     * @param rs - list of resources, should be the inventory of a player
     * @return - the given inventory minus the needed resources for crafting. If its size doesn't change,
     * then the robot cannot be crafted
     */
    public static List<Resource> CanCraft(List<Resource> rs) {
        List<Resource> list = robotBlueprint.IsCraftable(rs);
        return list;
    }

    public String toString() {
        return "Robot "+idx+"\n";
    }
}
