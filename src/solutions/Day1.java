package solutions;

import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day1 {
    public static Object preprocess(String input, int star) {
        Stream<Integer> newinput = Arrays.stream(input.split("\n\n")).map(s ->
                s.lines().map(Integer::parseInt).reduce(0, Integer::sum));
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(newinput) : solveStar2(newinput);
    }

    public static Object solveStar1(Stream<Integer> input) {
        return input.max(Integer::compareTo).orElse(-1);
    }

    public static Object solveStar2(Stream<Integer> input) {
        return input.sorted((a, b) -> -Integer.compare(a, b))
                .limit(3).reduce(0, Integer::sum);
    }
}
