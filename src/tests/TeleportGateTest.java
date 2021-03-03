package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.TeleportGate;

import static org.junit.jupiter.api.Assertions.*;

class TeleportGateTest {

    @Test
    void fullTest() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        TeleportGate gate1 = new TeleportGate(0);
        TeleportGate gate2 = new TeleportGate(1);
        gate1.SetPair(gate2);
        a1.PlaceTeleportGate(gate1);
        a2.PlaceTeleportGate(gate2);

        assertEquals(a1.GetTeleportGate(0), gate1);
        assertNull(a1.GetTeleportGate(1));
        assertEquals(a2.GetTeleportGate(1), gate2);
        assertNull(a2.GetTeleportGate(0));

        assertNotNull(gate1.GetPairAsteroid());
        assertEquals(gate1.GetPairAsteroid(), a2);
    }
}