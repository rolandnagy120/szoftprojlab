package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.entity.Player;
import szoftprojlab.resource.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void makeGates() {
        Player p = new Player("Player1");
        Asteroid a = new Asteroid(0, 1);
        a.Accept(p);

        assertNotNull(a.GetTeleportGates());
        assertEquals(a.GetTeleportGates().size(), 0);

        p.MakeGates();
        assertNotNull(p.GetTeleportGates());
        assertEquals(p.GetTeleportGates().size(), 0);

        Iron iron1 = new Iron();
        Iron iron2 = new Iron();
        Ice ice1 = new Ice();
        Uranium uranium1 = new Uranium();
        Iron iron3 = new Iron();
        Iron iron4 = new Iron();
        Ice ice2 = new Ice();
        Uranium uranium2 = new Uranium();

        p.AddResource(iron1);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(ice1);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(uranium1);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 0);

        p.AddResource(iron2);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 2);


        p.AddResource(iron3);
        p.AddResource(iron4);
        p.AddResource(ice2);
        p.AddResource(uranium2);

        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 2);
        p.PlaceGate();
        assertEquals(p.GetTeleportGates().size(), 1);
        p.MakeGates();
        assertEquals(p.GetTeleportGates().size(), 3);
    }

    @Test
    void moveToAsteroid() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        Asteroid a3 = new Asteroid(2, 1);
        a1.AddNeighbor(a2);
        a2.AddNeighbor(a3);

        Player p = new Player("Player1");
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
        Player p = new Player("Player1");
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