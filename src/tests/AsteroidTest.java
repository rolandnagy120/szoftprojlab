package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.TeleportGate;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Coal;
import szoftprojlab.resource.Ice;
import szoftprojlab.resource.Uranium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AsteroidTest {

    private Asteroid getAsteroidWithTwoEntitiesAndLayerOf(int numberOfLayers) {
        Asteroid asteroid = new Asteroid(0, numberOfLayers);
        Player p = new Player("Player1");
        Robot r = new Robot();
        asteroid.Accept(p);
        asteroid.Accept(r);
        assertNotNull(asteroid.GetEntities());
        assertEquals(asteroid.GetEntities().size(), 2);
        return asteroid;
    }

    @Test
    void sunStormWithEmptyAsteroids() {
        Asteroid emptyAsteroidWithLayer = getAsteroidWithTwoEntitiesAndLayerOf(1);
        Asteroid emptyAsteroidWithoutLayer = getAsteroidWithTwoEntitiesAndLayerOf(0);

        emptyAsteroidWithLayer.SunStorm();
        emptyAsteroidWithoutLayer.SunStorm();

        assertNotNull(emptyAsteroidWithLayer.GetEntities());
        assertNotNull(emptyAsteroidWithoutLayer.GetEntities());

        int EntityCountOnAsteroidWithLayer = emptyAsteroidWithLayer.GetEntities().size();
        int EntityCountOnAsteroidWithoutLayer = emptyAsteroidWithoutLayer.GetEntities().size();

        // Only those survive where the asteroid doesn't have any more layers
        assertEquals(EntityCountOnAsteroidWithLayer, 0);
        assertEquals(EntityCountOnAsteroidWithoutLayer, 2);
    }

    @Test
    void sunStormWithNotEmptyAsteroids() {
        Ice resource1 = new Ice();
        Ice resource2 = new Ice();

        Asteroid emptyAsteroidWithLayer = getAsteroidWithTwoEntitiesAndLayerOf(1);
        Asteroid emptyAsteroidWithoutLayer = getAsteroidWithTwoEntitiesAndLayerOf(0);

        emptyAsteroidWithLayer.AddResource(resource1);
        emptyAsteroidWithoutLayer.AddResource(resource2);

        emptyAsteroidWithLayer.SunStorm();
        emptyAsteroidWithoutLayer.SunStorm();

        assertNotNull(emptyAsteroidWithLayer.GetEntities());
        assertNotNull(emptyAsteroidWithoutLayer.GetEntities());

        int EntityCountOnAsteroidWithLayer = emptyAsteroidWithLayer.GetEntities().size();
        int EntityCountOnAsteroidWithoutLayer = emptyAsteroidWithoutLayer.GetEntities().size();

        // No one should survive
        assertEquals(EntityCountOnAsteroidWithLayer, 0);
        assertEquals(EntityCountOnAsteroidWithoutLayer, 0);
    }

    @Test
    void addNeighbor() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        a1.AddNeighbor(a2);

        assertEquals(a1.GetNeighbor(1), a2);
        assertEquals(a2.GetNeighbor(0), a1);
    }

    @Test
    void explode() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        Player p = new Player("Player1");
        Robot r = new Robot();
        a1.AddNeighbor(a2);
        a1.Accept(p);
        a1.Accept(r);

        a1.Explode();

        assertEquals(r.GetAsteroid(), a2);
        assertNotNull(a1.GetEntities());
        assertNotNull(a2.GetEntities());
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 1);
    }

    @Test
    void drill() {
        Asteroid a = new Asteroid(0, 2);
        assertEquals(a.GetLayerThickness(), 2);
        a.Drill();
        assertEquals(a.GetLayerThickness(), 1);
        a.Drill();
        assertEquals(a.GetLayerThickness(), 0);
    }

    @Test
    void accept() {
        Asteroid a = new Asteroid(0, 2);
        assertNotNull(a.GetEntities());
        assertEquals(a.GetEntities().size(), 0);

        Player p = new Player("Player1");
        a.Accept(p);
        assertEquals(a.GetEntities().size(), 1);

        Robot r = new Robot();
        a.Accept(r);
        assertEquals(a.GetEntities().size(), 2);

        a.Accept(r);
        assertEquals(a.GetEntities().size(), 2);
    }

    @Test
    void remove() {
        Asteroid a = new Asteroid(0, 2);
        assertNotNull(a.GetEntities());

        Player p = new Player("Player1");
        Robot r = new Robot();
        a.Accept(p);
        a.Accept(r);
        assertEquals(a.GetEntities().size(), 2);

        a.Remove(p);
        assertEquals(a.GetEntities().size(), 1);

        a.Remove(p);
        assertEquals(a.GetEntities().size(), 1);

        a.Remove(r);
        assertEquals(a.GetEntities().size(), 0);
    }

    @Test
    void mine() {
        Player p = new Player("Player1");
        Coal c = new Coal();
        Asteroid a = new Asteroid(0, 0);
        a.AddResource(c);

        assertNotNull(p.GetInventory());
        assertEquals(p.GetInventory().size(), 0);

        a.Mine(p);

        assertEquals(p.GetInventory().size(), 1);
    }

    @Test
    void place() {
        Player p = new Player("Player1");
        Coal c = new Coal();
        Asteroid a = new Asteroid(0, 0);
        a.Mine(p);

        assertNotNull(p.GetInventory());
        assertEquals(p.GetInventory().size(), 0);

        a.Place(c);
        a.Mine(p);

        assertEquals(p.GetInventory().size(), 1);
    }

    @Test
    void getTeleportGate() {
        int gateId = 17;
        Player p = new Player("Player1");
        Asteroid a = new Asteroid(0, 1);
        TeleportGate gate = new TeleportGate(gateId);
        a.PlaceTeleportGate(gate);

        assertEquals(a.GetTeleportGate(gateId), gate);
    }

    @Test
    void addEntitysResourcesToComparator() {
    }

    @Test
    void destroyResource() {
        Uranium u = new Uranium();
        Asteroid a = new Asteroid(0, 0);
        Player p = new Player("Player1");
        a.AddResource(u);
        a.DestroyResource();

        a.Mine(p);
        assertNotNull(p.GetInventory());
        assertEquals(p.GetInventory().size(), 0);
    }

    @Test
    void step() {
    }
}