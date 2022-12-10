package solutions;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day4 {
    // Nice preprocess...
    public static Object preprocess(String input, int star) {
        Pattern p = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
        int[] sol = Arrays.stream(input.split("\n")).map(s -> {
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
        star = Math.min(Math.max(star, 1), 2);
        return sol[star];
    }
}
