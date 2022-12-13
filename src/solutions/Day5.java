package solutions;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"unused","unchecked"})
public class Day5 {
    public static Object preprocess(String input, int star) {
        String[] arr = input.split("\n\n");
        String[] rows = arr[0].split("\n");
        ArrayDeque<Character>[] stacks =
                IntStream.range(0, Arrays.stream(rows[rows.length - 1].split("\\s+")).skip(1)
                        .map(Integer::parseInt).max(Integer::compareTo).orElse(-1))
                        .mapToObj(i -> new ArrayDeque<Character>()).toArray(ArrayDeque[]::new);
        Arrays.stream(rows).forEach(row -> { for (int i = 1; i < row.length(); i+=4)
                if (row.charAt(i) != ' ') stacks[(i - 1) / 4].offer(row.charAt(i));
        });
        return solve(stacks, arr[1].lines(), Math.min(Math.max(star, 1), 2));
    }
    public static Object solve(ArrayDeque<Character>[] stacks, Stream<String> steps, int star) {
        steps.forEach(step -> {Integer[] nums = Arrays.stream(step.split("\\D+")).skip(1).map(Integer::parseInt)
                .toArray(Integer[]::new);
            Stack<Character> s = new Stack<>();
            for (int i = 0; i < nums[0]; i++)
                if (star == 1) stacks[nums[2] - 1].push(Objects.requireNonNull(stacks[nums[1] - 1].poll()));
                else s.push(stacks[nums[1] - 1].poll());
            while (!s.isEmpty()) stacks[nums[2] - 1].push(s.pop());
        });
        return Arrays.stream(stacks).map(ArrayDeque::peekFirst)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }
}
