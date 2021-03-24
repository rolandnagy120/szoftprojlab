package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
import szoftprojlab.TeleportGate;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Uranium;

import static org.junit.jupiter.api.Assertions.*;

class SunTest {

    @Test
    void nearSun() {
        Sun sun = Sun.getInstance();
        sun.Init(1, 0);

        Asteroid a = new Asteroid(0, 2);
        sun.AddAsteroid(a);
        Uranium u = new Uranium();
        a.AddResource(u);
        Player p = new Player();
        Robot r = new Robot();
        a.Accept(p);
        a.Accept(r);

        a.Drill();
        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 2);

        sun.Step();

        a.Drill();
        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 2);
        sun.Step();
        assertEquals(a.GetEntities().size(), 2);
        sun.Step();
        assertEquals(a.GetEntities().size(), 2);
        sun.Step();
        assertEquals(a.GetEntities().size(), 2);
        sun.Step();
        assertEquals(a.GetEntities().size(), 0);
    }

    @Test
    void sunStorm() {
        Sun sun = Sun.getInstance();
        // We expect a sunstorm every turn, because the probability is 1
        sun.Init(10, 1);

        Asteroid a = new Asteroid(0, 1);
        Player p = new Player();
        a.Accept(p);
        sun.AddAsteroid(a);

        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 1);

        sun.Step();

        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 0);
    }

    @Test
    void dancingTeleportGates() {
        Sun sun = Sun.getInstance();
        sun.Init(10, 1);

        Asteroid asteroid1 = new Asteroid(0, 1);
        Asteroid asteroid2 = new Asteroid(1, 1);
        TeleportGate gate1 = new TeleportGate(1);
        TeleportGate gate2 = new TeleportGate(2);
        sun.AddAsteroid(asteroid1);
        asteroid1.AddNeighbor(asteroid2);
        asteroid1.PlaceTeleportGate(gate1);
        asteroid2.PlaceTeleportGate(gate2);


        assertNotNull(asteroid1.GetTeleportGates());
        assertEquals(asteroid1.GetTeleportGates().size(), 1);
        assertEquals(asteroid2.GetTeleportGates().size(), 1);
        assertNotNull(asteroid1.GetTeleportGate(1));
        assertNull(asteroid1.GetTeleportGate(2));
        assertNotNull(asteroid2.GetTeleportGate(2));
        assertNull(asteroid2.GetTeleportGate(1));

        sun.Step();

        // Az a jelenség most, az asteroid2-ről átmegynnek az asteroid1-re
        // Majd az asteroid1-ről megy mindenki a 2-re, ezért lesz ott az összes
        assertNull(asteroid1.GetTeleportGate(1));
        assertNull(asteroid1.GetTeleportGate(2));
        assertNotNull(asteroid2.GetTeleportGate(1));
        assertNotNull(asteroid2.GetTeleportGate(2));
    }
}