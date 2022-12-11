package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
            .map(s -> { Object[][] ss = Arrays.stream(s.split("\n")).map(sss ->
                            Stream.concat(Stream.of(sss), Stream.of((sss.split("\\D+").length > 1
                                            ? Long.parseLong(sss.split("\\D+")[1]) : 0))).toArray(Object[]::new))
                    .toArray(Object[][]::new);
                return new Monkey(
                        new ArrayList<>(List.of((Long)ss[0][1], 0L)),
                        new ArrayList<>(Arrays.stream(
                                ((String)ss[1][0]).split("\\D+")).skip(1).map(Long::parseLong).toList()),
                        new Function<>() {
                            final boolean multiply = ((String)ss[2][0]).contains("*");
                            @Override
                            public Long apply(Long i) {
                                Long n = (Long)(ss[2][1]);
                                n = n == 0 ? i : n;
                                if (multiply) return n * i; return n + i;
                            }
                        },
                        new ArrayList<>(List.of( ((Long)ss[3][1]), ((Long)ss[4][1]), ((Long)ss[5][1]))));
            }).collect(Collectors.toList());
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(newinput) : solveStar2(newinput);
    }
    public static Object solve(List<Monkey> input, int rounds, TwoParameterFunctionByReturn f) {
        for (int i = 0; i < rounds; i++) {
            for (Monkey m : input) {
                m.i.set(1, m.i.get(1) + m.wl.size());
                for (int k = m.wl.size() - 1; k >= 0; k--) {
                    Long a = f.apply(m.op.apply(m.wl.get(k)),
                            input.stream().map(mm -> mm.t.get(0)).reduce(1L, Math::multiplyExact));
                    int idx = (int)(long)m.t.get(a % m.t.get(0) == 0 ? 1 : 2);
                    input.get(idx).wl.add(a);
                    m.wl.remove(k);
                }
            }
        }
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
