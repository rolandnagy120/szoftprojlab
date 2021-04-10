package szoftprojlab;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Entity;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game singleClassInstance = null;


    private Sun sun = Sun.getInstance();
    private Timer timer = Timer.getInstance();
    private List<Player> players = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();
    //private List<Entity> entities = new ArrayList<>();

    private boolean gameWon = false;
    private boolean endGame;

    /*
    Blueprint for the base
     */
    private static Blueprint baseBluebrint = new Blueprint(
            new Iron(), new Iron(), new Iron(),
            new Coal(), new Coal(), new Coal(),
            new Uranium(), new Uranium(), new Uranium(),
            new Ice(), new Ice(), new Ice()
    );

    /**
     * Starts the game
     */
    public void StartGame() {

        /*
        timer.ClearSteppables();
        timer.AddSteppable(sun);
        sun.ClearAsteroids();
        sun.Init(10, 0.01);
        */
        /*
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);
        timer.AddSteppable(player1);
        timer.AddSteppable(player2);

        Asteroid a1 = new Asteroid(1, 1);
        Asteroid a2 = new Asteroid(2, 1);
        a1.AddNeighbor(a2);
        a1.Accept(player1);
        a1.Accept(player2);*/

        /*

        int numberOfAsteroids = 10;
        var maxNeighbors = 4;

        for (int i = 1; i <= numberOfAsteroids; i++) {
            int asteroidLayer = 1;
            var asteroid = new Asteroid(i, asteroidLayer);
            asteroids.add(asteroid);
            sun.AddAsteroid(asteroid);
        }
        for (Asteroid asteroid : asteroids) {
            Main.setNeighbors(asteroid, asteroids, maxNeighbors);
        }
        asteroids.get(0).Accept(player1);
        asteroids.get(0).Accept(player2);


        int oneResourceCount = asteroids.size() / 5;
        if (oneResourceCount == 0) oneResourceCount = 1;
        int i = 0;
        while (i < oneResourceCount && i < asteroids.size()) {
            Coal coal = new Coal();
            asteroids.get(i).AddResource(coal);
            i++;
        }
        while (i < oneResourceCount * 2 && i < asteroids.size()) {
            Ice ice = new Ice();
            asteroids.get(i).AddResource(ice);
            i++;
        }
        while (i < oneResourceCount * 3 && i < asteroids.size()) {
            Iron iron = new Iron();
            asteroids.get(i).AddResource(iron);
            i++;
        }
        while (i < oneResourceCount * 4 && i < asteroids.size()) {
            Uranium uranium = new Uranium();
            asteroids.get(i).AddResource(uranium);
            i++;
        } */

        endGame = false;

        while (!endGame && !gameWon) {
            Main.println("New round\n");
            timer.Tick();

            if (players.size() == 0) {
                Main.println("Everyone died");
                endGame = true;
            }
        }
        for (Asteroid a : asteroids)
            Main.println(a.toString());
        /*for (Player p :players)
            Main.println(p.toString());
        for(Entity e : entities)
            Main.println(e.toString());*/
    }

    /**
     * A player dies
     * checks for the end of game
     *
     * @param player - the player that died
     */
    public void PlayerDie(Player player) {
        System.out.println(player.name + " died in!");
        players.remove(player);
        Timer.getInstance().RemoveSteppable(player);

        if (players.size() <= 1) {
            EndGame();
        }
    }

    /**
     * Adds a player to the game
     *
     * @param player - the player that will be added
     */
    public void AddPlayer(Player player) {
        if (!players.contains(player)) {
            timer.AddSteppable(player);
            players.add(player);
        }
    }

    public Player GetPlayer(String name) {
        for (Player p : players) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public void AddEntity(Entity e) {
        //entities.add(e);
        timer.AddSteppable(e);
    }

   /* public void RemoveEntity(Entity e) {
        entities.remove(e);
    }*/

    /**
     * Ends the game
     */
    public void EndGame() {
        endGame = true;
    }

    public void AddAsteroid(Asteroid a) {
        asteroids.add(a);
        sun.AddAsteroid(a);
    }

    public Asteroid GetAsteroid(int i) {
        for (Asteroid a : asteroids) {
            if (a.GetId() == i)
                return a;
        }
        return null;
    }

    public void DisableSunstorm() {
        sun.Disable();
    }

    public void EnableSunstorm() {
        sun.Enable();
    }

    /**
     * Checks for victory
     *
     * @param resources - the summed inventory of the players on the same asteroid
     */
    public void CheckForVictory(List<Resource> resources) {
        // playerben már kész a craft check
//        baseBluebrint.IsCraftable(resources);

        List<Resource> inventoryAfterCrafting = baseBluebrint.IsCraftable(resources);

        if (inventoryAfterCrafting != null) {
            System.out.println("The base can be made now! The game is won!");
            gameWon = true;
        }
    }

    public void reset() {
        players.clear();
        asteroids.clear();
        //entities.clear();
        sun.ClearAsteroids();
        timer.ClearSteppables();
    }

    public static Game getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new Game();

        return singleClassInstance;
    }
}
