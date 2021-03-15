package szoftprojlab;

import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        int numberOfAsteroids = 3;
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 1; i <= numberOfAsteroids; i++) {
            int asteroidLayer = 1;
            asteroids.add(new Asteroid(i, asteroidLayer));
        }
    }

    private void setNeightbors(Asteroid current, List<Asteroid> all) {

    }
}
