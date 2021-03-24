package szoftprojlab;

import szoftprojlab.resource.Resource;

import java.util.ArrayList;
import java.util.List;

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
        List<Resource> resourcesCopy = new ArrayList<Resource>(rs);
        List<Resource> neededResourcesCopy = new ArrayList<Resource>(resources);

        for (Resource inventoryResource : resourcesCopy) {
            for (Resource neededResource : neededResourcesCopy) {
                if (inventoryResource.equals(neededResource)) {
                    neededResourcesCopy.remove(inventoryResource);
                    break;
                }
            }
        }

        if (neededResourcesCopy.size() == 0) {
            return resourcesCopy;
        }
        return null;
    }
}
