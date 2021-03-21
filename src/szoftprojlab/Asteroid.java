package szoftprojlab;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Asteroid.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Entity;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.Resource;
import szoftprojlab.skeleton.ObjectHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Asteroid {
	private int idx;
	private int layers;
	private boolean isEmpty = true;
	private boolean nearSun = false;
	private Resource resource;
	private List<Asteroid> neighbors = new ArrayList<>();
	private List<Entity> entities = new ArrayList<>();
	private List<TeleportGate> gates = new ArrayList<>();

	public Asteroid(int ID, int numberOfLayers) {
		System.out.println("Asteroid - create");
		idx = ID;
		numberOfLayers = layers;
		System.out.println("return from Asteroid - create");
	}

	public List<Entity> GetEntities() {
		return entities;
	}

	public void AddNeighbor(Asteroid newNeighbor) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".AddNeighbor()");
		neighbors.add(newNeighbor);
		if (!newNeighbor.neighbors.contains(this))
			newNeighbor.AddNeighbor(this);
		System.out.println("return from "+objectName+".AddNeighbor()");
	}

	public int GetLayerThickness() {
		return layers;
	}

	public void SunStorm() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".SunStorm()");

		System.out.print("Can the entities hide in the asteroid? (Y|N) ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();

		if (!input.equalsIgnoreCase("Y")) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).SunStorm();
			}
		}

		System.out.println("return from "+objectName+".SunStorm()");
	}
	
	public void ChangeNearSun() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".ChangeNearSun()");
		if (resource != null)
			resource.SeeSun(this);
		System.out.println("return from "+objectName+".ChangeNearSun()");
	}
	
	public void Explode() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Explode()");
		List<Entity> entitiesCopy = new ArrayList<Entity>(entities);
		entitiesCopy.forEach(Entity::Explode);
		System.out.println("return from "+objectName+".Explode()");
	}
	
	public void Drill() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Drill()");

		System.out.print("Did the drill strike through the asteroid? (Y|N) ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();

		if (input.equalsIgnoreCase("Y")) {
			System.out.print("Is the sun near? (Y|N) ");
			input = scanner.next();

			if (input.equalsIgnoreCase("Y")) {
				if (resource != null)
					resource.SeeSun(this);
			}
		}

		System.out.println("return from "+objectName+".Drill()");
	}
	
	public void Accept(Entity entity) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName + ".Accept()");
		if (!entities.contains(entity))
			entities.add(entity);
		entity.SetAsteroid(this);

		System.out.print("Do you want to run the check for victory? (Y|N) ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();

		if (input.equalsIgnoreCase("Y")) {
			Game game = Game.getInstance();
			oh.add(game, "game");
			game.CheckForVictory(new ArrayList<>());
		}

		System.out.println("return from "+objectName+".Accept()");
	}
	
	public void Remove(Entity entity) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Remove()");
		entities.remove(entity);
		System.out.println("return from "+objectName+".Remove()");
	}
	
	public void Mine(Player player) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Mine()");

		if (resource != null)
			player.AddResource(resource);

		System.out.println("return from "+objectName+".Mine()");
	}
	
	public void Place(Resource resource) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".Place()");
		System.out.println("return from "+objectName+".Place()");
	}
	
	public Asteroid GetNeighbor(int idx) {
		return null;
	}
	
	public TeleportGate GetTeleportGate(int idx) {
		return null;
	}

	public List<TeleportGate> GetTeleportGates() {
		return gates;
	}
	
	public void AddResource(Resource resource) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".AddResource()");
		if (this.resource == null)
			this.resource = resource;
		System.out.println("return from "+objectName+".AddResource()");
	}

	public void DestroyResource() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".DestroyResource()");
		System.out.println("return from "+objectName+".DestroyResource()");
	}
	
	public void PlaceTeleportGate(TeleportGate gate) {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".PlaceTeleportGate()");

		if (!gates.contains(gate)) {
			gates.add(gate);
			gate.Place(this);
		}

		System.out.println("return from "+objectName+".PlaceTeleportGate()");
	}

	public Asteroid GetRandomNeighbor() {
		ObjectHolder oh = ObjectHolder.getInstance();
		String objectName = oh.get(this);
		System.out.println(objectName+".GetRandomNeighbor()");
		if (neighbors.size() == 0)
			return null;
		System.out.println("return from "+objectName+".GetRandomNeighbor()");
		return neighbors.get(0);
	}

	public void Step() {

	}
}
