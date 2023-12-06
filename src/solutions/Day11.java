package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day11 {

    // Not the most functional of solutions ;(
    @FunctionalInterface
    public interface TwoParameterFunctionByReturn {
        Long apply(Long a, Long b);
    }

    public record Monkey(List<Long> i, List<Long> wl, Function<Long, Long> op, List<Long> t) {
    }

    public static Object preprocess(String input, int star) {
        List<Monkey> newinput = Arrays.stream(input.split("\n\n"))
                .map(s -> {
                    Object[][] ss = s.lines().map(sss -> // Get 2d Object arr -> row is line, column is str + digit
                                    Stream.concat(Stream.of(sss), Stream.of((sss.split("\\D+").length > 1
                                            ? Long.parseLong(sss.split("\\D+")[1]) : 0))).toArray(Object[]::new))
                            .toArray(Object[][]::new);
                    return new Monkey(new ArrayList<>(List.of((Long) ss[0][1], 0L)), // Monkey num
                            new ArrayList<>(Arrays.stream( // List of item weights
                                    ((String) ss[1][0]).split("\\D+")).skip(1).map(Long::parseLong).toList()),
                            i -> { // Func to apply on inspection
                                Long n = (Long) (ss[2][1]) == 0 ? i : (Long) (ss[2][1]);
                                if (((String) ss[2][0]).contains("*")) return n * i;
                                return n + i;
                            },
                            new ArrayList<>(List.of(((Long) ss[3][1]), ((Long) ss[4][1]), ((Long) ss[5][1])))); // Move test
                }).collect(Collectors.toList());
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(newinput) : solveStar2(newinput);
    }

    public static Object solve(List<Monkey> input, int rounds, TwoParameterFunctionByReturn f) {
        Long div = input.stream().map(mm -> mm.t.get(0)).reduce(1L, Math::multiplyExact);
        IntStream.range(0, rounds).forEach(i -> input.forEach(m -> {
            m.i.set(1, m.i.get(1) + m.wl.size()); // Update inspected item counter
            m.wl.forEach(item -> {
                Long a = f.apply(m.op.apply(item), div); // For each item, find new value
                input.get((int) (long) m.t.get(a % m.t.get(0) == 0 ? 1 : 2)).wl.add(a);
            });
            m.wl.clear(); // Move and clear
        }));
        return input.stream().map(m -> m.i.get(1)).sorted(Comparator.reverseOrder())
                .limit(2).reduce(1L, (acc, i) -> acc * i);
    }

    public static Object solveStar1(List<Monkey> input) {
        return solve(input, 20, (a, b) -> a / 3);
    }

    public static Object solveStar2(List<Monkey> input) {
        return solve(input, 10000, (a, b) -> a % b);
    }
}
