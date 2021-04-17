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
import szoftprojlab.Main;
import szoftprojlab.TeleportGate;
import szoftprojlab.Timer;
import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends Entity implements Miner {
    private List<TeleportGate> gates = new ArrayList<>();
    private List<Resource> inventory = new ArrayList<>();
    public String name;

    private static int maxGatesCount = 3;
    private static int invetoryMax = 10;

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

    /**
     * Player steps
     */
    public void Step() {
        Main.println("\nPlayer " + name + " steps:");
        //TODO
        //start sun storm asteroid idx dept

        // Move player (ex: move 7 -> move to the asteroid which has the id 7)
        Pattern Move = Pattern.compile("move\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        // Drill asteroid
        Pattern Drill = Pattern.compile("drill", Pattern.CASE_INSENSITIVE);
        // Mine asteroid
        Pattern Mine = Pattern.compile("mine", Pattern.CASE_INSENSITIVE);
        // Craft Robot
        Pattern CraftRobot = Pattern.compile("craft robot", Pattern.CASE_INSENSITIVE);
        // Craft gates
        Pattern CraftGates = Pattern.compile("craft gates", Pattern.CASE_INSENSITIVE);
        // Places the first gate in the inventory
        Pattern PlaceGate = Pattern.compile("place gate", Pattern.CASE_INSENSITIVE);
        // Teleport player (ex: teleport to 7 -> teleport to the asteroid which has the id 7)
        Pattern TeleportTo = Pattern.compile("teleport to\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        // Place back the given resource (ex: place Uranium)
        Pattern PlaceResource = Pattern.compile("place resource\\s+([a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
        while (true) {
            try {
                String input = Main.getGameInputScanner().nextLine();
                Matcher MoveM = Move.matcher(input);
                if (MoveM.find()) {
                    String selectedNeighbor = MoveM.group(1);
                    int[] neighborIds = asteroid.GetNeighborsIds();
                    for (int id : neighborIds) {
                        if (String.valueOf(id).equals(selectedNeighbor)) {
                            MoveTo(asteroid.GetNeighbor(id));
                            return;
                        }
                    }
                    Main.println("Neighbor with id " + selectedNeighbor + " not found");
                } else if (Drill.matcher(input).find()) {
                    Drill();
                    Main.println("Drilled");
                    return;
                } else if (Mine.matcher(input).find()) {
                    if (inventory.size() == invetoryMax) {
                        Main.println("Inventory full, can't mine more");
                    } else {
                        if (Mine()) {
                            //Main.println("Mine successful");
                            return;
                        }
                    }
                } else if (CraftRobot.matcher(input).find()) {
                    MakeAndPlaceRobot();
                    return;
                } else if (CraftGates.matcher(input).find()) {
                    MakeGates();
                    return;
                } else if (PlaceGate.matcher(input).find()) {
                    this.PlaceGate();
                    return;
                }

                Matcher TeleportMatcher = TeleportTo.matcher(input);
                if (TeleportMatcher.find()) {
                    List<TeleportGate> gates = asteroid.GetTeleportGates();
                    if (gates.size() == 0) {
                        Main.println("No gates on the asteroid");
                    } else {
                        String selectedNeighbor = TeleportMatcher.group(1);
                        for (TeleportGate gate : gates) {

                            int pairId = gate.GetPairAsteroid().GetId();

                            if (String.valueOf(pairId).equals(selectedNeighbor)) {
                                Teleport(gate);
                                return;
                            }
                        }
                    }
                }

                Matcher PlaceResourceMatcher = PlaceResource.matcher(input);
                if (PlaceResourceMatcher.find()) {
                    List<Resource> inventoryCopy = new ArrayList<>(inventory);
                    AtomicBoolean resourceFound = new AtomicBoolean(false);
                    inventoryCopy.forEach(resource -> {
                        String className = resource.getClass().getSimpleName();
                        String selectedResource = PlaceResourceMatcher.group(1);
                        if (selectedResource.equals(className) && !resourceFound.get()) {
                            PlaceResource(resource);
                            resourceFound.set(true);
                        }
                    });
                    if (!resourceFound.get()) {
                        Main.println("Resource not found");
                    } else {
                        return;
                    }
                }

            } catch (Exception e) {
                if (e.equals(new NoSuchElementException()))
                    e.printStackTrace();
                //test file over end game
                Game.getInstance().EndGame();
                break;
            }


        }
    }

    /**
     * The player mines the asteroid its on currently
     */
    public boolean Mine() {
        String rName = asteroid.GetResourceName();
        if (rName.equals("Empty")) {
            Main.println("Already empty, can't mine.");
            return false;
        } else if (rName.equals("Unknown")) {
            Main.println("Can't mine, asteroid haven't been broken trough.");
            return false;
        }
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
        if (inventory.contains(resource) && !asteroid.GetResourceName().equals(resource.getClass().getSimpleName())) {
            asteroid.Place(resource);
            if (asteroid.GetResourceName().equals(resource.getClass().getSimpleName()))
                inventory.remove(resource);
        }
        else
            Main.println("Couldn't place resource");
        return true;
    }

    /**
     * The players places a teleport gate on the current asteroid
     */
    public boolean PlaceGate() {
        if (asteroid != null && gates.size() > 0) {
            asteroid.PlaceTeleportGate(gates.get(0));
            gates.remove(0);
            Main.println("Gate placed");
        } else if (gates.size() == 0) {
            Main.println("No gates to place");
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
            Main.println("No more space in inventory to craft gates");
            return false;
        }

        List<Resource> inventoryAfterCrafting = TeleportGate.CanCraft(this.inventory);

        if (inventoryAfterCrafting != null || inventory.size() == 4) {
            TeleportGate tg1 = new TeleportGate();
            TeleportGate tg2 = new TeleportGate();
            tg1.SetPair(tg2);
            gates.add(tg1);
            gates.add(tg2);

            Timer timer = Timer.getInstance();
            timer.AddSteppable(tg1);
            timer.AddSteppable(tg2);

            Main.println("Gates crafted");
        } else {
            Main.println("Not enough resources to craft gates");
        }
        return true;
    }

    /**
     * The player crafts a robot, and places it on the current asteroid
     */
    public boolean MakeAndPlaceRobot() {
        List<Resource> inventoryAfterCrafting = Robot.CanCraft(this.inventory);

        if (inventoryAfterCrafting != null) {
            Robot r = new Robot(asteroid);
            Game.getInstance().AddRobot(r);
            Game.getInstance().AddEntity(r);
            inventory = inventoryAfterCrafting;

            Main.println("Robot crafted and placed");
            return true;
        } else {
            Main.println("Not enough resources to craft a robot");
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


    public void AddGate(TeleportGate gate) {
        gates.add(gate);
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
        Main.println("Player " + name + " exploded.");
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

    public String getName() {
        return name;
    }

    public String toString() {
        String ret = "\n\tPlayer " + name + "\n" +
                "\tinventory:";
        for (Resource r : inventory) {
            ret += "\n\t\t"+r.toString();
        }
        for(TeleportGate g: gates)
            ret += "\n\t\t"+g.toString();
        return ret;
    }
}
