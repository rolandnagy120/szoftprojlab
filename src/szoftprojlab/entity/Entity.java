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

	public Asteroid GetAsteroid() {
		return asteroid;
	}

	public void SetAsteroid(Asteroid asteroid) {
		this.asteroid = asteroid;
	}

	public void SunStorm() {
		Timer timer = new Timer();
		timer.RemoveSteppable(this);
		asteroid.Remove(this);
	}
	
	public void Explode() {
	}
	
	public void MoveTo(Asteroid newAsteroid) {
		if (asteroid.GetNeighbor(newAsteroid.GetId()) == null) {
			return;
		}

		if (this.asteroid != null)
			this.asteroid.Remove(this);
		newAsteroid.Accept(this);
		asteroid = newAsteroid;
	}
	
	public void Drill() {
		asteroid.Drill();
	}
	
	public void Teleport(TeleportGate gate) {
		Asteroid pairAsteroid = gate.GetPairAsteroid();
		asteroid.Remove(this);
		pairAsteroid.Accept(this);
	}

	public List<Resource> GetInventory() {
		return new ArrayList<>();
	}
}
