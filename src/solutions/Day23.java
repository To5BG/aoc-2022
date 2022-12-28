package solutions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day23 {
    public enum Direction {
        N(0, -1), S(0, 1), W(-1, 0), E(1, 0);
        final int x, y;
        Direction(int x, int y) { this.x = x; this.y = y; }
        List<Point> getPotentialBlockers(Point pos) {
            LinkedList<Point> ret = new LinkedList<>(List.of(new Point(pos.x + x, pos.y + y)));
            if (this == N || this == S) ret.addAll(List.of(new Point(pos.x + x + W.x, pos.y + y + W.y),
                    new Point(pos.x + x + E.x, pos.y + y + E.y)));
            else ret.addAll(List.of(new Point(pos.x + x + N.x, pos.y + y + N.y),
                    new Point(pos.x + x + S.x, pos.y + y + S.y)));
            return ret;
        }
    }
    public record Pair(Point a, Elf b) {}
    public record Point(int x, int y) {}
    static class Elf {
        Point pos;
        public Point move(Point pos, Map<Point, Elf> elves, List<Direction> dirs) {
            this.pos = pos;
            boolean otherElf = dirs.stream().map(d -> d.getPotentialBlockers(pos).stream().anyMatch(elves::containsKey))
                    .reduce(false, (acc, b) -> acc | b);
            Direction newdir = dirs.stream().filter(d -> d.getPotentialBlockers(pos).stream().noneMatch(elves::containsKey))
                    .findFirst().orElse(null);
            return (otherElf && newdir != null) ? new Point(pos.x + newdir.x, pos.y + newdir.y) : null;
        }
    }
    public static Object preprocess(String input, int star) {
        String[] lines = input.split("\n");
        Map<Point, Elf> elves = IntStream.range(0, lines.length).mapToObj(i -> IntStream.range(0, lines[0].length())
                        .mapToObj(j -> (lines[i].charAt(j) == '#') ? new Point(j, i) : null).filter(Objects::nonNull))
                .flatMap(Function.identity()).collect(Collectors.toMap(Function.identity(), e -> new Elf()));
        LinkedList<Direction> dirs = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(elves, dirs) : solveStar2(elves, dirs);
    }
    public static Object solveStar1(Map<Point, Elf> elves, LinkedList<Direction> dirs) {
        return solve(elves, dirs, 10)[0];
    }
    public static Object solveStar2(Map<Point, Elf> elves, LinkedList<Direction> dirs) {
        return solve(elves, dirs, Integer.MAX_VALUE)[1];
    }
    private static int area(Map<Point, Elf> elves) {
        int[] mm = elves.keySet().stream().collect(() -> new int[]{ Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0 },
                (e, e2) -> { e[0] = Math.min(e[0], e2.x); e[1] = Math.max(e[1], e2.x); e[2] = Math.min(e[2], e2.y);
            e[3] = Math.max(e[3], e2.y); }, (e, e2) -> {});
        return IntStream.rangeClosed(mm[2], mm[3]).map(i -> (int)IntStream.rangeClosed(mm[0], mm[1])
                        .filter(j -> !elves.containsKey(new Point(j, i))).count()).reduce(0, Integer::sum);
    }
    private static int[] solve(Map<Point, Elf> elves, LinkedList<Direction> dirs, int rounds) {
        HashMap<Point, List<Elf>> proposed = new HashMap<>();
        for (int r = 1; r <= rounds; r++) {
            proposed.clear();
            elves.entrySet().stream().map(e -> new Pair(e.getValue().move(e.getKey(), elves, dirs), e.getValue()))
                    .filter(p -> p.a != null).forEach(p -> proposed.merge(p.a, new LinkedList<>(List.of(p.b)),
                            (l1, l2) -> Stream.concat(l1.stream(), l2.stream()).collect(Collectors.toList())));
            AtomicBoolean moved = new AtomicBoolean(false);
            proposed.entrySet().stream().filter(e -> e.getValue().size() == 1).forEach(e -> { moved.set(true);
                elves.remove(e.getValue().get(0).pos); elves.put(e.getKey(), e.getValue().get(0)); });
            if (!moved.get() || r == rounds) return new int[]{ area(elves), r };
            dirs.addLast(dirs.removeFirst());
        }
        return new int[]{0, 0};
    }
}
