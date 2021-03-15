package szoftprojlab;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Player;
import szoftprojlab.resource.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game singleClassInstance = null;

    private Sun sun;
    private List<Player> players = new ArrayList<>();
    private static Blueprint baseBluebrint = new Blueprint(
            new Iron(), new Iron(), new Iron(),
            new Coal(), new Coal(), new Coal(),
            new Uranium(), new Uranium(), new Uranium(),
            new Ice(), new Ice(), new Ice()
    );

    public void StartGame() {
        sun = Sun.getInstance();
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        a1.AddNeighbor(a2);
        Player player = new Player();
    }

    public void PlayerDie(Player player) {
        players.remove(player);

        if (players.size() <= 1) {
            EndGame();
        }
    }

    public void AddPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public void EndGame() {
    }

    public void CheckForVictory(List<Resource> resources) {
        // playerben már kész a craft check
//        baseBluebrint.IsCraftable(resources);
    }

    public static Game getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new Game();

        return singleClassInstance;
    }
}
