package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.Sun;
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
}