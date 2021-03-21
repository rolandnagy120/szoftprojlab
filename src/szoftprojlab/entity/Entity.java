package szoftprojlab.entity;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : szoftprojlab
//  @ File Name : Entity.java
//  @ Date : 10/03/2021
//
//


import szoftprojlab.Asteroid;
import szoftprojlab.Steppable;
import szoftprojlab.TeleportGate;
import szoftprojlab.skeleton.ObjectHolder;

public abstract class Entity implements Steppable {
	protected Asteroid asteroid;

	/**
	 * Returns the asteroid which the entity is on
	 * @return
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}

	/**
	 * Sets the current asteroid
	 * @param asteroid
	 */
	public void SetAsteroid(Asteroid asteroid) {
		this.asteroid = asteroid;
	}

	/**
	 * A Sunstorm hits the entity
	 */
	public void SunStorm() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".SunStorm()");
		System.out.println(objectName+" is destroyed");
		System.out.println("return from "+objectName+".SunStorm()");
	}

	/**
	 * The entity explodes
	 */
	public void Explode() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Explode()");
		System.out.println("return from "+objectName+".Explode()");
	}

	/**
	 * Moves the entity from the current asteroid to a new asteroid
	 * @param newAsteroid - the next asteroid the entity will be on
	 */
	public void MoveTo(Asteroid newAsteroid) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".MoveTo()");

		if (this.asteroid != null)
			this.asteroid.Remove(this);
		newAsteroid.Accept(this);
		asteroid = newAsteroid;

		System.out.println("return from "+objectName+".MoveTo()");
	}

	/**
	 * The entity drills the asteroid its on
	 */
	public void Drill() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Drill()");
		asteroid.Drill();
		System.out.println("return from "+objectName+".Drill()");
	}

	/**
	 * The entity goes through a teleportgate,
	 * and ens up at the asteroid where the gate
	 * pair sits
	 * @param gate - the gate that the entity goes through
	 */
	public void Teleport(TeleportGate gate) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Teleport()");
		Asteroid pairAsteroid = gate.GetPairAsteroid();
		asteroid.Remove(this);
		pairAsteroid.Accept(this);
		System.out.println("return from "+objectName+".Teleport()");
	}
}
