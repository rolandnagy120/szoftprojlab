package szoftprojlab.skeleton;

import java.util.HashMap;
import java.util.Map;

public class ObjectHolder {
    private static ObjectHolder singleClassInstance = null;

    private Map<Object, String> objects = new HashMap();

    public String get(Object o) {
        return this.objects.get(o);
    }

    public void add(Object o, String name) {
        objects.put(o, name);
    }

    public void clear() {
        objects.clear();
    }

    public static ObjectHolder getInstance() {
        if (singleClassInstance == null)
            singleClassInstance = new ObjectHolder();
        return singleClassInstance;
    }
}
