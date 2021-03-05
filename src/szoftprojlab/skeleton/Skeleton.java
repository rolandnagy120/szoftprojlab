package szoftprojlab.skeleton;

import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.ResourceNames;

import java.util.ArrayList;
import java.util.List;

public class Skeleton {
    public void Run() {
        SkeletonSequence s1 = new SkeletonSequence(3, "PlayerCreateRobot", Skeleton::PlayerCreateRobot);
        s1.Run();
    }

    private List<SkeletonSequence> getSequences() {
        List<SkeletonSequence> sequences = new ArrayList<>();

        sequences.add(new SkeletonSequence(1, "AsteroidGetsNearSun", Skeleton::AsteroidGetsNearSun));
        sequences.add(new SkeletonSequence(2, "PlayerCreateGate", Skeleton::PlayerCreateGate));
        sequences.add(new SkeletonSequence(3, "PlayerCreateRobot", Skeleton::PlayerCreateRobot));

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
}
