package solutions;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day10 {
    public record Pair(int a, int b){}
    public static Object preprocess(String input, int star) {
        AtomicInteger c = new AtomicInteger(0);
        AtomicInteger s = new AtomicInteger(1);
        Stream<Pair> ops = Arrays.stream(Arrays.stream(input.split("\n")).map(ss ->
                        c.incrementAndGet() + " " + s.get() + "\n" + (ss.startsWith("noop") ? ""
                        : c.incrementAndGet() + " " + s.getAndAdd(Integer.parseInt(ss.split(" ")[1])) + "\n"))
                .collect(Collectors.joining()).split("\n")).map(ss ->
                new Pair(Integer.parseInt(ss.split(" ")[0]), Integer.parseInt(ss.split(" ")[1])));
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(ops) : solveStar2(ops);
    }
    public static Object solveStar1(Stream<Pair> ops) {
        return ops.filter(p -> p.a % 20 == 0 && p.a % 40 != 0).map(p -> p.b).reduce(0, Integer::sum);
    }
    public static Object solveStar2(Stream<Pair> ops) {
        return "\n" + ops.map(p -> (Math.abs((p.a - 1) % 40 - p.b) <= 1 ? "#" : ".") + (p.a % 40 == 0 ? "\n" : ""))
                .collect(Collectors.joining());
    }
}
