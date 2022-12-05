package solutions;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day4 {
    public static Object preprocess(String input, int star) {
        String[] arr = input.split("\n");
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(arr) : solveStar2(arr);
    }
    public static int[] solve(String[] input) {
        Pattern p = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
        return Arrays.stream(input).map(s -> {
            Matcher m = p.matcher(s);
            if (m.matches()) {
                int a = Integer.parseInt(m.group(1)), b = Integer.parseInt(m.group(2)),
                        c = Integer.parseInt(m.group(3)), d = Integer.parseInt(m.group(4));
                if ((a >= c && b <= d) || (a <= c && b >= d)) return 1;
                if (a <= d && c <= b) return 2;
            } return 0;
        }).collect(() -> new int[3], (l, el) -> {l[el]++; if (el == 1) l[2]++;}, (arr, arr2) -> {
            for (int i = 0; i < 3; i++) arr[i] += arr2[i];
        });
    }
    public static Object solveStar1(String[] input) {
        return solve(input)[1];
    }
    public static Object solveStar2(String[] input) {
        return solve(input)[2];
    }
}
