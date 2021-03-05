package szoftprojlab.skeleton;

import java.util.function.Consumer;

public class SkeletonSequence {
    private int id;
    private String name;
    private final Consumer<Void> function;

    public SkeletonSequence(int id, String name, Consumer<Void> function) {
        this.id = id;
        this.name = name;
        this.function = function;
    }

    public void Run() {
        System.out.println("Starting sequence " + id + ": " + name);
        function.accept(null);
    }
}
