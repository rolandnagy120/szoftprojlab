package szoftprojlab;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : TeleportGate.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//




public class TeleportGate {
	private int idx;
	private TeleportGate pair;
	private Asteroid asteroid;

	public TeleportGate(int ID) {
		idx = ID;

		System.out.println("TeleportGate - create");
		System.out.println("return from TeleportGate - create");
	}

	public Asteroid GetPairAsteroid() {
		return null;
	}
	
	public void Place(Asteroid asteroid) {
		System.out.println("TeleportGate.Place()");

		this.asteroid = asteroid;

		System.out.println("return from TeleportGate.Place()");
	}
	
	public void SetPair(TeleportGate gate) {
		System.out.println("TeleportGate.SetPair()");

		pair = gate;
		if (!gate.HasPair())
			gate.SetPair(this);

		System.out.println("return from TeleportGate.SetPair()");
	}

	public Boolean HasPair() {
		return pair != null;
	}
}
