package szoftprojlab;

import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.*;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner GameInput;
    private static Writer GameOutput;
    private static String OutputFile = null;
    private static Game game = Game.getInstance();


    public static Scanner getGameInputScanner() {
        return GameInput;
    }

    public static void main(String[] args) {
        GameOutput = new BufferedWriter(new OutputStreamWriter(System.out));
        process_input(new Scanner(System.in));
    }

    public static void process_input(Scanner scanner) {
        //load test 1
        Pattern Load = Pattern.compile("load\\s+test\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //save state
        Pattern Save = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Add a Neighbouring Asteroid to an asteroid
        Pattern SetAsteroidNeighbour = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+neighbour\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //Create Asteroid
        Pattern CreateAsteroid = Pattern.compile("create\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //Start game
        Pattern StartGame = Pattern.compile("start\\s+game", Pattern.CASE_INSENSITIVE);
        //Set asteroid layer
        Pattern SetAsteroidLayer = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+layer\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //Set asteroid resource
        Pattern SetAsteroidResource = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+resource\\s+([a-z]+)(\\s+(nearsun)\\s+([0-9]+))?", Pattern.CASE_INSENSITIVE);
        //create player 2 on asteroid 2
        Pattern CreatePlayer = Pattern.compile("create\\s+player\\s([a-zA-Z_0-9]+)\\s+on\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //add resource to player
        Pattern AddResourceToPlayer = Pattern.compile("add\\s+resource\\s+to\\s+player\\s+([a-zA-Z_0-9]+)\\s+([a-z]+)(\\s+seesun\\s+([0-9]+))?", Pattern.CASE_INSENSITIVE);
        //disable sunstorm
        Pattern DisableSunStorm = Pattern.compile("disable\\s+sunstorm", Pattern.CASE_INSENSITIVE);
        //enable sunstorm
        Pattern EnableSunStorm = Pattern.compile("enable\\s+sunstorm", Pattern.CASE_INSENSITIVE);
        //create gate
        Pattern CreateGate = Pattern.compile("create\\s+gate\\s+on\\s+(asteroid|player)\\s+([0-9]+)\\s(asteroid|player)\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //create robot
        Pattern CreateRobot = Pattern.compile("create\\s+robot\\s+on\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //create alien
        Pattern CreateAlien = Pattern.compile("create\\s+alien\\s+on\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //reset
        Pattern Reset = Pattern.compile("reset", Pattern.CASE_INSENSITIVE);
        //exit
        Pattern Exit = Pattern.compile("exit", Pattern.CASE_INSENSITIVE);

        try {
            while (true) {
                String input = scanner.nextLine();

                Matcher LoadM = Load.matcher(input);
                if (LoadM.find()) {
                    println("Loading test " + LoadM.group(1));
                    try {
                        GameInput = new Scanner(new File("test/" + LoadM.group(1) + ".txt"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    OutputFile = "out/" + LoadM.group(1) + ".txt";
                    process_input(GameInput);
                    continue;
                }

 /*           Matcher SaveM = Save.matcher(input);
            if (SaveM.find()) {
                //TODO
                continue;
            }
*/
                Matcher StartGameM = StartGame.matcher(input);
                if (StartGameM.find()) {
                    if (OutputFile != null) {
                        BufferedWriter bw = null;
                        try {
                            File file = new File(OutputFile);
                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            FileWriter fw = new FileWriter(file);
                            bw = new BufferedWriter(fw);
                            GameOutput = bw;
                            game.StartGame();
                            GameOutput = new BufferedWriter(new OutputStreamWriter(System.out));
                            OutputFile = null;
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        } finally {
                            try {
                                if (bw != null)
                                    bw.close();
                            } catch (Exception ex) {
                                System.out.println("Error in closing the BufferedWriter" + ex);
                            }
                        }
                    }
                    continue;
                }

                Matcher CreateAsteroidM = CreateAsteroid.matcher(input);
                if (CreateAsteroidM.find()) {
                    int id = Integer.parseInt(CreateAsteroidM.group(1));
                    if (game.GetAsteroid(id) == null)
                        game.AddAsteroid(new Asteroid(id));
                    continue;
                }

                Matcher SetAsteroidLayerM = SetAsteroidLayer.matcher(input);
                if (SetAsteroidLayerM.find()) {
                    int id = Integer.parseInt(SetAsteroidLayerM.group(1));
                    Asteroid a = game.GetAsteroid(id);
                    if (a != null)
                        a.SetLayers(Integer.parseInt(SetAsteroidLayerM.group(2)));
                    continue;
                }

                Matcher SetAsteroidResourceM = SetAsteroidResource.matcher(input);
                if (SetAsteroidResourceM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(SetAsteroidResourceM.group(1)));
                    Resource r = null;
                    switch (SetAsteroidResourceM.group(2).toLowerCase()) {
                        case "coal":
                            r = new Coal();
                            break;
                        case "ice":
                            r = new Ice();
                            break;
                        case "iron":
                            r = new Iron();
                            break;
                        case "uranium":
                            if (SetAsteroidResourceM.group(3) != null)
                                r = new Uranium(Integer.parseInt(SetAsteroidResourceM.group(5)));
                            else
                                r = new Uranium();
                            break;
                    }
                    a.AddResource(r);
                }

                Matcher CreatePlayerM = CreatePlayer.matcher(input);
                if (CreatePlayerM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CreatePlayerM.group(2)));
                    if (a != null) {
                        Player p = new Player(CreatePlayerM.group(1));
                        a.Accept(p);
                        game.AddPlayer(p);
                    }
                    continue;
                }

                Matcher AddResourceToPlayerM = AddResourceToPlayer.matcher(input);
                if (AddResourceToPlayerM.find()) {
                    System.out.println(AddResourceToPlayerM.group(1));
                    System.out.println(AddResourceToPlayerM.group(2));
                    System.out.println(AddResourceToPlayerM.group(3));
                    System.out.println(AddResourceToPlayerM.group(4));
                    Player p = game.GetPlayer(AddResourceToPlayerM.group(1));
                    if (p != null) {
                        Resource r = null;
                        switch (AddResourceToPlayerM.group(2).toLowerCase()) {
                            case "coal":
                                r = new Coal();
                                break;
                            case "ice":
                                r = new Ice();
                                break;
                            case "iron":
                                r = new Iron();
                                break;
                            case "uranium":
                                if (AddResourceToPlayerM.group(3) != null)
                                    r = new Uranium(Integer.parseInt(AddResourceToPlayerM.group(4)));
                                else
                                    r = new Uranium();
                                break;
                        }
                        p.AddResource(r);
                    }
                    continue;
                }

                Matcher SetAsteroidNeighbourM = SetAsteroidNeighbour.matcher(input);
                if (SetAsteroidNeighbourM.find()) {
                    Asteroid a1 = game.GetAsteroid(Integer.parseInt(SetAsteroidNeighbourM.group(1)));
                    Asteroid a2 = game.GetAsteroid(Integer.parseInt(SetAsteroidNeighbourM.group(2)));
                    if (a1 == null || a2 == null)
                        continue;
                    a1.AddNeighbor(a2);
                    continue;
                }

                Matcher DisableSunStormM = DisableSunStorm.matcher(input);
                if (DisableSunStormM.find()) {
                    game.DisableSunstorm();
                    continue;
                }

                Matcher EnableSunStormM = EnableSunStorm.matcher(input);
                if (EnableSunStormM.find()) {
                    game.EnableSunstorm();
                    continue;
                }

                Matcher CreateRobotM = CreateRobot.matcher(input);
                if (CreateRobotM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CreateRobotM.group(1)));
                    if (a != null) {
                        Robot r = new Robot(a);
                        game.AddEntity(r);
                    }
                    continue;
                }

                Matcher CreateAlienM = CreateAlien.matcher(input);
                if (CreateAlienM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CreateAlienM.group(1)));
                    if (a != null) {
                        Alien alien = new Alien(a);
                        game.AddEntity(alien);
                    }
                    continue;
                }
                Matcher CreateGateM = CreateGate.matcher(input);
                if (CreateGateM.find()) {
                    TeleportGate g1, g2;
                    Asteroid a1 = null, a2 = null;
                    Player p1 = null, p2 = null;
                    if ("asteroid".equals(CreateGateM.group(1))) {
                        a1 = game.GetAsteroid(Integer.parseInt(CreateGateM.group(2)));
                    } else {
                        p1 = game.GetPlayer(CreateGateM.group(2));
                    }
                    if ("asteroid".equals(CreateGateM.group(3))) {
                        a2 = game.GetAsteroid(Integer.parseInt(CreateGateM.group(4)));
                    } else {
                        p2 = game.GetPlayer(CreateGateM.group(4));
                    }
                    if (p1 != null && p2 != null) {
                    } else if (a1 != null && a2 != null) {

                    } else if (a1 != null && p1 != null) {

                    } else if (a1 != null && p2 != null) {

                    } else if (a2 != null && p1 != null) {

                    } else if (a2 != null && p2 != null) {

                    }
                    continue;
                }

                Matcher ResetM = Reset.matcher(input);
                if (ResetM.find()) {
                    game.reset();
                }
                Matcher ExitM = Exit.matcher(input);
                if (ExitM.find()) {
                    break;
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    public static void println(String s) {
        //System.out.println(s);
        try {
            GameOutput.write(s + "\n");
            GameOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void setNeighbors(Asteroid current, List<Asteroid> all, int maxNeighbors) {
        var numberOfNeighbors = maxNeighbors;
        numberOfNeighbors -= current.NeighborCount();
        var newNeighborIndex = -2;
        var newNeighbors = new ArrayList<Asteroid>();

        var firstRandom = -100;

        while (numberOfNeighbors-- > 0) {
            if (newNeighborIndex == -2) {
                Random rnd = new Random();
                newNeighborIndex = rnd.ints(0, all.size())
                        .findFirst()
                        .getAsInt();
            } else {
                newNeighborIndex++;
            }


            if (newNeighborIndex == all.size())
                newNeighborIndex = 0;

            if (firstRandom == -100) {
                firstRandom = newNeighborIndex;
            } else if (firstRandom == newNeighborIndex) {
                break;
            }

            var newNeighbor = all.get(newNeighborIndex);
            if (current == newNeighbor || newNeighbors.contains(newNeighbor) || current.GetNeighbor(newNeighbor.GetId()) != null || newNeighbor.NeighborCount() >= maxNeighbors) {
                numberOfNeighbors++;
                continue;
            }

            newNeighbors.add(all.get(newNeighborIndex));
            newNeighborIndex = -2;
        }

        for (Asteroid newNeighbor : newNeighbors) {
            current.AddNeighbor(newNeighbor);
        }
    }*/

}
