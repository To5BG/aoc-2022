package solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day3 {
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        String[] arr = input.split("\n");
        return star == 1 ? solveStar1(input.lines()) :
                solveStar2(IntStream.range(0, arr.length / 3).collect(ArrayList::new,
                        (acc, i) -> acc.add(new String[]{arr[3 * i], arr[3 * i + 1], arr[3 * i + 2]}),
                        ArrayList::addAll));
    }

    public static Object solve(Stream<Character> input) {
        return input.map(c -> c >= 97 ? c - 96 : c - 38).reduce(0, Integer::sum);
    }

    public static Object solveStar1(Stream<String> input) {
        return solve(input.map(s -> IntStream.range(0, s.length() / 2)
                .filter(i -> s.substring(s.length() / 2).contains(s.charAt(i) + ""))
                .mapToObj(s::charAt).findFirst().orElse('f')));
    }

    public static Object solveStar2(List<String[]> input) {
        return solve(input.stream().map(s -> s[0].chars().mapToObj(i -> (char) i)
                .filter(c -> s[1].contains(c + "") && s[2].contains(c + "")).findFirst().orElse('f')));
    }
}
