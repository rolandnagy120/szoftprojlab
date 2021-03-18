package szoftprojlab.skeleton;

import szoftprojlab.*;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.Iron;
import szoftprojlab.resource.Uranium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    public void Run() {
        System.out.println("Szoftprojlab Skeleton");
        List<SkeletonSequence> sequences = getSequences();

        boolean endLoop = false;

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

    private List<SkeletonSequence> getSequences() {
        List<SkeletonSequence> sequences = new ArrayList<>();

        sequences.add(new SkeletonSequence("PlayerDrillsAndLayerDecreases", Skeleton::PlayerDrillsAndLayerDecreases));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikeThroughWithExplosive", Skeleton::PlayerDrillsAndStrikeThroughWithExplosive));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikeThroughWithIce", Skeleton::PlayerDrillsAndStrikeThroughWithIce));
        sequences.add(new SkeletonSequence("PlayerDrillsAndStrikethroughWithIron", Skeleton::PlayerDrillsAndStrikethroughWithIron));
        sequences.add(new SkeletonSequence("PlayerCannotHideFromSunStorm", Skeleton::PlayerCannotHideFromSunStorm));
        sequences.add(new SkeletonSequence("PlayerHidesFromSunStorm", Skeleton::PlayerHidesFromSunStorm));
        sequences.add(new SkeletonSequence("PlayerCreatesTeleportGate", Skeleton::PlayerCreatesTeleportGate));
        sequences.add(new SkeletonSequence("PlayerCreatesRobot", Skeleton::PlayerCreatesRobot));
        sequences.add(new SkeletonSequence("PlayerMovesFromAsteroidToAsteroid", Skeleton::PlayerMovesFromAsteroidToAsteroid));
        sequences.add(new SkeletonSequence("PlayerPlacesTeleportGateAndPlacesThePair", Skeleton::PlayerPlacesTeleportGateAndPlacesThePair));
        sequences.add(new SkeletonSequence("RobotHidesFromSunStorm", Skeleton::RobotHidesFromSunStorm));
        sequences.add(new SkeletonSequence("RobotCannotHideFromSunStorm", Skeleton::RobotCannotHideFromSunStorm));
        sequences.add(new SkeletonSequence("RobotMovesAndDrills", Skeleton::RobotMovesAndDrills));

        return sequences;
    }

    private static void PlayerCreatesTeleportGate(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();

        System.out.println("Init finished");
        System.out.println();

        player.MakeGates();
    }

    private static void PlayerCreatesRobot(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();
        Asteroid a = new Asteroid(0, 1);
        a.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.MakeAndPlaceRobot();
    }

    private static void PlayerDrillsAndLayerDecreases(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        a.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerDrillsAndStrikeThroughWithIce(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Ice ice = new Ice();
        a.Accept(player);
        a.AddResource(ice);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerDrillsAndStrikethroughWithIron(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Iron iron = new Iron();
        a.Accept(player);
        a.AddResource(iron);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerDrillsAndStrikeThroughWithExplosive(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Uranium uranium = new Uranium();
        a.Accept(player);
        a.AddResource(uranium);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerMovesFromAsteroidToAsteroid(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a1 = new Asteroid(0, 3);
        Asteroid a2 = new Asteroid(1, 3);
        a1.AddNeighbor(a2);
        Player player = new Player();
        a1.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.MoveTo(a2);
    }

    private static void PlayerPlacesTeleportGateAndPlacesThePair(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a1 = new Asteroid(0, 3);
        Asteroid a2 = new Asteroid(1, 3);
        Player player = new Player();
        a1.Accept(player);
        a1.AddNeighbor(a2);
        player.MakeGates();

        System.out.println("Init finished");
        System.out.println();

        player.PlaceGate();
        player.MoveTo(a2);
        player.PlaceGate();
    }

    private static void RobotCannotHideFromSunStorm(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        sun.ClearAsteroids();
        timer.ClearSteppables();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        sun.AddAsteroid(asteroid);
        asteroid.Accept(robot);
        timer.AddSteppable(sun);
        timer.AddSteppable(robot);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    private static void RobotHidesFromSunStorm(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        sun.ClearAsteroids();
        timer.ClearSteppables();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 0);
        Robot robot = new Robot();
        sun.AddAsteroid(asteroid);
        asteroid.Accept(robot);
        timer.AddSteppable(sun);
        timer.AddSteppable(robot);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    private static void RobotMovesAndDrills(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        Coal coal = new Coal();
        asteroid.Accept(robot);
        asteroid.AddResource(coal);

        System.out.println("Init finished");
        System.out.println();

        robot.Drill();
    }

    private static void PlayerHidesFromSunStorm(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        timer.ClearSteppables();
        sun.ClearAsteroids();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 0);
        Player player = new Player();
        sun.AddAsteroid(asteroid);
        asteroid.Accept(player);
        timer.AddSteppable(sun);
        timer.AddSteppable(player);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    private static void PlayerCannotHideFromSunStorm(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        timer.ClearSteppables();
        sun.ClearAsteroids();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 1);
        Player player = new Player();
        sun.AddAsteroid(asteroid);
        asteroid.Accept(player);
        timer.AddSteppable(sun);
        timer.AddSteppable(player);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }
}
