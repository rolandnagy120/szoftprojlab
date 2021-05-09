package szoftprojlab.entity;

import szoftprojlab.resource.Resource;

public interface Miner {
    /**
     * Mines the current asteroid
     * @return  was the mine successful
     */
    public boolean Mine();

    /**
     * Ads resource to the current asteroid
     * @param resource  the resource that will be added
     * @return          was the addind successful
     */
    public boolean AddResource(Resource resource);
}
