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
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Iron;
import szoftprojlab.resource.Resource;
import szoftprojlab.resource.Uranium;
import szoftprojlab.skeleton.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

public class Robot extends Entity {
	private static Blueprint robotBlueprint = new Blueprint(new Iron(), new Coal(), new Uranium());

	public Robot() {
		System.out.println("Robot - create");
		System.out.println("return from Robot - create");
	}

	/**
	 * The robot steps
	 */
	public void Step() {
	}

	/**
	 * An explosion hits the robot
	 * The robots is cast aside on a neighbor asteroid
	 */
	public void Explode() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Explode()");
		Asteroid newAsteroid = asteroid.GetRandomNeighbor();
		asteroid.Remove(this);
		if (newAsteroid != null)
			newAsteroid.Accept(this);

		System.out.println("return from "+objectName+".Explode()");
	}

	/**
	 * Specifies if the robot can be crafted
	 * @param rs - list of resources, should be the inventory of a player
	 * @return - the given inventory minus the needed resources for crafting. If its size doesn't change,
	 * then the robot cannot be crafted
	 */
	public static List<Resource> CanCraft(List<Resource> rs) {
		return new ArrayList<>();
	}
}
