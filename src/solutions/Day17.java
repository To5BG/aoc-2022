package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

@SuppressWarnings("unused")
public class Day17 {
    record State(long stepC, long shape, List<Long> view) {
    }

    record StateRes(long r, long res) {
    }

    public static Object preprocess(String input, int star) {
        Character[] steps = input.chars().mapToObj(i -> (char) i).toArray(Character[]::new);
        Integer[][][] shapes = new Integer[][][]{
                {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
                {{1, 0}, {1, 1}, {0, 1}, {2, 1}, {1, 2}},
                {{2, 2}, {2, 1}, {2, 0}, {1, 0}, {0, 0}},
                {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
                {{0, 0}, {0, 1}, {1, 0}, {1, 1}}
        };
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(steps, shapes) : solveStar2(steps, shapes);
    }

    public static Object solve(Character[] steps, Integer[][][] shapes, long rounds) {
        long stepC = 0, resM = 0;
        AtomicLong res = new AtomicLong(0);
        final Map<Long, List<Long>> space = new HashMap<>();
        Map<State, StateRes> seen = new HashMap<>();
        LongStream.range(0, 7).forEach(j -> space.put(j, new ArrayList<>()));
        for (long r = 0; r < rounds; r++) {
            Long[][] rock = Arrays.stream(shapes[(int) (r % 5)]).map(p -> new Long[]{p[0] + 2L, p[1] + res.get() + 3})
                    .toArray(Long[][]::new);
            while (true) {
                Character step = steps[(int) ((stepC++) % steps.length)];
                Long[][] mRock = Arrays.stream(rock).map(p -> new Long[]{p[0] + (step.equals('<') ? -1L : 1L),
                        p[1]}).toArray(Long[][]::new);
                if (Arrays.stream(mRock).noneMatch(c -> c[0] < 0 || c[0] > 6 || (space.get(c[0]).contains(c[1]))))
                    rock = mRock;
                mRock = Arrays.stream(rock).map(x -> new Long[]{x[0], x[1] - 1}).toArray(Long[][]::new);
                if (Arrays.stream(mRock).anyMatch(c -> c[1] < 0 || space.get(c[0]).contains(c[1]))) {
                    Arrays.stream(rock).forEach(c -> {
                        space.get(c[0]).add(c[1]);
                        res.set(Math.max(res.get(), c[1] + 1));
                    });
                    break;
                } else rock = mRock;
            }
            long[] topview = LongStream.range(0, 7).map(i -> res.get() - space.get(i).stream()
                    .max(Comparator.naturalOrder()).orElse(res.get()) - 1).toArray();
            State s = new State((stepC - 1) % steps.length, r % shapes.length,
                    Arrays.stream(topview).boxed().toList());
            if (seen.containsKey(s)) {
                StateRes old = seen.get(s);
                long rep = (rounds - r) / (r - old.r) - (((rounds - r) % (r - old.r) == 0) ? 1 : 0);
                r += (r - old.r) * rep;
                resM += rep * (res.get() - old.res);
            }
            seen.put(s, new StateRes(r, res.get()));
        }
        return resM + res.get();
    }

    public static Object solveStar1(Character[] steps, Integer[][][] shapes) {
        return solve(steps, shapes, 2022L);
    }

    public static Object solveStar2(Character[] steps, Integer[][][] shapes) {
        return solve(steps, shapes, 1000000000000L);
    }
}
