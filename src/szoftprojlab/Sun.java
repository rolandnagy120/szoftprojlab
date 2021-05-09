package szoftprojlab;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Sun.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sun implements Steppable {
    private static Sun singleClassIntance = null;

    private int sunDistanceChangeCounter;
    private int sunDistanceChangeCycle;
    private double sunStormProbability;
    private int nextSunStormIn;
    private boolean sunDistanceChangeEnabled = true;

    private List<Asteroid> asteroids = new ArrayList<>();

    private boolean sunstormEnabled;
    private boolean sunstormOnce = false;
    private Asteroid sunstormAsteroid = null;
    private int sunStromDept = 2;

    /**
     * Initialize the sun
     *
     */
    public void Init() {
        sunDistanceChangeCycle = 20;
        sunDistanceChangeCounter = sunDistanceChangeCycle;
        sunStormProbability = 0.001;
        nextSunStormIn = (int) (1 / sunStormProbability);
        sunstormEnabled = true;
        sunStromDept = 2;
        sunstormOnce = false;
        sunstormAsteroid = null;
        sunDistanceChangeEnabled = true;
    }

    /**
     * Return the next suntorm arrival time
     * @return  the next sun storm arrival time
     */
    public int GetNextSunStormArrivalTime() {
        return nextSunStormIn;
    }

    /**
     * Clear the asteroids
     * Only needed for the skeleton
     */
    public void ClearAsteroids() {
        asteroids.clear();
    }

    /**
     * Steps the sun
     * Calls ChangeNearSun if it is the time for it
     * Calls sunstorm if it is time for it
     */
    public void Step() {
        sunDistanceChangeCounter--;
        nextSunStormIn--;

        if (sunDistanceChangeCounter == 0 && sunDistanceChangeEnabled) {
            ChangeNearSun();
            sunDistanceChangeCounter = sunDistanceChangeCycle;
        }

        if (nextSunStormIn == 0 && sunstormEnabled) {
            SunStorm();
            if (!sunstormOnce)
                nextSunStormIn = (int) (1 / sunStormProbability);
        }
        //Main.println("Sun Step called");
    }

    /**
     * Sends a sunstorm to the asteroids
     * Calls SunStorm on every asteroid
     */
    private void SunStormOld() {
        asteroids.forEach(Asteroid::SunStorm);
    }

    /**
     * Sends a sunstorm to the asteroids
     * Calls SunStorm on every asteroid
     */
    private void SunStorm() {
        Main.println("SunStorm hit the field");
        if (sunstormAsteroid == null) {
            Random rnd = new Random();
            int randomIndex = rnd.ints(0, asteroids.size())
                    .findFirst()
                    .getAsInt();
            asteroids.get(randomIndex).SendSunStorm(sunStromDept);
        } else {
            sunstormAsteroid.SendSunStorm(sunStromDept);
        }

    }

    /**
     * Sets the sunstorm recursive depts
     * @param dept  the depth of the sunstorm
     */
    public void SetSunstormDept(int dept) {
        sunStromDept = dept;
    }

    /**
     * Sets the sunstorm target
     * @param a the target asteroid
     */
    public void SetSunstromAsteroid(Asteroid a) {
        sunstormAsteroid = a;
    }

    /**
     * Changes the near sun state
     */
    private void ChangeNearSun() {
        asteroids.forEach(Asteroid::ChangeNearSun);
    }

    /**
     * Adds a new asteroid to the field
     *
     * @param asteroid - the asteroid that joins the field
     */
    public void AddAsteroid(Asteroid asteroid) {
        if (!asteroids.contains(asteroid))
            asteroids.add(asteroid);
    }

    /**
     * Disables the sunstorms
     */
    public void DisableSunstorm() {
        sunstormEnabled = false;
    }

    /**
     * Enables the sunstorms
     */
    public void EnableSunstorm() {
        sunstormEnabled = true;
    }

    /**
     * Sunstorm only appear once
     */
    public void SunstormOnce() {
        sunstormOnce = true;
    }

    /**
     * Disables the sun storm distance change
     */
    public void DisableSunStormDistanceChange() {
        sunDistanceChangeEnabled = false;
    }

    /**
     * Enables the sun storm distance change
     */
    public void EnableSunStormDistanceChange() {
        sunDistanceChangeEnabled = true;
    }

    /**
     * Sets the time of the next sunstorm
     * @param rounds    the round the next sun storm will arrive
     */
    public void SetNextSunStormIn(int rounds) {
        nextSunStormIn = rounds;
    }

    /**
     * Disables the sun distance change
     */
    public void DisableSunDistanceChange() {
        sunDistanceChangeEnabled = false;
    }

    /**
     * Enables the sun distance change
     */
    public void EnableSunDistanceChange() {
        sunDistanceChangeEnabled = true;
    }

    /**
     * Sets the sun distance change time to the given time
     * @param rounds    the rounds the sun will change distance
     */
    public void SetSunDistanceChangeTime(int rounds) {
        sunDistanceChangeCycle = rounds;
        sunDistanceChangeCounter = rounds;
    }

    /**
     * Gets the single instance of sun
     * @return  the single sun object
     */
    public static Sun getInstance() {
        if (singleClassIntance == null) {
            singleClassIntance = new Sun();
        }

        return singleClassIntance;
    }
}
