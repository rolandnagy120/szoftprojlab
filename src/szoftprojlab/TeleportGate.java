package szoftprojlab;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : TeleportGate.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.resource.*;

import java.util.List;

public class TeleportGate {
	private int idx;
	private TeleportGate pair;
	private Asteroid asteroid;
	private static Blueprint teleportgateBlueprint = new Blueprint(new Iron(), new Iron(), new Ice(), new Uranium());

	public TeleportGate(int ID) {
		idx = ID;
	}

	/**
	 * Gets the asteroid which holds the pair of this gate
	 * @return
	 */
	public Asteroid GetPairAsteroid() {
		return pair.asteroid;
	}

	/**
	 * Places the gate on the given asteroid
	 * @param asteroid - the asteroid that will hold the gate
	 */
	public void Place(Asteroid asteroid) {
		this.asteroid = asteroid;
	}

	/**
	 * Sets the pair of the asteroid, so they are linked
	 * @param gate - the gate that will be the pair
	 */
	public void SetPair(TeleportGate gate) {
		pair = gate;
		if (!gate.HasPair())
			gate.SetPair(this);
	}

	/**
	 * Returns if the gate has a pair
	 * Only for unit testing
	 * @return
	 */
	public Boolean HasPair() {
		return pair != null;
	}

	/**
	 * Specifies if the gate can be crafted
	 * @param rs - list of resources, should be the inventory of a player
	 * @return - the given inventory minus the needed resources for crafting. If its size doesn't change,
	 * then the gate cannot be crafted
	 */
	public static List<Resource> CanCraft(List<Resource> rs) {
		List<Resource> list = teleportgateBlueprint.IsCraftable(rs);
		return list;
	}

	public int GetId() {
		return idx;
	}
}
