package szoftprojlab;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Entity;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.*;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game singleClassInstance = null;


    private Sun sun = Sun.getInstance();
    private Timer timer = Timer.getInstance();
    private List<Player> players = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();
    private List<Alien> aliens = new ArrayList<>();
    private List<Robot> robots = new ArrayList<>();

    private boolean gameWon = false;
    private boolean endGame;
    private boolean stopgame;
    private int NearSunCycle = 10;
    private double SunStormProbability = 0.01;


    private View view;
    private Player activePlayer = null;

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
     * Created the game view
     */
    public Game() {
        view = new View(1000);
    }

    /**
     * Starts the game
     */
    public void StartGame() {
        sun.ClearAsteroids();
        timer.ClearSteppables();

        for (Player p : players) {
            timer.AddSteppable(p);
        }

        timer.AddSteppable(sun);

        for (Alien a : aliens) {
            timer.AddSteppable(a);
        }

        for (Robot r : robots) {
            timer.AddSteppable(r);
        }
        for (Asteroid a : asteroids) {
            sun.AddAsteroid(a);
        }


        view.update((Player) null);

        endGame = false;
        gameWon = false;
        stopgame = false;
        Timer.Start();
        while (!endGame && !gameWon && !stopgame) {
            Main.println("\nNew round\n");
            timer.Tick();

            if (players.size() < 1) {
                Main.println("Everyone died");
                endGame = true;
            } else if (players.size() == 1) {
                Main.println("Only one player remaining, the game cannot be won now!");
                endGame = true;
            }
        }

        if (!stopgame) {
            if (gameWon) {
                view.showGameWonDialog();
            } else {
                view.showGameOverDialog();
            }
        }
    }

    public void StopGame() {
        stopgame = true;
    }

    /**
     * A player dies
     * checks for the end of game
     *
     * @param player - the player that died
     */
    public void PlayerDie(Player player) {
        players.remove(player);
        Timer.getInstance().RemoveSteppable(player);
    }

    /**
     * Adds a player to the game
     *
     * @param player - the player that will be added
     */
    public void AddPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    /**
     * Gets the player by its name
     *
     * @param name the name of the player
     * @return the player if found, else null
     */
    public Player GetPlayer(String name) {
        for (Player p : players) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    /**
     * Adds an entity to the game
     *
     * @param e the new entity
     */
    public void AddEntity(Entity e) {
        timer.AddSteppable(e);
    }


    /**
     * Ends the game
     */
    public void EndGame() {
        endGame = true;
    }

    /**
     * Ads a new asteroid to the game
     *
     * @param a the new asteroid
     */
    public void AddAsteroid(Asteroid a) {
        asteroids.add(a);
        sun.AddAsteroid(a);
    }

    /**
     * Gets an asteroid by its id
     *
     * @param i the id of the asteroid
     * @return the asteroid if found, else null
     */
    public Asteroid GetAsteroid(int i) {
        for (Asteroid a : asteroids) {
            if (a.GetId() == i)
                return a;
        }
        return null;
    }

    /**
     * Disables sunstorms in the game
     */
    public void DisableSunstorm() {
        sun.DisableSunstorm();
    }

    /**
     * Enables sunstorms in the game
     */
    public void EnableSunstorm() {
        sun.EnableSunstorm();
    }

    /**
     * Checks for victory
     *
     * @param resources - the summed inventory of the players on the same asteroid
     */
    public void CheckForVictory(List<Resource> resources) {
        List<Resource> inventoryAfterCrafting = baseBluebrint.IsCraftable(resources);

        if (inventoryAfterCrafting != null) {
            Main.println("The base can be made now! The game is won!");
            Timer.Stop();
            gameWon = true;
        }
    }

    /**
     * Reset Game State to default for test
     */
    public void reset() {
        players.clear();
        asteroids.clear();
        aliens.clear();
        robots.clear();
        sun.ClearAsteroids();
        timer.ClearSteppables();
        Robot.resetId();
        Alien.resetId();
        TeleportGate.resetId();
        sun.Init();
    }

    /**
     * Gets the sing instance of game
     *
     * @return the single game object
     */
    public static Game getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new Game();

        return singleClassInstance;
    }

    /**
     * Find and return Alien by id
     *
     * @param id the id of the wanted alien
     * @return the alien if found, else null
     */
    public Alien GetAlien(int id) {
        for (Alien a : aliens)
            if (a.GetId() == id)
                return a;
        return null;
    }

    /**
     * Find and return Robot by id
     *
     * @param id the id of the robot
     * @return the robot if found, else null
     */
    public Robot GetRobot(int id) {
        for (Robot r : robots)
            if (r.GetId() == id)
                return r;
        return null;
    }

    /**
     * Add alien to game
     *
     * @param a the alien that will be added
     */
    public void AddAlien(Alien a) {
        aliens.add(a);
    }

    /**
     * Add robot to game
     *
     * @param r the robot that will be added
     */
    public void AddRobot(Robot r) {
        robots.add(r);
    }

    /**
     * Calls the update on view
     * The view will show the current state of the game
     *
     * @param activePlayer  the active player
     * @param listenForMove does the player want to move (if yes, then the asteroids need highlighting
     */
    public void drawRequired(Player activePlayer, boolean listenForMove) {
        this.activePlayer = activePlayer;
        if (listenForMove)
            updateAsteroidsWherePlayerCanMoveTo(activePlayer);
        else
            disableCanMoveForAsteroids();
        view.update(activePlayer);
    }

    /**
     * Find the asteroid from the given index,
     * and if found, call the draw on it
     *
     * @param i index of the asteroid
     */
    public void drawRequiredForAsteroid(int i) {
        if (asteroids.size() > i) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.draw(activePlayer, view);
        }
    }

    /**
     * Finds the asteroid where the player can move to, and
     * sets their canMoveHere property to true, so they will
     * be highlighted on the repaint
     *
     * @param player the player that wants to move
     */
    private void updateAsteroidsWherePlayerCanMoveTo(Player player) {
        Asteroid a = player.GetAsteroid();
        int[] neighborIds = a.GetNeighborsIds();
        for (int id : neighborIds) {
            Asteroid neighbor = a.GetNeighbor(id);
            neighbor.canMoveHere = true;
        }
        List<TeleportGate> gates = a.GetTeleportGates();
        if (gates != null && gates.size() > 0)
            gates.forEach(gate -> {
                Asteroid pair = gate.GetPairAsteroid();
                if (pair != null)
                    pair.canMoveHere = true;
            });
    }

    /**
     * Sets the canMoveHere for every asteroid
     */
    private void disableCanMoveForAsteroids() {
        asteroids.forEach(asteroid -> asteroid.canMoveHere = false);
    }

    /**
     * Get the id of the asteroid, where the x y coordinates are on the asteroid
     *
     * @param x x coordinate of the click
     * @param y y coordinate of the click
     * @return the clicked asteroid id of -1 if none were clicked
     */
    public int GetClickedAsteroidId(int x, int y) {
        for (Asteroid a : asteroids) {
            if (a.IsClicked(x, y))
                return a.GetId();
        }
        return -1;
    }

    /**
     * Saves the current state of the game in a file
     */
    public void Save() {
        StringBuilder save = new StringBuilder();
        save.append("exit\nreset\n");
        for (Asteroid a : asteroids) {
            save.append(a.Save());
        }

        for (Asteroid a : asteroids) {
            save.append(a.save_neighbours());
        }

        for (Player p : players) {
            save.append(p.Save());
        }

        for (Robot r : robots) {
            save.append("create robot on asteroid ").append(r.GetAsteroid().GetId()).append("\n");
        }

        for (Alien a : aliens) {
            save.append("create alien on asteroid ").append(a.GetAsteroid().GetId()).append("\n");
        }
        //Map<TeleportGate, String> map = new HashMap<TeleportGate, String>();
        List<Integer> creategates = new ArrayList<Integer>();

        for (Player p : players) {
            List<TeleportGate> gates = p.GetTeleportGates();
            for (TeleportGate gate : gates) {
                if (!creategates.contains(gate.GetId())) {
                    save.append("create create gate on ");
                    creategates.add(gate.GetId());
                    creategates.add(gate.GetPairId());
                    Asteroid a1 = gate.GetAsteroid();
                    Asteroid a2 = gate.GetPairAsteroid();
                    if (a1 == null)
                        save.append("player ").append(p.getName()).append(" ");
                    else
                        save.append("asteroid ").append(a1.GetId()).append(" ");
                    if (a2 == null)
                        save.append("player ").append(p.getName()).append("\n");
                    else
                        save.append("asteroid ").append(a2.GetId()).append("\n");
                }
            }
        }

        for (Asteroid a : asteroids) {
            List<TeleportGate> gates = a.GetTeleportGates();
            for (TeleportGate gate : gates) {
                if(!creategates.contains(gate.GetId())) {
                    creategates.add(gate.GetId());
                    creategates.add(gate.GetPairId());
                    save.append("create gate on asteroid ").append(a.GetId()).append(" asteroid ").append(gate.GetPairAsteroid().GetId()).append("\n");
                }
            }
        }

        save.append("continue from ").append(timer.GetStepIndex()).append("\n").append("start game\n");
        try {
            Writer writer = new FileWriter("save.txt");
            writer.append(save);
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Show an event string log on the game view
     *
     * @param s the string of the event(s)
     */
    public void WriteEvent(String s) {
        if (view != null)
            view.WriteEvent(s);
    }
}
