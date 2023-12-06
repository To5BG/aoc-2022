package solutions;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day7 {

    public record Pair(ArrayList<Integer> a, ArrayList<Integer> b) {
        void addAll(Pair p) {
            this.a.addAll(p.a);
            this.b.addAll(p.b);
        }

        Pair rev() {
            Collections.reverse(this.a);
            return this;
        }
    }

    public static Object preprocess(String input, int star) {
        Pair sol = Arrays.stream(input.split("\n\\$ ")).skip(1)
                .filter(s -> !s.startsWith("cd") || s.equals("cd .."))
                .collect(() -> new Pair(new ArrayList<>(), new ArrayList<>()), (p, s) -> {
                    int ss = p.a.size();
                    if (s.startsWith("cd")) {
                        if (ss >= 2) p.a.set(ss - 2, p.a.get(ss - 2) + p.a.get(ss - 1));
                        p.b.add(p.a.remove(ss - 1));
                    } else p.a.add(s.lines().skip(1).filter(st -> !st.startsWith("dir"))
                            .mapToInt(i -> Integer.parseInt(i.split(" ")[0]))
                            .reduce(0, Integer::sum));
                }, Pair::addAll).rev();
        IntStream.range(1, sol.a.size() - 1).boxed().sorted(Collections.reverseOrder())
                .forEach(i -> sol.a.set(i - 1, sol.a.get(i - 1) + sol.a.get(i)));
        Stream<Integer> newinput = Stream.concat(sol.b.stream(), sol.a.stream()).sorted();
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(newinput) : solveStar2(newinput);
    }

    public static Object solveStar1(Stream<Integer> input) {
        return input.reduce(0, (acc, i) -> i <= 100000 ? acc + i : acc);
    }

    public static Object solveStar2(Stream<Integer> input) {
        List<Integer> a = input.toList();
        return a.stream().filter(i -> i + 70000000 - a.get(a.size() - 1) >= 30000000)
                .limit(2).toList().get(1);
    }
}
