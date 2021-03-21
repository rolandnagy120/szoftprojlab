package szoftprojlab;

import szoftprojlab.resource.Resource;
import szoftprojlab.skeleton.ObjectHolder;

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

    /**
     * Calculates if the blueprint can be crafted
     * If it cannot be crafted, then returns the same
     * resource list its given.
     * If it can be crafted, then it returns teh resource
     * list, minus the resources needed for crafting
     * @param rs - resource list (inventory)
     * @return - the resource list after crafting
     */
    public List<Resource> IsCraftable(List<Resource> rs) {
        ObjectHolder oh = ObjectHolder.getInstance();
        String objectName = oh.get(this);
        System.out.println(objectName+".IsCraftable()");
        System.out.print("Is there enough resource to craft? (Y|N) ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        System.out.println("return from "+objectName+".IsCraftable()");
        if (input.equalsIgnoreCase("Y")) {
            return resources;
        }
        return null;
    }
}
