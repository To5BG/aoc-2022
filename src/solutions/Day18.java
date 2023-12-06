package solutions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day18 {
    public record Cube(int x, int y, int z) {
        Cube(String[] arr) {
            this(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
        }
    }

    public static Object preprocess(String input, int star) {
        List<Cube> arr = input.lines().map(s -> new Cube(s.split(","))).collect(Collectors.toList());
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(arr) : solveStar2(arr);
    }

    public static Object solve(List<Cube> input) {
        return null;
    }

    public static Object solveStar1(List<Cube> input) {
        return input.stream().map(c -> Stream.of(new Cube(c.x - 1, c.y, c.z), new Cube(c.x + 1, c.y, c.z),
                        new Cube(c.x, c.y - 1, c.z), new Cube(c.x, c.y + 1, c.z),
                        new Cube(c.x, c.y, c.z - 1), new Cube(c.x, c.y, c.z + 1))
                .filter(cc -> !input.contains(cc)).count()).reduce(0L, Long::sum);
    }

    public static Object solveStar2(List<Cube> input) {
        int[][][] grid = new int[25][25][25];
        input.forEach(c -> grid[c.x + 1][c.y + 1][c.z + 1] = 1);
        Stack<Cube> st = new Stack<>();
        Set<Cube> visited = new HashSet<>();
        int res = 0;
        st.push(new Cube(0, 0, 0));
        while (!st.isEmpty()) {
            Cube c = st.pop();
            if (grid[c.x][c.y][c.z] == 0) {
                grid[c.x][c.y][c.z] = 69;
                visited.add(c);
                for (Cube n : List.of(new Cube(c.x - 1, c.y, c.z), new Cube(c.x + 1, c.y, c.z),
                        new Cube(c.x, c.y - 1, c.z), new Cube(c.x, c.y + 1, c.z),
                        new Cube(c.x, c.y, c.z - 1), new Cube(c.x, c.y, c.z + 1)))
                    if (!visited.contains(n) && n.x >= 0 && n.x < grid.length &&
                            n.y >= 0 && n.y < grid[0].length && n.z >= 0 && n.z < grid[0][0].length) st.push(n);
            } else if (grid[c.x][c.y][c.z] == 1) res++;
        }
        return res;
    }
}
