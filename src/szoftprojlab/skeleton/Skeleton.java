package szoftprojlab.skeleton;

import szoftprojlab.*;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.ResourceNames;
import szoftprojlab.resource.Uranium;

import java.util.ArrayList;
import java.util.List;

public class Skeleton {
    public void Run() {
        SkeletonSequence s1 = new SkeletonSequence(3, "SequenceName", Skeleton::Init);
        s1.Run();
    }

    private List<SkeletonSequence> getSequences() {
        List<SkeletonSequence> sequences = new ArrayList<>();

        sequences.add(new SkeletonSequence(1, "AsteroidGetsNearSun", Skeleton::AsteroidGetsNearSun));
        sequences.add(new SkeletonSequence(2, "PlayerCreateGate", Skeleton::PlayerCreateGate));
        sequences.add(new SkeletonSequence(3, "PlayerCreateRobot", Skeleton::PlayerCreateRobot));
        sequences.add(new SkeletonSequence(4, "PlayerDrillsAsteroidNoStrikethrough", Skeleton::PlayerDrillsAsteroidNoStrikethrough));
        sequences.add(new SkeletonSequence(5, "PlayerDrillsAsteroidStrikethroughCoal", Skeleton::PlayerDrillsAsteroidStrikethroughCoal));
        sequences.add(new SkeletonSequence(6, "PlayerDrillsIce", Skeleton::PlayerDrillsIce));
        sequences.add(new SkeletonSequence(7, "PlayerDrillsUranAndExplodes", Skeleton::PlayerDrillsUranAndExplodes));
        sequences.add(new SkeletonSequence(8, "PlayerMovesFromAsteroidToAsteroid", Skeleton::PlayerMovesFromAsteroidToAsteroid));
        sequences.add(new SkeletonSequence(9, "PlayerPlacesTeleportGate", Skeleton::PlayerPlacesTeleportGate));
        sequences.add(new SkeletonSequence(10, "PlayerTeleports", Skeleton::PlayerTeleports));
        sequences.add(new SkeletonSequence(11, "RobotDiesInSunStorm", Skeleton::RobotDiesInSunStorm));
        sequences.add(new SkeletonSequence(12, "RobotDiesInSunStorm", Skeleton::RobotDrillsAsteroidStrikeThroughCoal));
        sequences.add(new SkeletonSequence(13, "RobotDiesInSunStorm", Skeleton::RobotDrillsIce));
        sequences.add(new SkeletonSequence(14, "RobotDrillsNoStrikeThrough", Skeleton::RobotDrillsNoStrikeThrough));
        sequences.add(new SkeletonSequence(15, "RobotDrillsNoStrikeThrough", Skeleton::RobotDrillsUranAndExplodes));
        sequences.add(new SkeletonSequence(16, "Init", Skeleton::Init));

        return sequences;
    }

    private static void AsteroidGetsNearSun(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Sun sun = new Sun();
        Asteroid asteroid = new Asteroid(0, 1);
        Ice ice = new Ice();
        sun.AddAsteroid(asteroid);
        asteroid.AddResource(ResourceNames.Ice, ice);

        System.out.println("Init finished");
        System.out.println();

        sun.Step();
    }

    private static void PlayerCreateGate(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();

        System.out.println("Init finished");
        System.out.println();

        player.MakeGates();
    }

    private static void PlayerCreateRobot(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Player player = new Player();
        Asteroid a = new Asteroid(0, 1);
        a.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.MakeAndPlaceRobot();
    }

    private static void PlayerDrillsAsteroidNoStrikethrough(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        a.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerDrillsAsteroidStrikethroughCoal(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Coal c = new Coal();
        a.Accept(player);
        a.AddResource(ResourceNames.Coal, c);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
        player.Mine();
    }

    private static void PlayerDrillsIce(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Ice ice = new Ice();
        a.Accept(player);
        a.AddResource(ResourceNames.Ice, ice);

        System.out.println("Init finished");
        System.out.println();

        player.Drill();
    }

    private static void PlayerDrillsUranAndExplodes(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid a = new Asteroid(0, 3);
        Player player = new Player();
        Uranium uranium = new Uranium();
        a.Accept(player);
        a.AddResource(ResourceNames.Uranium, uranium);

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

    private static void PlayerPlacesTeleportGate(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        Player player = new Player();
        asteroid.Accept(player);
        player.MakeGates();

        System.out.println("Init finished");
        System.out.println();

        player.PlaceGate();
    }

    private static void PlayerTeleports(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid1 = new Asteroid(0, 3);
        Asteroid asteroid2 = new Asteroid(1, 3);
        Player player = new Player();
        asteroid1.AddNeighbor(asteroid2);
        asteroid2.Accept(player);
        player.MakeGates();
        player.PlaceGate();
        player.MoveTo(asteroid1);
        player.PlaceGate();
        List<TeleportGate> gates = asteroid1.GetTeleportGates();

        System.out.println("Init finished");
        System.out.println();


        if (gates.size() > 0)
            player.Teleport(gates.get(0));
    }

    private static void RobotDiesInSunStorm(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Timer timer = Timer.getInstance();
        Sun sun = Sun.getInstance();
        sun.Init(10, 1);
        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        sun.AddAsteroid(asteroid);
        asteroid.Accept(robot);
        timer.AddSteppable(sun);
        timer.AddSteppable(asteroid);
        timer.AddSteppable(robot);

        System.out.println("Init finished");
        System.out.println();

        timer.Tick();
    }

    private static void RobotDrillsAsteroidStrikeThroughCoal(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        Coal coal = new Coal();
        asteroid.Accept(robot);
        asteroid.AddResource(ResourceNames.Coal, coal);

        System.out.println("Init finished");
        System.out.println();

        robot.Drill();
    }

    private static void RobotDrillsIce(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 1);
        Robot robot = new Robot();
        Ice ice = new Ice();
        asteroid.Accept(robot);
        asteroid.AddResource(ResourceNames.Ice, ice);

        System.out.println("Init finished");
        System.out.println();

        robot.Drill();
    }

    private static void RobotDrillsNoStrikeThrough(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid = new Asteroid(0, 3);
        Robot robot = new Robot();
        asteroid.Accept(robot);

        System.out.println("Init finished");
        System.out.println();

        robot.Drill();
    }

    private static void RobotDrillsUranAndExplodes(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Asteroid asteroid1 = new Asteroid(0, 3);
        Asteroid asteroid2 = new Asteroid(1, 3);
        Robot robot = new Robot();
        Uranium uranium = new Uranium();
        asteroid1.AddNeighbor(asteroid2);
        asteroid1.Accept(robot);
        asteroid1.AddResource(ResourceNames.Uranium, uranium);

        System.out.println("Init finished");
        System.out.println();

        robot.Drill();
    }

    private static void Init(Void unused) {
        System.out.println();
        System.out.println("Initializing");

        Game game = Game.getInstance();

        System.out.println("Init finished");
        System.out.println();

        game.StartGame();
    }
}
