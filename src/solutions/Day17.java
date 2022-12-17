package solutions;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day17 {
    public static Object preprocess(String input, int star) {
        Character[] steps = input.chars().mapToObj(i -> (char)i).toArray(Character[]::new);
        Integer[][][] shapes = new Integer[][][] {
                { {0, 0}, {1, 0}, {2, 0}, {3, 0} },
                { {1, 0}, {1, 1}, {0, 1}, {2, 1}, {1, 2} },
                { {2, 2}, {2, 1}, {2, 0}, {1, 0}, {0, 0} },
                { {0, 0}, {0, 1}, {0, 2}, {0, 3} },
                { {0, 0}, {0, 1}, {1, 0}, {1, 1} }
        };
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(steps, shapes) : solveStar2(steps, shapes);
    }
    public static Object solve(Character[] steps, Integer[][][] shapes, int rounds) {
        int stepC = 0;
        int res = -1;
        int resM = 0;
        Map<Integer, List<Integer>> space = new HashMap<>();
        for (int j = 0; j < 7; j++) space.put(j, new ArrayList<>());
        for (int i = 0; i < rounds; i++) {
            final int resf = res;
            Integer[][] rock = Arrays.stream(shapes[i % 5]).map(p -> new Integer[]{p[0] + 2, p[1] + resf + 4})
                    .toArray(Integer[][]::new);
            while(true) {
                Character step = steps[(stepC++) % steps.length];
                Integer[][] movedRock;
                if (step.equals('<'))
                    movedRock = Arrays.stream(rock).map(p -> new Integer[]{p[0] - 1, p[1]}).toArray(Integer[][]::new);
                else
                    movedRock = Arrays.stream(rock).map(p -> new Integer[]{p[0] + 1, p[1]}).toArray(Integer[][]::new);
                boolean repl = true;
                for (Integer[] c : movedRock)
                    if (c[0] < 0 || c[0] >= 7 || (space.get(c[0]).contains(c[1])))
                        repl = false;
                if (repl) rock = movedRock;

                movedRock = Arrays.stream(rock).map(x -> new Integer[]{x[0], x[1] - 1}).toArray(Integer[][]::new);
                boolean fin = false;
                for (Integer[] c : movedRock) if (c[1] < 0 || (space.get(c[0]).contains(c[1]))) fin = true;
                if (fin) {
                    for (Integer[] c : rock) {
                        space.get(c[0]).add(c[1]);
                        res = Math.max(res, c[1]);
                    }
                    break;
                } else rock = movedRock;
                if (res > 100) {
                    space = space.entrySet().stream().peek(e -> e.setValue(
                            e.getValue().stream().map(k -> k - 30).filter(k -> k >= 0)
                            .collect(Collectors.toList())))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    res -= 30;
                    resM++;
                }
            }
        }
        return resM * 30 + res + 1;
    }
    public static Object solveStar1(Character[] steps, Integer[][][] shapes) {
        return solve(steps, shapes, 2022);
    }
    public static Object solveStar2(Character[] steps, Integer[][][] shapes) {
        return null;
    }
}
