package szoftprojlab;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 02/03/2021
//  @ Author : 
//
//


public class Game {
    private static Game singleClassInstance = null;

    private Sun sun;
    private Player players;

    public void StartGame() {
    }

    public void AddResource(ResourceNames name, Resource resource) {
    }

    public void ClearResources() {
    }

    public void PlayerDie(Player player) {
    }

    public static Game getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new Game();

        return singleClassInstance;
    }
}
