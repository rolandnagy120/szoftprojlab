package szoftprojlab.skeleton;

import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.ResourceNames;
import szoftprojlab.resource.Uranium;

import java.util.ArrayList;
import java.util.List;

public class Skeleton {
    public void Run() {
        SkeletonSequence s1 = new SkeletonSequence(3, "SequenceName", Skeleton::PlayerPlacesTeleportGate);
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
        player.MakeGates();
        asteroid.Accept(player);

        System.out.println("Init finished");
        System.out.println();

        player.PlaceGate();
    }
}
