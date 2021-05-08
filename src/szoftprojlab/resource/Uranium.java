package szoftprojlab.resource;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Uranium.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.Asteroid;
import szoftprojlab.Main;

public class Uranium extends Resource {

    private int seeSunsBeforeExplosion = 3;

    public Uranium(int seesun) {
        seeSunsBeforeExplosion = 3 - seesun;
    }

    public Uranium() {

    }

    /**
     * The Sun gets close to the Uranium
     * This triggers an explosion if it is the third time
     *
     * @param asteroid - the asteroid which holds the resource
     */
    @Override
    public void SeeSun(Asteroid asteroid) {
        Main.println("Uranium seen sun.");
        seeSunsBeforeExplosion--;
        if (seeSunsBeforeExplosion == 0) {
            Main.println("The uranium exploded!");
            asteroid.Explode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof Uranium) {
            return true;
        }

        return false;
    }

    /**
     * Returns the resource part of the image
     *
     * @return resource name used in the images
     */
    @Override
    public String GetImagePart() {
        return "uranium";
    }

    @Override
    public String save() {
        return "uranium nearsun " + (3 - seeSunsBeforeExplosion);
    }

    public String toString() {
        return "Uranium";
    }

}
