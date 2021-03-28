package szoftprojlab.entity;

import szoftprojlab.resource.Resource;

public interface Miner {
    public boolean Mine();

    public boolean AddResource(Resource resource);
}
