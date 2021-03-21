package szoftprojlab.skeleton;

import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
import szoftprojlab.Timer;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.Iron;
import szoftprojlab.resource.Uranium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    /**
     * Runs the skeleton program
     */
    public void Run() {
        System.out.println("Szoftprojlab Skeleton");
        List<SkeletonSequence> sequences = getSequences();

        boolean endLoop = false;

        /*
          Infinite loop for the program
         */
        while (!endLoop) {
            System.out.print("\nSelect a skeleton!\n");
            for (int i = 0; i < sequences.size(); i++) {
                System.out.println((i + 1) + " - " + sequences.get(i).name);
            }
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            int sequenceNumber = Integer.parseInt(input);
            if (sequenceNumber > 0 && sequenceNumber <= sequences.size()) {
                sequences.get(sequenceNumber - 1).Run();
                System.out.println();
                System.out.println("Sequence finished");
            }


            System.out.println();
            System.out.println("Do you want to exit the program? (Y|N) ");
            input = scanner.next();
            if (input.equalsIgnoreCase("Y")) {
                endLoop = true;
            }
        }
    }

    /**
     * Create the list of skeletons
     * @return
     */
    private List<SkeletonSequence> getSequences() {
        List<SkeletonSequence> sequences = new ArrayList<>();

        sequences.add(new SkeletonSequence("PlayerDrillsAndLayerDecreases", Skeleton::PlayerDrillsAndLayerDecreases, "n, n"));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikeThroughWithExplosive", Skeleton::PlayerDrillsAndStrikeThroughWithExplosive, "n, y, y, y"));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikeThroughWithIce", Skeleton::PlayerDrillsAndStrikeThroughWithIce, "n, y, y"));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikethroughWithIron", Skeleton::PlayerDrillsAndStrikethroughWithIron, "n, y, y"));
        sequences.add(new SkeletonSequence("PlayerCannotHideFromSunStorm", Skeleton::PlayerCannotHideFromSunStorm, "n, n, y, n, y"));
        sequences.add(new SkeletonSequence("PlayerHidesFromSunStorm", Skeleton::PlayerHidesFromSunStorm, "n, n, y, y"));
        sequences.add(new SkeletonSequence("PlayerCreatesTeleportGate", Skeleton::PlayerCreatesTeleportGate, "y"));
        sequences.add(new SkeletonSequence("PlayerCreatesRobot", Skeleton::PlayerCreatesRobot, "n, y, n"));
        sequences.add(new SkeletonSequence("PlayerMovesFromAsteroidToAsteroid", Skeleton::PlayerMovesFromAsteroidToAsteroid, "n, n"));
        sequences.add(new SkeletonSequence("PlayerPlacesTeleportGateAndPlacesThePair", Skeleton::PlayerPlacesTeleportGateAndPlacesThePair, "n, y, n"));
        sequences.add(new SkeletonSequence("RobotHidesFromSunStorm", Skeleton::RobotHidesFromSunStorm, "n, n, y, y"));
        sequences.add(new SkeletonSequence("RobotCannotHideFromSunStorm", Skeleton::RobotCannotHideFromSunStorm, "n, n, y, n"));
        sequences.add(new SkeletonSequence("RobotMovesAndDrills", Skeleton::RobotMovesAndDrills, "n, n, n"));

        return sequences;
    }

    /**
     * The player creates a pair of teleport gates
     * @param unused
     */
    private static void PlayerCreatesTeleportGate(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();
        oh.add(player, "player");

        System.out.println("Init finished");
        System.out.println();

        player.MakeGates();
    }

    /**
     * The player creates a robot
     * @param unused
     */
    private static void PlayerCreatesRobot(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();
        oh.add(player, "player");
        Asteroid asteroid = new Asteroid(0, 1);
        oh.add(asteroid, "asteroid");
        asteroid.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.MakeAndPlaceRobot();
    }

    /**
     * The player drills an asteroid
     * @param unused
     */
    private static void PlayerDrillsAndLayerDecreases(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        oh.add(asteroid, "asteroid");
        Player player = new Player();
        oh.add(player, "player");
        asteroid.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    /**
     * The player drilss and asteroid, and the ice
     * is destored in it
     * @param unused
     */
    private static void PlayerDrillsAndStrikeThroughWithIce(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        oh.add(asteroid, "asteroid");
        Player player = new Player();
        oh.add(player, "player");
        Ice ice = new Ice();
        oh.add(ice, "ice");
        asteroid.Accept(player);
        asteroid.AddResource(ice);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    /**
     * The player dirss and asteroid, which holds iron
     * nothing happens
     * @param unused
     */
    private static void PlayerDrillsAndStrikethroughWithIron(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        Player player = new Player();
        Iron iron = new Iron();
        oh.add(asteroid, "asteroid");
        oh.add(player, "player");
        oh.add(iron, "iron");
        asteroid.Accept(player);
        asteroid.AddResource(iron);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    /**
     * The players dirlls an asteroid with uranium,
     * and it explodes, the player dies
     * @param unused
     */
    private static void PlayerDrillsAndStrikeThroughWithExplosive(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        Player player = new Player();
        Uranium uranium = new Uranium();
        oh.add(asteroid, "asteroid");
        oh.add(player, "player");
        oh.add(uranium, "uranium");
        asteroid.Accept(player);
        asteroid.AddResource(uranium);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    /**
     * The player moves from one asteroid to another
     * @param unused
     */
    private static void PlayerMovesFromAsteroidToAsteroid(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid1 = new Asteroid(0, 3);
        Asteroid asteroid2 = new Asteroid(1, 3);
        oh.add(asteroid1, "asteroid1");
        oh.add(asteroid2, "asteroid2");
        asteroid1.AddNeighbor(asteroid2);
        Player player = new Player();
        oh.add(player, "player");
        asteroid1.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.MoveTo(asteroid2);
    }

    /**
     * The player created a pair of teleportgates and places them
     * @param unused
     */
    private static void PlayerPlacesTeleportGateAndPlacesThePair(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid1 = new Asteroid(0, 3);
        Asteroid asteroid2 = new Asteroid(1, 3);
        Player player = new Player();
        oh.add(asteroid1, "asteroid1");
        oh.add(asteroid2, "asteroid2");
        oh.add(player, "player");
        asteroid1.Accept(player);
        asteroid1.AddNeighbor(asteroid2);
        player.MakeGates();

        System.out.println("Init finished");
        System.out.println();

        player.PlaceGate();
        player.MoveTo(asteroid2);
        player.PlaceGate();
    }

    /**
     * A sunstorm hits a robot and it cannot hide in an asteroid
     * @param unused
     */
    private static void RobotCannotHideFromSunStorm(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        oh.add(timer, "timer");
        oh.add(sun, "sun");
        sun.ClearAsteroids();
        timer.ClearSteppables();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        oh.add(asteroid, "asteroid");
        oh.add(robot, "robot");
        sun.AddAsteroid(asteroid);
        asteroid.Accept(robot);
        timer.AddSteppable(sun);
        timer.AddSteppable(robot);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    /**
     * A sunstorm hit a robot, but it can hide
     * in the asteroid
     * @param unused
     */
    private static void RobotHidesFromSunStorm(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        oh.add(timer, "timer");
        oh.add(sun, "sun");
        sun.ClearAsteroids();
        timer.ClearSteppables();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 0);
        Robot robot = new Robot();
        oh.add(asteroid, "asteroid");
        oh.add(robot, "robot");
        sun.AddAsteroid(asteroid);
        asteroid.Accept(robot);
        timer.AddSteppable(sun);
        timer.AddSteppable(robot);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    /**
     * The robot moves to another asteroid,
     * and drills it
     * @param unused
     */
    private static void RobotMovesAndDrills(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid1 = new Asteroid(0, 1);
        Asteroid asteroid2 = new Asteroid(0, 2);
        Robot robot = new Robot();
        oh.add(asteroid1, "asteroid1");
        oh.add(asteroid2, "asteroid2");
        oh.add(robot, "robot");
        asteroid1.Accept(robot);
        asteroid2.AddNeighbor(asteroid1);

        System.out.println("Init finished");
        System.out.println();

        robot.MoveTo(asteroid2);
        robot.Drill();
    }

    /**
     * A sunstorm hits the player, but it can
     * hide in an asteroid
     * @param unused
     */
    private static void PlayerHidesFromSunStorm(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        oh.add(timer, "timer");
        oh.add(sun, "sun");
        timer.ClearSteppables();
        sun.ClearAsteroids();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 0);
        Player player = new Player();
        oh.add(asteroid, "asteroid");
        oh.add(player, "player");
        sun.AddAsteroid(asteroid);
        asteroid.Accept(player);
        timer.AddSteppable(sun);
        timer.AddSteppable(player);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    /**
     * A sunstorm hits the player, and
     * it can hide in an asteroid
     * @param unused
     */
    private static void PlayerCannotHideFromSunStorm(Void unused) {
        ObjectHolder oh = ObjectHolder.getInstance();
        oh.clear();
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        oh.add(timer, "timer");
        oh.add(sun, "sun");
        timer.ClearSteppables();
        sun.ClearAsteroids();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 1);
        Player player = new Player();
        oh.add(asteroid, "asteroid");
        oh.add(player, "player");
        sun.AddAsteroid(asteroid);
        asteroid.Accept(player);
        timer.AddSteppable(sun);
        timer.AddSteppable(player);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }
}
