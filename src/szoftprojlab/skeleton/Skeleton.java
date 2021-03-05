package szoftprojlab.skeleton;

import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.ResourceNames;

import java.util.ArrayList;
import java.util.List;

public class Skeleton {
    public void Run() {
        SkeletonSequence s1 = new SkeletonSequence(1, "AsteroidGetsNearSun", Skeleton::AsteroidGetsNearSun);
        s1.Run();
    }

    private List<SkeletonSequence> getSequences() {
        List<SkeletonSequence> sequences = new ArrayList<>();

        SkeletonSequence s1 = new SkeletonSequence(1, "AsteroidGetsNearSun", Skeleton::AsteroidGetsNearSun);
        s1.Run();

        sequences.add(s1);

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
}
