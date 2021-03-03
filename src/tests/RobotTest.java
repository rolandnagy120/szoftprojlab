package tests;

import org.junit.jupiter.api.Test;
import szoftprojlab.Asteroid;
import szoftprojlab.entity.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RobotTest {
    @Test
    void moveToAsteroid() {
        Asteroid a1 = new Asteroid(0, 1);
        Asteroid a2 = new Asteroid(1, 1);
        Asteroid a3 = new Asteroid(2, 1);
        a1.AddNeighbor(a2);
        a2.AddNeighbor(a3);

        Robot r = new Robot();
        a1.Accept(r);

        assertNotNull(a1.GetEntities());
        assertNotNull(a2.GetEntities());
        assertNotNull(a3.GetEntities());

        assertEquals(a1.GetEntities().size(), 1);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 0);

        r.MoveTo(a3);
        assertEquals(a1.GetEntities().size(), 1);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 0);

        r.MoveTo(a2);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 1);
        assertEquals(a3.GetEntities().size(), 0);

        r.MoveTo(a3);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 0);
        assertEquals(a3.GetEntities().size(), 1);

        r.MoveTo(a2);
        assertEquals(a1.GetEntities().size(), 0);
        assertEquals(a2.GetEntities().size(), 1);
        assertEquals(a3.GetEntities().size(), 0);
    }
}