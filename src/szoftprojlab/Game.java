package szoftprojlab;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Player;
import szoftprojlab.resource.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static Game singleClassInstance = null;

    private Sun sun = Sun.getInstance();
    private Timer timer = Timer.getInstance();
    private List<Player> players = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();

    private boolean gameWon = false;

    private boolean sunStormenabled = true;

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
        timer.ClearSteppables();
        timer.AddSteppable(sun);
        sun.ClearAsteroids();
        sun.Init(10, 0.01);

        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);


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
        timer.AddSteppable(player1);
        timer.AddSteppable(player2);

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
        }


        boolean endGame = false;

        while (!endGame && !gameWon) {
            System.out.println("New round\n");
            timer.Tick();

            if (players.size() == 0) {
                System.out.println("Everyone died");
                endGame = true;
            }
        }
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
        if (!players.contains(player))
            players.add(player);
    }

    /**
     * Ends the game
     */
    public void EndGame() {
    }

    public void ModifyGame() {

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1 - Disable SunStorm");
            System.out.println("2 - Enable SunStorm");
            System.out.println("3 - Set Sunstorm range");
            System.out.println("4 - Cause Sunstorm to an asteroid");
            System.out.println("5 - Modify asteroid");
            System.out.println("6 - Modify player");
            System.out.println("7 - Add Asteroid");
            System.out.println("8 - Add Player");
            System.out.println("9 - Remove Asteroid");
            System.out.println("10 - Remove Player");

            System.out.println("e - exit");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();

            if (input.equalsIgnoreCase("1")) {

            } else if (input.equalsIgnoreCase("2")) {

            } else if (input.equalsIgnoreCase("3")) {

            } else if (input.equalsIgnoreCase("4")) {

            } else if (input.equalsIgnoreCase("5")) {
                while (true) {
                    System.out.println("Select Asteroid:");
                    System.out.println("1");
                    System.out.println("e - exit");
                    input = scanner.next();
                    if (input.equalsIgnoreCase("1")) {
                        asteroids.get(0).ModifyAsteroid();
                    } else if (input.equalsIgnoreCase("e")) {
                        break;
                    }
                }
            } else if (input.equalsIgnoreCase("6")) {
                while (true) {
                    System.out.println("Select Player:");
                    System.out.println("1");
                    System.out.println("e");
                    input = scanner.next();
                    if (input.equalsIgnoreCase("1")) {
                        players.get(0).ModifyPlayer();
                    } else if (input.equalsIgnoreCase("e")) {
                        break;
                    }
                }
            } else if (input.equalsIgnoreCase("7")) {

            } else if (input.equalsIgnoreCase("8")) {

            } else if (input.equalsIgnoreCase("e")) {
                return;
            }
        }
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

    public static Game getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new Game();

        return singleClassInstance;
    }
}
