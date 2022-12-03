package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day3 {
    public static Object preprocess(Path filepath, int star) {
        String input;
        try {
            input = Files.readString(filepath);
        } catch (IOException e) {
            System.out.println("IO bad");
            return null;
        }
        star = Math.min(Math.max(star, 1), 2);
        String[] arr = input.split("\n");
        return star == 1 ? solveStar1(arr) :
                solveStar2(IntStream.range(0, arr.length / 3).collect(ArrayList::new,
                        (acc, i) -> acc.add(new String[]{arr[3 * i], arr[3 * i + 1], arr[3 * i + 2]}),
                        ArrayList::addAll));
    }
    public static Object solve(Stream<Character> input) {
        return input.map(c -> c >= 97 ? c - 96 : c - 38).reduce(0, Integer::sum);
    }
    public static Object solveStar1(String[] input) {
        return solve(Arrays.stream(input).map(s -> {
            String a = s.substring(0, s.length() / 2), b = s.substring(s.length() / 2);
            for (int i = 0; i < a.length(); i++) if (b.contains(a.charAt(i) + "")) return a.charAt(i);
            return 'f';
        }));
    }
    public static Object solveStar2(List<String[]> input) {
        return solve(input.stream().map(s -> {
            for (int i = 0; i < s[0].length(); i++)
                if (s[1].contains(s[0].charAt(i) + "") && s[2].contains(s[0].charAt(i) + "")) return s[0].charAt(i);
            return 'f';
        }));
    }
}
