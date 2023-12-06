package solutions;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class Day24 {
    public record Point(int x, int y) {
    }

    public record State(Point p, int minute, boolean visitedStart, boolean visitedEnd) {
    }

    public static Object preprocess(String input, int star) {
        Character[][] grid = input.lines().map(l -> l.chars().mapToObj(i -> (char) i)
                .toArray(Character[]::new)).toArray(Character[][]::new);
        State start = new State(new Point(IntStream.range(0, grid.length).filter(i -> grid[0][i] == '.')
                .findFirst().orElse(-1), 0), 0, false, false);
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(grid, start) : solveStar2(grid, start);
    }

    public static int solve(Character[][] grid, State start, boolean stopAtFirst) {
        Map<Integer, Set<Point>> gridStates = createGrid(grid);
        Queue<State> q = new ArrayDeque<>();
        q.add(start);
        Set<State> visited = new HashSet<>();
        while (!q.isEmpty()) {
            State s = q.remove();
            if (s.p.x < 0 || s.p.y < 0 || s.p.x >= grid[0].length || s.p.y >= grid.length || grid[s.p.y][s.p.x] == '#')
                continue;
            if (s.p.y == grid.length - 1) {
                if (stopAtFirst || s.visitedStart) return s.minute;
                else s = new State(s.p, s.minute, false, true);
            }
            if (s.p.y == 0 && s.visitedEnd) s = new State(s.p, s.minute, true, true);
            if (visited.contains(s)) continue;
            visited.add(s);
            Set<Point> curr = gridStates.get(s.minute + 1);
            for (Point next : List.of(s.p, new Point(s.p.x + 1, s.p.y), new Point(s.p.x - 1, s.p.y),
                    new Point(s.p.x, s.p.y + 1), new Point(s.p.x, s.p.y - 1)))
                if (!curr.contains(next)) q.add(new State(next, s.minute + 1, s.visitedStart, s.visitedEnd));
        }
        return -1;
    }

    private static Map<Integer, Set<Point>> createGrid(Character[][] grid) {
        Map<Integer, Set<Point>> gridStates = new HashMap<>();
        for (int min = 0; min <= 800; min++) {
            Set<Point> bliz = new HashSet<>();
            for (int y = 0; y < grid.length; y++)
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[y][x] == '>') bliz.add(new Point(1 + (x - 1 + min) % (grid[y].length - 2), y));
                    else if (grid[y][x] == 'v') bliz.add(new Point(x, 1 + (y - 1 + min) % (grid.length - 2)));
                    else if (grid[y][x] == '<')
                        bliz.add(new Point(1 + Math.floorMod(x - 1 - min, grid[y].length - 2), y));
                    else if (grid[y][x] == '^')
                        bliz.add(new Point(x, 1 + Math.floorMod(y - 1 - min, grid.length - 2)));
                }
            gridStates.put(min, bliz);
        }
        return gridStates;
    }

    public static Object solveStar1(Character[][] grid, State start) {
        return solve(grid, start, true);
    }

    public static Object solveStar2(Character[][] grid, State start) {
        return solve(grid, start, false);
    }
}
