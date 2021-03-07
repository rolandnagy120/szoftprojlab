package szoftprojlab.skeleton;

import java.util.function.Consumer;

public class SkeletonSequence {
    public String name;
    private final Consumer<Void> function;

    public SkeletonSequence(String name, Consumer<Void> function) {
        this.name = name;
        this.function = function;
    }

    public void Run() {
        System.out.println("Starting sequence: " + name);
        function.accept(null);
    }
}
