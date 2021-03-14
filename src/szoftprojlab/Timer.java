package szoftprojlab;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Timer.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


import java.util.ArrayList;
import java.util.List;

public class Timer {
	private static Timer singleClassIntance = null;
	private final List<Steppable> steppables = new ArrayList<>();

	public void Tick() {
		System.out.println("Timer.Tick()");
		steppables.forEach(Steppable::Step);
		System.out.println("return from Timer.Tick()");
	}

	public void ClearSteppables() {
		steppables.clear();
	}

	public void AddSteppable(Steppable s) {
		System.out.println("Timer.AddSteppable()");
		if (!steppables.contains(s))
			steppables.add(s);
		System.out.println("return from Timer.AddSteppable()");
	}

	public static Timer getInstance() {
		if (singleClassIntance == null)
			singleClassIntance = new Timer();

		return singleClassIntance;
	}
}
