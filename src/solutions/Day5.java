package solutions;

import java.util.*;

@SuppressWarnings({"unused","unchecked"})
public class Day5 {
    public static Object preprocess(String input, int star) {
        String[] arr = input.split("\n\n");
        String[] rows = arr[0].split("\n");
        int stackNum = Arrays.stream(rows[rows.length - 1].split("\\s+")).skip(1)
                .map(Integer::parseInt).max(Integer::compareTo).orElse(-1);
        ArrayDeque<Character>[] stacks = new ArrayDeque[stackNum];
        for (int i = 0; i < stackNum; i++) stacks[i] = new ArrayDeque<>();
        for (String row : rows) for (int i = 1; i < row.length(); i+=4)
                if (row.charAt(i) != ' ') stacks[(i - 1) / 4].offer(row.charAt(i));
        return solve(stacks, arr[1].split("\n"), Math.min(Math.max(star, 1), 2));
    }
    public static Object solve(ArrayDeque<Character>[] stacks, String[] steps, int star) {
        for (String step : steps) {
            Scanner scr = new Scanner(step.substring(5)).useDelimiter("\\D+");
            int count = scr.nextInt(), a = scr.nextInt(), b = scr.nextInt();
            Stack<Character> s = new Stack<>();
            for (int i = 0; i < count; i++)
                if (star == 1) stacks[b - 1].push(Objects.requireNonNull(stacks[a - 1].poll()));
                else s.push(stacks[a - 1].poll());
            while (!s.isEmpty()) stacks[b - 1].push(s.pop());
        }
        return Arrays.stream(stacks).map(ArrayDeque::peekFirst)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }
}
