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
import szoftprojlab.resource.*;

import java.util.List;

public class Robot extends Entity {
	private static Blueprint robotBlueprint = new Blueprint(new Iron(), new Coal(), new Uranium());

	public Robot() {
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
		Asteroid newAsteroid = asteroid.GetRandomNeighbor();
		asteroid.Remove(this);
		if (newAsteroid != null)
			newAsteroid.Accept(this);
	}
	
	public void Robot(Asteroid asteroid) {
	}

	/**
	 * Specifies if the robot can be crafted
	 * @param rs - list of resources, should be the inventory of a player
	 * @return - the given inventory minus the needed resources for crafting. If its size doesn't change,
	 * then the robot cannot be crafted
	 */
	public static List<Resource> CanCraft(List<Resource> rs) {
		List<Resource> list = robotBlueprint.IsCraftable(rs);
		return list;
	}
}
