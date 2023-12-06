package solutions;

import java.util.*;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class Day12 {
    public record Pair(int a, int b) {
    }

    public static Object preprocess(String input, int star) {
        Pair start = new Pair(input.indexOf('S') / (input.split("\n")[0].length() + 1),
                input.indexOf('S') % (input.split("\n")[0].length() + 1));
        Integer[][] grid = input.lines().map(s -> s.chars()
                .map(i -> Math.max(-1, i - 97)).boxed().toArray(Integer[]::new)).toArray(Integer[][]::new);
        grid[start.a][start.b] = star - 2;
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solve(grid, start) : solveStar2(grid);
    }

    // My inner hatred for recursion won't let me do it functionally, lmao
    public static Object solve(Integer[][] grid, Pair start) {
        Map<Pair, Pair> dirs = new HashMap<>();
        dirs.put(start, null);
        Queue<Pair> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            Pair curr = q.remove();
            if (grid[curr.a][curr.b] == -1 && !curr.equals(start)) {
                int dist = 1;
                while ((curr = dirs.get(curr)) != start) dist++;
                return dist;
            }
            for (Pair near : List.of(new Pair(curr.a - 1, curr.b), new Pair(curr.a, curr.b - 1),
                    new Pair(curr.a + 1, curr.b), new Pair(curr.a, curr.b + 1))) {
                if (near.a < 0 || near.a >= grid.length || near.b < 0 || near.b >= grid[0].length) continue;
                if (!dirs.containsKey(near) && (grid[near.a][near.b] - grid[curr.a][curr.b] <= 1
                        || (grid[near.a][near.b] == -1 && grid[curr.a][curr.b] >= 25))) {
                    dirs.put(near, curr);
                    q.add(near);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public static Object solveStar2(Integer[][] grid) {
        return IntStream.range(0, grid.length).mapToObj(i -> IntStream.range(0, grid[0].length)
                        .mapToObj(j -> new Pair(i, j))).flatMap(i -> i)
                .filter(p -> grid[p.a][p.b] == 0).map(p -> (Integer) solve(grid, p))
                .min(Integer::compareTo).orElse(-1);
    }
}
