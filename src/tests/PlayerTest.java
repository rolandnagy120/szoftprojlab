package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void makeGates() {
        Player p = new Player();
        Asteroid a = new Asteroid(0, 1);
        a.Accept(p);

        assertNotNull(a.GetTeleportGates());
        assertEquals(a.GetTeleportGates().size(), 0);

        p.MakeGates();
        assertNotNull(p.GetTeleportGates());
        assertEquals(p.GetTeleportGates().size(), 0);

        Iron iron1 = new Iron();
        Iron iron2 = new Iron();
        Ice ice = new Ice();
        Uranium uranium = new Uranium();

        p.AddResource(iron1);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(ice);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(uranium);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(iron2);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 2);
    }

    @Test
    void moveToAsteroid() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        Asteroid a3 = new Asteroid(2, 1);
        a1.AddNeighbor(a2);
        a2.AddNeighbor(a3);

        Player p = new Player();
        a1.Accept(p);

        assertNotNull(a1.GetEntities());
        assertNotNull(a2.GetEntities());
        assertNotNull(a3.GetEntities());

        assertEquals(a1.GetEntities().size(), 1);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 0);

        p.MoveTo(a3);
        assertEquals(a1.GetEntities().size(), 1);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 0);

        p.MoveTo(a2);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 1);
        assertEquals(a3.GetEntities().size(), 0);

        p.MoveTo(a3);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 1);

        p.MoveTo(a2);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 1);
        assertEquals(a3.GetEntities().size(), 0);
    }

    @Test
    void createRobot() {
        Asteroid a = new Asteroid(0, 1);
        Player p = new Player();
        a.Accept(p);

        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 1);

        p.MakeAndPlaceRobot();
        assertEquals(a.GetEntities().size(), 1);

        Iron iron = new Iron();
        Coal coal = new Coal();
        Uranium uranium = new Uranium();

        p.AddResource(iron);
        p.MakeAndPlaceRobot();
        assertEquals(a.GetEntities().size(), 1);

        p.AddResource(coal);
        p.MakeAndPlaceRobot();
        assertEquals(a.GetEntities().size(), 1);

        p.AddResource(uranium);
        p.MakeAndPlaceRobot();
        assertEquals(a.GetEntities().size(), 2);
    }
}