package szoftprojlab.skeleton;

import java.util.function.Consumer;

public class SkeletonSequence {
    public String name;
    public String input;
    private final Consumer<Void> function;

    public SkeletonSequence(String name, Consumer<Void> function, String input) {
        this.name = name;
        this.input = input;
        this.function = function;
    }

    public void Run() {
        System.out.println("Starting sequence: " + name);
        System.out.println("Expected input: " + input);
        function.accept(null);
    }
}
