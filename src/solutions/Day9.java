package solutions;

import java.util.*;

@SuppressWarnings("unused")
public class Day9 {
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solve(input, 1) : solve(input, 9);
    }

    private static int dir(int lx, int cx, int ly, int cy) {
        return (lx - cx > 1 || (lx - cx == 1 && Math.abs(ly - cy) > 1)) ? 1
                : (lx - cx < -1 || (lx - cx == -1 && Math.abs(ly - cy) > 1)) ? -1 : 0;
    }

    public static Object solve(String input, int tail) {
        Map<Integer, Set<Integer>> m = new HashMap<>();
        m.put(0, new HashSet<>(Set.of(0)));
        int[] t = new int[20];
        for (String step : input.split("\n")) {
            String[] chars = step.split(" ");
            char dir = chars[0].charAt(0);
            int s = Integer.parseInt(chars[1]);
            for (int i = 0; i < s; i++) {
                t[0] += dir == 'L' ? -1 : dir == 'R' ? 1 : 0;
                t[1] += dir == 'D' ? -1 : dir == 'U' ? 1 : 0;
                for (int ii = 1; ii < 10; ii++) {
                    int x = dir(t[2 * ii - 2], t[2 * ii], t[2 * ii - 1], t[2 * ii + 1]),
                            y = dir(t[2 * ii - 1], t[2 * ii + 1], t[2 * ii - 2], t[2 * ii]);
                    t[2 * ii] += x;
                    t[2 * ii + 1] += y;
                }
                if (m.containsKey(t[tail * 2])) m.get(t[tail * 2]).add(t[tail * 2 + 1]);
                else m.put(t[tail * 2], new HashSet<>(Set.of(t[tail * 2 + 1])));
            }
        }
        return m.values().stream().mapToLong(Collection::size).sum();
    }
}
