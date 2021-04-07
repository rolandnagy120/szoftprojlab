package szoftprojlab;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) {
        process_input(System.in);
    }

    public static void process_input(InputStream in)
    {
        //load test 1
        Pattern Load = Pattern.compile("\\s*load\\s*test\\s*([0-9]+)", Pattern.CASE_INSENSITIVE);
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


        while (true) {
            Scanner scanner = new Scanner(in);
            String input = scanner.nextLine();

            Matcher LoadM = Load.matcher(input);
            if (LoadM.find()) {
                println("Loading test " + LoadM.group(1));
                continue;
            }
            Matcher SaveM = Save.matcher(input);
            if (SaveM.find()) {
                //TODO
                continue;
            }

        }
    }

    public static void println(String s) {
        //TODO: PRINT TO FILE
        System.out.println(s);
    }

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
    }
}
