package szoftprojlab;

import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner GameInput;
    private static Writer GameOutput;
    private static String OutputFile = null;
    private static Game game = Game.getInstance();


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

    public static void AddCommand(String command) {
        commands.add(command);
    }

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
        //set asteroid 1 explosion neighbour 2
        Pattern AsteroidExplosionNeighbour = Pattern.compile("set\\s+asteroid\\s+([0-9]+)\\s+explosion\\s+neighbour\\s+([0-9]+)", Pattern.CASE_INSENSITIVE);
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
                        game.AddEntity(r);
                        game.AddRobot(r);
                    }
                    continue;
                }

                Matcher CreateAlienM = CreateAlien.matcher(input);
                if (CreateAlienM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(CreateAlienM.group(1)));
                    if (a != null) {
                        Alien alien = new Alien(a);
                        game.AddEntity(alien);
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

                Matcher AsteroidExplosionNeighbourM = AsteroidExplosionNeighbour.matcher(input);
                if (AsteroidExplosionNeighbourM.find()) {
                    Asteroid a = game.GetAsteroid(Integer.parseInt(AsteroidExplosionNeighbourM.group(1)));
                    if (a != null) {
                        a.SetExplosionNeighbour(Integer.parseInt(AsteroidExplosionNeighbourM.group(2)));
                    }
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

                System.out.println("Unknown command: " + input);

            }
        } catch (Exception e) {
            if (e.equals(new NoSuchElementException()))
                e.printStackTrace();
            return;
        }

    }

    public static void println(String s) {
        //System.out.println(s);
        if (GameOutput == null)
            return;

        try {
            GameOutput.write(s + "\n");
            GameOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void SetupMap() {
        AddCommand("reset");
        AddCommand("create asteroid 1 0 0");
        AddCommand("create asteroid 2 60 0");
        AddCommand("create asteroid 3 120 0");
        AddCommand("create asteroid 4 180 0");
        AddCommand("create asteroid 5 240 0");
        AddCommand("create asteroid 6 300 0");
        AddCommand("create asteroid 7 360 0");
        AddCommand("create asteroid 8 420 0");
        AddCommand("create asteroid 9 480 0");
        AddCommand("create asteroid 10 540 0");
        AddCommand("create asteroid 11 600 0");
        AddCommand("create asteroid 12 660 0");
        AddCommand("set asteroid 1 neighbour 2");
        AddCommand("set asteroid 2 neighbour 3");
        AddCommand("set asteroid 3 neighbour 4");
        AddCommand("set asteroid 4 neighbour 5");
        AddCommand("set asteroid 5 neighbour 6");
        AddCommand("set asteroid 6 neighbour 7");
        AddCommand("set asteroid 7 neighbour 8");
        AddCommand("set asteroid 8 neighbour 9");
        AddCommand("set asteroid 9 neighbour 10");
        AddCommand("set asteroid 10 neighbour 11");
        AddCommand("set asteroid 11 neighbour 12");
        AddCommand("set asteroid 12 neighbour 1");
        AddCommand("set asteroid 1 layer 3");
        AddCommand("set asteroid 2 layer 3");
        AddCommand("set asteroid 3 layer 3");
        AddCommand("set asteroid 4 layer 3");
        AddCommand("set asteroid 5 layer 3");
        AddCommand("set asteroid 6 layer 3");
        AddCommand("set asteroid 7 layer 3");
        AddCommand("set asteroid 8 layer 3");
        AddCommand("set asteroid 9 layer 3");
        AddCommand("set asteroid 10 layer 3");
        AddCommand("set asteroid 11 layer 3");
        AddCommand("set asteroid 12 layer 3");
        AddCommand("set asteroid 1 resource iron");
        AddCommand("set asteroid 2 resource coal");
        AddCommand("set asteroid 3 resource uranium");
        AddCommand("set asteroid 4 resource ice");
        AddCommand("set asteroid 5 resource iron");
        AddCommand("set asteroid 6 resource coal");
        AddCommand("set asteroid 7 resource uranium");
        AddCommand("set asteroid 8 resource ice");
        AddCommand("set asteroid 9 resource iron");
        AddCommand("set asteroid 10 resource coal");
        AddCommand("set asteroid 11 resource uranium");
        AddCommand("set asteroid 12 resource ice");
        AddCommand("set asteroid 1 distant to sun");
        AddCommand("set asteroid 2 distant to sun");
        AddCommand("set asteroid 3 distant to sun");
        AddCommand("set asteroid 4 distant to sun");
        AddCommand("set asteroid 5 distant to sun");
        AddCommand("set asteroid 6 distant to sun");
        AddCommand("set asteroid 7 distant to sun");
        AddCommand("set asteroid 8 distant to sun");
        AddCommand("set asteroid 9 distant to sun");
        AddCommand("set asteroid 10 distant to sun");
        AddCommand("set asteroid 11 distant to sun");
        AddCommand("set asteroid 12 distant to sun");
        AddCommand("create robot on asteroid 2");
        AddCommand("create alien on asteroid 5");
        AddCommand("create player 1 on asteroid 1");
        AddCommand("create player 2 on asteroid 4");
        AddCommand("set robot 0 next asteroid 3");
        AddCommand("set robot 0 next asteroid 2");
        AddCommand("set robot 0 next asteroid 3");
        AddCommand("disable sunstorm");
        AddCommand("set robot 0 next asteroid 2");
        AddCommand("start game");
    }
}
