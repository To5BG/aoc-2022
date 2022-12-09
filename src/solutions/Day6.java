package solutions;

import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day6 {

    public static Stream<List<Integer>> sliding(Stream<Integer> stream, int window) {
        Queue<Integer> q = new ArrayDeque<>();
        return stream.dropWhile(i -> {
            if (q.size() < window - 1) q.add(i);
            return q.size() < window - 1;
        }).map(i -> {q.add(i); List<Integer> ret = q.stream().toList(); q.remove(); return ret;});
    }
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solve(input, 4) : solve(input, 14);
    }
    public static Object solve(String input, int checkChar) {
        return input.length() - sliding(input.chars().boxed(), checkChar)
                .dropWhile(i -> i.stream().distinct().count() != i.size()).count() + 1;
    }
}
