package szoftprojlab.entity;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Player.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


import szoftprojlab.Game;
import szoftprojlab.TeleportGate;
import szoftprojlab.resource.Resource;
import szoftprojlab.resource.ResourceNames;
import szoftprojlab.resource.ResourceStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements ResourceStorage {
	private List<TeleportGate> gates = new ArrayList<>();
	private List<Resource> inventory = new ArrayList<>();

	public Player() {
		System.out.println("Player - create");
		System.out.println("return from Player - create");
	}

	public List<Resource> GetInventory() {
		return inventory;
	}

	public void Step() {
	}
	
	public void Mine() {
		System.out.println("Player.Mine()");

		asteroid.Mine(this);

		System.out.println("return from Player.Mine()");
	}
	
	public void PlaceResource(Resource resource) {
		System.out.println("Player.PlaceResource()");

		if (inventory.contains(resource))
			asteroid.Place(resource);

		System.out.println("return from Player.PlaceResource()");
	}
	
	public void PlaceGate() {
		System.out.println("Player.PlaceGate()");

		if (asteroid != null && gates.size() > 0) {
			asteroid.PlaceTeleportGate(gates.get(0));
			gates.remove(0);
		}
		System.out.println("return from Player.PlaceGate()");
	}
	
	public void MakeGates() {
		System.out.println("Player.MakeGates()");

		try {
			System.out.print("Does the player have enough resources to create the gates? (Y|N) ");
			char input = (char)System.in.read();

			if (Character.toUpperCase(input) == 'Y') {
				TeleportGate tg1 = new TeleportGate(1);
				TeleportGate tg2 = new TeleportGate(2);
				tg1.SetPair(tg2);
				gates.add(tg1);
				gates.add(tg2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("return from Player.MakeGates()");
	}
	
	public void MakeAndPlaceRobot() {
		System.out.println("Player.MakeAndPlaceRobot()");

		try {
			System.out.print("Does the player have enough resources to create the robot? (Y|N) ");
			char input = (char)System.in.read();

			if (Character.toUpperCase(input) == 'Y') {
				Robot r = new Robot();
				asteroid.Accept(r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("return from Player.MakeAndPlaceRobot()");
	}

	public List<TeleportGate> GetTeleportGates() {
		return gates;
	}

	public void AddResource(ResourceNames name, Resource resource) {
		System.out.println("Player.AddResource()");
		inventory.add(resource);
		System.out.println("return from Player.AddResource()");
	}

	@Override
	public void AddResourcesToComparator() {
		System.out.println("Player.AddResourcesToComparator()");
		Game game = Game.getInstance();
		inventory.forEach(game::AddToOwner);
		System.out.println("return from Player.AddResourcesToComparator()");
	}

	@Override
	public void Explode() {
		System.out.println("Player.Explode()");
		Game game = Game.getInstance();
		game.PlayerDie(this);
		System.out.println("return from Player.Explode()");
	}
}
