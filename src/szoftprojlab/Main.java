package szoftprojlab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        Game.getInstance().StartGame();
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
