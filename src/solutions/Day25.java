package solutions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day25 {
    static Map<Character, Integer> snafuMap = new HashMap<>(Map.of('=', -2, '-', -1, '0', 0,
            '1', 1, '2', 2));
    static Map<Integer, Character> invMap = snafuMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    private static Deque<Integer> currSum;

    public static Object preprocess(String input, int star) {
        currSum = new ArrayDeque<>();
        Arrays.stream(input.split("\n")).forEach(Day25::solve);
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1() : solveStar2();
    }

    private static void solve(String str) {
        Deque<Integer> a = currSum, b = str.chars().mapToObj(c -> snafuMap.get((char) c))
                .collect(Collectors.toCollection(ArrayDeque::new));
        int c = 0;
        currSum = new ArrayDeque<>();
        while (!a.isEmpty() || !b.isEmpty()) {
            int sum = (a.isEmpty() ? 0 : a.removeLast()) + (b.isEmpty() ? 0 : b.removeLast()) + c;
            currSum.addFirst((sum + 7) % 5 - 2);
            c = (sum + 7) / 5 - 1;
        }
        if (c != 0) currSum.addFirst(c);
    }

    public static Object solveStar1() {
        return currSum.stream().map(s -> invMap.get(s) + "").collect(Collectors.joining());
    }

    public static Object solveStar2() {
        return "Job well done. See you next year!";
    }
}
