package solutions;

import java.util.Arrays;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class Day2 {
    public static Object preprocess(String input, int star) {
        String[] rounds = input.split("\n");
        star = Math.min(Math.max(star, 1), 2);
        return solve(rounds, star == 1
                ? (a, b) -> (1 + b) + 3 * ((b - a + 4) % 3)
                : (a, b) -> (3 * b) + (1 + ((a + b + 2) % 3)));
    }
    public static Object solve(String[] input, BiFunction<Integer, Integer, Integer> f) {
        return Arrays.stream(input).map(s -> f.apply(s.charAt(0) - 65, s.charAt(2) - 88))
                .reduce(0, Integer::sum);
    }
}
