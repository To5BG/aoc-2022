package solutions;

import java.util.function.BiFunction;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day2 {
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return solve(input.lines(), star == 1
                ? (a, b) -> (1 + b) + 3 * ((b - a + 4) % 3)
                : (a, b) -> (3 * b) + (1 + ((a + b + 2) % 3)));
    }

    public static Object solve(Stream<String> input, BiFunction<Integer, Integer, Integer> f) {
        return input.map(s -> f.apply(s.charAt(0) - 65, s.charAt(2) - 88))
                .reduce(0, Integer::sum);
    }
}
