package szoftprojlab;

import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner GameInput;
    private static Writer GameOutput;
    private static String OutputFile = null;
    private static Game game = Game.getInstance();

    /**
     * Gets the current input scanner
     * @return  the current input scanner
     */
    public static Scanner getGameInputScanner() {
        if (GameInput == null)
            GameInput = new Scanner(System.in);
        return GameInput;
    }

    public static void main(String[] args) {

        SetupMap();

        GameOutput = new BufferedWriter(new OutputStreamWriter(System.out));
        process_input(new Scanner(System.in));
    }

    private static List<String> commands = new ArrayList<>();

    private static Scanner consoleScanner = new Scanner(System.in);

    /**
     * Adds a command to the command queue
     * @param command   the new command
     */
    public static void AddCommand(String command) {
        commands.add(command);
    }

    /**
     * Returns the next command
     * @return  the next command
     */
    public static String GetNextCommand() {
        while (commands.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String command = commands.get(0);
        commands.remove(0);
        return command;
    }

    public static void load_game() {
        commands.clear();
        try {
            FileInputStream fis = new FileInputStream("save.txt");
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                AddCommand(sc.nextLine());
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process inputs with the given scanner
     * @param _scanner  the scanner where the input will be given
     */
    public static void process_input(Scanner _scanner) {
        Scanner scanner = _scanner;
        //load test 1
        Pattern Load = Pattern.compile("load\\s+test\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //save state
        Pattern Save = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Add a Neighbouring Asteroid to an asteroid
        Pattern SetAsteroidNeighbour = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+neighbour\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //Create Asteroid
        Pattern CreateAsteroid = Pattern.compile("create\\s+asteroid ([0-9]+) ([0-9]+) ([0-9]+)", Pattern.CASE_INSENSITIVE);
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
        //set suntorm in
        Pattern SunStormIn = Pattern.compile("set\\s+sunstorm\\s+in\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //set sunstorm once
        Pattern SunStormOnce = Pattern.compile("set\\s+sunstorm\\s+once", Pattern.CASE_INSENSITIVE);
        //set alien 1 next asteroid 2
        Pattern NextAsteroid = Pattern.compile("set\\s+(alien|robot)\\s+([0-9]+)\\s+next\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //set asteroid 1 close to sun
        Pattern CloseToSun = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+close\\s+to\\s+sun", Pattern.CASE_INSENSITIVE);
        //set asteroid 1 distant to sun
        Pattern DistantToSun = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+distant\\s+to\\s+sun", Pattern.CASE_INSENSITIVE);
        //set sunstorm asteroid 1
        Pattern SunstormAsteroid = Pattern.compile("set\\s+sunstrom\\s+asteroid\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //set sunstorm dept 1
        Pattern SunstormDistance = Pattern.compile("set\\s+sunstorm\\s+dept\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //disable sundistance change
        Pattern DisableSundistanceChange = Pattern.compile("disable\\s+asteroid\\s+sun\\s+distance\\s+change", Pattern.CASE_INSENSITIVE);
        //
        Pattern EnableSundistanceChange = Pattern.compile("enable\\s+asteroid\\s+sun\\s+distance\\s+change", Pattern.CASE_INSENSITIVE);
        //set sun asteroid sun distance change time 10
        Pattern SetDistanceTime = Pattern.compile("set\\s+sun\\s+asteroid\\s+sun\\s+distance\\s+change\\s+time\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        //continue from 10
        Pattern ContinueFrom = Pattern.compile("continue\\s+from\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
        try {
            while (true) {
                String input = GetNextCommand();

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
                    GameOutput.close();
                    GameOutput = new BufferedWriter(new OutputStreamWriter(System.out));
                    Main.println("Test results written to out/" + LoadM.group(1) + ".txt");
                    GameInput = new Scanner(System.in);
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
                    } else {
                        game.StartGame();
                    }
                    continue;
                }

                Matcher CreateAsteroidM = CreateAsteroid.matcher(input);
                if (CreateAsteroidM.find()) {
                    int id = Integer.parseInt(CreateAsteroidM.group(1));
                    int x = Integer.parseInt(CreateAsteroidM.group(2));
                    int y = Integer.parseInt(CreateAsteroidM.group(3));
                    if (game.GetAsteroid(id) == null) {
                        Asteroid asteroid = new Asteroid(id, x, y);
                        game.AddAsteroid(asteroid);
                    }
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
                            if (SetAsteroidResourceM.group(3) != null) {
                                r = new Uranium(Integer.parseInt(SetAsteroidResourceM.group(5)));
                            } else
                                r = new Uranium();
                            break;
                    }
                    a.AddResource(r);
                    continue;
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
                        game.AddRobot(r);
                        game.AddEntity(r);
                    }
                    continue;
                }

                Matcher CreateAlienM = CreateAlien.matcher(input);
                if (CreateAlienM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CreateAlienM.group(1)));
                    if (a != null) {
                        Alien alien = new Alien(a);
                        //game.AddEntity(alien);
                        game.AddAlien(alien);
                    }
                    continue;
                }

                Matcher CreateGateM = CreateGate.matcher(input);
                if (CreateGateM.find()) {
                    TeleportGate g1 = new TeleportGate();
                    TeleportGate g2 = new TeleportGate();
                    g1.SetPair(g2);
                    g2.SetPair(g1);
                    Timer.getInstance().AddSteppable(g1);
                    Timer.getInstance().AddSteppable(g2);
                    if ("asteroid".equals(CreateGateM.group(1))) {
                        Asteroid a = game.GetAsteroid(Integer.parseInt(CreateGateM.group(2)));
                        a.PlaceTeleportGate(g1);
                    } else {
                        Player p = game.GetPlayer(CreateGateM.group(2));
                        p.AddGate(g1);
                    }
                    if ("asteroid".equals(CreateGateM.group(3))) {
                        Asteroid a = game.GetAsteroid(Integer.parseInt(CreateGateM.group(4)));
                        a.PlaceTeleportGate(g2);
                    } else {
                        Player p = game.GetPlayer(CreateGateM.group(4));
                        p.AddGate(g2);
                    }
                    continue;
                }

                Matcher ResetM = Reset.matcher(input);
                if (ResetM.find()) {
                    game.reset();
                    continue;
                }

                Matcher ExitM = Exit.matcher(input);
                if (ExitM.find()) {
                    break;
                }

                Matcher SunStormOnceM = SunStormOnce.matcher(input);
                if (SunStormOnceM.find()) {
                    Sun.getInstance().SunstormOnce();
                    continue;
                }

                Matcher SunStormInM = SunStormIn.matcher(input);
                if (SunStormInM.find()) {
                    Sun.getInstance().SetNextSunStormIn(Integer.parseInt(SunStormInM.group(1)));
                    continue;
                }

                Matcher NextAsteroidM = NextAsteroid.matcher(input);
                if (NextAsteroidM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(NextAsteroidM.group(3)));
                    if (a != null) {
                        if (("alien".equals(NextAsteroidM.group(1)))) {
                            Alien alien = game.GetAlien(Integer.parseInt(NextAsteroidM.group(2)));
                            if (alien != null) {
                                alien.SetNextAsteroid(a);
                            }
                        } else {
                            Robot r = game.GetRobot(Integer.parseInt(NextAsteroidM.group(2)));
                            if (r != null) {
                                r.SetNextAsteroid(a);
                            }
                        }

                    }
                    continue;
                }

                Matcher CloseToSunM = CloseToSun.matcher(input);
                if (CloseToSunM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CloseToSunM.group(1)));
                    if (a != null)
                        a.SetCloseToSun();
                    continue;
                }

                Matcher DistantToSunM = DistantToSun.matcher(input);
                if (DistantToSunM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(DistantToSunM.group(1)));
                    if (a != null)
                        a.SetDistantToSun();
                    continue;
                }

                Matcher SunstormAsteroidM = SunstormAsteroid.matcher(input);
                if (SunstormAsteroidM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(SunstormAsteroidM.group(1)));
                    if (a != null)
                        Sun.getInstance().SetSunstromAsteroid(a);
                    continue;
                }

                Matcher SunstormDistanceM = SunstormDistance.matcher(input);
                if (SunstormDistanceM.find()) {
                    Sun.getInstance().SetSunstormDept(Integer.parseInt(SunstormDistanceM.group(1)));
                    continue;
                }

                //DisableSundistanceChange
                Matcher DisableSundistanceChangeM = DisableSundistanceChange.matcher(input);
                if (DisableSundistanceChangeM.find()) {
                    Sun.getInstance().DisableSunDistanceChange();
                    continue;
                }

                //SetDistanceTime
                Matcher SetDistanceTimeM = SetDistanceTime.matcher(input);
                if (SetDistanceTimeM.find()) {
                    Sun.getInstance().SetSunDistanceChangeTime(Integer.parseInt(SetDistanceTimeM.group(1)));
                    continue;
                }

                Matcher EnableSundistanceChangeM = EnableSundistanceChange.matcher(input);
                if (EnableSundistanceChangeM.find()) {
                    Sun.getInstance().EnableSunDistanceChange();
                    continue;
                }

                Matcher ContinueFromM = ContinueFrom.matcher(input);
                if (ContinueFromM.find()) {
                    Timer.getInstance().ContinueFromIndex(Integer.parseInt(ContinueFromM.group(1)));
                    continue;
                }

                System.out.println("Unknown command: " + input);

            }
        } catch (Exception e) {
            if (e.equals(new NoSuchElementException()))
                e.printStackTrace();
            return;
        }

    }

    /**
     * Writes the given string to the current output
     * and to the event list
     *
     * @param s the string that will bre written out
     */
    public static void println(String s) {
        game.WriteEvent(s);

        if (GameOutput == null)
            return;

        try {
            GameOutput.write(s + "\n");
            GameOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the map with commands
     */
    private static void SetupMap() {
        AddCommand("reset");
        // Create asteroids
        AddCommand("create asteroid 1 30 30");
        AddCommand("create asteroid 2 343 33");
        AddCommand("create asteroid 3 150 60");
        AddCommand("create asteroid 4 237 78");
        AddCommand("create asteroid 5 443 53");
        AddCommand("create asteroid 6 598 57");
        AddCommand("create asteroid 7 100 100");
        AddCommand("create asteroid 8 525 108");
        AddCommand("create asteroid 9 679 94");
        AddCommand("create asteroid 10 376 148");
        AddCommand("create asteroid 11 449 165");
        AddCommand("create asteroid 12 74 183");
        AddCommand("create asteroid 13 342 201");
        AddCommand("create asteroid 14 402 208");
        AddCommand("create asteroid 15 551 215");
        AddCommand("create asteroid 16 631 215");
        AddCommand("create asteroid 17 129 224");
        AddCommand("create asteroid 18 266 224");
        AddCommand("create asteroid 19 69 282");
        AddCommand("create asteroid 20 315 274");
        AddCommand("create asteroid 21 397 284");
        AddCommand("create asteroid 22 185 308");
        AddCommand("create asteroid 23 586 308");
        AddCommand("create asteroid 24 499 332");
        AddCommand("create asteroid 25 53 378");
        AddCommand("create asteroid 26 198 381");
        AddCommand("create asteroid 27 300 351");
        AddCommand("create asteroid 28 402 368");
        AddCommand("create asteroid 29 617 379");
        AddCommand("create asteroid 30 694 371");
        // Set neighbors
        AddCommand("set asteroid 1 neighbour 7");
        AddCommand("set asteroid 2 neighbour 4");
        AddCommand("set asteroid 2 neighbour 5");
        AddCommand("set asteroid 3 neighbour 4");
        AddCommand("set asteroid 3 neighbour 7");
        AddCommand("set asteroid 5 neighbour 8");
        AddCommand("set asteroid 6 neighbour 8");
        AddCommand("set asteroid 6 neighbour 9");
        AddCommand("set asteroid 7 neighbour 12");
        AddCommand("set asteroid 8 neighbour 11");
        AddCommand("set asteroid 8 neighbour 15");
        AddCommand("set asteroid 10 neighbour 11");
        AddCommand("set asteroid 10 neighbour 13");
        AddCommand("set asteroid 10 neighbour 14");
        AddCommand("set asteroid 11 neighbour 13");
        AddCommand("set asteroid 11 neighbour 14");
        AddCommand("set asteroid 12 neighbour 17");
        AddCommand("set asteroid 13 neighbour 14");
        AddCommand("set asteroid 13 neighbour 18");
        AddCommand("set asteroid 14 neighbour 21");
        AddCommand("set asteroid 15 neighbour 16");
        AddCommand("set asteroid 15 neighbour 23");
        AddCommand("set asteroid 16 neighbour 23");
        AddCommand("set asteroid 17 neighbour 19");
        AddCommand("set asteroid 17 neighbour 22");
        AddCommand("set asteroid 18 neighbour 20");
        AddCommand("set asteroid 19 neighbour 25");
        AddCommand("set asteroid 20 neighbour 21");
        AddCommand("set asteroid 20 neighbour 27");
        AddCommand("set asteroid 21 neighbour 28");
        AddCommand("set asteroid 22 neighbour 26");
        AddCommand("set asteroid 23 neighbour 24");
        AddCommand("set asteroid 23 neighbour 29");
        AddCommand("set asteroid 24 neighbour 28");
        AddCommand("set asteroid 29 neighbour 30");
        // Set layers, sun distance
        Random r = new Random();
        for (int i = 1; i < 31; i++) {
            AddCommand("set asteroid " + i + " layer " + (r.nextInt(5)+1));
            AddCommand("set asteroid " + i + " distant to sun");
        }
        AddCommand("set asteroid 6 close to sun");
        AddCommand("set asteroid 8 close to sun");
        AddCommand("set asteroid 9 close to sun");
        AddCommand("set asteroid 15 close to sun");
        AddCommand("set asteroid 16 close to sun");
        AddCommand("set asteroid 23 close to sun");
        AddCommand("set asteroid 24 close to sun");
        AddCommand("set asteroid 29 close to sun");
        AddCommand("set asteroid 30 close to sun");
        // Set resources
        AddCommand("set asteroid 1 resource iron");
        AddCommand("set asteroid 3 resource uranium");
        AddCommand("set asteroid 4 resource iron");
        AddCommand("set asteroid 5 resource ice");
        AddCommand("set asteroid 6 resource uranium");
        AddCommand("set asteroid 7 resource coal");
        AddCommand("set asteroid 8 resource coal");
        AddCommand("set asteroid 10 resource uranium");
        AddCommand("set asteroid 11 resource uranium");
        AddCommand("set asteroid 12 resource iron");
        AddCommand("set asteroid 13 resource uranium");
        AddCommand("set asteroid 14 resource iron");
        AddCommand("set asteroid 15 resource iron");
        AddCommand("set asteroid 16 resource iron");
        AddCommand("set asteroid 17 resource ice");
        AddCommand("set asteroid 18 resource ice");
        AddCommand("set asteroid 19 resource iron");
        AddCommand("set asteroid 20 resource ice");
        AddCommand("set asteroid 21 resource coal");
        AddCommand("set asteroid 22 resource iron");
        AddCommand("set asteroid 23 resource iron");
        AddCommand("set asteroid 24 resource coal");
        AddCommand("set asteroid 25 resource uranium");
        AddCommand("set asteroid 26 resource iron");
        AddCommand("set asteroid 27 resource ice");
        AddCommand("set asteroid 28 resource coal");
        AddCommand("set asteroid 29 resource coal");
        AddCommand("set asteroid 30 resource iron");
        // Set players and alien
        AddCommand("create player 1 on asteroid 1");
        AddCommand("create player 1 on asteroid 7");
        AddCommand("create alien on asteroid 30");
        // Set sunstorm and sun distance change
        AddCommand("enable sunstorm");
        AddCommand("set sunstorm dept 4");
        AddCommand("enable asteroid sun distance change");
        AddCommand("set sun asteroid sun distance change time 12");
        AddCommand("start game");
    }
}
