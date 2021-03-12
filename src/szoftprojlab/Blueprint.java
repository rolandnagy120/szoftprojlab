package szoftprojlab;

import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blueprint {
    private List<Resource> resources = new ArrayList<>();

    public Blueprint(Resource... args) {
        for (Resource r: args) {
            resources.add(r);
        }
    }

    public List<Resource> IsCraftable(List<Resource> rs) {
        System.out.print("Is there enough resource to craft? (Y|N) ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        if (input.equalsIgnoreCase("Y")) {
            return resources;
        }
        return null;
//        List<Resource> temp = new ArrayList<>(rs);
//        int count = 0;
//        for (Resource ri : resources) {
//            for (Resource rj : temp) {
//                if (ri.equals(rj)) {
//                    count++;
//                    temp.remove(rj);
//                    break;
//                }
//            }
//        }
//        if (count == resources.size()) {
//            return resources;
//        } else {
//            return null;
//        }
    }
}
