package szoftprojlab.entity;

//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Player.java
//  @ Date : 10/03/2021
//  @ Author : 
//
//


import szoftprojlab.Game;
import szoftprojlab.Sun;
import szoftprojlab.TeleportGate;
import szoftprojlab.Timer;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Entity implements Miner {
    private List<TeleportGate> gates = new ArrayList<>();
    private List<Resource> inventory = new ArrayList<>();
    public String name;

    private static int maxGatesCount = 3;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Gets the resources stored in the player inventory
     *
     * @return
     */
    @Override
    public List<Resource> GetInventory() {
        return inventory;
    }

    public void ModifyPlayer() {
        while (true) {
            System.out.println("1 - List inventory");
            System.out.println("2 - Add Resource to inventory");
            System.out.println("3 - Remove Resource from inventory");
            System.out.println("4 - Set Current Position");
            System.out.println("e - exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();

            if (input.equalsIgnoreCase("1")) {

            } else if (input.equalsIgnoreCase("2")) {

            } else if (input.equalsIgnoreCase("3")) {

            } else if (input.equalsIgnoreCase("4")) {

            } else if (input.equalsIgnoreCase("e")) {
                return;
            }
        }
    }

    /**
     * Player steps
     */
    public void Step() {
        boolean endStep = false;

        while (!endStep) {
            System.out.println("\n" + name + " - What do you want to do?");
            System.out.println("1 - Get current asteroid number of layers");
            System.out.println("2 - Get resource type inside asteroid");
            System.out.println("3 - Drill");
            System.out.println("4 - Mine");
            System.out.println("5 - Move");
            System.out.println("6 - List invenory");
            System.out.println("7 - Get next sunstorm time");
            System.out.println("8 - Craft and place robot");
            System.out.println("9 - Craft gates");
            System.out.println("10 - Place a teleport gate");
            System.out.println("11 - Go through teleport gate");
            System.out.println("s - Save Game");
            System.out.println("q - Quit Game");
            System.out.println("m - Modify Game");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();

            if (input.equalsIgnoreCase("1")) {
                System.out.println("The layer is: " + asteroid.GetLayerThickness());
            } else if (input.equalsIgnoreCase("2")) {
                System.out.println("The resource is: " + asteroid.GetResourceName());
            } else if (input.equalsIgnoreCase("3")) {
                Drill();
                endStep = true;
            } else if (input.equalsIgnoreCase("4")) {
                Mine();
                endStep = true;
            } else if (input.equalsIgnoreCase("5")) {
                var neighborIds = asteroid.GetNeighborsIds();
                System.out.println("To which asteroid do you want to go?");
                for (int id : neighborIds) {
                    System.out.println(id + " (layers: " + asteroid.GetNeighbor(id).GetLayerThickness() + ")");
                }
                String input2 = scanner.next();
                for (int id : neighborIds) {
                    if (String.valueOf(id).equals(input2)) {
                        MoveTo(asteroid.GetNeighbor(id));
                        endStep = true;
                    }
                }
            } else if (input.equalsIgnoreCase("6")) {
                for (Resource resource : inventory) {
                    System.out.println(resource.getClass().getSimpleName());
                }
            } else if (input.equalsIgnoreCase("7")) {
                System.out.println("The next sunstorm arrive in " + Sun.getInstance().GetNextSunStormArrivalTime() + " turns");
            } else if (input.equalsIgnoreCase("8")) {
                MakeAndPlaceRobot();
            } else if (input.equalsIgnoreCase("9")) {
                MakeGates();
            } else if (input.equalsIgnoreCase("10")) {
                PlaceGate();
            } else if (input.equalsIgnoreCase("11")) {
                var gates = asteroid.GetTeleportGates();
                if (gates.size() == 0) {
                    System.out.println("No gates on the asteroid");
                } else {
                    for (int i = 0; i < gates.size(); i++) {
                        var gate = gates.get(i);
                        System.out.println((i + 1) + " - asteroid " + gate.GetPairAsteroid().GetId());
                    }
                    String input2 = scanner.next();
                    for (int i = 0; i < gates.size(); i++) {
                        if (String.valueOf(i + 1).equals(input2)) {
                            Teleport(gates.get(i));
                            System.out.println("Teleported");
                            break;
                        }
                    }
                }
            } else if (input.equalsIgnoreCase("m")) {
                Game.getInstance().ModifyGame();
            }

        }
    }

    /**
     * The player mines the asteroid its on currently
     */
    public boolean Mine() {
        asteroid.Mine(this);
        return true;
    }

    /**
     * The player places back a resource to the asteroid its on
     * the asteroid should be emptyto receive the resource
     *
     * @param resource
     */
    public boolean PlaceResource(Resource resource) {
        if (inventory.contains(resource))
            asteroid.Place(resource);
        return true;
    }

    /**
     * The players places a teleport gate on the current asteroid
     */
    public boolean PlaceGate() {
        if (asteroid != null && gates.size() > 0) {
            asteroid.PlaceTeleportGate(gates.get(0));
            gates.remove(0);
            System.out.println("Gate placed");
        } else if (gates.size() == 0) {
            System.out.println("No gates to place");
        }
        return true;
    }

    /**
     * The player crafts a pair of teleport gates from
     * the resources stored in its iventory
     * If the player has more than 1 gate, then return, because there
     * if no room for 2 more teleport gates
     */
    public boolean MakeGates() {
        if (gates.size() > maxGatesCount - 2) {
            System.out.println("No more space to craft gates");
            return false;
        }

        List<Resource> inventoryAfterCrafting = TeleportGate.CanCraft(this.inventory);

        if (inventoryAfterCrafting != null || inventory.size() == 4) {
            TeleportGate tg1 = new TeleportGate(1);
            TeleportGate tg2 = new TeleportGate(2);
            tg1.SetPair(tg2);
            gates.add(tg1);
            gates.add(tg2);

            Timer timer = Timer.getInstance();
            timer.AddSteppable(tg1);
            timer.AddSteppable(tg2);

            System.out.println("Gates crafted");
        } else {
            System.out.println("Not enough resources to craft gates");
        }
        return true;
    }

    /**
     * The player crafts a robot, and places it on the current asteroid
     */
    public boolean MakeAndPlaceRobot() {
        List<Resource> inventoryAfterCrafting = Robot.CanCraft(this.inventory);

        if (inventoryAfterCrafting != null) {
            Robot r = new Robot();
            asteroid.Accept(r);
            Timer.getInstance().AddSteppable(r);
            inventory = inventoryAfterCrafting;

            System.out.println("Robot crafted and placed");
            return true;
        } else {
            System.out.println("Not enough resources to craft a robot");
            return false;
        }

    }

    /**
     * Get the teleport gates the player has
     * Only use for unit tests
     *
     * @return
     */
    public List<TeleportGate> GetTeleportGates() {
        return gates;
    }

    /**
     * Adds a resource to the players inventory
     *
     * @param resource - the resource that will be added to the inventory
     */
    public boolean AddResource(Resource resource) {
        inventory.add(resource);
        return true;
    }

    /**
     * An exlosion hits the player.
     * The player dies here
     */
    @Override
    public void Explode() {
        Game game = Game.getInstance();
        asteroid.Remove(this);
        game.PlayerDie(this);
    }

    /**
     * A sunstorm hits the player
     * The player dies
     */
    @Override
    public void SunStorm() {
        Timer timer = new Timer();
        timer.RemoveSteppable(this);
        asteroid.Remove(this);
        Game.getInstance().PlayerDie(this);
    }
}
