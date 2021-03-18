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
		System.out.println("Asteroid.AddNeighbor()");
		neighbors.add(newNeighbor);
		if (!newNeighbor.neighbors.contains(this))
			newNeighbor.AddNeighbor(this);
		System.out.println("return from Asteroid.AddNeighbor()");
	}

	public int GetLayerThickness() {
		return layers;
	}

	public void SunStorm() {
		System.out.println("Asteroid.SunStorm()");

		System.out.print("Can the entities hide in the asteroid? (Y|N) ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();

		if (!input.equalsIgnoreCase("Y")) {
			entities.forEach(Entity::SunStorm);
		}

		System.out.println("return from Asteroid.SunStorm()");
	}
	
	public void ChangeNearSun() {
		System.out.println("Asteroid.ChangeNearSun()");
		if (resource != null)
			resource.SeeSun(this);
		System.out.println("return from Asteroid.ChangeNearSun()");
	}
	
	public void Explode() {
		System.out.println("Asteroid.Explode()");
		List<Entity> entitiesCopy = new ArrayList<Entity>(entities);
		entitiesCopy.forEach(Entity::Explode);
		System.out.println("return from Asteroid.Explode()");
	}
	
	public void Drill() {
		System.out.println("Asteroid.Drill()");

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

		System.out.println("return from Asteroid.Drill()");
	}
	
	public void Accept(Entity entity) {
		System.out.println("Asteroid.Accept()");
		if (!entities.contains(entity))
			entities.add(entity);
		entity.SetAsteroid(this);

		System.out.print("Do you want to run the check for victory? (Y|N) ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();

		if (input.equalsIgnoreCase("Y")) {
			Game.getInstance().CheckForVictory(new ArrayList<>());
		}

		System.out.println("return from Asteroid.Accept()");
	}
	
	public void Remove(Entity entity) {
		System.out.println("Asteroid.Remove()");
		entities.remove(entity);
		System.out.println("return from Asteroid.Remove()");
	}
	
	public void Mine(Player player) {
		System.out.println("Asteroid.Mine()");

		if (resource != null)
			player.AddResource(resource);

		System.out.println("return from Asteroid.Mine()");
	}
	
	public void Place(Resource resource) {
		System.out.println("Asteroid.Place()");
		System.out.println("return from Asteroid.Place()");
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
		System.out.println("Asteroid.AddResource()");
		if (this.resource == null)
			this.resource = resource;
		System.out.println("return from Asteroid.AddResource()");
	}

	public void DestroyResource() {
		System.out.println("Asteroid.DestroyResource()");
		System.out.println("return from Asteroid.DestroyResource()");
	}
	
	public void PlaceTeleportGate(TeleportGate gate) {
		System.out.println("Asteroid.PlaceTeleportGate()");

		if (!gates.contains(gate)) {
			gates.add(gate);
			gate.Place(this);
		}

		System.out.println("return from Asteroid.PlaceTeleportGate()");
	}

	public Asteroid GetRandomNeighbor() {
		System.out.println("Asteroid.GetRandomNeighbor()");
		if (neighbors.size() == 0)
			return null;
		System.out.println("return from Asteroid.GetRandomNeighbor()");
		return neighbors.get(0);
	}

	public void Step() {

	}
}
