package szoftprojlab.resource;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Uranium.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


import szoftprojlab.Asteroid;

public class Uranium extends Resource {
	@Override
	public void SeeSun(Asteroid asteroid) {
		asteroid.Explode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Uranium) {
			return true;
		}

		return false;
	}
}
