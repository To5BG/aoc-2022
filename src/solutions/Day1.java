package solutions;

import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day1 {
    public static Object preprocess(String input, int star) {
        String[] arr = input.split("\n\n");
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(arr) : solveStar2(arr);
    }
    public static Stream<Integer> solve(String[] input) {
        return Arrays.stream(input).map(s ->
                Arrays.stream(s.split("\n"))
                        .map(Integer::parseInt)
                        .reduce(0, Integer::sum));
    }
    public static Object solveStar1(String[] input) {
        return solve(input).max(Integer::compareTo).orElse(-1);
    }
    public static Object solveStar2(String[] input) {
        return solve(input).sorted((a, b) -> -Integer.compare(a, b))
                .limit(3).reduce(0, Integer::sum);
    }
}
