package szoftprojlab;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner GameInput;
    private static Writer GameOutput;
    private static String OutputFile = null;

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
        Pattern SetAsteroidNeighbour = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Remove a Neighbouring Asteroid from an asteroid
        Pattern RemoveAsteroidNeighbour = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Create Asteroid
        Pattern CreateAsteroid = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Remove Asteroid
        Pattern RemoveAsteroid = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Set Asteroid Resource
        Pattern SetAsteroidResource = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        //Start game
        Pattern StartGame = Pattern.compile("start\\s+game", Pattern.CASE_INSENSITIVE);

        while (true) {
            Scanner scanner = new Scanner(in);
            String input = scanner.nextLine();

            Matcher LoadM = Load.matcher(input);
            if (LoadM.find()) {
                println("Loading test " + LoadM.group(1));
                println("Output set to out/" + LoadM.group(1) + ".txt");
                println("You can change it with:\n" +
                        "set test output file path\n" +
                        "or with:\n" +
                        "set test output console");
                try {
                    GameInput = new Scanner(new File("test/1.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OutputFile = "out/"+LoadM.group(1) + ".txt";
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
                        println("Test is over, results written to "+OutputFile);
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
