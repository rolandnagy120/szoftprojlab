package szoftprojlab;

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
        process_input(System.in);
    }

    public static void process_input(InputStream in) {
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
        //
        Pattern SetAsteroidResource = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+resource\\s+([a-z]+)(\\s+(nearsun)\\s+([0-9]+))?", Pattern.CASE_INSENSITIVE);
        //create player 2 inventory coal coal uranium(1)
        Pattern CreatePlayer = Pattern.compile("create\\s+player\\s+inventory(\\s+[a-z]+)+", Pattern.CASE_INSENSITIVE);
        //disable sunstorm
        Pattern DisableSunStorm = Pattern.compile("disable\\s+sunstorm", Pattern.CASE_INSENSITIVE);
        //create gate
        Pattern CreateGate;
        //create robot
        Pattern CreateRobot = Pattern.compile("create\\s+robot", Pattern.CASE_INSENSITIVE);
        //create alien
        Pattern CreateAlien = Pattern.compile("create\\s+alien", Pattern.CASE_INSENSITIVE);

        while (true) {
            Scanner scanner = new Scanner(in);
            String input = scanner.nextLine();

            Matcher LoadM = Load.matcher(input);
            if (LoadM.find()) {
                println("Loading test " + LoadM.group(1));
                try {
                    GameInput = new Scanner(new File("test/1.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OutputFile = "out/" + LoadM.group(1) + ".txt";
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
                        Game g = Game.getInstance();
                        g.StartGame();
                        GameOutput = new BufferedWriter(new OutputStreamWriter(System.out));
                        println("Test is over, results written to " + OutputFile);
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
                int id = Integer.parseInt(LoadM.group(1));
                if (game.GetAsteroid(id) == null)
                    game.AddAsteroid(new Asteroid(id));
                continue;
            }

            Matcher SetAsteroidLayerM = SetAsteroidLayer.matcher(input);
            if (SetAsteroidLayerM.find()) {
                int id = Integer.parseInt(LoadM.group(1));
                Asteroid a = game.GetAsteroid(id);
                if (a != null)
                    a.SetLayers(Integer.parseInt(LoadM.group(2)));
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
                //TODO
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
                //TODO
                continue;
            }

            Matcher CreateRobotM =  CreateRobot.matcher(input);
            if (CreateRobotM.find()) {
                //TODO
                continue;
            }

            Matcher CreateAlienM =  CreateAlien.matcher(input);
            if (CreateAlienM.find()) {
                //TODO
                continue;
            }
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
